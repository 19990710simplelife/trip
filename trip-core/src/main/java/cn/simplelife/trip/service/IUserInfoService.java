package cn.simplelife.trip.service;

import cn.simplelife.trip.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @ClassName IUserInfoService
 * @Description
 * @Author simplelife
 * @Date 2023/6/4 11:17
 * @Version 1.0
 */

public interface IUserInfoService extends IService<UserInfo> {
    /**
     * 校验用户电话号码是否已经被注册
     *
     * @param phone 手机号
     * @return true 已注册 false 没有注册
     */
    boolean checkPhone(String phone);

    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     */
    String sendVerifyCode(String phone);

    /**
     * 用户注册
     *
     * @param phone 用户手机号
     * @param nickName 昵称
     * @param password 密码
     * @param rpassword 确认密码
     * @param verifyCode 验证码
     */
    void regist(String phone, String nickName, String password, String rpassword, String verifyCode);

    UserInfo login(String username, String password);
}
