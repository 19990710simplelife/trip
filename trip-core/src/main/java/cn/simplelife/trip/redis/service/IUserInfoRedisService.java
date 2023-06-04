package cn.simplelife.trip.redis.service;

/**
 * @ClassName IUserInfoRedisService
 * @Description
 * @Author simplelife
 * @Date 2023/6/4 17:12
 * @Version 1.0
 */

public interface IUserInfoRedisService {
    /**
     * 缓存注册验证码
     *
     * @param phone 手机号
     * @param code  验证码
     */
    void saveRegisterVerifyCode(String phone, String code);
}
