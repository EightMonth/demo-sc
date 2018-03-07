package io.eightmonth.sc.democonsumermovie.feign;

import io.eightmonth.sc.democonsumermovie.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用consul时，feign跟hystrix整合使用不生效，暂时不知道为什么
 */
@FeignClient(name = "provider-user", fallback = UserFeign.UserFeignImpl.class)
public interface UserFeign {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@RequestParam("id") Long id);

    @Component
    class UserFeignImpl implements UserFeign{
        @Override
        public User getUserById(Long id) {
            User user = new User();
            user.setId(-1L);
            user.setUsername("默认用户");
            return user;
        }
    }
}
