package com.linkedboard; 

import org.apache.catalina.LifecycleException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class LinkedBoardApplication {
	
//	@Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(ServletContext.class);
//    }

	public static void main(String[] args) throws LifecycleException {
		SpringApplication.run(LinkedBoardApplication.class, args);
	}
}