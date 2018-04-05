package com.abner.codebase.reporting.config;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import com.abner.codebase.reporting.infra.Delimiter;
import com.abner.codebase.reporting.infra.FileDescriber;
import com.abner.codebase.reporting.infra.impl.DefaultFileDescriber;

public class FileConfiguration {
	private static final String JOINER = ".";
	private static final String FILE_PATH = "filePath";
	private static final String FILE_NAME_PATTERN = "fileNamePattern";
	private static final String DELIMITER = "delimiter";
	private static final String COLUMN_COUNT = "columnCount";
	
	public FileDescriber getFileDescriber(Class<?> clazz) {
		DefaultFileDescriber fileDescriberImpl = new DefaultFileDescriber();
		Properties properties = new Properties();
		try {
			properties.load(getClass().getResourceAsStream("/reporting.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String filePath = properties.getProperty(filePathKey(clazz));
		String fileNamePattern = properties.getProperty(fileNamePatternKey(clazz));
		String delimiter = properties.getProperty(delimiterKey(clazz));
		String columnCount = properties.getProperty(columnCountKey(clazz));
		
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath() + filePath + File.separator + fileNamePattern);
		
		fileDescriberImpl.setFile(new File(Thread.currentThread().getContextClassLoader().getResource("").getPath() + filePath + File.separator + fileNamePattern));
		fileDescriberImpl.setFilePath(filePath);
		fileDescriberImpl.setFileNamePattern(fileNamePattern);
		fileDescriberImpl.setDelimiter(Delimiter.parse(delimiter));
		fileDescriberImpl.setColumnCount(Integer.valueOf(columnCount));
		return fileDescriberImpl;
	}
	
	private String filePathKey(Class<?> clazz){
		return String.join(JOINER, clazz.getSimpleName(), FILE_PATH);
	}
	
	private String fileNamePatternKey(Class<?> clazz){
		return String.join(JOINER, clazz.getSimpleName(), FILE_NAME_PATTERN);
	}
	
	private String delimiterKey(Class<?> clazz){
		return String.join(JOINER, clazz.getSimpleName(), DELIMITER);
	}
	
	private String columnCountKey(Class<?> clazz){
		return String.join(JOINER, clazz.getSimpleName(), COLUMN_COUNT);
	}
	
	private String resolvedFilePath(String originalPath) {
		return null;
	}
}



