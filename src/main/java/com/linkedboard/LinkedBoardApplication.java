package com.linkedboard; 

import org.apache.catalina.LifecycleException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/config.properties")
@SpringBootApplication
public class LinkedBoardApplication {

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(LinkedBoardApplication.class);
//	}
  
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {SecurityConfig.class};
    }

	public static void main(String[] args) throws LifecycleException {
		SpringApplication.run(LinkedBoardApplication.class, args);
	}
}
