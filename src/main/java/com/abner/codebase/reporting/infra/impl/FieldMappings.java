package com.abner.codebase.reporting.infra.impl;

import com.abner.codebase.reporting.infra.FieldMapping;

public class FieldMappings {
	FieldMapping<Integer> indexMapping;
	FieldMapping<String> headerMapping;
	
	public FieldMappings(FieldMapping<Integer> indexMapping, FieldMapping<String> headerMapping) {
		this.indexMapping = indexMapping;
		this.headerMapping = headerMapping;
	}
}
