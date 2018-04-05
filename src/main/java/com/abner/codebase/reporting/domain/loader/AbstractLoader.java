package com.abner.codebase.reporting.domain.loader;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Provider;

import com.abner.codebase.reporting.config.FileConfigurationProvider;
import com.abner.codebase.reporting.infra.CsvReader;
import com.abner.codebase.reporting.infra.Domain;
import com.abner.codebase.reporting.infra.DomainDescriberDictionary;
import com.abner.codebase.reporting.infra.FieldMapping;
import com.abner.codebase.reporting.infra.FileDescriber;
import com.abner.codebase.reporting.infra.impl.BeanReaderBuilder;
import com.abner.codebase.reporting.infra.impl.CacheManager;


public class AbstractLoader<T extends Domain> implements Loader<T>{

	@Inject
	protected DomainDescriberDictionary domainDescriberDictionary;
	
	@Inject
	private Provider<BeanReaderBuilder<T>> beanReaderBuilderProvider;
	
	@Inject
	protected CacheManager cacheManager;
	
	private static final Map<Class<? extends AbstractLoader>, Class<? extends Domain>> DOMAIN_INTERFACE_CACHE
		= new ConcurrentHashMap<>();
	
	@Override
	public List<T> load() {
		List<T> result = loadFromFile();
		if(result == null || result.isEmpty()){
			loadDependencies();
			result = stitch();
		}
		return result;
	}
	
	private void loadDependencies() {
		Class<T> domain = getInterface();
		Collection<Class<? extends Domain>> inputDomains = domainDescriberDictionary.getDomainDescriber(domain).getInputDomains();
		Collection<Loader<?>> loaders = new ArrayList<>();
		inputDomains.forEach(inputDomain -> {
			Loader<? extends Domain> loader = domainDescriberDictionary.getDomainDescriber(inputDomain).getLoader();
			if( loader == null ){
				throw new RuntimeException("Unable to find loader for " + inputDomain.getSimpleName());
			} else{
				loaders.add(loader);
			}
		});
		loaders.forEach(loader -> {
			loader.load();
		});
	}

	private List<T> loadFromFile() {
		FileDescriber fileDescriber = FileConfigurationProvider.getFileConfiguration().getFileDescriber(getInterface());
		Collection<File> files = fileDescriber.getFiles();
		BeanReaderBuilder<T> beanReaderBuilder = beanReaderBuilderProvider.get();
		char delimiter = fileDescriber.getDelimiter().asChar();
		FieldMapping<Integer> fieldMapping = getFieldMapping();
		CsvReader<T> csvReader = beanReaderBuilder.beanImplClass(getImplClass())
				.files(files)
				.indexMapping(fieldMapping)
				.delimiter(delimiter)
				.buildBeanReader();
		List<T> list = csvReader.read();
		return list;
	}
	
	protected List<T> stitch() {
		throw new RuntimeException("stitch() to be implement for domain");
	}
	
	protected FieldMapping<Integer> getFieldMapping(){
		Class<T> domain = getInterface();
		return domainDescriberDictionary.getDomainDescriber(domain).getIndexMapping();
	}
	
	protected Class<? extends T> getImplClass() {
		return domainDescriberDictionary.getDomainDescriber(getInterface()).getImplClass();
	}
	
	protected Class<T> getInterface(){
		Class<? extends AbstractLoader> loaderClass = this.getClass();
		if(DOMAIN_INTERFACE_CACHE.containsKey(loaderClass)){
			return (Class<T>)DOMAIN_INTERFACE_CACHE.get(loaderClass);
		}
		ParameterizedType type = (ParameterizedType) loaderClass.getGenericSuperclass();
		Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
		DOMAIN_INTERFACE_CACHE.put(loaderClass, clazz);
		return clazz;
	}
}
