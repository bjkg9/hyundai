package com.Hyundai.Sso;

import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HyundaiSsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HyundaiSsoApplication.class, args);
	}

}
