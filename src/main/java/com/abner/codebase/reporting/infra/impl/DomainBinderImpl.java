package com.abner.codebase.reporting.infra.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.abner.codebase.reporting.infra.Domain;
import com.abner.codebase.reporting.infra.DomainBinder;
import com.abner.codebase.reporting.infra.DomainBindingBuilder;

public class DomainBinderImpl implements DomainBinder,ApplicationContextAware{

	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext =  applicationContext;
	}

	@Override
	public <T extends Domain> DomainBindingBuilder<T> bind(Class<T> domainClass) {
		return new DomainBindingBuilderImpl<>(domainClass,applicationContext);
	}
	
}
