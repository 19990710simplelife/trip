package cn.simplelife.trip.controller;

import cn.simplelife.trip.domain.UserInfo;
import cn.simplelife.trip.exception.LogicException;
import cn.simplelife.trip.redis.service.IUserInfoRedisService;
import cn.simplelife.trip.service.IUserInfoService;
import cn.simplelife.trip.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private IUserInfoRedisService userInfoRedisService;

    @ApiOperation("获取当前登录的用户信息")
    @GetMapping("/detail")
    public JsonResult currentUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfo userInfo = userInfoRedisService.getCurrentUser(token);
        return JsonResult.success(userInfo);
    }

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

    @ApiOperation("用户注册")
    @PostMapping("/regist")
    public JsonResult regist(String phone, String nickname, String password, String rpassword, String verifyCode) {
        userInfoService.regist(phone, nickname, password, rpassword, verifyCode);
        return JsonResult.success();
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public JsonResult login(String username, String password) {
        UserInfo currentUser = userInfoService.login(username, password);
        // 创建token 缓存到redis
        String token = userInfoRedisService.setToken(currentUser);
        Map<String, Object> userLoginMap = new HashMap<>();
        userLoginMap.put("token", token);
        userLoginMap.put("user", currentUser);
        return JsonResult.success(userLoginMap);
    }
}
