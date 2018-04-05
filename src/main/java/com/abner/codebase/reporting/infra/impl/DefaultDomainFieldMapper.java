package com.abner.codebase.reporting.infra.impl;

import com.abner.codebase.reporting.infra.Domain;
import com.abner.codebase.reporting.infra.DomainFieldMapper;
import com.abner.codebase.reporting.infra.FieldMapping;

public class DefaultDomainFieldMapper implements DomainFieldMapper{

	@Override
	public <T extends Domain> FieldMapping<String> getHeaderMapping(Class<? extends T> domainClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Domain> FieldMapping<Integer> getIndexMapping(Class<? extends T> domainClass) {
		// TODO Auto-generated method stub
		return null;
	}

}
