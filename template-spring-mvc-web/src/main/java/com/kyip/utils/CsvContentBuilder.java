package com.kyip.utils;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kyip.dto.User;

@Component
public class CsvContentBuilder {
	private static final Logger logger = LoggerFactory.getLogger(CsvContentBuilder.class);

	private static final String DELIMETER = ",";
	private static final String NEW_LINE_DELIMETER = "\n";
	private static final String NOT_AVAILABLE = "N/A";

	public StringBuilder buildUserCsvContent(List<User> users) {
		logger.trace("buildUserCsvContent start");
		String fileHeader = "Name,Company,Date of Birth";

		StringBuilder writer = new StringBuilder();
		writer.append(fileHeader);
		writer.append(NEW_LINE_DELIMETER);

		if (CollectionUtils.isEmpty(users)) {
			logger.trace("No data for invoice csv");
			return writer;
		}
		for (User user : users) {
			writer.append(user.getName()).append(DELIMETER);
			writer.append(StringUtils.isBlank(user.getCompany())? NOT_AVAILABLE : user.getCompany()).append(DELIMETER);
			writer.append(user.getDob()).append(DELIMETER);

			writer.append(NEW_LINE_DELIMETER);
		}

		logger.trace("buildBuyerInvoiceCsvContent end");
		return writer;
	}

}
