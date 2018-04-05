package com.abner.codebase.reporting.infra;

import java.util.function.Supplier;

import com.abner.codebase.reporting.domain.dumper.FileDumper;
import com.abner.codebase.reporting.domain.loader.Loader;
import com.abner.codebase.reporting.service.Service;

public interface DomainBindingBuilder<T extends Domain> extends Service{
	
	@SuppressWarnings("rawtypes")
	DomainBinding<T> with(Class... classes);
	
	DomainBindingBuilder<T> loader(Class<? extends Loader<T>> class1);
	
	DomainBindingBuilder<T> dumper(Class<? extends FileDumper<T>> dumperClass);
	
	DomainBindingBuilder<T> indexMapping(Supplier<FieldMapping<Integer>> indexMapping);
	
	DomainBindingBuilder<T> headerMapping(Supplier<FieldMapping<String>> headerMapping);
	
	DomainBindingBuilder<T> domainImplClass(Class<? extends T> domainImplClass);
	
}
