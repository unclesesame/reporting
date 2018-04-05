package com.abner.codebase.reporting.infra.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.print.attribute.standard.RequestingUserName;

import com.abner.codebase.reporting.infra.Cache;
import com.abner.codebase.reporting.infra.Domain;
import com.abner.codebase.reporting.infra.Store;

public class DefaultCache implements Cache{
	private final Map<Class<? extends Domain>, Store<Domain>> domainCache
		= new ConcurrentHashMap<>();
	private final CacheKey cacheKey;

	@Override
	public Set<Class<? extends Domain>> cacheDomain() {
		return null;
	}
	
	public DefaultCache(CacheKey cacheKey) {
		this.cacheKey = cacheKey;
	}

	@Override
	public <T extends Domain> boolean add(T instance) {
		return false;
	}

	@Override
	public <T extends Domain> boolean addAll(Collection<T> instances) {
		if(instances.isEmpty()){
			return false;
		}
		
		Class<T> domain = getInterface((Class<? extends T>) instances.iterator().next().getClass());
		
		return false;
	}

	@Override
	public <T extends Domain> boolean addAll(Collection<T> instances, Class<T> klass) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T extends Domain> boolean exists(Class<T> key) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private <T extends Domain> Class<T> getInterface(Class<? extends T> klass){
		return null;
	}

	@Override
	public void evict() {
		domainCache.clear();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Domain> Collection<T> get(Class<T> domain) {
		if(domainCache.containsKey(domain)) {
			throw new RuntimeException(domain.getSimpleName() + "has to be loaded first");
		}
		return (Collection<T>) domainCache.get(domain).getItems();
	}
}
