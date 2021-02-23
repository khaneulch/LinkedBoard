package com.linkedboard;

import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedboard.utils.GetPropertyUtils;

@Configuration
@EnableWebMvc
@ComponentScan(
		basePackages="com.linkedboard",
		includeFilters={
				@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Controller.class)},
		excludeFilters={
				@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Service.class)
				, @ComponentScan.Filter(type=FilterType.ANNOTATION, value=Repository.class)
				, @ComponentScan.Filter(type=FilterType.ANNOTATION, value=Configuration.class)
		}
)
@PropertySource("classpath:config.properties")
@EnableScheduling
public class ServletContext implements WebMvcConfigurer {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(jsonConverter());
	}

	@Bean
	public MappingJackson2HttpMessageConverter jsonConverter(){
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
									.serializationInclusion( Include.NON_NULL).build();
		jsonConverter.setObjectMapper(objectMapper);
	    return jsonConverter;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.beanName();
		registry.jsp("/WEB-INF/views/", ".jsp");
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
		StandardServletMultipartResolver commonsMultipartResolver = new StandardServletMultipartResolver();
	    return new StandardServletMultipartResolver();
	}
}
