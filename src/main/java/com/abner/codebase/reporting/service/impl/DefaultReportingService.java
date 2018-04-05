package com.abner.codebase.reporting.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.abner.codebase.reporting.infra.Domain;
import com.abner.codebase.reporting.infra.DomainDescriber;
import com.abner.codebase.reporting.infra.DomainDescriberDictionary;
import com.abner.codebase.reporting.service.ReportingService;

public class DefaultReportingService implements ReportingService {

	private DomainDescriberDictionary domainDescriberDictionary;
	
	public DefaultReportingService(DomainDescriberDictionary domainDescriberDictionary) {
		this.domainDescriberDictionary = domainDescriberDictionary;
	}
	
	@Override
	public <T extends Domain> List<T> generateReport(String domainName) {
		return generateReport(getDomainByName(domainName));
	}

	@Override
	public <T extends Domain> List<T> generateReport(Class<? extends T> domain) {
		DomainDescriber<T> domainDescriber = domainDescriberDictionary.getDomainDescriber(domain);
		List<T> result = domainDescriber.getLoader().load();
		return result;
	} 
	
	@SuppressWarnings("unchecked")
	private <T extends Domain> Class<T> getDomainByName(String domainName){
		Optional<Class<T>> domain = Optional.empty();
		Collection<DomainDescriber<? extends Domain>> domainDescribers
			= domainDescriberDictionary.getAllDomainDescriber();
		for(DomainDescriber<? extends Domain> domainDescriber : domainDescribers){
			if(domainName.equalsIgnoreCase(domainDescriber.getDomain().getSimpleName())
				|| domainName.equalsIgnoreCase(domainDescriber.getDomain().getName())){
				if(!domain.isPresent()){
					domain = Optional.of((Class<T>) domainDescriber.getDomain());
				}else{
					throw new RuntimeException(domainName + " duplicated");
				}
			}
		}
		if(domain.isPresent()){
			return domain.get();
		}else{
			throw new RuntimeException("Invalid domain name " + domainName);
		}
	}
	
}
