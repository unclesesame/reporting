package com.abner.codebase.reporting.infra;

import java.util.Collection;

import com.abner.codebase.reporting.service.Service;

public interface DomainDescriberDictionary extends Service{
	<T extends Domain> DomainDescriber<T> getDomainDescriber(Class<? extends T> domain);
	
	Collection<DomainDescriber<? extends Domain>> getAllDomainDescriber();
}
