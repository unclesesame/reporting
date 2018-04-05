package com.abner.codebase.reporting.infra;

import java.util.Collection;
import java.util.Collections;

import com.abner.codebase.reporting.domain.dumper.FileDumper;
import com.abner.codebase.reporting.domain.loader.Loader;

public interface DomainDescriber<T extends Domain> {
	Class<T> getDomain();
	
	Collection<Class<? extends Domain>> getInputDomains();
	
	Collection<Class<? extends Domain>> getOutputDomains();
	
	Class<? extends T> getImplClass();
	
	Loader<T> getLoader();
	
	FileDumper<T> getFileDumper();
	
	boolean addInputDomains(Collection<Class<? extends Domain>> inputDomains);
	
	boolean addOutputDomain(Class<? extends Domain> outputDomain);
	
	FieldMapping<String> getHeaderMapping();
	
	FieldMapping<Integer> getIndexMapping();
	
	
}
