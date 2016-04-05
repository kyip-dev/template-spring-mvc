package com.kyip.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public ResponseEntity<byte[]> generateResponseEntity(String contentType, String fileName, byte[] fileData) {
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		headers.setContentType(MediaType.parseMediaType(contentType));

		headers.setContentDispositionFormData(fileName, fileName);
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(fileData, headers, HttpStatus.OK);
		return response;
	}

	public File generateTempFile(byte[] fileByteArray, String tempFilename, String tempFileMediaType) throws IOException {
		File tmpFile = null;
		FileOutputStream outputStream = null;

		try {
			tmpFile = File.createTempFile(tempFilename, "." + tempFileMediaType);
			outputStream = new FileOutputStream(tmpFile);
			outputStream.write(fileByteArray);
		} finally {
			if (outputStream != null) {
				outputStream.flush();
				outputStream.close();
			}
		}
		return tmpFile;
	}

	/**
	 * Add files to zip file
	 *
	 * @param file
	 *            the file to zip
	 * @param fileName
	 *            filename displayed inside zip file
	 * @param zos
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void addToZipFile(File file, String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			ZipEntry zipEntry = new ZipEntry(fileName);
			zos.putNextEntry(zipEntry);

			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zos.write(bytes, 0, length);
			}
		} finally {
			if (zos != null) {
				zos.closeEntry();
			}
			if (fis != null) {
				fis.close();
			}
		}
	}

	/**
	 * Export csv with different writer content
	 *
	 * @param response
	 * @param fileHeader
	 * @param writer
	 */
	public void exportCsv(HttpServletResponse response, StringBuilder writer, String fileName) {
		response.setContentType("text/csv; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		try {
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(writer.toString().getBytes());
			outputStream.flush();
		} catch (Exception e) {
			logger.error("Unknown Error", e);
		}
	}

}
