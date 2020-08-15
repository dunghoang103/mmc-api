package com.mmc.Tuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mmc.Tuan")
public class SpringApplicationGG{

	public static void main(String[] args) {
		SpringApplication.run(SpringApplicationGG.class, args);
	}

}
