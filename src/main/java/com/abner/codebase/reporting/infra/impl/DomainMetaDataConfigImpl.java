package com.abner.codebase.reporting.infra.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.abner.codebase.reporting.infra.Domain;
import com.abner.codebase.reporting.infra.DomainDescriberDictionary;
import com.abner.codebase.reporting.infra.DomainMetaData;
import com.abner.codebase.reporting.infra.DomainMetaDataConfig;

public class DomainMetaDataConfigImpl implements DomainMetaDataConfig{

	private final DomainDescriberDictionary domainDescriberDictionary;
	private List<DomainMetaData> domainMetaData = new ArrayList<>();
	
	public DomainMetaDataConfigImpl(DomainDescriberDictionary domainDescriberDictionary){
		this.domainDescriberDictionary = domainDescriberDictionary;
	}
	
	@Override
	public void install(DomainMetaData metaData) {
		domainMetaData.add(metaData);
	}

	@Override
	public void configure() {
		domainMetaData.forEach(DomainMetaData::bind);
	}

	@Override
	public <T extends Domain> Collection<Class<? extends Domain>> getInputsForDomain(Class<T> domainClass) {
		return domainDescriberDictionary.getDomainDescriber(domainClass).getInputDomains();
	}

	@Override
	public <T extends Domain> Collection<Class<? extends Domain>> getOutputsForDomain(Class<T> domainClass) {
		 return domainDescriberDictionary.getDomainDescriber(domainClass).getOutputDomains();
	}

	@Override
	public <T extends Domain> Collection<Class<? extends Domain>> getAllOutputsForDomain(Class<T> domainClass) {
		return null;
	}

	@Override
	public <T extends Domain> Collection<Class<? extends Domain>> getAllInputsForDomain(Class<T> domainClass) {
		return null;
	}
	
}
