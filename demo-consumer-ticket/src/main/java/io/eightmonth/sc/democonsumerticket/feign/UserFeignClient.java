package io.eightmonth.sc.democonsumerticket.feign;

import feign.hystrix.FallbackFactory;
import io.eightmonth.sc.democonsumerticket.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 这里需要注意，当有fallback属性和fallbackFactory属性同时存在，fallbackFactory属性不生效，只生效fallback属性
 * hystrix的方法会包裹feign接口的所有方法，若禁用可通过配置类,可查看 UnenableHystrixByFeign
 * 若全局禁用， 在application.yml 配置 feign.hystrix.enabled = false
 *
 */
@FeignClient(name = "provider-user",
        fallback = UserFeignClient.FeignClientFallback.class,
        fallbackFactory = UserFeignClient.FeignClientFallbackFactory.class)
public interface UserFeignClient {
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User findById(@PathVariable("id") Long id);

    // 多参数feign请求
//    @RequestMapping(value = "/get", method = RequestMethod.GET)
//    public User get1(@RequestParam("id") Long id, @RequestParam("username") String username);
//
//    @RequestMapping(value = "/get2", method = RequestMethod.GET)
//    public User get2(@RequestParam Map<String, Object> param);
//
//    // feign post 请求方式
//    @RequestMapping(value = "/post", method = RequestMethod.POST)
//    public User post(@RequestBody User user);

    /**
     * feign请求时回退入口
     */
    @Component
    class FeignClientFallback implements UserFeignClient {
        @Override
        public User findById(Long id) {
            User user = new User();
            user.setId(-2L);
            user.setUsername("默认用户");
            return user;

        }
    }

    /**
     * fallback时记录日志
     * --------------------------
     * hystrix是允许出现重复的FallbackFactory
     */
    @Component
    class FeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {
        private static final Logger LOGGER = LoggerFactory.getLogger(FeignClientFallbackFactory.class);
        @Override
        public UserFeignClient create(Throwable cause) {
            return (id) -> {
                // 日志最好放在各个fallback方法中， 而不要直接放在create方法中。
                // 否则在引用启动时，就会打印该日志
                LOGGER.info("fallback; reason was:", cause);
                User user = new User();
                user.setId(-2L);
                user.setUsername("默认用户");
                return user;
            };
        }
    }
}
