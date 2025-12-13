package com.example.Secureservice;

import com.example.Secureservice.config.AppProperties;
import com.example.Secureservice.config.KeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class, KeyProperties.class})
public class SecureserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureserviceApplication.class, args);
	}

}
