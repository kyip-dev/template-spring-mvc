package com.kyip.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@ControllerAdvice
public class ResourceUrlAdvice {

	@Autowired
	ResourceUrlProvider resourceUrlProvider;

	@ModelAttribute("resourceUrl")
	public ResourceUrlProvider resourceUrl() {
		return this.resourceUrlProvider;
	}
}
