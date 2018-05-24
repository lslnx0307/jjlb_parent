package com.jjlb.common;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    /**
     * 后台登陆用户默认密码
     */
    public static final String SYS_USER_DEFAULT_PASSWORD = "123456";

    /**
     * 默认分页
     */
    public final static Integer DEFAULT_PAGE_NUM = 1;

    /**
     * 默认分页
     */
    public final static Integer DEFAULT_PAGE_SIZE = 10;


    /**
     * 收入/支出
     */
    public static final String DICTIONARY_BALANCETYPE = "balanceType";

    /**
     * 交易类型-平台收支
     */
    public static final String DICTIONARY_COMPANYFUNDS = "companyfunds";

    /**
     * 交易类型-用户收支
     */
    public static final String DICTIONARY_FUNDTYPE = "fundType";

    /**
     * 充值渠道
     */
    public static final String DICTIONARY_CHANNEL = "channel";

    /**
     * 冲值状态
     */
    public static final String DICTIONARY_CRUSHSTATUS = "crushstatus";

    /**
     * 冲值类型
     */
    public static final String DICTIONARY_PAYTYPE = "payType";

    /**
     * 提现状态
     */
    public static final String DICTIONARY_CARRYSTATUS = "carrystatus";

    /**
     * 银行渠道
     */
    public static final String DICTIONARY_BANKCHANNEL = "bankChannel";

    /**
     * 银行第三方支持分类
     */
    public static final String DICTIONARY_BANKSUPPORTTYPE = "bankSupportType";

    /**
     * 短信通道
     */
    public static final String DICTIONARY_SMSCHANEL = "smsChanel";

    /**
     * 资金类型
     */
    public static final String DICTIONARY_TRADETYPE = "tradeType";

    /**
     * 资金记录类型
     */
    public static final String DICTIONARY_FUND_RECORD_TYPE = "recordStatus";

    /**
     * 加载数据字典
     */
    public static Map<String, Map<Integer, String>> dictionaryMap = new HashMap<String, Map<Integer, String>>();

    /**
     * 加载银行字典
     */
    public static Map<String, String> bankMap = new HashMap<String, String>();

    /**
     * 加载银行城市字典
     */
    public static Map<String, String> bankCityMap = new HashMap<String, String>();

    /**
     * 加载银行地区字典
     */
    public static Map<String, String> bankAreaMap = new HashMap<String, String>();

    /**
     * JWT盐(翡翠岛MD5)
     */
    public static final String JWT_SECRET = "19a27a26e5de584b5e8098e4af45644d";

    public static final String JWT_ID = "jwt";

    public static final long JWT_TTL = 24 * 60 * 60 * 1000 * 7;

    public static final long JWT_TOKEN_TTL = 30 * 60 * 1000;


    public static final String APPLICATION_JSON = "application/json";

    public static final String APPLICATION_NAME = "翡翠岛";

    /**
     * redis 短信保存有效期
     */
    public static int REDIS_SMS_CACHE_PERIOD = 180;

    /**
     * redis token保存有效期
     */
    public static int REDIS_TOKEN_CACHE_PERIOD = 1800;

    /**
     * 短信有效期
     */
    public static int SMS_PERIOD = 180;

    /**
     * 每个手机号每天限制次数
     */
    public static int SMS_DAY_LIMIT = 10;

    /**
     * 每个手机号每种类型发送的一次验证码每天校验失败限制次数
     */
    public static int SMS_TYPE_FAILURE_LIMIT = 5;

    /**
     * 交易密码可尝试次数
     */
    public static int TPASSWORD_TRY_NUM = 5;

    /**
     * 交易密码尝试次数有效期2小时
     */
    public static int TPASSWORD_TRY_NUM_PERIOD = 7200;

    /**
     * 默认ip地址
     */
    public static String DEFAULT_IPADDR = "127.0.0.1";

    /**
     * 验证码可尝试次数
     */
    public static int MSGCODE_MAX_TRY_NUM = 3;

    /**
     * 登录密码可尝试次数
     */
    public static int LOGIN_MAX_TRY_NUM = 5;

    /**
     * 手续费金额
     */
    public static int WITHDRAWAL_POUNDAGE = 3;

    /**
     * redis 短信保存有效期
     */
    public static int REDIS_SMS_INTERVAL_PERIOD = 60;

    /**
     * 默认充值银行机构代码
     */
    public static String RECHARGE_PAY_TYPE = "B2B";

    /**
     * 常量0
     */
    public final static int ZERO = 0;
    /**
     * 常量1
     */
    public final static int ONE = 1;
    /**
     * 常量2
     */
    public final static int TWO = 2;
    /**
     * 常量3
     */
    public final static int THREE = 3;
    /**
     * 常量4
     */
    public final static int FORE = 4;
    /**
     * 常量5
     */
    public final static int FIVE = 5;
    /**
     * 常量6
     */
    public final static int SIX = 6;
    /**
     * 常量7
     */
    public final static int SEVEN = 7;
    /**
     * 常量8
     */
    public final static int EIGHT = 8;
    /**
     * 常量9
     */
    public final static int NINE = 9;

    /**
     * fuiou请求的成功的返回码
     */
    public final static String FUIOU_SUCCESS_CODE = "0000";

}