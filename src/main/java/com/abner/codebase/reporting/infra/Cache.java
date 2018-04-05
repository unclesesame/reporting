package com.abner.codebase.reporting.infra;

import java.util.Collection;
import java.util.Set;

public interface Cache {
	Set<Class<? extends Domain>> cacheDomain();
	
	<T extends Domain> boolean add(T instance);
	
	<T extends Domain> boolean addAll(Collection<T> instances);
	
	<T extends Domain> boolean addAll(Collection<T> instances, Class<T> klass);
	
	<T extends Domain> boolean exists(Class<T> key);
	
	<T extends Domain> Collection<T> get(Class<T> domain);
	
	void evict();
}
