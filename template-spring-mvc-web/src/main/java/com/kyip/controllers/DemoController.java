package com.kyip.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kyip.dto.User;
import com.kyip.service.DemoService;
import com.kyip.utils.CsvContentBuilder;
import com.kyip.utils.FileUtil;
import com.kyip.utils.PdfFileBuilder;


@Controller
@RequestMapping(value = "/demo")
public class DemoController {

	@Autowired
	DemoService demoService;

	@Autowired
	protected MessageSource messageSource;

	@Resource
	FileUtil fileUtil;

	@Resource
	PdfFileBuilder pdfFileBuilder;

	@Resource
	CsvContentBuilder csvContentBuilder;

	private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Locale locale, Model model, HttpServletRequest request) {
		ResourceBundle bundle = ResourceBundle.getBundle("message", Locale.TAIWAN);
		String test = bundle.getString("title.login");
		logger.info("test RB1 : {}", test);

		String msg = messageSource.getMessage("title.login", null, Locale.TAIWAN);
		logger.info("test RB2 : {}", msg);
		return "Authentication/Login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String home(Locale locale, Model model, HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return "Authentication/Login";
		}

		if (username.equals("123") && password.equals("123")) {
			model.addAttribute("users", demoService.generateStubUserList());
			return "Authentication/Landing";
		}

		return "Authentication/Login";
	}

	@RequestMapping(value = "/downloadUser", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> downloadCreditNote(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uIds = request.getParameter("userIds").replace("[", "").replace("]", "").replaceAll("\"", ""); // remove [] and ""
		if (StringUtils.isBlank(uIds)) {
			logger.trace("No credit note id is requested, no action to download");
			return null;
		}

		logger.info("preparing to zip/download user pdf in [IDs:{}]", uIds);
		String[] temp = uIds.split(",");
		List<String> userIds = Arrays.asList(temp);

		// create response using byte array for only 1 file
		if (userIds.size() == 1) {
			String filename = userIds.get(0)+ ".pdf";
			String filePath = System.getProperty("java.io.tmpdir") + File.separator + filename;
			pdfFileBuilder.build(filePath);
			Path path = Paths.get(filePath);
			byte[] userPdfFile = Files.readAllBytes(path);
			if (userPdfFile == null || userPdfFile.length == 0) {
				logger.error("byte array is empty for [FILENAME:{}]", filename);
			}
			return fileUtil.generateResponseEntity("application/pdf", filename, userPdfFile);
		}

		return demoService.genUserFileZip(userIds);
	}


	@RequestMapping(value = "/downloadAllUser", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> downloadAllUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<User> users = demoService.generateStubUserList();
		List<String> userIds = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(users)) {
			for (User user : users) {
				userIds.add(user.getUserId().toString());
			}
		}
		return demoService.genUserFileZip(userIds);
	}

	@RequestMapping(value = "/downloadCsv", method = RequestMethod.GET)
	@ResponseBody
	public void exp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<User> users = demoService.generateStubUserList();
		StringBuilder builder = csvContentBuilder.buildUserCsvContent(users);
		fileUtil.exportCsv(response, builder, "Users.csv");
	}

}
