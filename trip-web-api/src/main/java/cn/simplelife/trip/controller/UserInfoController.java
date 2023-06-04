package cn.simplelife.trip.controller;

import cn.simplelife.trip.domain.UserInfo;
import cn.simplelife.trip.service.IUserInfoService;
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
@RestController
@RequestMapping("/users")
public class UserInfoController {

    @Autowired
    private IUserInfoService userInfoService;

    @GetMapping("/detail")
    public UserInfo detail(Long id) {
        return userInfoService.getById(id);
    }
}
