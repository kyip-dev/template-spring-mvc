package com.kyip.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kyip.dto.User;
import com.kyip.utils.FileUtil;
import com.kyip.utils.PdfFileBuilder;

/**
 * TODO: move service to service project is better?
 * @author kyip
 *
 */
@Service
public class DemoService {
	private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

	@Resource
	FileUtil fileUtil;

	@Resource
	PdfFileBuilder pdfFileBuilder;

	private User generateStubUser(Long userId, String name, String company, LocalDate dob) {
		User user= new User();
		user.setUserId(userId);
		user.setName(name);
		user.setCompany(company);
		user.setDob(dob);
		return user;
	}

	public List<User> generateStubUserList() {
		List<User> users = new ArrayList<>();
		users.add(generateStubUser(1L, "userA", "company abc", LocalDate.of(2000, 1, 1)));
		users.add(generateStubUser(2L, "userB", "company abc", LocalDate.of(1980, 5, 3)));
		users.add(generateStubUser(3L, "userc", "company xyz", LocalDate.of(1999, 9, 5)));
		users.add(generateStubUser(4L, "userd", "", LocalDate.of(2001, 2, 3)));
		return users;
	}

	public ResponseEntity<byte[]> genUserFileZip(List<String> userIds) throws IOException {
		File zipFile = null;
		try {
			zipFile = File.createTempFile("Users", ".ZIP");
			FileOutputStream outputStream = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(outputStream);
			for (String userId : userIds) {

				String filename = userId + ".pdf";
				String filePath = System.getProperty("java.io.tmpdir") + File.separator + filename;
				pdfFileBuilder.build(filePath);
				Path path = Paths.get(filePath);
				byte[] creditNote = Files.readAllBytes(path);

				if (creditNote == null || creditNote.length == 0) {
					// no error handling for this, as credit note file not exist should not happen in production
					logger.error("byte array is empty for [FILENAME:{}], skip this file to zip", filename);
					continue;
				}

				File tempFile = null;
				try {
					tempFile = fileUtil.generateTempFile(creditNote, filename, "pdf");
					fileUtil.addToZipFile(tempFile, filename, zos);
				} finally {
					if (tempFile != null) {
						tempFile.delete();
					}
				}
			}
			zos.flush();
			zos.close();
			outputStream.flush();
			outputStream.close();
			byte[] data = Files.readAllBytes(zipFile.toPath());
			return fileUtil.generateResponseEntity("application/zip", "users.zip", data);
		} finally {
			if (zipFile != null) {
				zipFile.delete();
			}
		}

	}
}
