package com.abner.codebase.reporting.infra.impl;

import com.abner.codebase.reporting.infra.Domain;
import com.abner.codebase.reporting.infra.DomainBinding;
import com.abner.codebase.reporting.infra.DomainDescriber;

public class DomainBindingImpl<T extends Domain> implements DomainBinding<T> {

	private Class<T> key;
	
	private DomainDescriber<T> domainDescriber;
	
	public DomainBindingImpl(Class<T> key, DomainDescriber<T> domainDescriber) {
		this.key = key;
		this.domainDescriber = domainDescriber;
	}
	
	@Override
	public Class<T> getKey() {
		return this.key;
	}

	@Override
	public DomainDescriber<T> getDomainDescriber() {
		return this.domainDescriber;
	}
	
}
