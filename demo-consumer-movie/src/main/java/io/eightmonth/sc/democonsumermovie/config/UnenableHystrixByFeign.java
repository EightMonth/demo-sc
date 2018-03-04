package io.eightmonth.sc.democonsumermovie.config;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * 通过配置类禁用hystrix，在使用feign的基础上
 */
public class UnenableHystrixByFeign {
    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder(){
        return Feign.builder();
    }
}
