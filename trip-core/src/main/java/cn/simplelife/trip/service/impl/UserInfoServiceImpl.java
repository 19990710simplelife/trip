package cn.simplelife.trip.service.impl;

import cn.simplelife.trip.domain.UserInfo;
import cn.simplelife.trip.mapper.UserInfoMapper;
import cn.simplelife.trip.redis.service.IUserInfoRedisService;
import cn.simplelife.trip.service.IUserInfoService;
import cn.simplelife.trip.utils.Constants;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @ClassName UserInfoServiceImpl
 * @Description
 * @Author simplelife
 * @Date 2023/6/4 11:17
 * @Version 1.0
 */
@Service
@Transactional
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    @Autowired
    private IUserInfoRedisService userInfoRedisService;

    @Override
    public boolean checkPhone(String phone) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getPhone, phone);
        UserInfo user = super.getOne(queryWrapper);
        return user != null;
    }

    @Override
    public void sendVerifyCode(String phone) {
        // 1 生成验证码
        String code = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        StringBuilder stringBuilder = new StringBuilder(80);
        stringBuilder.append("您注册的验证码为:").append(code).append(",请在").append(Constants.REGISTER_VERIFY_CODE_VAI_TIME * 60L).append("分钟内使用!");
        System.out.println(stringBuilder);
        userInfoRedisService.saveRegisterVerifyCode(phone, code);
    }
}
