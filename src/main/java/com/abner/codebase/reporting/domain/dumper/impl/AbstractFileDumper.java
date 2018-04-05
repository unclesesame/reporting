package com.abner.codebase.reporting.domain.dumper.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.abner.codebase.reporting.config.FileConfigurationProvider;
import com.abner.codebase.reporting.domain.dumper.FileDumper;
import com.abner.codebase.reporting.infra.Domain;
import com.abner.codebase.reporting.infra.DomainDescriberDictionary;
import com.abner.codebase.reporting.infra.FieldMapping;

public class AbstractFileDumper<T extends Domain> implements FileDumper<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFileDumper.class);
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	@SuppressWarnings("rawtypes")
	private static final Map<Class<? extends AbstractFileDumper>, Class<? extends Domain>> domainInterfaceCache = new ConcurrentHashMap<>();
	
	@Inject
	private DomainDescriberDictionary domainDescriberDictionary;
	
	@Override
	public void dumpToFile(Collection<T> instances) {
		File file = getFile();
		if(file == null) {
			return;
		}
		
		FieldMapping<String> fieldMapping = getFieldMapping();
		char delimiter = FileConfigurationProvider.getFileConfiguration().getFileDescriber(getInterface()).getDelimiter().asChar();
		try(CsvBeanWriter writer = new CsvBeanWriter(Files.newBufferedWriter(Paths.get(file.getPath()), 
				StandardCharsets.UTF_8), new CsvPreference.Builder((char)0, delimiter,LINE_SEPARATOR).build())) {
			writer.writeHeader(fieldMapping.columns());
			for (T instance : instances) {
				writer.write(instance, fieldMapping.fields(), fieldMapping.cellProcessors());
			}
			LOGGER.info("Dumping {} for , to file: " + file.getAbsolutePath(), getInterface().getSimpleName());
		} catch (IOException e) {
			throw new RuntimeException("Failed to dump domain " + getInterface() + " to file: " + file.getAbsolutePath());
		}
		
 	}
	
	protected FieldMapping<String> getFieldMapping() {
		return domainDescriberDictionary.getDomainDescriber(getInterface()).getHeaderMapping();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Class<T> getInterface() {
		Class<? extends AbstractFileDumper> dumperClass = this.getClass();
		if(domainInterfaceCache.containsKey(this.getClass())) {
			return (Class<T>) domainInterfaceCache.get(this.getClass());
		}
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<T> klass = (Class<T>) type.getActualTypeArguments()[0];
		domainInterfaceCache.put(dumperClass, klass);
		return klass;
	}
	
	protected File getFile() {
		String filePath = FileConfigurationProvider.getFileConfiguration().getFileDescriber(getInterface()).getFilePath();
		String fileName = FileConfigurationProvider.getFileConfiguration().getFileDescriber(getInterface()).getFileName();
		if(StringUtils.isEmpty(filePath) || StringUtils.isEmpty(fileName)) {
			return null;
		}else{
			return checkFile(filePath + File.pathSeparator + fileName);
		}
	}
	
	protected File checkFile(String absoluteFileName) {
		String resolvedFileName = absoluteFileName;
		if(!Paths.get(absoluteFileName).isAbsolute() && !absoluteFileName.startsWith("/")){
			resolvedFileName = FileUtils.getTempDirectoryPath()+absoluteFileName;
		}
		File file = new File(resolvedFileName);
		if(!file.exists()) {
			try {
				Files.createDirectories(Paths.get(file.getParentFile().getPath()));
				Files.createFile(Paths.get(file.getPath()));
			} catch (IOException e) {
				throw new RuntimeException("Unable to create path " + file.getPath() + " for file "
						+ file.getName() + " to dump domain" + getInterface().getSimpleName());
			}
		}
		return file;
	}
	
}
