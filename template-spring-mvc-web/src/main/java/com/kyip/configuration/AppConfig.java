package com.kyip.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@ComponentScan(basePackages="com.kyip",
excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class),
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = ControllerAdvice.class),
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)})
public class AppConfig {

	@Bean
	public MultipartResolver filterMultipartResolver() {
		return new CommonsMultipartResolver();
	}

}
