package com.abner.codebase.reporting.infra.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.Tokenizer;
import org.supercsv.prefs.CsvPreference;

import com.abner.codebase.reporting.infra.CsvReader;
import com.abner.codebase.reporting.infra.EmptyList;
import com.abner.codebase.reporting.infra.FieldMapping;
import com.abner.codebase.reporting.infra.Symbol;

public class BeanReader<T> implements CsvReader<T>{

	private final Collection<File> files;
	
	private final char delimiter;
	
	private final FieldMapping<Integer> fieldMapping;
	
	private final Class<? extends T> beanImplClass;
	
	
	public BeanReader(Collection<File> files, char delimiter, FieldMapping<Integer> fieldMapping,
			Class<? extends T> beanImplClass) {
		this.files = files;
		this.delimiter = delimiter;
		this.fieldMapping = fieldMapping;
		this.beanImplClass = beanImplClass;
	}

	@Override
	public List<T> read() {
		final List<T> identity = new ArrayList<>();
		if(files.isEmpty()){
			return new EmptyList<>();
		}
		return files.stream().map(this::readFile).reduce(identity,(t1,t2)->{
			t1.addAll(t2);
			return t1;
		});
	}
	
	protected List<T> readFile(File file){
		return readFile(file, t->t);
	}
	
	protected List<T> readFile(File file, Function<T,T> f){
		List<T> result = new ArrayList<>();
		if(!file.exists()){
			return result;
		}
		long row = 1;
		try(Reader reader = bufferedReader(file);
				CsvListReader csvListReader = getCsvListReader(file);) {
			CsvBeanReader csvBeanReader = getCsvBeanReader(reader);
			
			csvBeanReader.getHeader(true);
			csvListReader.getHeader(true);
			List<String> firstRow = csvListReader.read();
			String[] fields = fieldMapping.fields();
			CellProcessor[] processors = fieldMapping.cellProcessors();
			if(firstRow != null){
				fields = fieldMapping.fields();
				processors = fieldMapping.cellProcessors();
			}
			T instance;
			while((instance = csvBeanReader.read(beanImplClass, fields,processors)) !=null ) {
				result.add(f.apply(instance));
				row++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private BufferedReader bufferedReader(File file) throws IOException{
		return Files.newBufferedReader(Paths.get(file.getPath()));
	}
	
	private CsvListReader getCsvListReader(File file) throws IOException {
		CsvPreference csvPreference = new CsvPreference.Builder(Symbol.QUOTE.asChar(), 
				delimiter, Symbol.NULL.asString()).build();
		Reader reader = bufferedReader(file);
		Tokenizer tokenizer = new Tokenizer(reader, csvPreference);
		return new CsvListReader(tokenizer, csvPreference);
	}
	
	private CsvBeanReader getCsvBeanReader(Reader reader) {
		CsvPreference csvPreference = new CsvPreference.Builder(Symbol.QUOTE.asChar(), 
				delimiter, Symbol.NULL.asString()).build();
		Tokenizer tokenizer = new Tokenizer(reader, csvPreference);
		CsvBeanReader csvBeanReader = new CsvBeanReader(tokenizer, csvPreference);
		return csvBeanReader;
	}
	
}
