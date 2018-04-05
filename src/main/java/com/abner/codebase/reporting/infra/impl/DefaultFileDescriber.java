package com.abner.codebase.reporting.infra.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import com.abner.codebase.reporting.infra.Delimiter;
import com.abner.codebase.reporting.infra.FileDescriber;
import com.sun.org.glassfish.external.arc.Stability;

public class DefaultFileDescriber implements FileDescriber{

	/*public static FileDescriberBuilder getBuilder() {
		return new FileDescriberBuilder();
	}
	
	public static class FileDescriberBuilder {
		private String fileNamePattern;
		private String fileName;
		private Delimiter delimiter;
		private String filePath;
		private Integer columnCount;
		private Collection<File> files = new ArrayList<>();
		
		public FileDescriberBuilder fileNamePattern(String fileNamePattern) {
			this.fileNamePattern = fileNamePattern;
			return this;
		}
		
		public FileDescriberBuilder path(String filePath) {
			this.filePath = filePath;
			return this;
		}
		
		public FileDescriberBuilder delimiter(Delimiter delimiter) {
			this.delimiter = delimiter;
			return this;
		}
		
		public FileDescriberBuilder files(Collection<File> files) {
			this.files.addAll(files);
			return this;
		}
		
		public FileDescriberBuilder columnCount(Integer columnCount) {
			this.columnCount =  columnCount;
			return this;
		}
		
		public FileDescriberBuilder file(File file) {
			this.files.add(file);
			return this;
		}
		
		public FileDescriber build() {
			return new FileDescriber() {
				@Override
				public Collection<File> getFiles() {
					return FileDescriberBuilder.this.files;
				}
				
				@Override
				public String getFilePath() {
					return FileDescriberBuilder.this.filePath;
				}
				
				@Override
				public String getFileNamePattern() {
					return FileDescriberBuilder.this.fileNamePattern;
				}
				
				@Override
				public String getFileName() {
					return FileDescriberBuilder.this.fileName;
				}
				
				@Override
				public File getFile() {
					if(files.isEmpty()){
						return null;
					}
					return FileDescriberBuilder.this.files.iterator().next();
				}
				
				@Override
				public Delimiter getDelimiter() {
					return FileDescriberBuilder.this.delimiter;
				}
				
				@Override
				public Integer getColumnCount() {
					return FileDescriberBuilder.this.columnCount;
				}
			};
		}
	}*/
	
	private String filePath;
	private String fileName;
	private String fileNamePattern;
	private Collection<File> files = new ArrayList<>();
	private Delimiter delimiter;
	private Integer columnCount;
	@Override
	public String getFilePath() {
		return filePath;
	}
	@Override
	public String getFileName() {
		return fileName;
	}
	@Override
	public File getFile() {
		return files.iterator().next();
	}
	@Override
	public Delimiter getDelimiter() {
		return delimiter;
	}
	@Override
	public Integer getColumnCount() {
		return columnCount;
	}
	@Override
	public String getFileNamePattern() {
		return fileNamePattern;
	}
	@Override
	public Collection<File> getFiles() {
		return files;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void setFileNamePattern(String fileNamePattern) {
		this.fileNamePattern = fileNamePattern;
	}
	
	public void setFile(File file) {
		files.add(file);
	}
	
	public void setDelimiter(Delimiter delimiter) {
		this.delimiter = delimiter;
	}
	
	public void setColumnCount(Integer columnCount) {
		this.columnCount = columnCount;
	}
	
	public void setFiles(Collection<File> files){
		files.addAll(files);
	}
}
