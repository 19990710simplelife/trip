package cn.simplelife.trip.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName CoreConfig
 * @Description
 * @Author simplelife
 * @Date 2023/6/4 11:19
 * @Version 1.0
 */
@Configuration
// 从classpath中将配置文件加载到容器中
@PropertySource("classpath:core.properties")
@MapperScan(basePackages = "cn.simplelife.trip.mapper")
public class CoreConfig {

    // 分页配置
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(true);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}
