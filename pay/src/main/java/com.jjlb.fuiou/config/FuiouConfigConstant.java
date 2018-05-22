package com.jjlb.fuiou.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * fuiou配置文件信息
 * Created by lslnx0307 on 2018/5/21.
 */
public class FuiouConfigConstant {

    /**
     * 商户代码
     */
    public static String MCHNT_CD;
    /**
     * 版本
     */
    public static String VER;
    /**
     * 登录id
     */
    public static String LOGIN_ID;
    /**
     * 商户异步地址
     */
    public static String OPENACCOUNT_BACK_NOTIFY_URL;
    /**
     * 商户同步通知地址
     */
    public static String OPENACCOUNT_PAGE_NOTIFY_URL;


    /**
     * 预授权接口地址（直连）
     */
    public static String PREAUTHURL;
    /**
     * 撤销预授权接口地址（直连）
     */
    public static String PREAUTHCANCELURL;
    /**
     * 个人用户自助开户注册（网页版）
     */
    public static String REGURL;
    /**
     *APP个人用户自助开户注册
     */
    public static String APPWEBREGURL;
    /**
     * APP免登签约
     */
    public static String APPSIGN_CARDURL;
    /**
     * 商户APP个人用户免登录快速充值
     */
    public static String APPQRECHARGEURL;
    /**
     * 商户APP个人用户免登录快捷充值
     */
    public static String APPQRECHARGE2URL;
    /**
     * 商户APP个人用户免登录提现
     */
    public static String APPWITHDRAWURL;
    /**
     * 用户密码修改重置免登陆接口(app版)
     */
    public static String APPRESETPASSWORDURL;
    /**
     * 商户P2P网站免登录网银充值（网页版）
     */
    public static String BRECHARGEURL;
    /**
     * 快捷充值短信直连
     */
    public static String MPAY_SENDSMSURL;
    /**
     *快捷充值
     */
    public static String MPAY_SENDPAYURL;
    /**
     * 冻结
     */
    public static String FREEZEURL;
    /**
     * 解冻
     */
    public static String UNFREEZEURL;
    /**
     * 充值提现查询接口
     */
    public static String QUERYCZTXURL;
    /**
     * 转账（商户与个人之）
     */
    public static String TRANSFERBMUURL;

    /**
     * 开户注册（直连）
     */
    public final static String REG = "000000";
    /**
     * 法人开户注册（直连）
     */
    public final static String ARTIFREG = "000001";
    /**
     * 预授权（直连）
     */
    public final static String PREAUTH = "000002";
    /**
     * 撤销预授权（直连）
     */
    public final static String PREAUTHCANCEL = "000003";
    /**
     * 转账（商户与个人之）（直连）
     */
    public final static String TRANSFERBMU = "000004";
    /**
     * 划拨(个人与个人之间)（直连）
     */
    public final static String TRANSFERBU = "000005";
    /**
     * 冻结（直连）
     */
    public final static String FREEZE = "000006";
    /**
     * 转账预冻结（直连）
     */
    public final static String TRANSFERBMUANDFREEZE = "000007";
    /**
     * 划拨预冻结（直连）
     */
    public final static String TRANSFERBUANDFREEZE = "000008";
    /**
     * 冻结到冻结接口（直连）
     */
    public final static String TRANSFERBUANDFREEZE2FREEZE = "000009";
    /**
     * 解冻（直连）
     */
    public final static String UNFREEZE = "000010";
    /**
     * 用户更换银行卡接口（直连）
     */
    public final static String USERCHANGECARD = "000011";
    /**
     * 个人用户自助开户注册（网页版）
     */
    public final static String WEBREG = "000012";
    /**
     * 法人用户自助开户注册（网页版）
     */
    public final static String WEBARTIFREG = "000013";
    /**
     * 商户P2P网站免登录快速充值（网页版）
     */
    public final static String QRECHARGE = "000014";
    /**
     * 商户P2P网站免登录网银充值（网页版）
     */
    public final static String BRECHARGE = "000015";
    /**
     * 商户P2P网站免登录提现接口（网页版）
     */
    public final static String WITHDRAW = "000016";
    /**
     * 用户密码修改重置免登陆接口(网页版)
     */
    public final static String RESETPASSWORD = "000017";
    /**
     * PC端个人用户免登录快捷充值(网页版)
     */
    public final static String PCQRECHARGE500405 = "000018";
    /**
     * APP个人用户自助开户注册
     */
    public final static String APPWEBREG = "000019";
    /**
     * APP免登签约
     */
    public final static String APPSIGN_CARD = "000020";
    /**
     * 商户APP个人用户免登录快速充值
     */
    public final static String APPQRECHARGE = "000021";
    /**
     * 商户APP个人用户免登录快捷充值
     */
    public final static String APPQRECHARGE2 = "000022";
    /**
     * 商户APP个人用户免登录提现
     */
    public final static String APPWITHDRAW = "000023";
    /**
     * 用户密码修改重置免登陆接口(app版)
     */
    public final static String APPRESETPASSWORD = "000024";
    /**
     * 快捷充值短信
     */
    public final static String MPAY_SENDSMS = "000025";
    /**
     * 快捷充值短信与充值
     */
    public final static String MPAY_SENDPAY = "000026";

    @Value("${mchntCd}")
    public void setMchntCd(String mchntCd) {
        MCHNT_CD = mchntCd;
    }

    @Value("${VER}")
    public void setVER(String VER) {
        FuiouConfigConstant.VER = VER;
    }

    @Value("${loginId}")
    public void setLoginId(String loginId) {
        LOGIN_ID = loginId;
    }

    @Value("${openaccountBackNotifyUrl}")
    public void setOpenaccountBackNotifyUrl(String openaccountBackNotifyUrl) {
        OPENACCOUNT_BACK_NOTIFY_URL = openaccountBackNotifyUrl;
    }

    @Value("${openaccountPageNotifyUrl}")
    public void setOpenaccountPageNotifyUrl(String openaccountPageNotifyUrl) {
        OPENACCOUNT_PAGE_NOTIFY_URL = openaccountPageNotifyUrl;
    }

    @Value("${PREAUTHURL}")
    public void setPREAUTHURL(String PREAUTHURL) {
        FuiouConfigConstant.PREAUTHURL = PREAUTHURL;
    }

    @Value("${PREAUTHCANCELURL}")
    public void setPREAUTHCANCELURL(String PREAUTHCANCELURL) {
        FuiouConfigConstant.PREAUTHCANCELURL = PREAUTHCANCELURL;
    }

    @Value("${REGURL}")
    public void setREGURL(String REGURL) {
        FuiouConfigConstant.REGURL = REGURL;
    }

    @Value("${APPWEBREGURL}")
    public void setAPPWEBREGURL(String APPWEBREGURL) {
        FuiouConfigConstant.APPWEBREGURL = APPWEBREGURL;
    }

    @Value("${appsignCardurl}")
    public void setAppsignCardurl(String appsignCardurl) {
        APPSIGN_CARDURL = appsignCardurl;
    }

    @Value("${APPQRECHARGEURL}")
    public void setAPPQRECHARGEURL(String APPQRECHARGEURL) {
        FuiouConfigConstant.APPQRECHARGEURL = APPQRECHARGEURL;
    }

    @Value("${APPQRECHARGE2URL}")
    public void setAPPQRECHARGE2URL(String APPQRECHARGE2URL) {
        FuiouConfigConstant.APPQRECHARGE2URL = APPQRECHARGE2URL;
    }

    @Value("${APPWITHDRAWURL}")
    public void setAPPWITHDRAWURL(String APPWITHDRAWURL) {
        FuiouConfigConstant.APPWITHDRAWURL = APPWITHDRAWURL;
    }

    @Value("${APPRESETPASSWORDURL}")
    public void setAPPRESETPASSWORDURL(String APPRESETPASSWORDURL) {
        FuiouConfigConstant.APPRESETPASSWORDURL = APPRESETPASSWORDURL;
    }

    @Value("${BRECHARGEURL}")
    public void setBRECHARGEURL(String BRECHARGEURL) {
        FuiouConfigConstant.BRECHARGEURL = BRECHARGEURL;
    }

    @Value("${mpaySendsmsurl}")
    public void setMpaySendsmsurl(String mpaySendsmsurl) {
        MPAY_SENDSMSURL = mpaySendsmsurl;
    }

    @Value("${mpaySendpayurl}")
    public void setMpaySendpayurl(String mpaySendpayurl) {
        MPAY_SENDPAYURL = mpaySendpayurl;
    }

    @Value("${FREEZEURL}")
    public static void setFREEZEURL(String FREEZEURL) {
        FuiouConfigConstant.FREEZEURL = FREEZEURL;
    }

    @Value("${UNFREEZEURL}")
    public static void setUNFREEZEURL(String UNFREEZEURL) {
        FuiouConfigConstant.UNFREEZEURL = UNFREEZEURL;
    }

    @Value("${QUERYCZTXURL}")
    public static void setQUERYCZTXURL(String QUERYCZTXURL) {
        FuiouConfigConstant.QUERYCZTXURL = QUERYCZTXURL;
    }

    @Value("${TRANSFERBMUURL}")
    public static void setTRANSFERBMUURL(String TRANSFERBMUURL) {
        FuiouConfigConstant.TRANSFERBMUURL = TRANSFERBMUURL;
    }
}
