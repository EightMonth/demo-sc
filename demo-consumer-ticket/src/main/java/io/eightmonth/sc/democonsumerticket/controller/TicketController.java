package io.eightmonth.sc.democonsumerticket.controller;

import io.eightmonth.sc.democonsumerticket.entity.User;
import io.eightmonth.sc.democonsumerticket.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * 访问服务方式三：使用Feign，具备ribbon特性
     * 推荐使用
     * 推荐理由：花样多
     * @param id
     * @return
     */
    @GetMapping("/feign/user/{id}")
    public User findUserById(@PathVariable Long id){
        return userFeignClient.findById(id);
    }

}
