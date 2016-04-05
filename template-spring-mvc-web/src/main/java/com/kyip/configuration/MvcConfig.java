package com.kyip.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.resource.CssLinkResourceTransformer;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableSpringDataWebSupport
@ComponentScan(basePackages = "com.kyip", excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class) , includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class),
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = ControllerAdvice.class) })
public class MvcConfig extends WebMvcConfigurerAdapter {


	private boolean isDevMode = true;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/").setCachePeriod(60 * 60 * 24 * 365)
		.resourceChain(!isDevMode)
		.addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
		.addTransformer(new CssLinkResourceTransformer());
	}

	@Bean
	public ViewResolver viewResolver() {
		FreeMarkerViewResolver viewResolve = new FreeMarkerViewResolver();
		viewResolve.setSuffix(".ftl");
		viewResolve.setContentType("text/html;charset=UTF-8");
		viewResolve.setCache(!isDevMode);
		return viewResolve;
	}

	@Bean(name = "localeResolver")
	public CookieLocaleResolver a() {
		CookieLocaleResolver cr = new CookieLocaleResolver();
		cr.setCookieName("clientlanguage");
		cr.setCookieMaxAge(94608000);
		cr.setDefaultLocale(Locale.US);
		return cr;
	}

	@Bean(name = "messageSource")
	public ResourceBundleMessageSource rbms() {
		ResourceBundleMessageSource rbms = new ResourceBundleMessageSource();
		rbms.setDefaultEncoding("UTF-8");
		rbms.setBasenames("message", "test");
		return rbms;
	}

	@Bean
	public FreeMarkerConfigurer freemarkerConfig() {
		FreeMarkerConfigurer config = new FreeMarkerConfigurer();
		config.setTemplateLoaderPath("/WEB-INF/views/");
		config.setDefaultEncoding("UTF-8");
		return config;
	}


}
