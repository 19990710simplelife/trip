package cn.simplelife.trip.service.impl;

import cn.simplelife.trip.domain.UserInfo;
import cn.simplelife.trip.mapper.UserInfoMapper;
import cn.simplelife.trip.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
