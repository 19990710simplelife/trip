package cn.simplelife.trip.redis.service;

import cn.simplelife.trip.domain.UserInfo;

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

    /**
     * 获取注册验证码
     *
     * @param phone 手机号
     * @return 注册验证码
     */
    String getVerifyCode(String phone);

    /**
     * 创建token并缓存到redis
     *
     * @param currentUser 用户对象
     * @return token
     */
    String setToken(UserInfo currentUser);

    /**
     * 从缓存中获取当前登录用户信息
     *
     * @param token 令牌信息
     * @return 用户对象
     */
    UserInfo getCurrentUser(String token);
}
