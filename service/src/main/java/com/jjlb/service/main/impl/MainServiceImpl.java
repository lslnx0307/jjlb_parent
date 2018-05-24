package com.jjlb.service.main.impl;

import com.alibaba.fastjson.JSONObject;
import com.jjlb.common.*;
import com.jjlb.dao.bank.SysBankDAO;
import com.jjlb.dao.fuiou.SysFuiouNoticeLogDAO;
import com.jjlb.dao.funds.*;
import com.jjlb.dao.member.*;
import com.jjlb.dao.product.DrProductInfoDAO;
import com.jjlb.dao.product.DrProductInvestDAO;
import com.jjlb.dao.sys.DrCarryParamDAO;
import com.jjlb.jzh.FuiouConfig;
import com.jjlb.model.dto.fuiou.FastRechageDto;
import com.jjlb.model.dto.fuiou.FreeDto;
import com.jjlb.model.dto.fuiou.OnlineBankingRechargeDto;
import com.jjlb.model.dto.fuiou.OnlineBankingRechargeResultDto;
import com.jjlb.model.dto.member.DrMemberBankDto;
import com.jjlb.model.dto.member.DrMemberFastRechargeDto;
import com.jjlb.model.dto.member.DrMemberOnlineBankingRechargeDto;
import com.jjlb.model.dto.member.DrMemberWithdrawalsDto;
import com.jjlb.model.dto.product.InvestDto;
import com.jjlb.model.entity.fuiou.SysFuiouNoticeLog;
import com.jjlb.model.entity.funds.*;
import com.jjlb.model.entity.member.*;
import com.jjlb.model.entity.product.DrProductInfo;
import com.jjlb.model.entity.product.DrProductInvest;
import com.jjlb.model.entity.sys.DrCarryParam;
import com.jjlb.service.main.MainService;
import com.jjlb.service.redis.RedisClientTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author lslnx0307
 * @date 2018/5/22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MainServiceImpl implements MainService {
    private Logger logger = LoggerFactory.getLogger(MainServiceImpl.class);
    @Autowired
    DrMemberDAO drMemberDAO;
    @Autowired
    DrMemberBaseInfoDAO drMemberBaseInfoDAO;
    @Autowired
    RedisClientTemplate redisClientTemplate;
    @Autowired
    SysBankDAO sysBankDAO;
    @Autowired
    DrMemberBankDAO drMemberBankDAO;
    @Autowired
    DrMemberFuiouDAO drMemberFuiouDAO;
    @Autowired
    DrMemberCrushDAO drMemberCrushDAO;
    @Autowired
    DrMemberFundsDAO drMemberFundsDAO;
    @Autowired
    DrMemberFundsRecordDAO drMemberFundsRecordDAO;
    @Autowired
    DrMemberFundsLogDAO drMemberFundsLogDAO;
    @Autowired
    JsCompanyAccountLogDAO jsCompanyAccountLogDAO;
    @Autowired
    DrProductInfoDAO drProductInfoDAO;
    @Autowired
    DrProductInvestDAO drProductInvestDAO;
    @Autowired
    DrMemberFavourableDAO drMemberFavourableDAO;
    @Autowired
    SysFuiouNoticeLogDAO sysFuiouNoticeLogDAO;
    @Autowired
    DrMemberMsgDAO drMemberMsgDAO;
    @Autowired
    DrMemberCarryDAO drMemberCarryDAO;
    @Autowired
    JsMemberRewardsDAO jsMemberRewardsDAO;
    @Autowired
    DrCarryParamDAO drCarryParamDAO;


    @Override
    public BaseResult<String> openAccount(DrMemberBankDto dto) {
        BaseResult br = null;
        try {
            DrMember m = drMemberDAO.selectByPrimaryKey(dto.getUid());
            if (Utils.isObjectNotEmpty(m) && 0 == m.getIsFuiou()) {
                Map<String, String> map = new HashMap<String, String>();
                if (Utils.isObjectNotEmpty(m.getRealName())) {
                    dto.setCust_nm(m.getRealName());
                    dto.setCertif_id(m.getIdCards());
                }
                Map<String, Object> cardMap = new HashMap<String, Object>();
                cardMap.put("idCards", dto.getCertif_id());
                Integer count = drMemberBaseInfoDAO.queryMemberBaseInfoCountByMap(cardMap);
                if (count != null && m.getRealVerify() == 0 && count > 0) {
                    return new BaseResult<String>(false, "1003", "身份证已存在");
                }

                if (!Utils.strIsNull(dto.getCust_nm()) && !Utils.strIsNull(dto.getCertif_id()) && !Utils.strIsNull(dto.getCity_id()) && !Utils.strIsNull(dto.getParent_bank_id()) && !Utils.strIsNull(dto.getCapAcntNo()) && !Utils.isBlank(dto.getChannel()) && !Utils.strIsNull(dto.getPassword()) && dto.getPassword().equals(dto.getRpassword()) && !Utils.strIsNull(dto.getMobile_no())) {

                    dto.setMchnt_txn_ssn(Utils.createOrderNo(6, m.getUid(), null));

                    br = FuiouConfig.reg(dto);

                    if (br.isSuccess()) {
                        String resp_code = (String) br.getMap().get("resp_code");
                        if (resp_code.equals("0000")) {
                            map.put("phone", m.getMobilephone());

                            System.out.println("个人开户验签成功---------------------");
                            boolean lockFlag = false;
                            String openAccountRes = "openAccountRes_" + dto.getMchnt_txn_ssn();
                            try {
                                lockFlag = redisClientTemplate.tryLock(openAccountRes, 3, TimeUnit.SECONDS, false);
                                if (lockFlag) {
                                    DrMember member = drMemberDAO.selectDrMemberByMobilephone(m.getMobilephone());
                                    if (Utils.isObjectNotEmpty(m) && (m.getIsFuiou() == null || m.getIsFuiou() == 0)) {
                                        //用户表
                                        m.setIsFuiou(1);
                                        m.setMchnt_txn_ssn(dto.getMchnt_txn_ssn());
                                        drMemberDAO.updateByPrimaryKey(m);
                                        //银行表
                                        DrMemberBank bank = new DrMemberBank();
                                        bank.setAddTime(new Date());
                                        bank.setAddUser(0);
                                        String parent_bank_id = dto.getParent_bank_id();

                                        String bankname = sysBankDAO.selectBankByCode(parent_bank_id + "0000");
                                        bank.setBankName(bankname);
                                        bank.setBankNum(dto.getCapAcntNo());
                                        bank.setStatus(1);
                                        bank.setChannel(Integer.valueOf(dto.getChannel()));
                                        bank.setType(3);
                                        bank.setUid(m.getUid());
                                        bank.setMobilePhone(dto.getMobile_no());
                                        drMemberBankDAO.insertDrMemberBank(bank);
                                        //插入平台和富有账户关联表
                                        DrMemberFuIou drMemberFuIou = new DrMemberFuIou();
                                        drMemberFuIou.setUserId(m.getUid());
                                        drMemberFuIou.setFuiouId(dto.getMobile_no());
                                        drMemberFuiouDAO.insertDrMemberFuiou(drMemberFuIou);
                                        //基础表
                                        DrMemberBaseInfo info = drMemberBaseInfoDAO.queryMemberBaseInfoByUid(m.getUid());
                                        if (Utils.isObjectNotEmpty(info) && (Utils.isObjectEmpty(info.getRealName()) || Utils.isObjectEmpty(info.getIdCards()))) {
                                            info.setRealName(dto.getCust_nm());
                                            info.setIdCards(dto.getCertif_id());

                                            // 性别和生日
                                            Integer sexNum = Integer.parseInt(info.getIdCards().substring(16, 17));
                                            String birthday = info.getIdCards().substring(6, 14);
                                            info.setSex(sexNum % 2 != 0 ? 1 : 2);
                                            info.setBirthDate(new SimpleDateFormat("yyyyMMdd").parse(birthday));

                                            drMemberBaseInfoDAO.updateDrMemberBaseInfoById(info);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                throw e;
                            } finally {
                                if (lockFlag) {
                                    redisClientTemplate.del(openAccountRes);
                                }
                            }
                            br.setMsg("开户成功");
                            br.setSuccess(true);
                        } else {
                            br.setSuccess(false);
                            br.setMsg(resp_code + ":" + (String) br.getMap().get("resp_desc") + ("5344".equals(resp_code) || "5343".equals(resp_code) ? "-->请咨询客服" : ""));
                        }
                    } else {
                        br.setMsg("系统错误:" + br.getMsg());
                    }
                } else {
                    br.setCode("1002");
                    br.setMsg("参数错误");
                    br.setSuccess(false);
                    return br;
                }
            } else {
                br.setCode("1001");
                br.setMsg("用户已开通");
                br.setSuccess(false);
                return br;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BaseResult<String> fastRecharge(DrMemberFastRechargeDto dto) throws Exception{
        DrMember member = drMemberDAO.selectByPrimaryKey(dto.getUid());
        if (member == null) {
            throw new BusinessException(MessageSourceHelper.getMessage("member.uid.error"));
        }
        BaseResult br = new BaseResult();
        String fSS = null;
        fSS = "fSS_" + member.getUid();
        DrMemberCrush drMemberCrush = drMemberCrushDAO.getDrMemberCrushByPayNum(dto.getCode());
        DrMemberFuIou fuiou = drMemberFuiouDAO.getDrMemberFuiouByUserId(dto.getUid());
        try {
        if (Utils.isObjectNotEmpty(drMemberCrush) && drMemberCrush.getStatus() == 0 &&
                drMemberCrush.getAmount().compareTo(new BigDecimal(dto.getAmt())) == 0) {
            FastRechageDto fastRechageDto = new FastRechageDto();
            fastRechageDto.setMchnt_txn_ssn(dto.getCode());
            fastRechageDto.setTxn_date(Utils.format(new Date(), "yyyyMMdd"));
            fastRechageDto.setChannel(dto.getChannel());
            fastRechageDto.setYzm(dto.getYzm());
            fastRechageDto.setLogin_id(fuiou.getFuiouId());
            br = FuiouConfig.fastRecharg(fastRechageDto);
            String resp_code = "";
            String resp_desc = "";
            //获取最新的充值数据
            drMemberCrush = drMemberCrushDAO.getDrMemberCrushByPayNum(dto.getCode());
            if (drMemberCrush.getStatus() == 0) {
                resp_code = (String) br.getMap().get("resp_code");
                resp_desc = (String) br.getMap().get("resp_desc");
                resp_desc = Utils.strIsNull(resp_desc) ? getFuiouCode(resp_code) : resp_desc;

                if (br.isSuccess()) {
                    if ("0000".equals(br.getMap().get("resp_code"))) {
                        //插入充值记录
                        drMemberCrush.setStatus(1);
                        drMemberCrush.setRemark("成功");
                        br.setSuccess(true);
                    } else {
                        drMemberCrush.setStatus(2);
                        drMemberCrush.setRemark(resp_code + "|" + resp_desc);

                        br.setSuccess(false);
                    }
                } else {//失败
                    resp_desc = resp_desc == null ? "系统错误" : resp_desc;
                    drMemberCrush.setRemark(resp_code + "|" + resp_desc);
                }
                br.setMsg(resp_desc);
                br.setCode(resp_code);
                //充值数据处理
                DrMemberFunds fund = drMemberFundsDAO.queryDrMemberFundsByUid(member.getUid());
                //充值记录
                drMemberCrush.setAuditTime(new Date());
                drMemberCrush.setAuditId(0);
                //成功
                if(drMemberCrush.getStatus() == 1){
                    DrMemberFunds funds = new DrMemberFunds();
                    funds.setUid(fund.getUid());
                    funds.setFuiou_balance(fund.getFuiou_balance().add(drMemberCrush.getAmount()));
                    funds.setFuiou_crushcount(fund.getFuiou_crushcount().add(drMemberCrush.getAmount()));

                    drMemberFundsDAO.updateDrMemberFunds(funds);

                    drMemberCrushDAO.updateMemberCrushById(drMemberCrush);

                    DrMemberFundsRecord record = new DrMemberFundsRecord(null,null,drMemberCrush.getUid(), 1, 1,drMemberCrush.getAmount(), funds.getFuiou_balance(),3,
                            "充值金额：【"+ drMemberCrush.getAmount().setScale(2)  + "】", drMemberCrush.getPayNum());
                    drMemberFundsRecordDAO.insert(record);

                    DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(),record.getId(),drMemberCrush.getAmount(),44,1,
                            "充值金额：【"+ drMemberCrush.getAmount().setScale(2)  + "】");
                    drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);

                    //记公司账户日志 回款营销收益
                    JsCompanyAccountLog companyAccountLog=new JsCompanyAccountLog();
                    //资金类型
                    companyAccountLog.setCompanyfunds(59);
                    //支出
                    companyAccountLog.setType(0);
                    BigDecimal amount = drMemberCrush.getAmount().multiply(new BigDecimal(1.5)).divide(new BigDecimal(1000),2,BigDecimal.ROUND_DOWN);
                    BigDecimal poundage = new BigDecimal("2");

                    poundage = poundage.compareTo(amount) == 1? poundage : amount;

                    //金额
                    companyAccountLog.setAmount(poundage);
				    companyAccountLog.setStatus(3);
                    companyAccountLog.setRemark(member.getMobilephone()+"充值手续费:"+poundage+",充值金额:"+drMemberCrush.getAmount());
                    companyAccountLog.setAddTime(new Date());
                    //存管
                    companyAccountLog.setChannelType(2);
                    //用户id
                    companyAccountLog.setUid(member.getUid());
                    jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);


                    // 充值成功 发送站内信
                    DrMemberFunds f = new DrMemberFunds();
                    f.setBalance(funds.getFuiou_balance());
//                  sendMsg(drMemberCrush,f,member);
                }else if(drMemberCrush.getStatus() == 2){
                    drMemberCrushDAO.updateMemberCrushById(drMemberCrush);
                }
            } else if (drMemberCrush.getStatus() == 1) {
                br.setMsg("成功");
                br.setSuccess(true);
            } else {
                br.setSuccess(false);
                br.setMsg("失败");
            }
        }
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResult<String>(false,"9999","系统错误");
        }
        return br;
    }

    @Override
    public BaseResult<OnlineBankingRechargeResultDto> onlineBankingRecharge(DrMemberOnlineBankingRechargeDto dto) {
        DrMember member = drMemberDAO.selectByPrimaryKey(dto.getUid());
        if (member == null) {
            throw new BusinessException(MessageSourceHelper.getMessage("member.uid.error"));
        }
        try {
            if (1 == member.getIsFuiou()) {
                DrMemberFuIou fuiou = drMemberFuiouDAO.getDrMemberFuiouByUserId(member.getUid());
                OnlineBankingRechargeDto onlineBankingRechargeDto = new OnlineBankingRechargeDto();
                onlineBankingRechargeDto.setMchnt_txn_ssn(Utils.createOrderNo(6, member.getUid(), ""));
                onlineBankingRechargeDto.setAmt(dto.getAmt());
                onlineBankingRechargeDto.setLogin_id(fuiou.getFuiouId());
                onlineBankingRechargeDto.setIss_ins_cd(dto.getIss_ins_cd());
                onlineBankingRechargeDto.setOrder_pay_type(dto.getOrder_pay_type());
                //网银充值跳银行
                String signature = FuiouConfig.onlineBankingRecharge12Data(onlineBankingRechargeDto);
                DrMemberCrush drMemberCrush = new DrMemberCrush();
                drMemberCrush.setStatus(0);
                drMemberCrush.setAddTime(new Date());
                drMemberCrush.setUid(dto.getUid());
                drMemberCrush.setAmount(new BigDecimal(dto.getAmt()));
                drMemberCrush.setPayNum(onlineBankingRechargeDto.getMchnt_txn_ssn());
                drMemberCrush.setType(6);
                //pc
                drMemberCrush.setChannel(0);
                drMemberCrushDAO.insertDrMemberCrush(drMemberCrush);
                OnlineBankingRechargeResultDto resultDto = new OnlineBankingRechargeResultDto();
                resultDto.setSignature(signature);
                resultDto.setFuiouUrl(FuiouConfig.BRECHARGE12URL);
                return new BaseResult<OnlineBankingRechargeResultDto>(true,"10000","充值成功",resultDto);
            } else {
                return new BaseResult<OnlineBankingRechargeResultDto>(false, "1001", "参数有误");
            }
        } catch (Exception e) {
            logger.error("网银充值",e);
            return new BaseResult<OnlineBankingRechargeResultDto>(false, "9999", "系统错误");
        }
    }

    @Override
    public BaseResult<String> invest(InvestDto dto) {
        BaseResult br = new BaseResult();
        DrMember loginMember = null;
        FreeDto freeDto = new FreeDto();
        try {
        // 返回参数的map
        Map<String,Object> backMap = new HashMap<String, Object>();
        BigDecimal amount = dto.getAmount();
        DrMemberFunds funds = drMemberFundsDAO.queryDrMemberFundsByUid(dto.getUid());
        DrProductInfo pInfo = drProductInfoDAO.selectProductById(dto.getPid());
        loginMember =drMemberDAO.selectByPrimaryKey(dto.getUid());
        if (Utils.isObjectEmpty(pInfo) || pInfo.getStatus() != 5) {
            return new BaseResult<String>(false,"1002","产品不存在或者产品未上架");
        } else if ((pInfo.getType() == 2 || pInfo.getType() == 3)  && pInfo.getSurplusAmount().compareTo(amount) < 0) {
            return new BaseResult<String>(false,"1003","剩余可投资金额不足");
        } else if (pInfo.getLeastaAmount().compareTo(amount) > 0) {
            return new BaseResult<String>(false,"1004","投资金额小于起投金额");
        } else if (amount.remainder(pInfo.getIncreasAmount()).compareTo(BigDecimal.ZERO) != 0) {
            return new BaseResult<String>(false,"1005","投资金额非递增金额的整数倍");
        } else if (amount.compareTo(pInfo.getMaxAmount()) > 0) {
            return new BaseResult<String>(false,"1006","投资金额大于个人可投资金额");
        }else if(loginMember.getIsFuiou() != 1){
            return new BaseResult<String>(false,"1013","提示开通存管");
        }else if (funds.getFuiou_balance().compareTo(amount) < 0) {
            return new BaseResult<String>(false,"1007","账户可用余额不足");
        }
        //新手标
        Map<String, Object> map = new HashMap<String, Object>();
        //存管新手标
        Map<String, Object> param = new HashMap<String, Object>();

        if (pInfo.getType() == 3) {
            param.put("uid", loginMember.getUid());
            //2是投资失败的，可以继续投
            param.put("statuses", new Integer[] { 0,1,3,4 });
            param.put("type", 3);
            String mobilePhone2 = null;
            mobilePhone2 = redisClientTemplate.getProperties("systemPhone");
            if(!loginMember.getMobilephone().equals(mobilePhone2)){
                Integer rows = drProductInvestDAO.selectInvestLogCountByParam(param);
                if (rows > 0) {
                    br.setSuccess(false);
                    br.setCode("1008");
                    return br;
                }
            }

        }

        DrMemberFavourable dmf = null;
        Integer fid = dto.getFid();
        if(Utils.isObjectNotEmpty(fid)){
            DrMemberFavourable drMemberFavourable = drMemberFavourableDAO.selectByPrimaryKey(fid);
            if(pInfo.getType() != Constants.FIVE){
                if(pInfo.getIsDouble() == Constants.ZERO && drMemberFavourable.getType() == Constants.FORE){
                    return new BaseResult<String>(false,"1015","翻倍券只能用于翻倍标");
                }
                if(pInfo.getIsInterest() == Constants.ZERO && drMemberFavourable.getType() == Constants.TWO){
                    return new BaseResult<String>(false,"1016","该标不能使用加息劵");
                }
                if(pInfo.getIsCash() == Constants.ZERO && drMemberFavourable.getType() == Constants.ONE){
                    return new BaseResult<String>(false,"1017","该标不能使用红包");
                }
            }
        }
        // 使用优惠券
        if (fid != null) {
            map.clear();
            map.put("uid", loginMember.getUid());
            map.put("status", 0);
            map.put("amount", amount);
            map.put("id", fid);
            map.put("deadline", pInfo.getDeadline());
            map.put("repayType", pInfo.getRepayType());
            //5是体验金,体验金只有体验标可用
            if (pInfo.getType() != Constants.ONE || pInfo.getType() != Constants.THREE) {
                map.put("time", new Date());
            }
            List<DrMemberFavourable> list = drMemberFavourableDAO.getMemberFavourableByParam(map);
            dmf = list.size() > 0 ? list.get(0) : null;
            if (dmf == null) {
                return new BaseResult<String>(false,"1010","该标不能使用红包");
            }else{
                //非首投用户，不能使用系统赠送的翻倍券
                if(dmf.getType()==4 && dmf.getSource()==0){
                    map.clear();
                    map.put("uid", loginMember.getUid());
                    map.put("barring", new Integer[]{1,5});
                    Integer investCount = drProductInvestDAO.selectInvestCountByMap(map);
                    if(investCount>0){
                        return new BaseResult<String>(false,"1012","非首投用户，不能使用系统赠送的翻倍券");
                    }
                }
            }
        }
            // 恒丰存管-冻结接口
            //预授权合同号
            String contract_no = null;
            String mchnt_txn_ssn = null;

            //2.0参数
            DrMemberFuIou fuiou = drMemberFuiouDAO.getDrMemberFuiouByUserId(loginMember.getUid());
            freeDto.setUid(dto.getUid());
            freeDto.setCust_no(fuiou.getFuiouId());
            freeDto.setAmt(dto.getAmount().toString());
            freeDto.setRem( "投资" + pInfo.getFullName() + "|pid:" + pInfo.getId() + "|code:" + pInfo.getCode());
            BaseResult baseResult = FuiouConfig.freeze(freeDto);

            //授权用户，直连冻结
            if (!baseResult.isSuccess()) {
                return baseResult;
            } else {
                String resp_code = (String) baseResult.getMap().get("resp_code");
                if (!Constants.FUIOU_SUCCESS_CODE.equals(resp_code)) {
                    baseResult.setCode((String) baseResult.getMap().get("resp_code"));
                    baseResult.setMsg((String) baseResult.getMap().get("resp_desc"));
                    baseResult.setMap(null);
                    baseResult.setSuccess(false);
                    return baseResult;
                }
            }
            mchnt_txn_ssn = baseResult.getMap().get("mchnt_txn_ssn").toString();
            // 更新产品信息
            pInfo.setAlreadyRaiseAmount(pInfo.getAlreadyRaiseAmount().add(amount));
            pInfo.setSurplusAmount(pInfo.getSurplusAmount().subtract(amount));
            //募集完成 新手标始终处于募集中
            if (pInfo.getType() != Constants.ONE && pInfo.getType() != Constants.FORE
                    && pInfo.getSurplusAmount().compareTo(BigDecimal.ZERO) == 0) {
                pInfo.setStatus(6);
                pInfo.setFullDate(new Date());
                pInfo.setEstablish(Utils.getDayNumOfAppointDate(Utils.format(
                        Utils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd"), -1));
                pInfo.setExpireDate(
                        Utils.getDayNumOfAppointDate(pInfo.getEstablish(), 0 - pInfo.getDeadline()));
                redisClientTemplate.del("product.info." + pInfo.getId());
            }

            redisClientTemplate.setex(("product.info." + pInfo.getId()).getBytes(), 600,
                    SerializeUtil.serialize(pInfo));
            drProductInfoDAO.updateProductSelective(pInfo);
            // 将优惠券置为已使用
            if (dmf != null) {
                dmf.setStatus(1);
                dmf.setUsedTime(new Date());
                drMemberFavourableDAO.updateFavourableStatus(dmf);
            }

            // 插入投资记录
            DrProductInvest invest = new DrProductInvest();
            invest.setAmount(amount);
            invest.setUid(loginMember.getUid());
            invest.setJoinType(dto.getChannel());
            invest.setStatus(0);
            invest.setPid(pInfo.getId());
            invest.setFid(fid);
            invest.setContract_no(contract_no);
            invest.setMchnt_txn_ssn(mchnt_txn_ssn);
            BigDecimal dayRate = Utils.nwdDivide(Utils.nwdDivide(pInfo.getRate().add(pInfo.getActivityRate()), 100),
                    360);
            invest.setInterest(amount.multiply(dayRate).multiply(new BigDecimal(pInfo.getDeadline())).setScale(
                    2, BigDecimal.ROUND_FLOOR));
            invest.setInvestTime(new Date());
            drProductInvestDAO.insertSelective(invest);
            DrMemberFundsRecord fundsRecord = new DrMemberFundsRecord(pInfo.getId(), invest.getId(),
                    loginMember.getUid(), 3, 0, amount, funds.getFuiou_balance().subtract(amount),
                    4, "投资【" + pInfo.getFullName() + ":" + pInfo.getCode() + "】产品", null);
            drMemberFundsRecordDAO.insert(fundsRecord);

            //返现//返现金额
            BigDecimal balanceProfit = BigDecimal.ZERO;
            DrMemberFavourable dm = drMemberFavourableDAO.selectByPrimaryKey(fid);
            if (dm != null && dm.getType() == 1) {
                balanceProfit = dm.getAmount();
                if (balanceProfit.compareTo(BigDecimal.ZERO) > 0) {
                    //放到 redis 缓存
                    Map<String, Object> crushBackMap = new HashMap<String, Object>();
                    crushBackMap.put("type", 52);
                    crushBackMap.put("uid", loginMember.getUid());
                    crushBackMap.put("investId", invest.getId());
                    crushBackMap.put("project_no", pInfo.getProject_no());

                    backMap.put("crushBackMap", crushBackMap);

                }
            }
            DrMemberFundsLog fundslog = new DrMemberFundsLog(loginMember.getUid(), fundsRecord.getId(), amount,
                    48, 0, "投资【" + pInfo.getFullName() + "】产品,资金冻结");
            //投资成功
            drMemberFundsLogDAO.insertDrMemberFundsLog(fundslog);

            // 发送站内信
            DrMemberMsg msg = new DrMemberMsg(loginMember.getUid(), 0, 3, "投资成功", new Date(), 0, 0,
                    redisClientTemplate.getProperties("investSuccess").replace("${fullName}",
                            pInfo.getFullName()).replace("${amount}", invest.getAmount().toString()));
            drMemberMsgDAO.insertDrMemberMsg(msg);

            //用户资金记录 修改用户资金 可用余额减去投资金额
            funds.setFuiou_balance(funds.getFuiou_balance().subtract(amount));
            funds.setFuiou_freeze(funds.getFuiou_freeze().add(amount));
            drMemberFundsDAO.updateDrMemberFunds(funds);

            backMap.put("investTime", new Date());
            backMap.put("investId", invest.getId());
            backMap.put("amount", amount);
            backMap.put("deadline", pInfo.getDeadline());
            if (pInfo.getType() == 1) {
                backMap.put("expireDate", Utils.getDayNumOfAppointDate(Utils.format(Utils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd"), -(pInfo.getDeadline() + 1)));
            } else {
                backMap.put("expireDate", pInfo.getExpireDate());
            }
            SysFuiouNoticeLog noticeLog = new SysFuiouNoticeLog();
            noticeLog.setStatus(2);
            noticeLog.setInvest_id(invest.getId());
            sysFuiouNoticeLogDAO.update(noticeLog);
            br.setMap(backMap);
            br.setSuccess(true);
        } catch (Exception e1) {
            logger.error(loginMember.getUid() + "投资冻结失败", e1);
            br.setSuccess(false);
            br.setCode("9999");
            redisClientTemplate.del("product.info." + dto.getPid());
            FuiouConfig.freezeCancel(freeDto);
            // 让事务回滚
            throw new RuntimeException();
        }
        return br;
    }

    @Override
    public BaseResult<String> withdraw(DrMemberWithdrawalsDto dto) {
            BaseResult br = new BaseResult();
        try {
            DrMember member = drMemberDAO.selectByPrimaryKey(dto.getUid());
            if(member == null){
                return new BaseResult<String>(false,"9999","用户未登陆");
            }
            if(member.getIsFuiou() == Constants.ONE){
                //处理上一笔未处理的提现记录


                if(Utils.isObjectEmpty(dto.getAmount())){
                    return new BaseResult<String>(false,"1001","提现金额有误");
                }

                if(Utils.isObjectEmpty(dto.getIsChargeFlag())){
                    return  new BaseResult<String>(false,"9999","系统异常");
                }
                boolean result = false;
                Integer investCount = drProductInvestDAO.selectInvestCount(dto.getUid());
                //type = 1 存管，type = 2平台
                BigDecimal carryAmountCount = drMemberCarryDAO.selectCarryAmountCount(dto.getUid(),1);
                BigDecimal crushAmountCount = drMemberCrushDAO.getDrMemberCrushAmountByUID(dto.getUid(),1);
                //提现大于充值并且未投资除体验标之外的产品
                if(investCount !=  0 && !(dto.getAmount().add(carryAmountCount).compareTo(crushAmountCount)==1)){
                    result = true;
                }
                if(!result){
                    return new BaseResult<String>(false,"2002","返现需完成一次真实投资后才可提现");
                }
                redisClientTemplate.lock("fuiou_withdrawals"+member.getUid().toString());
                DrMemberCarry drMemberCarry = new DrMemberCarry();
                Integer quantity= jsMemberRewardsDAO.getQuantityCount(member.getUid());
                Integer isCharge=0;
                //需要手续费
                if(quantity==0){
                    isCharge=1;
                }
                if(isCharge.intValue() != dto.getIsChargeFlag().intValue()){
                    if(isCharge == 1){
                        return new BaseResult<String>(false,"1007","该笔需要收取手续费");
                    }else{
                        return new BaseResult<String>(false,"1008","该笔不需要收取手续费");
                    }
                }else{
                    if(isCharge == 1){
                        if(dto.getAmount().compareTo(new BigDecimal(Constants.THREE)) < 0){
                            return new BaseResult<String>(false,"1001","提现金额有误");
                        }
                        drMemberCarry.setPoundage(new BigDecimal(2));
                    }else{
                        if(dto.getAmount().compareTo(new BigDecimal(Constants.ONE)) < 0){
                            return new BaseResult<String>(false,"1001","提现金额有误");
                        }
                        drMemberCarry.setPoundage(new BigDecimal(0));
                    }
                }
                drMemberCarry.setAmount(dto.getAmount().subtract(drMemberCarry.getPoundage()));
                DrMemberBank drMemberBank = drMemberBankDAO.selectFuiouIdentificationBank(member.getUid());
                DrMemberFunds drMemberFunds = drMemberFundsDAO.queryDrMemberFundsByUid(member.getUid());
                //TODO 判断存管户
                if(drMemberFunds.getFuiou_balance().compareTo(drMemberCarry.getAmount().add(
                        drMemberCarry.getPoundage()))<0){
                    return new BaseResult<String>(false,"1004","余额不足");
                }

                DrCarryParam drCarryParam = drCarryParamDAO.getDrCarryParam();
                drMemberCarry.setChannel(dto.getChannel());

                drMemberCarry.setUid(member.getUid());
                drMemberCarry.setPaymentNum(Utils.createOrderNo(6,drMemberCarry.getUid(),""));
                drMemberCarry.setAddTime(new Date());
                drMemberCarry.setBankId(drMemberBank.getId());
                drMemberCarry.setBankName(drMemberBank.getBankName());
                drMemberCarry.setBankNum(drMemberBank.getBankNum().substring(0,4)+"********"+
                        drMemberBank.getBankNum().substring(drMemberBank.getBankNum().length()-4,
                                drMemberBank.getBankNum().length()));
                drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);
                //提现状态，0未处理 1处理中 2成功 3失败  4拒绝 5超时
                drMemberCarry.setStatus(0);
                //存管
                drMemberCarry.setType(3);
                //收入
                BigDecimal income  = drMemberFunds.getFuiou_crushcount().add(drMemberFunds.getFuiou_investProfit().add(
                        drMemberFunds.getFuiou_spreadProfit()));
                //支出
                BigDecimal expenditure = drMemberFunds.getFuiou_balance().add(drMemberFunds.getFuiou_freeze().add(
                        drMemberFunds.getFuiou_wprincipal().add(drMemberFunds.getFuiou_carrycount())));
                //收入 - 支出 = 0
                if(income.subtract(expenditure).compareTo(BigDecimal.ZERO)!=0){
//                    sendSmsAndMail(member.getUid());
                    drMemberCarry.setStatus(0);
                    drMemberCarry.setReason("资金账户出现异常!");
                    return new BaseResult<String>(false,"1006","资金账户出现异常!");
                }

                drMemberCarryDAO.insertDrMemberCarry(drMemberCarry);

                DrMemberCarry carry = drMemberCarry;
                DrMemberFuIou fuiou = drMemberFuiouDAO.getDrMemberFuiouByUserId(dto.getUid());
                if(Utils.isObjectNotEmpty(carry.getId())){
                    Map<String, Object> m = new HashMap<String, Object>();
                    m.put("signature", FuiouConfig.withdrawals(fuiou.getFuiouId(),carry.getPaymentNum(),
                            carry.getAmount().toString(),dto.getChannel() == 1||dto.getChannel() == 2 ?"app":"wap"));
                    m.put("fuiouUrl", FuiouConfig.APPWITHDRAWURL);
                    m.putAll(JSONObject.parseObject(m.get("signature").toString()));
                    br.setMap(m);
                    br.setSuccess(true);
                    return  br;
                }
            }else {
                return  new BaseResult<String>(false,"9999","请先开通存管账户");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResult<String>(false,"9999","系统异常");
        }
        return null;
    }

    /**
         *  获取恒丰返回码描述
         * @param code
         * @return
         */

    private String getFuiouCode(String code) {
        String desc = "";
        if (!Utils.strIsNull(code)) {
            try {
                desc = redisClientTemplate.hget("fuiouCode", code);
            } catch (Exception e) {
            }
        }
        return desc;
    }
}
