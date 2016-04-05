package com.kyip.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/Error")
@Controller
public class ErrorController {
	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String error404(Model model, HttpServletRequest request) {
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		logger.warn("error 404 is triggered, user request [REQUEST_URI:{}] not found, redirect to error page", requestUri);
		model.addAttribute("message", "Page not found");
		return "error";
	}

	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public String error500(Model model, HttpServletRequest request) {
		logger.warn("error 500 is triggered, redirect to error page");
		model.addAttribute("message", "Internal server error");
		return "error";
	}

}
