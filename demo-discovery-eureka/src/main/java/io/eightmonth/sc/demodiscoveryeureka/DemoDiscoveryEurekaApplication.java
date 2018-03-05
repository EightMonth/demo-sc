package io.eightmonth.sc.demodiscoveryeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableEurekaServer
public class DemoDiscoveryEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoDiscoveryEurekaApplication.class, args);
	}
}
