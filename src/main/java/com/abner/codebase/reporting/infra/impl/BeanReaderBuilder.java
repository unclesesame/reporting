package com.abner.codebase.reporting.infra.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.abner.codebase.reporting.infra.CsvReader;
import com.abner.codebase.reporting.infra.FieldMapping;

public class BeanReaderBuilder<T> {
	private char delimiter;
	private FieldMapping<Integer> fieldMapping;
	private Collection<File> files = new ArrayList<>();
	private List<Integer> columns = new ArrayList<>();
	private Class<? extends T> beanImplClass;
	
	public CsvReader<T> buildBeanReader() {
		return new BeanReader<>(files, delimiter, fieldMapping, beanImplClass);
	}
	
	public BeanReaderBuilder<T> delimiter(char delimiter){
		this.delimiter = delimiter;
		return this;
	}
	
	public BeanReaderBuilder<T> file(File file) {
		if(file != null){
			this.files.add(file);
		}
		return this;
	}
	
	public BeanReaderBuilder<T> files(Collection<File> files) {
		this.files.addAll(files);
		return this;
	}
	
	public BeanReaderBuilder<T> indexMapping(FieldMapping<Integer> fieldMapping) {
		this.fieldMapping = fieldMapping;
		return this;
	}
	
	public BeanReaderBuilder<T> beanImplClass(Class<? extends T> beanImplClass) {
		this.beanImplClass = beanImplClass;
		return this;
	}
	
	public BeanReaderBuilder<T> columns(Integer... columns) {
		this.columns.addAll(Arrays.asList(columns));
		return this;
	}
}
