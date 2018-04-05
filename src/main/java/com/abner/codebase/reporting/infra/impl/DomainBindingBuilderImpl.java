package com.abner.codebase.reporting.infra.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;


import org.springframework.context.ApplicationContext;

import com.abner.codebase.reporting.domain.dumper.FileDumper;
import com.abner.codebase.reporting.domain.dumper.impl.AbstractFileDumper;
import com.abner.codebase.reporting.domain.loader.AbstractLoader;
import com.abner.codebase.reporting.domain.loader.Loader;
import com.abner.codebase.reporting.infra.Attribute;
import com.abner.codebase.reporting.infra.AttributeParser;
import com.abner.codebase.reporting.infra.Domain;
import com.abner.codebase.reporting.infra.DomainBinding;
import com.abner.codebase.reporting.infra.DomainBindingBuilder;
import com.abner.codebase.reporting.infra.DomainDescriberDictionary;
import com.abner.codebase.reporting.infra.DomainFieldMapper;
import com.abner.codebase.reporting.infra.FieldMapping;

public class DomainBindingBuilderImpl<T extends Domain> implements DomainBindingBuilder<T> {
	private final ApplicationContext applicationContext;
	private final AttributeParser attributeParser;
	private final DomainFieldMapper domainFieldMapper;
	private Class<T> key;
	
	private Optional<Class<? extends Loader<T>>> loaderClass = Optional.empty();
	private Optional<Supplier<FieldMapping<Integer>>> indexMapping = Optional.empty();
	private Optional<Supplier<FieldMapping<String>>> headerMapping = Optional.empty();
	private Class<? extends T> domainImplClass;
	private Optional<Class<? extends FileDumper<T>>> dumperClass = Optional.empty();
	
	public DomainBindingBuilderImpl(Class<T> key, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.attributeParser = applicationContext.getBean(AttributeParser.class);
		domainFieldMapper = applicationContext.getBean(DomainFieldMapper.class);
		this.key = key;
	}
	
	public DomainBindingBuilderImpl(ApplicationContext applicationContext, AttributeParser attributeParser,
			DomainFieldMapper domainFieldMapper, Class<T> key) {
		this.applicationContext = applicationContext;
		this.attributeParser = attributeParser;
		this.domainFieldMapper = domainFieldMapper;
		this.key = key;
	}
	@Override
	public DomainBinding<T> with(Class... classes) {
		Map<String, Attribute<T, ?>> attributes = attributeParser.parse(domainImplClass);
		List<Class<? extends Domain>> inputs = Arrays.asList(classes);
		DomainDescriberDictionary domainDescriberDictionary = applicationContext.getBean(DomainDescriberDictionary.class);
		DomainDescriberImpl<T> domainDescriber = getDomainDescriber(domainDescriberDictionary,key);
		domainDescriber.addInputDomains(inputs);
		domainDescriber.setLoader(loaderInstance(loaderClass));
		domainDescriber.setDumper(dumperInstance(dumperClass));
		domainDescriber.setIndexMapping(this::getIndexMapping);
		domainDescriber.setHeaderMapping(this::getHeaderMapping);
		inputs.forEach(inputDomain -> {
			DomainDescriberImpl<T> inputDomainDescriber = getDomainDescriber(domainDescriberDictionary, (Class<T>)inputDomain);
			inputDomainDescriber.addOutputDomain(key);
			((DefaultDomainDescriberDictionary)domainDescriberDictionary).registerDomain(key);
		});
		return new DomainBindingImpl<>(key,domainDescriber);
	}
	@Override
	public DomainBindingBuilder<T> loader(Class<? extends Loader<T>> loaderClass) {
		this.loaderClass = Optional.ofNullable(loaderClass);
		return this;
	}
	@Override
	public DomainBindingBuilder<T> dumper(Class<? extends FileDumper<T>> dumperClass) {
		this.dumperClass = Optional.ofNullable(dumperClass);
		return this;
	}
	@Override
	public DomainBindingBuilder<T> indexMapping(Supplier<FieldMapping<Integer>> indexMapping) {
		this.indexMapping = Optional.ofNullable(indexMapping);
		return this;
	}
	@Override
	public DomainBindingBuilder<T> headerMapping(Supplier<FieldMapping<String>> headerMapping) {
		this.headerMapping = Optional.ofNullable(headerMapping);
		return this;
	}
	@Override
	public DomainBindingBuilder<T> domainImplClass(Class<? extends T> domainImplClass) {
		Objects.requireNonNull(domainImplClass);
		if(domainImplClass.isInterface()) {
			throw new RuntimeException(domainImplClass.getSimpleName()+" the class must be a implementation class");
		}
		this.domainImplClass = domainImplClass;
		return this;
	}
	
	private DomainDescriberImpl<T> getDomainDescriber(DomainDescriberDictionary domainDescriberDictionary, Class<T> domain){
		return (DomainDescriberImpl<T>) domainDescriberDictionary.getDomainDescriber(domain);
	}
	
	private Loader<T> loaderInstance(Optional<Class<? extends Loader<T>>> loaderClass) {
		if(!loaderClass.isPresent()) {
			AbstractLoader<T> loader = new AbstractLoader<T>() {
				@Override
				protected Class<? extends T> getImplClass() {
					return domainImplClass;
				}
				@Override
				protected FieldMapping<Integer> getFieldMapping() {
					return getIndexMapping();
				}
				@Override
				protected Class<T> getInterface() {
					return key;
				}
			};
			applicationContext.getAutowireCapableBeanFactory().autowireBean(loader);
			return loader;
		} else{
			try{
				return applicationContext.getBean(loaderClass.get());
			} catch (Exception e) {
				throw new RuntimeException("Unable to instantiate load " + loaderClass + e);
			}
		}
		
	}
	
	private FileDumper<T> dumperInstance(Optional<Class<? extends FileDumper<T>>> dumperClass) {
		if(!dumperClass.isPresent()) {
			AbstractFileDumper<T> fileDumper = new AbstractFileDumper<T>() {
				@Override
				protected FieldMapping<String> getFieldMapping() {
					return headerMapping.orElse(DomainBindingBuilderImpl.this::getHeaderMapping).get();
				}
				@Override
				protected Class<T> getInterface() {
					return key;
				}
			};
			applicationContext.getAutowireCapableBeanFactory().autowireBean(fileDumper);
			return fileDumper;
		}else{
			try {
				return applicationContext.getBean(dumperClass.get());
			} catch (Exception e) {
				throw new RuntimeException("unable to instantiate dumper" + dumperClass);
			}
		}
	}
	
	private FieldMapping<Integer> getIndexMapping() {
		if(this.indexMapping.isPresent()) {
			return this.indexMapping.get().get();
		}else{
			return domainFieldMapper.getIndexMapping(domainImplClass);
		}
	}
	
	private FieldMapping<String> getHeaderMapping() {
		if(this.headerMapping.isPresent()) {
			return this.headerMapping.get().get();
		}else{
			return domainFieldMapper.getHeaderMapping(domainImplClass);
		}
	}
	
}
