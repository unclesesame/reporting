package com.abner.codebase.reporting.infra.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

import com.abner.codebase.reporting.domain.dumper.FileDumper;
import com.abner.codebase.reporting.domain.loader.Loader;
import com.abner.codebase.reporting.infra.Domain;
import com.abner.codebase.reporting.infra.DomainDescriber;
import com.abner.codebase.reporting.infra.FieldMapping;

public class DomainDescriberImpl<T extends Domain> implements DomainDescriber<T>{
	private Class<T> domain;
	private Collection<Class<? extends Domain>> inputDomains = new HashSet<>();
	private Collection<Class<? extends Domain>> outputDomains = new HashSet<>();
	private Class<? extends T> implClass;
	private Supplier<FieldMapping<String>> headerMapping;
	private Supplier<FieldMapping<Integer>> indexMapping;
	private Loader<T> loader;
	private FileDumper<T> dumper;
	
	public DomainDescriberImpl(Class<T> domain) {
		this.domain = domain;
	}
	
	@Override
	public Class<T> getDomain() {
		return domain;
	}

	@Override
	public Collection<Class<? extends Domain>> getInputDomains() {
		return inputDomains;
	}

	@Override
	public Collection<Class<? extends Domain>> getOutputDomains() {
		return outputDomains;
	}

	@Override
	public Class<? extends T> getImplClass() {
		return implClass;
	}

	@Override
	public Loader<T> getLoader() {
		return loader;
	}

	@Override
	public FileDumper<T> getFileDumper() {
		return dumper;
	}

	@Override
	public boolean addInputDomains(Collection<Class<? extends Domain>> inputDomains) {
		return this.inputDomains.addAll(inputDomains);
	}

	@Override
	public boolean addOutputDomain(Class<? extends Domain> outputDomain) {
		return outputDomains.add(outputDomain);
	}

	@Override
	public FieldMapping<String> getHeaderMapping() {
		return headerMapping.get();
	}

	@Override
	public FieldMapping<Integer> getIndexMapping() {
		return indexMapping.get();
	}

	public void setDomain(Class<T> domain) {
		this.domain = domain;
	}

	public void setInputDomains(Collection<Class<? extends Domain>> inputDomains) {
		this.inputDomains = inputDomains;
	}

	public void setOutputDomains(Collection<Class<? extends Domain>> outputDomains) {
		this.outputDomains = outputDomains;
	}

	public void setImplClass(Class<? extends T> implClass) {
		this.implClass = implClass;
	}

	public void setHeaderMapping(Supplier<FieldMapping<String>> headerMapping) {
		this.headerMapping = headerMapping;
	}

	public void setIndexMapping(Supplier<FieldMapping<Integer>> indexMapping) {
		this.indexMapping = indexMapping;
	}

	public void setLoader(Loader<T> loader) {
		this.loader = loader;
	}

	public void setDumper(FileDumper<T> dumper) {
		this.dumper = dumper;
	}
	
}
