package com.kyip.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
@RequestMapping(value = "/ChangeLocale")
public class ChangeLocaleController {

	private static final Logger logger = LoggerFactory.getLogger(ChangeLocaleController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changeLanguage(@RequestParam String locale, HttpServletRequest request, HttpServletResponse response) {
		try {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			if (localeResolver == null) {
				throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
			}

			LocaleEditor localeEditor = new LocaleEditor();
			localeEditor.setAsText(locale);
			localeResolver.setLocale(request, response, (Locale) localeEditor.getValue());
			logger.info("[LOCALE:{}] is changed", locale);
		} catch (Exception ex) {
			throw new InternalError("Fail to change [LOCALE:{"+locale+"}]");
		}
	}

}
