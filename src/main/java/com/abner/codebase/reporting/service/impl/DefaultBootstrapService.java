package com.abner.codebase.reporting.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abner.codebase.reporting.domain.Employee;
import com.abner.codebase.reporting.domain.Human;
import com.abner.codebase.reporting.domain.Student;
import com.abner.codebase.reporting.domain.impl.EmployeeImpl;
import com.abner.codebase.reporting.domain.impl.HumanImpl;
import com.abner.codebase.reporting.domain.impl.StudentImpl;
import com.abner.codebase.reporting.domain.loader.AbstractLoader;
import com.abner.codebase.reporting.domain.loader.HumanLoader;
import com.abner.codebase.reporting.domain.loader.Loader;
import com.abner.codebase.reporting.infra.Domain;
import com.abner.codebase.reporting.infra.DomainBinder;
import com.abner.codebase.reporting.infra.DomainDescriberDictionary;
import com.abner.codebase.reporting.infra.DomainMetaDataConfig;
import com.abner.codebase.reporting.infra.DomainParser;
import com.abner.codebase.reporting.infra.impl.AbstractDomainMetaData;
import com.abner.codebase.reporting.infra.impl.CacheManager;
import com.abner.codebase.reporting.infra.impl.DefaultDomainDescriberDictionary;
import com.abner.codebase.reporting.service.BootstrapService;

public class DefaultBootstrapService implements BootstrapService{

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultBootstrapService.class);
	
	private final DomainMetaDataConfig domainMetaDataConfig;
	//private final DomainParser domainParser;
	private final DomainBinder domainBinder;
	private final DomainDescriberDictionary domainDescriberDictionary;
	private final CacheManager cacheManager;
	
	public DefaultBootstrapService(DomainMetaDataConfig domainMetaDataConfig,
			//DomainParser domainParser, 
			DomainBinder domainBinder, 
			DomainDescriberDictionary domainDescriberDictionary, 
			CacheManager cacheManager ) {
		this.domainMetaDataConfig = domainMetaDataConfig;
		//this.domainParser = domainParser;
		this.domainBinder = domainBinder;
		this.domainDescriberDictionary = domainDescriberDictionary;
		this.cacheManager = cacheManager;
	}
	
	@Override
	public void boot() {
		domainMetaDataConfig.install(new HumanReportMetaData());
		domainMetaDataConfig.configure();
	}

	@Override
	public void shutdown() {
		LOGGER.info("Shutting down and clearing");
		((DefaultDomainDescriberDictionary)domainDescriberDictionary).clear();
		cacheManager.clear();
	}
	
	private class HumanReportMetaData extends AbstractDomainMetaData{
		@Override
		public void bind(){
			domainBinder.bind(Student.class).domainImplClass(StudentImpl.class).with();
			domainBinder.bind(Employee.class).domainImplClass(EmployeeImpl.class).with();
			domainBinder.bind(Human.class).domainImplClass(HumanImpl.class).loader(HumanLoader.class).with(Student.class,Employee.class);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Collection<Class<? extends Domain>> cast(Collection<Class<?>> domains) {
		return domains.stream().map(domain -> {
			if(!Domain.class.isAssignableFrom(domain)) {
				throw new ClassCastException("wrong application of annotation on class" + domain.toString());
			}
			return (Class<? extends Domain>) domain;
		}).collect(Collectors.toList());
	}

}
