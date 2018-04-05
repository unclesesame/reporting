package com.abner.codebase.reporting.infra;

public interface DomainFieldMapper {
	<T extends Domain> FieldMapping<String> getHeaderMapping(Class<? extends T> domainClass);
	
	<T extends Domain> FieldMapping<Integer> getIndexMapping(Class<? extends T> domainClass);
}
