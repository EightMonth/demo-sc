package io.eightmonth.sc.democonsumermovie.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.eightmonth.sc.democonsumermovie.entity.User;
import io.eightmonth.sc.democonsumermovie.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class HomeController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/")
    public String home(){
        return "Hello World";
    }

    @GetMapping("/user/{id}")
    @HystrixCommand(fallbackMethod = "fallback")
    public User getUserById(@PathVariable Long id){
        User user = restTemplate.getForObject("http://provider-user/"+id,User.class);
        return user;
    }

    @GetMapping("/feign/user/{id}")
    public User getUser(@PathVariable Long id){
        return userFeign.getUserById(id);
    }

    @GetMapping("/services")
    public List<URI> services(){
        List<ServiceInstance> list = discoveryClient.getInstances("STORES");
        if(Objects.nonNull(list) && list.size() > 0)
            return list.stream().map(ServiceInstance::getUri).collect(Collectors.toList());
        return null;
    }

    public User fallback(Long id){
        User user = new User();
        user.setId(-2L);
        user.setUsername("居居用户");
        return user;
    }
}
