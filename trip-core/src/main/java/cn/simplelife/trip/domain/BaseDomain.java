package cn.simplelife.trip.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName BaseDomain
 * @Description
 * @Author simplelife
 * @Date 2023/6/4 11:14
 * @Version 1.0
 */
@Setter
@Getter
public class BaseDomain implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
}
