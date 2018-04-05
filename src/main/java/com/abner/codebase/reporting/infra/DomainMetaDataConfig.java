package com.abner.codebase.reporting.infra;

import java.util.Collection;

import com.abner.codebase.reporting.service.Service;

public interface DomainMetaDataConfig extends Service{
	void configure();
	void install(DomainMetaData metaData);
	<T extends Domain> Collection<Class<? extends Domain>> getInputsForDomain(Class<T> domainClass);
	<T extends Domain> Collection<Class<? extends Domain>> getOutputsForDomain(Class<T> domainClass);
	<T extends Domain> Collection<Class<? extends Domain>> getAllInputsForDomain(Class<T> domainClass);
	<T extends Domain> Collection<Class<? extends Domain>> getAllOutputsForDomain(Class<T> domainClass);
}
