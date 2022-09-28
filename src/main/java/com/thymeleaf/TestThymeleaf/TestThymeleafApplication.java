package com.thymeleaf.TestThymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class TestThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestThymeleafApplication.class, args);
		System.out.println("Thymeleaf loaded !");
	}
}
