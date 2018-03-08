package io.eightmonth.sc.demosidecar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableSidecar
public class DemoSidecarApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoSidecarApplication.class, args);
	}
}
