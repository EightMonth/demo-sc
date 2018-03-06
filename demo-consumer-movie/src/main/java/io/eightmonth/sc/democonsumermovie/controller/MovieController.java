package io.eightmonth.sc.democonsumermovie.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.eightmonth.sc.democonsumermovie.entity.User;
import io.eightmonth.sc.democonsumermovie.feign.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class MovieController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    @Value("${user.userServiceUrl}")
    private String userServiceUrl;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private EurekaClient eurekaClient;
    @Autowired
    private UserFeignClient userFeignClient;

    // 访问sidecar整合的非JVM服务
    @GetMapping("/test")
    public String test(){
        return restTemplate.getForObject("http://sidecar/", String.class);
    }

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

    @HystrixCommand(fallbackMethod = "findByIdFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
    },threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "1"),
            @HystrixProperty(name = "maxQueueSize", value = "10")
    })
    /**
     * 访问服务方式一：使用restTemplate
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id){
        return restTemplate.getForObject(userServiceUrl + id, User.class);
        // 接入eureka之后的服务调用
//        return restTemplate.getForObject("http://provider-user/" + id, User.class);
    }

    public User findByIdFallback(Long id){
        User user = new User();
        user.setId(-1L);
        user.setName("默认用户");
        return user;
    }

    /**
     * 访问服务方式二：使用ribbon,具备LB
     */
    @GetMapping("/log-instance")
    public void logUserInstance(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("provider-user");
        // 打印当前选择的节点
        LOGGER.info("{}:{}:{}", serviceInstance.getServiceId(),serviceInstance.getHost(),serviceInstance.getPort());
    }

    @GetMapping("/user-InstanceStatus")
    public InstanceInfo.InstanceStatus showInfo(){
        return eurekaClient.getInstanceRemoteStatus();
    }

}
