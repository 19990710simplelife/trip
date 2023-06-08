package cn.simplelife.trip.service.impl;

import cn.simplelife.trip.domain.UserInfo;
import cn.simplelife.trip.exception.LogicException;
import cn.simplelife.trip.mapper.UserInfoMapper;
import cn.simplelife.trip.redis.service.IUserInfoRedisService;
import cn.simplelife.trip.service.IUserInfoService;
import cn.simplelife.trip.utils.AssertUtil;
import cn.simplelife.trip.utils.Constants;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    public String sendVerifyCode(String phone) {
        // 1 生成验证码
        String code = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        StringBuilder stringBuilder = new StringBuilder(80);
        stringBuilder.append("您注册的验证码为:").append(code).append(",请在").append(Constants.REGISTER_VERIFY_CODE_VAI_TIME * 60L).append("分钟内使用!");
        System.out.println(stringBuilder);
        userInfoRedisService.saveRegisterVerifyCode(phone, code);
        return code;
    }

    @Override
    public void regist(String phone, String nickname, String password, String rpassword, String verifyCode) {
        // 1、判断参数
        AssertUtil.hasLength(phone, "手机号不能为空！");
        AssertUtil.hasLength(nickname, "昵称不能为空！");
        AssertUtil.hasLength(password, "密码不能为空！");
        AssertUtil.hasLength(rpassword, "确认密码不能为空！");
        AssertUtil.isEquals(password, rpassword, "两次密码不一致！");
        AssertUtil.hasLength(verifyCode, "验证码不能为空！");
        // 2、判断手机是否已经被注册
        if (this.checkPhone(phone)) {
            throw new RuntimeException("手机号已经被注册了!");
        }
        // 3、判断验证码是否正确
        String code = userInfoRedisService.getVerifyCode(phone);
        if (!verifyCode.equalsIgnoreCase(code)) {
            throw new RuntimeException("验证码失效或验证码错误!");
        }
        // 4、用户注册
        UserInfo userInfo = new UserInfo();
        userInfo.setNickname(password);
        userInfo.setPhone(phone);
        userInfo.setHeadImgUrl("/images/default.jpg");
        userInfo.setState(UserInfo.STATE_NORMAL);
        super.save(userInfo);
    }

    @Override
    public UserInfo login(String username, String password) {
        // 参数判断
        AssertUtil.hasLength(username, "用户名不能为空！");
        AssertUtil.hasLength(password, "密码不能为空！");
        // 查询数据库
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getPhone, username);
        queryWrapper.eq(UserInfo::getPassword, password);
        UserInfo info = super.getOne(queryWrapper);
        UserInfo userInfo = new UserInfo();
        if (info != null) {
            userInfo.setId(info.getId());
            userInfo.setNickname(info.getNickname());
            userInfo.setPhone(info.getPhone());
            userInfo.setEmail(info.getEmail());
            userInfo.setGender(info.getGender());
            userInfo.setCity(info.getCity());
            userInfo.setHeadImgUrl(info.getHeadImgUrl());
            userInfo.setInfo(info.getInfo());
            userInfo.setState(info.getState());
        } else {
            throw new LogicException("用户名或密码错误！");
        }
        return userInfo;
    }
}
