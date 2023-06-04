package cn.simplelife.trip.redis.service.impl;

import cn.simplelife.trip.redis.service.IUserInfoRedisService;
import cn.simplelife.trip.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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
        String key = Constants.VERIFY_CODE + phone;
        redisTemplate.opsForValue().set(key, code, Constants.REGISTER_VERIFY_CODE_VAI_TIME * 60L, TimeUnit.SECONDS);
    }
}
