package io.eightmonth.sc.demozipkinserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class DemoZipkinServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoZipkinServerApplication.class, args);
	}
}
