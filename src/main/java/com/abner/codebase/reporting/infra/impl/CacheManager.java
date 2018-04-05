package com.abner.codebase.reporting.infra.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.abner.codebase.reporting.infra.Cache;

public class CacheManager implements ApplicationContextAware {

	private final Map<CacheKey, Cache> caches = new ConcurrentHashMap<>();
	private CacheKey defaultCacheKey;
	private ApplicationContext applicationContext;
	
	public CacheManager() {
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public Cache getCache(CacheKey cacheKey) {
		if(caches.get(cacheKey) == null) {
			Cache cache = new DefaultCache(cacheKey);
			applicationContext.getAutowireCapableBeanFactory().autowireBean(cache);
			caches.put(cacheKey, cache);
		}
		return caches.get(cacheKey);
	}
	
	//TO-DO:
	public Cache getCache() {
		return caches.get(defaultCacheKey);
	}
	
	public void clear() {
		defaultCacheKey = null;
		caches.values().forEach(Cache::evict);
		caches.clear();
	}
}
