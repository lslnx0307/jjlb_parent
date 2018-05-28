package com.jjlb.service.main.impl;

import com.alibaba.fastjson.JSONObject;
import com.jjlb.common.*;
import com.jjlb.dao.bank.SysBankDAO;
import com.jjlb.dao.claims.DrClaimsCustomerDAO;
import com.jjlb.dao.claims.DrClaimsLoanDAO;
import com.jjlb.dao.fuiou.SysFuiouNoticeLogDAO;
import com.jjlb.dao.funds.*;
import com.jjlb.dao.member.*;
import com.jjlb.dao.product.DrProductInfoDAO;
import com.jjlb.dao.product.DrProductInvestDAO;
import com.jjlb.dao.product.DrProductInvestRepayInfoDAO;
import com.jjlb.dao.subject.DrSubjectInfoDAO;
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
import com.jjlb.model.entity.claims.DrClaimsCustomer;
import com.jjlb.model.entity.claims.DrClaimsLoan;
import com.jjlb.model.entity.fuiou.SysFuiouNoticeLog;
import com.jjlb.model.entity.fuiou.SysMessageLog;
import com.jjlb.model.entity.funds.*;
import com.jjlb.model.entity.member.*;
import com.jjlb.model.entity.product.DrProductInfo;
import com.jjlb.model.entity.product.DrProductInfoRepayDetail;
import com.jjlb.model.entity.product.DrProductInvest;
import com.jjlb.model.entity.product.DrProductInvestRepayInfo;
import com.jjlb.model.entity.subject.DrSubjectInfo;
import com.jjlb.model.entity.sys.DrCarryParam;
import com.jjlb.model.entity.sys.JsMerchantMarketing;
import com.jjlb.service.main.MainService;
import com.jjlb.service.redis.RedisClientTemplate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
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
    @Autowired
    DrClaimsLoanDAO drClaimsLoanDAO;
    @Autowired
    DrClaimsCustomerDAO drClaimsCustomerDAO;
    @Autowired
    DrProductInvestRepayInfoDAO drProductInvestRepayInfoDAO;
    @Autowired
    DrCompanyFundsLogDAO drCompanyFundsLogDAO;
    @Autowired
    JsMerchantMarketingDAO jsMerchantMarketingDAO;
    @Autowired
    DrSubjectInfoDAO drSubjectInfoDAO;

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

                if (!Utils.strIsNull(dto.getCust_nm()) &&
                        !Utils.strIsNull(dto.getCertif_id()) && !Utils.strIsNull(dto.getCity_id()) &&
                        !Utils.strIsNull(dto.getParent_bank_id()) && !Utils.strIsNull(dto.getCapAcntNo()) &&
                        !Utils.isBlank(dto.getChannel()) &&
                        !Utils.strIsNull(dto.getPassword()) && dto.getPassword().equals(dto.getRpassword()) &&
                        !Utils.strIsNull(dto.getMobile_no())) {

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
                                        if (Utils.isObjectNotEmpty(info) && (
                                                Utils.isObjectEmpty(info.getRealName())
                                                        || Utils.isObjectEmpty(info.getIdCards()))) {
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
                            br.setMsg(resp_code + ":" + (String) br.getMap().get("resp_desc")
                                    + ("5344".equals(resp_code) || "5343".equals(resp_code) ? "-->请咨询客服" : ""));
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
    public BaseResult<String> fastRecharge(DrMemberFastRechargeDto dto) throws Exception {
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
                    if (drMemberCrush.getStatus() == 1) {
                        DrMemberFunds funds = new DrMemberFunds();
                        funds.setUid(fund.getUid());
                        funds.setFuiou_balance(fund.getFuiou_balance().add(drMemberCrush.getAmount()));
                        funds.setFuiou_crushcount(fund.getFuiou_crushcount().add(drMemberCrush.getAmount()));

                        drMemberFundsDAO.updateDrMemberFunds(funds);

                        drMemberCrushDAO.updateMemberCrushById(drMemberCrush);

                        DrMemberFundsRecord record = new DrMemberFundsRecord(null, null,
                                drMemberCrush.getUid(), 1, 1, drMemberCrush.getAmount(),
                                funds.getFuiou_balance(), 3,
                                "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】",
                                drMemberCrush.getPayNum());
                        drMemberFundsRecordDAO.insert(record);

                        DrMemberFundsLog drMemberFundsLog = new DrMemberFundsLog(drMemberCrush.getUid(), record.getId(),
                                drMemberCrush.getAmount(), 44,
                                1, "充值金额：【" + drMemberCrush.getAmount().setScale(2) + "】");
                        drMemberFundsLogDAO.insertDrMemberFundsLog(drMemberFundsLog);

                        //记公司账户日志 回款营销收益
                        JsCompanyAccountLog companyAccountLog = new JsCompanyAccountLog();
                        //资金类型
                        companyAccountLog.setCompanyfunds(59);
                        //支出
                        companyAccountLog.setType(0);
                        BigDecimal amount = drMemberCrush.getAmount().multiply(new BigDecimal(1.5)).divide(
                                new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN);
                        BigDecimal poundage = new BigDecimal("2");

                        poundage = poundage.compareTo(amount) == 1 ? poundage : amount;

                        //金额
                        companyAccountLog.setAmount(poundage);
                        companyAccountLog.setStatus(3);
                        companyAccountLog.setRemark(member.getMobilephone() + "充值手续费:" + poundage + ",充值金额:" + drMemberCrush.getAmount());
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
                    } else if (drMemberCrush.getStatus() == 2) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResult<String>(false, "9999", "系统错误");
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
                return new BaseResult<OnlineBankingRechargeResultDto>(true, "10000", "充值成功", resultDto);
            } else {
                return new BaseResult<OnlineBankingRechargeResultDto>(false, "1001", "参数有误");
            }
        } catch (Exception e) {
            logger.error("网银充值", e);
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
            Map<String, Object> backMap = new HashMap<String, Object>();
            BigDecimal amount = dto.getAmount();
            DrMemberFunds funds = drMemberFundsDAO.queryDrMemberFundsByUid(dto.getUid());
            DrProductInfo pInfo = drProductInfoDAO.selectProductById(dto.getPid());
            loginMember = drMemberDAO.selectByPrimaryKey(dto.getUid());
            if (Utils.isObjectEmpty(pInfo) || pInfo.getStatus() != 5) {
                return new BaseResult<String>(false, "1002", "产品不存在或者产品未上架");
            } else if ((pInfo.getType() == 2 || pInfo.getType() == 3) && pInfo.getSurplusAmount().compareTo(amount) < 0) {
                return new BaseResult<String>(false, "1003", "剩余可投资金额不足");
            } else if (pInfo.getLeastaAmount().compareTo(amount) > 0) {
                return new BaseResult<String>(false, "1004", "投资金额小于起投金额");
            } else if (amount.remainder(pInfo.getIncreasAmount()).compareTo(BigDecimal.ZERO) != 0) {
                return new BaseResult<String>(false, "1005", "投资金额非递增金额的整数倍");
            } else if (amount.compareTo(pInfo.getMaxAmount()) > 0) {
                return new BaseResult<String>(false, "1006", "投资金额大于个人可投资金额");
            } else if (loginMember.getIsFuiou() != 1) {
                return new BaseResult<String>(false, "1013", "提示开通存管");
            } else if (funds.getFuiou_balance().compareTo(amount) < 0) {
                return new BaseResult<String>(false, "1007", "账户可用余额不足");
            }
            //新手标
            Map<String, Object> map = new HashMap<String, Object>();
            //存管新手标
            Map<String, Object> param = new HashMap<String, Object>();

            if (pInfo.getType() == 3) {
                param.put("uid", loginMember.getUid());
                //2是投资失败的，可以继续投
                param.put("statuses", new Integer[]{0, 1, 3, 4});
                param.put("type", 3);
                String mobilePhone2 = null;
                mobilePhone2 = redisClientTemplate.getProperties("systemPhone");
                if (!loginMember.getMobilephone().equals(mobilePhone2)) {
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
            if (Utils.isObjectNotEmpty(fid)) {
                DrMemberFavourable drMemberFavourable = drMemberFavourableDAO.selectByPrimaryKey(fid);
                if (pInfo.getType() != Constants.FIVE) {
                    if (pInfo.getIsDouble() == Constants.ZERO && drMemberFavourable.getType() == Constants.FORE) {
                        return new BaseResult<String>(false, "1015", "翻倍券只能用于翻倍标");
                    }
                    if (pInfo.getIsInterest() == Constants.ZERO && drMemberFavourable.getType() == Constants.TWO) {
                        return new BaseResult<String>(false, "1016", "该标不能使用加息劵");
                    }
                    if (pInfo.getIsCash() == Constants.ZERO && drMemberFavourable.getType() == Constants.ONE) {
                        return new BaseResult<String>(false, "1017", "该标不能使用红包");
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
                    return new BaseResult<String>(false, "1010", "该标不能使用红包");
                } else {
                    //非首投用户，不能使用系统赠送的翻倍券
                    if (dmf.getType() == 4 && dmf.getSource() == 0) {
                        map.clear();
                        map.put("uid", loginMember.getUid());
                        map.put("barring", new Integer[]{1, 5});
                        Integer investCount = drProductInvestDAO.selectInvestCountByMap(map);
                        if (investCount > 0) {
                            return new BaseResult<String>(false, "1012", "非首投用户，不能使用系统赠送的翻倍券");
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
            freeDto.setRem("投资" + pInfo.getFullName() + "|pid:" + pInfo.getId() + "|code:" + pInfo.getCode());
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
            if (pInfo.getType() != Constants.ONE && pInfo.getType() != Constants.FORE && pInfo.getSurplusAmount().compareTo(BigDecimal.ZERO) == 0) {
                pInfo.setStatus(6);
                pInfo.setFullDate(new Date());
                pInfo.setEstablish(Utils.getDayNumOfAppointDate(
                        Utils.format(Utils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd"), -1));
                pInfo.setExpireDate(Utils.getDayNumOfAppointDate(pInfo.getEstablish(), 0 - pInfo.getDeadline()));
                redisClientTemplate.del("product.info." + pInfo.getId());
            }

            redisClientTemplate.setex(("product.info." + pInfo.getId()).getBytes(),
                    600, SerializeUtil.serialize(pInfo));
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
            BigDecimal dayRate = Utils.nwdDivide(Utils.nwdDivide(
                    pInfo.getRate().add(pInfo.getActivityRate()), 100), 360);
            invest.setInterest(amount.multiply(dayRate).multiply(
                    new BigDecimal(pInfo.getDeadline())).setScale(2, BigDecimal.ROUND_FLOOR));
            invest.setInvestTime(new Date());
            drProductInvestDAO.insertSelective(invest);
            DrMemberFundsRecord fundsRecord = new DrMemberFundsRecord(
                    pInfo.getId(), invest.getId(),
                    loginMember.getUid(), 3, 0, amount,
                    funds.getFuiou_balance().subtract(amount), 4,
                    "投资【" + pInfo.getFullName() + ":" + pInfo.getCode() + "】产品", null);
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
            DrMemberMsg msg = new DrMemberMsg(loginMember.getUid(), 0, 3, "投资成功", new Date(),
                    0, 0,
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
                backMap.put("expireDate", Utils.getDayNumOfAppointDate(Utils.format(Utils.format(new Date(),
                        "yyyy-MM-dd"), "yyyy-MM-dd"), -(pInfo.getDeadline() + 1)));
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
            if (member == null) {
                return new BaseResult<String>(false, "9999", "用户未登陆");
            }
            if (member.getIsFuiou() == Constants.ONE) {
                //处理上一笔未处理的提现记录


                if (Utils.isObjectEmpty(dto.getAmount())) {
                    return new BaseResult<String>(false, "1001", "提现金额有误");
                }

                if (Utils.isObjectEmpty(dto.getIsChargeFlag())) {
                    return new BaseResult<String>(false, "9999", "系统异常");
                }
                boolean result = false;
                Integer investCount = drProductInvestDAO.selectInvestCount(dto.getUid());
                //type = 1 存管，type = 2平台
                BigDecimal carryAmountCount = drMemberCarryDAO.selectCarryAmountCount(dto.getUid(), 1);
                BigDecimal crushAmountCount = drMemberCrushDAO.getDrMemberCrushAmountByUID(dto.getUid(), 1);
                //提现大于充值并且未投资除体验标之外的产品
                if (investCount != 0 && !(dto.getAmount().add(carryAmountCount).compareTo(crushAmountCount) == 1)) {
                    result = true;
                }
                if (!result) {
                    return new BaseResult<String>(false, "2002", "返现需完成一次真实投资后才可提现");
                }
                redisClientTemplate.lock("fuiou_withdrawals" + member.getUid().toString());
                DrMemberCarry drMemberCarry = new DrMemberCarry();
                Integer quantity = jsMemberRewardsDAO.getQuantityCount(member.getUid());
                Integer isCharge = 0;
                //需要手续费
                if (quantity == 0) {
                    isCharge = 1;
                }
                if (isCharge.intValue() != dto.getIsChargeFlag().intValue()) {
                    if (isCharge == 1) {
                        return new BaseResult<String>(false, "1007", "该笔需要收取手续费");
                    } else {
                        return new BaseResult<String>(false, "1008", "该笔不需要收取手续费");
                    }
                } else {
                    if (isCharge == 1) {
                        if (dto.getAmount().compareTo(new BigDecimal(Constants.THREE)) < 0) {
                            return new BaseResult<String>(false, "1001", "提现金额有误");
                        }
                        drMemberCarry.setPoundage(new BigDecimal(2));
                    } else {
                        if (dto.getAmount().compareTo(new BigDecimal(Constants.ONE)) < 0) {
                            return new BaseResult<String>(false, "1001", "提现金额有误");
                        }
                        drMemberCarry.setPoundage(new BigDecimal(0));
                    }
                }
                drMemberCarry.setAmount(dto.getAmount().subtract(drMemberCarry.getPoundage()));
                DrMemberBank drMemberBank = drMemberBankDAO.selectFuiouIdentificationBank(member.getUid());
                DrMemberFunds drMemberFunds = drMemberFundsDAO.queryDrMemberFundsByUid(member.getUid());
                //TODO 判断存管户
                if (drMemberFunds.getFuiou_balance().compareTo(drMemberCarry.getAmount().add(
                        drMemberCarry.getPoundage())) < 0) {
                    return new BaseResult<String>(false, "1004", "余额不足");
                }

                DrCarryParam drCarryParam = drCarryParamDAO.getDrCarryParam();
                drMemberCarry.setChannel(dto.getChannel());

                drMemberCarry.setUid(member.getUid());
                drMemberCarry.setPaymentNum(Utils.createOrderNo(6, drMemberCarry.getUid(), ""));
                drMemberCarry.setAddTime(new Date());
                drMemberCarry.setBankId(drMemberBank.getId());
                drMemberCarry.setBankName(drMemberBank.getBankName());
                drMemberCarry.setBankNum(drMemberBank.getBankNum().substring(0, 4) + "********" +
                        drMemberBank.getBankNum().substring(drMemberBank.getBankNum().length() - 4,
                                drMemberBank.getBankNum().length()));
                drMemberFundsDAO.updateDrMemberFunds(drMemberFunds);
                //提现状态，0未处理 1处理中 2成功 3失败  4拒绝 5超时
                drMemberCarry.setStatus(0);
                //存管
                drMemberCarry.setType(3);
                //收入
                BigDecimal income = drMemberFunds.getFuiou_crushcount().add(
                        drMemberFunds.getFuiou_investProfit().add(drMemberFunds.getFuiou_spreadProfit()));
                //支出
                BigDecimal expenditure = drMemberFunds.getFuiou_balance().add(
                        drMemberFunds.getFuiou_freeze().add(drMemberFunds.getFuiou_wprincipal().add(
                                drMemberFunds.getFuiou_carrycount())));
                //收入 - 支出 = 0
                if (income.subtract(expenditure).compareTo(BigDecimal.ZERO) != 0) {
//                    sendSmsAndMail(member.getUid());
                    drMemberCarry.setStatus(0);
                    drMemberCarry.setReason("资金账户出现异常!");
                    return new BaseResult<String>(false, "1006", "资金账户出现异常!");
                }

                drMemberCarryDAO.insertDrMemberCarry(drMemberCarry);

                DrMemberCarry carry = drMemberCarry;
                DrMemberFuIou fuiou = drMemberFuiouDAO.getDrMemberFuiouByUserId(dto.getUid());
                if (Utils.isObjectNotEmpty(carry.getId())) {
                    Map<String, Object> m = new HashMap<String, Object>();
                    m.put("signature", FuiouConfig.withdrawals(fuiou.getFuiouId(), carry.getPaymentNum(),
                            carry.getAmount().toString(),
                            dto.getChannel() == 1 || dto.getChannel() == 2 ? "app" : "wap"));
                    m.put("fuiouUrl", FuiouConfig.APPWITHDRAWURL);
                    m.putAll(JSONObject.parseObject(m.get("signature").toString()));
                    br.setMap(m);
                    br.setSuccess(true);
                    return br;
                }
            } else {
                return new BaseResult<String>(false, "9999", "请先开通存管账户");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResult<String>(false, "9999", "系统异常");
        }
        return null;
    }

    @Override
    public void interestRate() {
        logger.info("计息开始......");
        try {
            List<DrProductInfo> drProductInfos = drProductInfoDAO.selectRaiseSuccesProductInfo();
            char[] ary2 = {'0', '0', '0', '0'};
            //记录计息次数
            int nums = 0;
            for (DrProductInfo info : drProductInfos) {
                if (Utils.isObjectNotEmpty(info.getProject_no())) {
                    // 保存投资成功的记录的回款信息
                    List<DrProductInvestRepayInfo> insertRepayInfoList = new ArrayList<DrProductInvestRepayInfo>();
                    // 按月付息产品每期回款明细信息
                    List<DrProductInfoRepayDetail> productRepayDetailList = new ArrayList<DrProductInfoRepayDetail>();
                    // 保存投资成功的记录
                    List<DrProductInvest> successInvestList = new ArrayList<DrProductInvest>();
                    // 保存投资失败的记录ID
                    StringBuffer failId = new StringBuffer();
                    // 需要发送的站内信
                    List<DrMemberMsg> msgList = new ArrayList<DrMemberMsg>();
                    List<SysMessageLog> smsList = new ArrayList<SysMessageLog>();
                    // 投资用户资金信息
                    Map<Integer, DrMemberFunds> fundsMap = new HashMap<Integer, DrMemberFunds>();
                    // 交易日志
                    List<DrMemberFundsLog> fundsLogsList = new ArrayList<DrMemberFundsLog>();
                    // 交易记录
                    List<DrMemberFundsRecord> fundsRecordList = new ArrayList<DrMemberFundsRecord>();
                    Date now = new Date();
                    logger.info("产品[" + info.getFullName() + ":" + info.getCode() + info.getCode() + "]开始计息");
                    // 1=30/360 2=30/365 日期模式
                    Integer dateType = 1;
                    // 日息
                    BigDecimal dayRate = Utils.nwdDivide(Utils.nwdDivide(info.getRate().add(
                            info.getActivityRate()), 100), dateType == 1 ? 360 : 365);
                    // 日息不加活动利率
                    BigDecimal rate = Utils.nwdDivide(Utils.nwdDivide(info.getRate(), 100), dateType == 1 ? 360 : 365);
                    // 查询所有的投资记录
                    List<DrProductInvest> investList = drProductInvestDAO.getFuiouDrProductInvestListByPid(info.getId());
                    //债权
                    DrClaimsLoan loan = drClaimsLoanDAO.getDrClaimsLoanBySid(info.getSid());
                    // 借款人
                    DrClaimsCustomer customer = drClaimsCustomerDAO.getDrClaimsCustomerByLid(loan.getId());
                    // 产品下总的投资笔数
                    int total = investList.size();
                    // 投资成功笔数
                    int successTotal = 0;
                    // 已投资金额
                    BigDecimal totalAmount = BigDecimal.ZERO;
                    int periods = 0;
                    for (int j = 0; j < investList.size(); j++) {
                        nums++;
                        DrProductInvest invest = investList.get(j);
                        DrMember member = drMemberDAO.selectByPrimaryKey(invest.getUid());
                        if (totalAmount.compareTo(info.getAmount()) < 0 || info.getType() == 1 ||
                                info.getType() == 4 || info.getType() == 5) {
                            totalAmount = totalAmount.add(invest.getAmount());
                            // 投资总额大于产品金额，此笔投资只能部分成功
                            if (totalAmount.compareTo(info.getAmount()) > 0 && info.getType() != 1 && info.getType() != 5) {
                                // 实际投资金额
                                invest.setFactAmount(info.getAmount().subtract(totalAmount.subtract(invest.getAmount())));
                                // =
                                // 产品金额-(已投资总额-本次投资金额)
                                totalAmount = totalAmount.subtract(invest.getAmount()).add(invest.getFactAmount());
                            } else {
                                invest.setFactAmount(invest.getAmount());
                            }

                            // 对于状态是为未处理或者失败数据进行冻结到冻结
                            if (invest.getStatus() == 0) {
                                JSONObject profitJson = null;
                                if (info.getRepayType() == 1) {
                                    // 到期一次性还本付息
                                    // 计算收益
                                    profitJson = this.getInvestProfit(info.getDeadline(), invest, dayRate,
                                            info.getType(), info.getRate());
                                    //基本利息
                                    BigDecimal profit = invest.getFactAmount().multiply(rate).multiply(
                                            new BigDecimal(info.getDeadline())).setScale(2,
                                            BigDecimal.ROUND_FLOOR);
                                    //平台贴息
                                    BigDecimal platformInterest = invest.getFactAmount().multiply(
                                            dayRate.subtract(rate)).multiply(new BigDecimal(
                                                    info.getDeadline())).setScale(2, BigDecimal.ROUND_FLOOR);
                                    //加息券利息
                                    BigDecimal interestRateCoupon = profitJson.getBigDecimal("interestRateCoupon");
                                    //翻倍券利息
                                    BigDecimal interestDoubleCoupons = profitJson.getBigDecimal("interestDoubleCoupons");
                                    //未满标贴息
                                    BigDecimal interestSubsidy = profitJson.getBigDecimal("interestSubsidy");
                                    BigDecimal interest = profit.add(platformInterest).add(
                                            interestRateCoupon).add(interestDoubleCoupons).add(interestSubsidy);
                                    //interest小于profitJson.getBigDecimal("interestProfit")
                                    if (interest.compareTo(profitJson.getBigDecimal("interestProfit")) == -1) {
                                        //若分开算的金额少于总的金额 则在平台贴息把差补全
                                        platformInterest = platformInterest.add(
                                                profitJson.getBigDecimal("interestProfit").subtract(interest));
                                    }
                                    //interest大于profitJson.getBigDecimal("interestProfit")
                                    if (interest.compareTo(profitJson.getBigDecimal("interestProfit")) == 1) {
                                        //若分开算的金额大于总的金额 则在平台贴息把差减去
                                        platformInterest = platformInterest.subtract(
                                                interest.subtract(profitJson.getBigDecimal("interestProfit")));
                                    }
                                    DrProductInvestRepayInfo repayinfo = new DrProductInvestRepayInfo(
                                            invest.getUid(), invest.getId(), info.getId(), invest.getFactAmount(),
                                            BigDecimal.ZERO, profitJson.getBigDecimal("interestProfit"),
                                            BigDecimal.ZERO, BigDecimal.ZERO, 0,
                                            Utils.getDayNumOfAppointDate(now, -info.getDeadline()),
                                            profit, platformInterest, interestRateCoupon,
                                            interestDoubleCoupons, interestSubsidy);

                                    insertRepayInfoList.add(repayinfo);
                                }
                                invest.setFactInterest(profitJson.getBigDecimal("interestProfit"));
                                invest.setStatus(1);
                                // 协议编号
                                System.arraycopy((nums + "").toCharArray(), 0, ary2,
                                        ary2.length - (nums + "").toCharArray().length,
                                        (nums + "").toCharArray().length);
                                invest.setAgreementNo("JJLB" + Utils.format(new Date(), "yyMMdd") +
                                        info.getType() + new String(ary2));
                                successInvestList.add(invest);
                                // 用户资金
                                DrMemberFunds funds = null;
                                if (fundsMap.containsKey(invest.getUid())) {
                                    funds = fundsMap.get(invest.getUid());
                                } else {
                                    funds = drMemberFundsDAO.queryDrMemberFundsByUid(invest.getUid());
                                }
                                DrMemberFundsRecord fundsRecord = drMemberFundsRecordDAO.
                                        selectByParamForProductInterest(invest.getId(), 4);
                                // 投资解冻
                                funds.setFuiou_balance(funds.getFuiou_balance().add(invest.getAmount()));
                                funds.setFuiou_freeze(funds.getFuiou_freeze().subtract(invest.getAmount()));
                                funds.setFuiou_balance(funds.getFuiou_balance().subtract(invest.getFactAmount()));
                                funds.setFuiou_wprincipal(funds.getFuiou_wprincipal().add(invest.getFactAmount()));
                                funds.setFuiou_winterest(funds.getFuiou_winterest().add(invest.getFactInterest()));
                                funds.setFuiou_investAmount(funds.getFuiou_investAmount().add(invest.getFactAmount()));
                                // 投资成功
                                fundsRecord.setStatus(3);
                                fundsRecord.setBalance(fundsRecord.getBalance().add(invest.getAmount().subtract(invest.getFactAmount())));
                                fundsRecord.setAmount(invest.getAmount());

                                fundsMap.put(funds.getUid(), funds);
                                fundsRecordList.add(fundsRecord);
                                DrMemberMsg msg = new DrMemberMsg(invest.getUid(), 0, 3, "计息成功",
                                    now, 0, 0, PropertyUtil.getProperties("interestSuccessMsg")
                                    .replace("${fullName}", info.getFullName()).replace("${amount}",
                                    invest.getFactAmount().toString()).replace("${interest}",
                                    invest.getFactInterest().toString()).replace("${date}",
                                    Utils.getDayNumOfAppointDate(now, -info.getDeadline(), "yyyy年MM月dd日")));
                                msgList.add(msg);
                                SysMessageLog smslog = new SysMessageLog(invest.getUid(), PropertyUtil
                                        .getProperties("interestSuccessSms").replace("${realName}",
                                            Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName() : member
                                                    .getMobilephone()).replace("${fullName}", info.getFullName())
                                        .replace("${amount}", invest.getFactAmount().toString()).
                                                replace("${interest}", invest.getFactInterest().toString()).
                                                replace("${date}", Utils.getDayNumOfAppointDate(now,
                                                        -info.getDeadline(), "yyyy年MM月dd日")), 17,
                                        Utils.parseDate(Utils.format(now, "yyyy-MM-dd 10:00:00"),
                                                "yyyy-MM-dd HH:mm:ss"), member.getMobilephone());
                                smsList.add(smslog);
                                successTotal++;
                            }
                        } else {
                            invest.setFactAmount(BigDecimal.ZERO);
                            invest.setFactInterest(BigDecimal.ZERO);
                            invest.setStatus(2);
                            failId.append(invest.getId() + ",");
                            // 用户资金
                            DrMemberFunds funds = null;
                            if (fundsMap.containsKey(invest.getUid())) {
                                funds = fundsMap.get(invest.getUid());
                            } else {
                                funds = drMemberFundsDAO.queryDrMemberFundsByUid(invest.getUid());
                            }
                            DrMemberFundsRecord fundsRecord = drMemberFundsRecordDAO.selectByParam(invest.getId(), null);
                            // 投资解冻
                            funds.setFuiou_balance(funds.getFuiou_balance().add(invest.getAmount()));
                            funds.setFuiou_freeze(funds.getFuiou_freeze().subtract(invest.getAmount()));
                            // 投资失败
                            fundsRecord.setStatus(2);
                            fundsRecord.setBalance(fundsRecord.getBalance().add(invest.getAmount().subtract(invest.getFactAmount())));
                            fundsRecord.setAmount(invest.getAmount());

                            fundsMap.put(funds.getUid(), funds);
                            fundsRecordList.add(fundsRecord);
                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.error("计息失败"+e);
            try {
                //发短信
            } catch (Exception e1) {
                logger.error(e1+"");
            }
        }
        logger.info("计息完成......");
    }

    @Override
    public void productRepay() {
        try {
            logger.info("回款开始......");
            List<DrProductInfo> drProductInfos = drProductInfoDAO.selectExpireProductInfo();
            for (DrProductInfo info : drProductInfos) {
                Map<Integer,DrMemberFunds> fundsMap = new HashMap<Integer, DrMemberFunds>();
                List<SysMessageLog> smsList = new ArrayList<SysMessageLog>();
                //还款时间
                Date now = new Date();
                StringBuffer buff = new StringBuffer();
                List<DrProductInvestRepayInfo> repayList = drProductInvestRepayInfoDAO.selectShouldRepayInfo(info.getId());
                DrProductInfoRepayDetail productRepayDetail = null;
                //最后一期标记位
                boolean expireFlag = true;
                List<Map<String,Object>> othreInterestCache = new ArrayList<Map<String,Object>>();
                //支付总利息
                BigDecimal infoInterest = BigDecimal.ZERO;
                BigDecimal infoPrincipal = BigDecimal.ZERO;
                //产品下总的还款笔数
                int total = repayList.size();
                //还款成功笔数
                int successTotal = 0;
                // 企业 余额//可用余额
                BigDecimal businessAccountBalance = BigDecimal.ZERO;
                //垫付付总金额
                BigDecimal advanceBalance = BigDecimal.ZERO;
                //是否垫付
                boolean isAdvance = false;
                String toAdvance = null;
                //公司垫付 记录
                JsMerchantMarketing jmm;
                List<JsMerchantMarketing> jmmlist = new ArrayList<JsMerchantMarketing>();
                Map<String,Object> advanceMap = new HashMap<String, Object>(16);
                //理财计划项目编号
                String link_project_no = info.getId()+"_"+Utils.format(new Date(), "yyyy-MM-dd");
                String out_cust_no = "";
                //非体验标
                if(info.getType() != 5){
                    out_cust_no = drProductInfoDAO.getIn_cust_noByProductId(info.getId());

                    //
                    Map<String,String> balanceActionMap = new HashMap<String, String>(16);
                    balanceActionMap.put("cust_no", out_cust_no);
                    BaseResult bresult = FuiouConfig.balanceAction(balanceActionMap);

                    if(bresult.isSuccess()){
                        JSONObject accountresults= (JSONObject) bresult.getMap().get("results");
                        JSONObject accountresult=(JSONObject) accountresults.get("result");
                        //可用余额
                        businessAccountBalance = FuiouConfig.centToYuan(accountresult.get("ca_balance").toString());

                    }

                }
                if(repayList.size()>0){
                    for (int j = 0; j < repayList.size(); j++) {
                        //回款信息
                        DrProductInvestRepayInfo repayInfo = repayList.get(j);
                        //应收利息
                        infoInterest = infoInterest.add(repayInfo.getShouldInterest());
                        //应收本金
                        infoPrincipal = infoPrincipal.add(repayInfo.getShouldPrincipal());
                        //对于状态是为划拨或者划拨失败数据进行划拨
                        if(repayInfo.getRemitStatus() == 1 || repayInfo.getRemitStatus() == 3){
                            //投资人
                            DrMember member = drMemberDAO.selectByPrimaryKey(repayInfo.getUid());
                            BigDecimal normalInterest = BigDecimal.ZERO;
                            BigDecimal otherInterest = BigDecimal.ZERO;

                            //基本收益
                            normalInterest = repayInfo.getBasicprofit();
                            //其他收益 --> 走营销存管账户=应收收益-产品年化收益
                            otherInterest  = repayInfo.getShouldInterest().subtract(normalInterest);


                            //本息合计=本金 +产品年化收益  --> 走存管商户账户
                            BigDecimal principalInterest = repayInfo.getShouldPrincipal().add(normalInterest);

                            BaseResult br = new BaseResult();
                            br.setSuccess(true);
                            //非体验标
                            if(info.getType() != 5){
                                //根据可用余额判断是否 要 商户 给企业垫付回款
                                if(!isAdvance && businessAccountBalance.compareTo(principalInterest)<0){
                                    isAdvance = true;
                                }

                                Map<String, String>params = new HashMap<String, String>();
                                if(!isAdvance){
                                    //流水号
                                    String remitMchntTxnSsn = Utils.createOrderNo(6, repayInfo.getId(), "");
                                    repayInfo.setRemitMchntTxnSsn(remitMchntTxnSsn);
                                    params.put("out_cust_no", out_cust_no);
                                    params.put("in_cust_no", drMemberFuiouDAO.getDrMemberFuiouByUserId(member.getUid()).getFuiouId());
                                    //精确到分
                                    params.put("amt", principalInterest.toString());
                                    params.put("rem", "产品回款|pid:"+repayInfo.getPid()+"|repayInfoId:"+repayInfo.getId());
                                    params.put("mchnt_txn_ssn", remitMchntTxnSsn);
                                    br=FuiouConfig.transferBu(params);
                                }

                                //
                                if(!isAdvance && !br.isSuccess() ){
                                    String transferBuCode = br.getMap().get("resp_code")+"";
                                    //只有转帐企业可用余额不足时 商户才垫付, 其他错误 不垫付
                                    if("3017".equals(transferBuCode) || "3018".equals(transferBuCode) || "3023".equals(transferBuCode)){
                                        isAdvance = true;
                                    }
                                }

                                if(isAdvance){
                                    //流水号
                                    String remitMchntTxnSsn = Utils.createOrderNo(6, repayInfo.getId(), "");
                                    advanceMap.put("mchnt_txn_ssn", remitMchntTxnSsn);
                                    advanceMap.put("out_cust_no", FuiouConfig.LOGIN_ID);
                                    advanceMap.put("in_cust_no", drMemberFuiouDAO.getDrMemberFuiouByUserId(member.getUid()).getFuiouId());
                                    advanceMap.put("amt", principalInterest.toString());
                                    advanceMap.put("rem",  "产品回款-公司垫付|pid:"+repayInfo.getPid()+"|repayInfoId:"+repayInfo.getId());
                                    advanceMap.put("contract_no", "");
                                    advanceMap.put("icd_name", "产品回款-公司垫付|pid:"+repayInfo.getPid()+"|repayInfoId:"+repayInfo.getId());
                                    // 公司垫付转帐
                                    br=FuiouConfig.transferBmu(advanceMap);

                                    // 记录到垫付记录表里以方便转账统一报备
                                    jmm = new JsMerchantMarketing(principalInterest, repayInfo.getPid(), repayInfo.getInvestId(), null,
                                            repayInfo.getUid(), 6, new Date(), remitMchntTxnSsn, null,
                                            null, null, "公司垫付", 1);
                                    jmmlist.add(jmm);
                                }


                            }

                            if(!br.isSuccess()){
                                //划拨失败
                                repayInfo.setRemitStatus(3);
                                //失败原因
                                repayInfo.setRemitFailReson(StringUtils.left(br.getMsg(), 255));
                                repayInfo.setRemitMchntTxnSsn(br.getMap().get("mchnt_txn_ssn").toString());
                                drProductInvestRepayInfoDAO.updateByIdFuiou(repayInfo);
                                continue;
                            }else{

                                if(!isAdvance){
                                    //不是公司垫付 企业余额 减去 还款
                                    businessAccountBalance = businessAccountBalance.subtract(principalInterest);
                                }else{
                                    //垫付总额加上 公司垫付
                                    advanceBalance = advanceBalance.add(principalInterest);
                                }
                                //成功
                                repayInfo.setRemitStatus(2);
                                if(info.getType() != 5){
                                    repayInfo.setRemitMchntTxnSsn(br.getMap().get("mchnt_txn_ssn").toString());
                                }
                                //发放  其他收益  走队列发放
                                Map<String,Object> othreInterestMap =new HashMap<String, Object>(16);
                                othreInterestMap.put("uid", repayInfo.getUid());
                                //回款id可用 作验证
                                othreInterestMap.put("repayInfoId", repayInfo.getId());
                                //其他收益额度
                                othreInterestMap.put("otherInterest", otherInterest);
                                //理财计划项目编号
                                othreInterestMap.put("link_project_no", link_project_no);
                                //项目编号
                                othreInterestMap.put("project_no", info.getProject_no());
                                //是否体验标
                                othreInterestMap.put("productType", info.getType());
                                //产品ID
                                othreInterestMap.put("project_id", info.getId());
                                //平台贴息
                                othreInterestMap.put("platformInterest", repayInfo.getPlatformInterest());
                                //加息券利息
                                othreInterestMap.put("interestRateCoupon", repayInfo.getInterestRateCoupon());
                                //翻倍券利息
                                othreInterestMap.put("interestDoubleCoupons", repayInfo.getInterestDoubleCoupons());
                                //未满标贴息
                                othreInterestMap.put("interestSubsidy", repayInfo.getInterestSubsidy());
                                //标类型
                                othreInterestMap.put("type", info.getType());
                                othreInterestCache.add(othreInterestMap);
                            }
                            repayInfo.setFactPrincipal(repayInfo.getShouldPrincipal());
                            repayInfo.setFactInterest(repayInfo.getShouldInterest());
                            repayInfo.setFactTime(now);
                            //已还款
                            repayInfo.setStatus(1);
                            //0 正常回款,1商户垫付
                            repayInfo.setMchntPay(isAdvance?1:0);


                            //资金变动
                            DrMemberFunds funds = null;
                            if(fundsMap.containsKey(repayInfo.getUid())){
                                funds = fundsMap.get(repayInfo.getUid());
                            }else{
                                funds = drMemberFundsDAO.queryDrMemberFundsByUid(repayInfo.getUid());
                            }
                            //账户余额=余额+其他收益+本息合计
                            funds.setFuiou_balance(funds.getFuiou_balance().add(repayInfo.getFactPrincipal()
                                    .add(repayInfo.getFactInterest())));
                            //待收本金
                            funds.setFuiou_wprincipal(funds.getFuiou_wprincipal().subtract(repayInfo.getFactPrincipal()));
                            //待收利息
                            funds.setFuiou_winterest(funds.getFuiou_winterest().subtract(repayInfo.getFactInterest()));
                            //投资收益
                            funds.setFuiou_investProfit(funds.getFuiou_investProfit().add(repayInfo.getFactInterest()));
                            drMemberFundsDAO.updateDrMemberFunds(funds);
                            fundsMap.put(repayInfo.getUid(), funds);

                            Map<String,Object> m=new HashMap<>(16);
                            if(info.getRepayType() == 2 || info.getRepayType() == 3 || info.getRepayType() == 4){
                                DrMemberFundsRecord fundsRecord = new DrMemberFundsRecord(repayInfo.getPid(),
                                        repayInfo.getInvestId(), repayInfo.getUid(),
                                        6, 1, repayInfo.getFactInterest().add(
                                                repayInfo.getFactPrincipal()), funds.getFuiou_balance(),
                                        3, "产品【"+info.getFullName()+":"+info.getCode()+"】第" +
                                        productRepayDetail.getPeriods() +"期回款", null);
                                drMemberFundsRecordDAO.insert(fundsRecord);
                                //基础利息 (101)
                                DrMemberFundsLog fundsLog1 = new DrMemberFundsLog(repayInfo.getUid(),
                                        fundsRecord.getId(), repayInfo.getBasicprofit(),
                                        41, 1, "产品【"+info.getFullName()+":"
                                        + info.getCode()+"】第" + productRepayDetail.getPeriods() +"期回款");
                                drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog1);

                                JsCompanyAccountLog companyAccountLog1=new JsCompanyAccountLog();
                                //资金类型
                                companyAccountLog1.setCompanyfunds(40);
                                //支出
                                companyAccountLog1.setType(0);
                                //金额
                                companyAccountLog1.setAmount(repayInfo.getShouldPrincipal());
                                //成功
                                companyAccountLog1.setStatus(3);
                                companyAccountLog1.setPid(repayInfo.getPid());
                                companyAccountLog1.setRemark(member.getMobilephone()+"回款营销收益");
                                companyAccountLog1.setAddTime(new Date());
                                //存管
                                companyAccountLog1.setChannelType(2);
                                //用户id
                                companyAccountLog1.setUid(member.getUid());
                                //本金
                                jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog1);


                                JsCompanyAccountLog companyAccountLog2=new JsCompanyAccountLog();
                                //资金类型
                                companyAccountLog2.setCompanyfunds(41);
                                //支出
                                companyAccountLog2.setType(0);
                                //金额
                                companyAccountLog2.setAmount(repayInfo.getBasicprofit());
                                //成功
                                companyAccountLog2.setStatus(3);
                                companyAccountLog2.setPid(repayInfo.getPid());
                                companyAccountLog2.setRemark(member.getMobilephone()+"回款营销收益");
                                companyAccountLog2.setAddTime(new Date());
                                //存管
                                companyAccountLog2.setChannelType(2);
                                //用户id
                                companyAccountLog2.setUid(member.getUid());
                                //基本利息
                                jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog2);

                                if(repayInfo.getFactPrincipal()!=null &&
                                        repayInfo.getFactPrincipal().compareTo(new BigDecimal("0"))==1){
                                    DrMemberFundsLog fundsLog = new DrMemberFundsLog(repayInfo.getUid(),
                                            fundsRecord.getId(),repayInfo.getFactPrincipal(),
                                            40, 1,
                                            "产品【"+info.getFullName()+":"+info.getCode()+"】第" +
                                                    productRepayDetail.getPeriods() +"期回款");//本金 (102)
                                    drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog);
                                }

                                if(repayInfo.getPlatformInterest()!=null && repayInfo.getPlatformInterest()
                                        .compareTo(new BigDecimal("0"))==1){
                                    //平台贴息(103)
                                    DrMemberFundsLog fundsLog = new DrMemberFundsLog(repayInfo.getUid(),
                                            fundsRecord.getId(),repayInfo.getPlatformInterest(),
                                            13, 1, "产品【"+info.getFullName()+":"
                                            +info.getCode()+"】第" + productRepayDetail.getPeriods() +"期回款");
                                    drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog);
                                }

                                if(repayInfo.getInterestRateCoupon()!=null &&
                                        repayInfo.getInterestRateCoupon().compareTo(new BigDecimal("0"))==1){
                                    m.clear();
                                    m.put("investId", repayInfo.getInvestId());
                                    m = drMemberFavourableDAO.getFavourableById(m);
                                    //加息券利息(104)
                                    DrMemberFundsLog fundsLog = new DrMemberFundsLog(repayInfo.getUid(),
                                            fundsRecord.getId(),repayInfo.getInterestRateCoupon(),
                                            m==null || m.get("ahsId")==null?null:(Integer)m.get("ahsId"), 1,
                                            "产品【"+info.getFullName()+":"+info.getCode()+"】第" +
                                                    productRepayDetail.getPeriods() +"期回款");
                                    drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog);

                                }

                                if(repayInfo.getInterestDoubleCoupons()!=null &&
                                        repayInfo.getInterestDoubleCoupons().compareTo(new BigDecimal("0"))==1){
                                    m.clear();
                                    m.put("investId", repayInfo.getInvestId());
                                    m = drMemberFavourableDAO.getFavourableById(m);
                                    //翻倍券利息
                                    DrMemberFundsLog fundsLog = new DrMemberFundsLog(repayInfo.getUid(),
                                            fundsRecord.getId(),repayInfo.getInterestDoubleCoupons(),
                                            m==null || m.get("ahsId")==null?null:(Integer)m.get("ahsId"), 1,
                                            "产品【"+info.getFullName()+":"+info.getCode()+"】第"
                                                    + productRepayDetail.getPeriods() +"期回款");
                                    drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog);
                                }

                                if(repayInfo.getInterestSubsidy()!=null &&
                                        repayInfo.getInterestSubsidy().compareTo(new BigDecimal("0"))==1){
                                    //未满标贴息
                                    DrMemberFundsLog fundsLog = new DrMemberFundsLog(repayInfo.getUid(),
                                            fundsRecord.getId(),repayInfo.getInterestSubsidy(),
                                            12, 1, "产品【"+info.getFullName()+":"
                                            + info.getCode()+"】第" + productRepayDetail.getPeriods() +"期回款");
                                    drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog);
                                }

                            }else{
                                DrMemberFundsRecord fundsRecord = new DrMemberFundsRecord(repayInfo.getPid(),
                                        repayInfo.getInvestId(), repayInfo.getUid(),
                                        info.getType()== 5?7:6, 1,
                                        repayInfo.getFactInterest().add(repayInfo.getFactPrincipal()),
                                        funds.getFuiou_balance(), 3, "产品【"+info.getFullName() +
                                        ":"+info.getCode()+"】回款", null);
                                drMemberFundsRecordDAO.insert(fundsRecord);
                                //基础利息
                                DrMemberFundsLog fundsLog1 = new DrMemberFundsLog(repayInfo.getUid(),
                                        fundsRecord.getId(),repayInfo.getBasicprofit(),
                                        41, 1,
                                        "产品【"+info.getFullName()+":"+info.getCode()+"】回款");
                                drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog1);
                                //本金
                                DrMemberFundsLog fundsLog2 = new DrMemberFundsLog(repayInfo.getUid(),
                                        fundsRecord.getId(),repayInfo.getFactPrincipal(),
                                        40, 1,
                                        "产品【"+info.getFullName()+":"+info.getCode()+"】回款");
                                drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog2);

                                if(repayInfo.getPlatformInterest()!=null &&
                                        repayInfo.getPlatformInterest().compareTo(new BigDecimal("0"))==1){
                                    //平台贴息
                                    DrMemberFundsLog fundsLog = new DrMemberFundsLog(repayInfo.getUid(),
                                            fundsRecord.getId(), repayInfo.getPlatformInterest(),
                                            13, 1, "产品【"+info.getFullName()+":"+info.getCode()+"】回款");
                                    drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog);

                                }

                                if(repayInfo.getInterestRateCoupon()!=null &&
                                        repayInfo.getInterestRateCoupon().compareTo(new BigDecimal("0"))==1){
                                    m.clear();
                                    m.put("investId", repayInfo.getInvestId());
                                    m = drMemberFavourableDAO.getFavourableById(m);
                                    //加息券利息
                                    DrMemberFundsLog fundsLog = new DrMemberFundsLog(repayInfo.getUid(),
                                            fundsRecord.getId(), repayInfo.getInterestRateCoupon(),
                                            m==null || m.get("ahsId")==null?null:(Integer)m.get("ahsId"), 1,
                                            "产品【"+info.getFullName()+":"+info.getCode()+"】回款");
                                    drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog);

                                }

                                if(repayInfo.getInterestDoubleCoupons()!=null &&
                                        repayInfo.getInterestDoubleCoupons().compareTo(new BigDecimal("0"))==1){
                                    m.clear();
                                    m.put("investId", repayInfo.getInvestId());
                                    m=drMemberFavourableDAO.getFavourableById(m);
                                    //翻倍券利息
                                    DrMemberFundsLog fundsLog = new DrMemberFundsLog(repayInfo.getUid(),
                                            fundsRecord.getId(), repayInfo.getInterestDoubleCoupons(),
                                            m==null || m.get("ahsId")==null?null:(Integer)m.get("ahsId"), 1,
                                            "产品【"+info.getFullName()+":"+info.getCode()+"】回款");
                                    drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog);
                                }

                                if(repayInfo.getInterestSubsidy()!=null &&
                                        repayInfo.getInterestSubsidy().compareTo(new BigDecimal("0"))==1){
                                    //未满标贴息
                                    DrMemberFundsLog fundsLog = new DrMemberFundsLog(repayInfo.getUid(),
                                            fundsRecord.getId(), repayInfo.getInterestSubsidy(),
                                            12, 1,
                                            "产品【"+info.getFullName()+":"+info.getCode()+"】回款");
                                    drMemberFundsLogDAO.insertDrMemberFundsLog(fundsLog);
                                }
                            }
                            //站内信
                            String msgStr;
                            //短信
                            String smslogStr;
                            //站内信
                            msgStr = PropertyUtil.getProperties("paymentMsg")
                                    .replace("${fullName}", info.getFullName())
                                    .replace("${amount}",
                                            repayInfo.getFactInterest().add(repayInfo.getFactPrincipal()).toString())
                                    .replace("${principal}", repayInfo.getFactPrincipal().toString())
                                    .replace("${interest}", repayInfo.getFactInterest().toString());
                            //短信
                            BigDecimal amount = drMemberFavourableDAO.selectHasRedPacket(repayInfo.getUid());
                            if(Utils.isObjectNotEmpty(amount)){
                                smslogStr = PropertyUtil.getProperties("redPacktpaymentSms")
                                        .replace("${fullName}", info.getFullName())
                                        .replace("${principal}", repayInfo.getFactPrincipal().toString())
                                        .replace("${interest}", repayInfo.getFactInterest().toString())
                                        .replace("${amount}", amount.toString());
                            }else{
                                smslogStr = PropertyUtil.getProperties("paymentSms")
                                        .replace("${fullName}", info.getFullName())
                                        .replace("${principal}", repayInfo.getFactPrincipal().toString())
                                        .replace("${interest}", repayInfo.getFactInterest().toString());
                            }
                            //回款成功站内信
                            DrMemberMsg msg = new DrMemberMsg(repayInfo.getUid(), 0, 3, "回款通知",
                                    now, 0, 0,msgStr);
                            drMemberMsgDAO.insertDrMemberMsg(msg);
                            //回款成功短信
                            SysMessageLog smslog = new SysMessageLog(repayInfo.getUid(),smslogStr ,
                                    14, Utils.parseDate(Utils.format(now, "yyyy-MM-dd 10:mm:ss"),
                                    "yyyy-MM-dd HH:mm:ss"), member.getMobilephone());
                            smsList.add(smslog);

                            drProductInvestRepayInfoDAO.updateByIdFuiou(repayInfo);
                            if(expireFlag){
                                buff.append(repayInfo.getInvestId().toString()+",");
                            }
                            successTotal ++;
                        }else{
                            successTotal ++;
                        }

                    }

                    if(successTotal >0){
                        DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(8, null,
                                info.getId(), infoInterest, 0,
                                "产品【"+info.getFullName()+"】回款,支付利息", null);
                        drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);

                        //垫付总额记公司日志
                        if(advanceBalance.compareTo(BigDecimal.ZERO)==1){
                            //记公司账户日志 收取手续费
                            JsCompanyAccountLog accountLog=new JsCompanyAccountLog();
                            //资金类型:平台垫付
                            accountLog.setCompanyfunds(80);
                            //支出
                            accountLog.setType(0);
                            //金额
                            accountLog.setAmount(advanceBalance);
                            //成功
        					accountLog.setStatus(3);
                            accountLog.setRemark("-公司垫付:产品回款["+info.getFullName()+"," +
                                    "CODE="+info.getCode()+",产品ID="+info.getId()+",垫付总额:"+advanceBalance+"]");
                            accountLog.setAddTime(now);
                            //存管
                            accountLog.setChannelType(2);
                            accountLog.setUid(null);
                            jsCompanyAccountLogDAO.insertCompanyAccountLog(accountLog);

                            //转账记录
                            if(!Utils.isEmptyList(jmmlist)){
                                jsMerchantMarketingDAO.insertBatch(jmmlist);
                            }

                            //短信
                            toAdvance = PropertyUtil.getProperties("toAdvance")
                                    .replace("${date}",  Utils.format(now, "yyyy年MM月dd日"))
                                    .replace("${business}",  "")
                                    .replace("${product}",  info.getFullName())
                                    .replace("${amount}", info.getAmount().toString())
                                    .replace("${advanceBalance}", advanceBalance.toString());

                            SysMessageLog smslog = new SysMessageLog(null,toAdvance ,
                                    12, null, PropertyUtil.getProperties("advanceToMobile"));
//                            sysMessageLogService.sendMsg(smslog);

                        }

                        if(!Utils.isEmptyList(othreInterestCache) ){
                            //其他收益放到redis里
                            Map<String,Object> map = new HashMap<String, Object>(16);
                            map.put("list", othreInterestCache);
                            //发放其他收益
                            map.put("type", 51);
                            try {
                                redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(),
                                        SerializeUtil.serialize(map));
                            } catch (Exception e) {
                                logger.error("redis 异常:发放  其他收益失败,json:"+
                                        JSONObject.toJSONString(othreInterestCache));
                            }
                        }
                        //普标
                        if((info.getType() == 2 || info.getType() == 3) && expireFlag){
                            info.setStatus(9);
                            drProductInfoDAO.updateDrProductInfoStatusById(info.getStatus(), info.getId());
                            if(successTotal == total){
                                //修改标的剩余金额
                                //标的以及部分贷款项目信息
                                DrSubjectInfo drSubjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(info.getSid());
                                drSubjectInfo.setSurplusAmount(
                                        drSubjectInfo.getSurplusAmount().multiply(
                                                new BigDecimal(10000)).add(info.getAmount()));
                                drSubjectInfo.setAmount(drSubjectInfo.getAmount().multiply(new BigDecimal(10000)));
                                drSubjectInfoDAO.updateDrSubjectInfo(drSubjectInfo);
                            }
                        }
                    }
                    //更新产品的回款状态
                    Map<String, Object>infoMap = new HashMap<>();
                    infoMap.put("repay_success_num", successTotal);
                    infoMap.put("repay_fail_num", total - successTotal);
                    if(expireFlag){
                        //回款失败,1-未回款，2-回款成功，3-回款失败
                        infoMap.put("repay_status", successTotal == total ? 2 : 3);
                    }
                    drProductInfoDAO.updateDrProductInfoInterestRepay(infoMap);
                }

                if(fundsMap.values().size()>0){
                    drMemberFundsDAO.batchUpdateDrMemberFunds(new ArrayList<DrMemberFunds>(fundsMap.values()));
                }
                if(buff.toString().length()>0){
                    drProductInvestDAO.updateStatusByIds("3",
                            buff.toString().substring(0, buff.lastIndexOf(",")).split(","));
                }
                //短信发送
                for (int i = 0; i < smsList.size(); i++) {
//                    sysMessageLogService.sendMsg(smsList.get(i));
                }
            }

        }catch (Exception e){
            logger.error("回款失败: "+e);
        }
        logger.info("回款结束......");
    }

    /**
     * 获取恒丰返回码描述
     *
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

    /**
     * 计算投资金额以及优惠券可获得收益
     *
     * @param deadline    产品期限
     * @param invest      投资记录
     * @param dayRate     计息天数 360/365
     * @param productType 产品类型 1=新手，2=安选，3=优选
     * @return balanceProfit 返还到账户余额的收益，interestProfit 利息收益
     * @throws Exception
     */
    public JSONObject getInvestProfit(Integer deadline, DrProductInvest invest,
                                      BigDecimal dayRate, Integer productType, BigDecimal rate) throws Exception {
        JSONObject obj = new JSONObject();
        // 返现金额
        BigDecimal balanceProfit = BigDecimal.ZERO;
        // 应得总利息
        BigDecimal interestProfit = BigDecimal.ZERO;
        // 贴息
        BigDecimal interestSubsidy = BigDecimal.ZERO;
        //加息券
        BigDecimal interestRateCoupon = BigDecimal.ZERO;
        //翻倍券
        BigDecimal interestDoubleCoupons = BigDecimal.ZERO;
        int interestSubsidyDays = 0;
        if (productType == Constants.TWO || productType == Constants.SIX) {
            // 贴息天数
            interestSubsidyDays = Integer.parseInt(Long.toString(
                    Utils.getQuot(Utils.format(new Date(), "yyyy-MM-dd"),
                            Utils.format(invest.getInvestTime(), "yyyy-MM-dd")))) - 1;
            deadline = deadline + interestSubsidyDays;
        }
        Integer day = deadline;
        if (productType == 2 || productType == 6) {
            day = deadline - interestSubsidyDays;
        }
        // 使用优惠券
        if (invest.getFid() != null) {
            DrMemberFavourable dmf = drMemberFavourableDAO.selectByPrimaryKey(invest.getFid());
            // 红包
            if (dmf.getType() == 1) {
                balanceProfit = dmf.getAmount();
                interestProfit = invest.getFactAmount().multiply(dayRate).multiply(
                        new BigDecimal(deadline)).setScale(2, BigDecimal.ROUND_FLOOR);
                interestSubsidy = invest.getFactAmount().multiply(dayRate).multiply(
                        new BigDecimal(interestSubsidyDays)).setScale(2, BigDecimal.ROUND_FLOOR);
                dmf.setProfitAmount(dmf.getAmount());
                // 加息券
            } else if (dmf.getType() == 2) {
                // 日息
                BigDecimal activityRate = Utils.nwdDivide(Utils.nwdDivide(dmf.getRaisedRates(), 100), 360);
                //加息券利息
                interestRateCoupon = invest.getFactAmount().multiply(
                        activityRate).multiply(new BigDecimal(day)).setScale(2, BigDecimal.ROUND_FLOOR);

                interestProfit = invest.getFactAmount().multiply(
                        activityRate.add(dayRate)).multiply(new BigDecimal(deadline)).setScale(2, BigDecimal.ROUND_FLOOR);
                interestSubsidy = invest.getFactAmount().multiply(
                        activityRate.add(dayRate)).multiply(new BigDecimal(interestSubsidyDays)).setScale(2, BigDecimal.ROUND_FLOOR);
                dmf.setProfitAmount(interestProfit.subtract(
                        invest.getFactAmount().multiply(dayRate).multiply(new BigDecimal(deadline)).setScale(2, BigDecimal.ROUND_FLOOR)));
                // 体验金
            } else if (dmf.getType() == 3) {
                interestProfit = dmf.getAmount().add(
                        invest.getFactAmount()).multiply(dayRate).multiply(
                                new BigDecimal(deadline)).setScale(2, BigDecimal.ROUND_FLOOR);
                interestSubsidy = dmf.getAmount().add(
                        invest.getFactAmount()).multiply(dayRate).multiply(
                                new BigDecimal(interestSubsidyDays)).setScale(2, BigDecimal.ROUND_FLOOR);
                dmf.setProfitAmount(interestProfit.subtract(
                        invest.getFactAmount().multiply(dayRate).multiply(
                                new BigDecimal(deadline)).setScale(2, BigDecimal.ROUND_FLOOR)));
                // 翻倍券
            } else if (dmf.getType() == 4) {
                // 针对基础利率翻倍
                // 基础日息
                BigDecimal dRate = Utils.nwdDivide(Utils.nwdDivide(rate, 100), 360);
                //翻倍券
                interestDoubleCoupons = invest.getFactAmount().multiply(dRate).multiply(
                        dmf.getMultiple().subtract(new BigDecimal(1))).multiply(
                                new BigDecimal(day).setScale(2, BigDecimal.ROUND_FLOOR));
                // 基础收益
                BigDecimal sourceProfit = invest.getFactAmount().multiply(dayRate).multiply(
                        new BigDecimal(deadline).setScale(2, BigDecimal.ROUND_FLOOR));
                // 基础日息*翻倍+其他活动日息
                dayRate = Utils.nwdBcadd(Utils.nwdBcsub(dayRate, dRate), Utils.nwdMultiply(dRate, dmf.getMultiple()));

                interestProfit = invest.getFactAmount().multiply(dayRate).multiply(
                        new BigDecimal(deadline)).setScale(2, BigDecimal.ROUND_FLOOR);
                interestSubsidy = invest.getFactAmount().multiply(dayRate).multiply(
                        new BigDecimal(interestSubsidyDays)).setScale(2, BigDecimal.ROUND_FLOOR);
                dmf.setProfitAmount(interestProfit.subtract(sourceProfit));
            }
            dmf.setStatus(1);
            drMemberFavourableDAO.updateByPrimaryKey(dmf);
        } else if (Utils.isObjectNotEmpty(invest.getExperience())) {
            Map<String, Object> map = new HashMap<>(16);
            map.put("experience", invest.getExperience());
            map.put("uid", invest.getUid());

            BigDecimal experienceAmount = drMemberFavourableDAO.getExperienceAmount(map);
            interestProfit = experienceAmount.multiply(dayRate).multiply(new BigDecimal(deadline)).setScale(2, BigDecimal.ROUND_FLOOR);
            map.put("deadline", deadline);
            map.put("dayRate", dayRate);
            drMemberFavourableDAO.updateByExperience(map);
        } else {
            interestProfit = invest.getFactAmount().multiply(dayRate).multiply(
                    new BigDecimal(deadline)).setScale(2, BigDecimal.ROUND_FLOOR);
            interestSubsidy = invest.getFactAmount().multiply(dayRate).multiply(
                    new BigDecimal(interestSubsidyDays)).setScale(2, BigDecimal.ROUND_FLOOR);
        }
        obj.put("balanceProfit", balanceProfit);
        obj.put("interestProfit", interestProfit);
        obj.put("interestSubsidy", interestSubsidy);
        //加息券利息
        obj.put("interestRateCoupon", interestRateCoupon);
        //翻倍券
        obj.put("interestDoubleCoupons", interestDoubleCoupons);
        return obj;
    }
}
