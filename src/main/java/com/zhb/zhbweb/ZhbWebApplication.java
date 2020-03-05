package com.zhb.zhbweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.zhb.zhbweb.service")
@ComponentScan(basePackages = "com.zhb.zhbweb.mapper")
public class ZhbWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhbWebApplication.class, args);
	}

}
