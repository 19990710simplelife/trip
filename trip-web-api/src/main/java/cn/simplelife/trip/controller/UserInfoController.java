package cn.simplelife.trip.controller;

import cn.simplelife.trip.domain.UserInfo;
import cn.simplelife.trip.service.IUserInfoService;
import cn.simplelife.trip.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserInfoController
 * @Description
 * @Author simplelife
 * @Date 2023/6/4 11:31
 * @Version 1.0
 */
@Api(value = "用户相关模块", description = "用户资源控制器")
@RestController
@RequestMapping("/users")
public class UserInfoController {

    @Autowired
    private IUserInfoService userInfoService;

    @GetMapping("/detail")
    public UserInfo detail(Long id) {
        return userInfoService.getById(id);
    }

    @ApiOperation("校验用户手机号是否注册")
    @GetMapping("/checkPhone")
    public boolean checkPhone(String phone) {
        return userInfoService.checkPhone(phone);
    }

    @ApiOperation("发送短信验证码")
    @GetMapping("/sendVerifyCode")
    public JsonResult sendVerifyCode(String phone) {
        return JsonResult.success(userInfoService.sendVerifyCode(phone));
    }
}
