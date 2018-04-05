package com.abner.codebase.reporting.infra;

public interface DomainBinding<T extends Domain> {
	Class<T> getKey();
	
	DomainDescriber<T> getDomainDescriber();
}
