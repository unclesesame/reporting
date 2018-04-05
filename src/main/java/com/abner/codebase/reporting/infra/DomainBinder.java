package com.abner.codebase.reporting.infra;

public interface DomainBinder {
	<T extends Domain> DomainBindingBuilder<T> bind(Class<T> domainClass);
}
