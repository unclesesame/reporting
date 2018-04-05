package com.abner.codebase.reporting.infra.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.wiring.BeanWiringInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.abner.codebase.reporting.infra.Domain;
import com.abner.codebase.reporting.infra.DomainBindingBuilder;
import com.abner.codebase.reporting.infra.DomainDescriber;
import com.abner.codebase.reporting.infra.DomainDescriberDictionary;
import com.abner.codebase.reporting.infra.DomainFieldMapper;
import com.abner.codebase.reporting.infra.DomainParser;

public class DefaultDomainParser implements DomainParser, ApplicationContextAware{

	private final DomainDescriberDictionary domainDescriberDictionary;
	private ApplicationContext applicationContext;
	private final DomainFieldMapper DomainFieldMapper;
	
	public DefaultDomainParser(DomainDescriberDictionary domainDescriberDictionary, DomainFieldMapper domainFieldMapper) {
		this.domainDescriberDictionary = domainDescriberDictionary;
		this.DomainFieldMapper = domainFieldMapper;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public <T extends Domain> DomainDescriber<T> parser(Class<T> domain) {
		/*DomainBindingBuilder<T> domainBindingBuilder = BeanWiringInfo DomainBindingBuilderImpl<>(domain,applicationContext);
		if(((DefaultDomainDescriberDictionary)domainDescriberDictionary)).isRegistered(domain){
			
		}*/
		return null;
	}
	
}
