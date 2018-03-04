package io.eightmonth.sc.democonsumermovie.config;

import feign.Logger;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {
    // 可以在这里进行feign 的自定义配置，然后在feignClient里面引用这个配置类达到自定义feign配置的效果
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
