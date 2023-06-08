package cn.simplelife.trip.redis.service.impl;

import cn.simplelife.trip.domain.UserInfo;
import cn.simplelife.trip.redis.service.IUserInfoRedisService;
import cn.simplelife.trip.redis.utils.RedisKeys;
import cn.simplelife.trip.utils.Constants;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName UserInfoRedisServiceImpl
 * @Description
 * @Author simplelife
 * @Date 2023/6/4 17:12
 * @Version 1.0
 */
@Service
public class UserInfoRedisServiceImpl implements IUserInfoRedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void saveRegisterVerifyCode(String phone, String code) {
        String key = RedisKeys.REGISTER_VERIFY_CODE.join(phone);
        redisTemplate.opsForValue().set(key, code, Constants.REGISTER_VERIFY_CODE_VAI_TIME * 60L, TimeUnit.SECONDS);
    }

    @Override
    public String getVerifyCode(String phone) {
        String key = RedisKeys.REGISTER_VERIFY_CODE.join(phone);
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public String setToken(UserInfo currentUser) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        String key = RedisKeys.USER_LOGIN_TOKEN.join(token);
        String value = JSON.toJSONString(currentUser);
        redisTemplate.opsForValue().set(key, value, RedisKeys.USER_LOGIN_TOKEN.getTime(), TimeUnit.SECONDS);
        return token;
    }

    @Override
    public UserInfo getCurrentUser(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        String key = RedisKeys.USER_LOGIN_TOKEN.join(token);
        if (redisTemplate.hasKey(key)) {
            String userJson = redisTemplate.opsForValue().get(key);
            redisTemplate.expire(key, RedisKeys.USER_LOGIN_TOKEN.getTime(), TimeUnit.SECONDS);
            return JSON.parseObject(userJson, UserInfo.class);
        }
        return null;
    }
}
