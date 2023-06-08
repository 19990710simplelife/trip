package cn.simplelife.trip.redis.utils;

import cn.simplelife.trip.utils.Constants;
import lombok.Getter;

/**
 * @ClassName RedisKeys
 * @Description
 * @Author simplelife
 * @Date 2023/6/4 17:11
 * @Version 1.0
 */
@Getter
public enum RedisKeys {
    // 用户注册码实例
    REGISTER_VERIFY_CODE(Constants.VERIFY_CODE, Constants.REGISTER_VERIFY_CODE_VAI_TIME * 60L),
    // Token实例
    USER_LOGIN_TOKEN(Constants.USER_LOGIN_TOKEN_PREFIX, Constants.USER_INFO_TOKEN_VAI_TIME * 60L);
    private String prefix;
    private Long time;

    RedisKeys(String prefix, Long time) {
        this.prefix = prefix;
        this.time = time;
    }

    public String join(String... values) {
        StringBuilder stringBuilder = new StringBuilder(80);
        stringBuilder.append(prefix);
        for (String value : values) {
            stringBuilder.append(":").append(value);
        }
        return stringBuilder.toString();
    }
}
