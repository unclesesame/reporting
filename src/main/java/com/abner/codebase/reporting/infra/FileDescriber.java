package com.abner.codebase.reporting.infra;

import java.io.File;
import java.util.Collection;

public interface FileDescriber {
	String getFilePath();
	
	String getFileName();
	
	File getFile();
	
	Collection<File> getFiles();
	
	Delimiter getDelimiter();
	
	Integer getColumnCount();
	
	String getFileNamePattern();
}
