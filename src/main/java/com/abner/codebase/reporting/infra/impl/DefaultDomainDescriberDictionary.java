package com.abner.codebase.reporting.infra.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.abner.codebase.reporting.infra.Domain;
import com.abner.codebase.reporting.infra.DomainDescriber;
import com.abner.codebase.reporting.infra.DomainDescriberDictionary;

public class DefaultDomainDescriberDictionary implements DomainDescriberDictionary{

	private static final Map<Class<? extends Domain>, DomainDescriber<? extends Domain>> domainDescribers 
		= new HashMap<>();
	
	private static final Set<Class<? extends Domain>> parsedDomains = new HashSet<>();
	
	@Override
	public <T extends Domain> DomainDescriber<T> getDomainDescriber(Class<? extends T> domain) {
		domainDescribers.computeIfAbsent(domain, k -> new DomainDescriberImpl<>(domain));
		return (DomainDescriber<T>) domainDescribers.get(domain);
	}

	@Override
	public Collection<DomainDescriber<? extends Domain>> getAllDomainDescriber() {
		return domainDescribers.values();
	}
	
	public void clear() {
		domainDescribers.clear();
		parsedDomains.clear();
	}
	
	boolean registerDomain(Class<? extends Domain> domain) {
		return parsedDomains.add(domain);
	}
	
	boolean isRegistered(Class<? extends Domain> domain) {
		return parsedDomains.contains(domain);
	}

}
