package cn.simplelife.trip.utils;

/**
 * 系统常量
 */
public class Constants {
    //用户注册，验证码有效时间
    public static final int REGISTER_VERIFY_CODE_VAI_TIME = 5;  //单位分
    //用户token有效时间
    public static final int USER_INFO_TOKEN_VAI_TIME = 30;  //单位分
    // 用户token前缀
    public static final String USER_LOGIN_TOKEN_PREFIX = "user_token";
    // 注册验证码前缀
    public static final String VERIFY_CODE = "register_verify_code";

    public String join() {
        return ":";
    }
}
