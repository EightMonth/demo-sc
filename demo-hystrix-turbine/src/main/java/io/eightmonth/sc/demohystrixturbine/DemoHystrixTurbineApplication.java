package io.eightmonth.sc.demohystrixturbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
public class DemoHystrixTurbineApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoHystrixTurbineApplication.class, args);
	}
}
