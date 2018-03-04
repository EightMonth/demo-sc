package io.eightmonth.sc.democonsumermovie.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@RibbonClient(name = "provider-user", configuration = RibbonConfiguration.class)
public class TestConfiguration {
    /**
     * 此类与RibbonConfiguration联合生效。
     * 等同配置
     * 在application.yml输入
     * provider-user:
     *   ribbon:
     *     NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
     */
}
