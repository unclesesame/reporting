package com.abner.codebase.reporting.infra;

import java.util.Collection;

public interface Store<T extends Domain>{
	boolean put(Collection<? extends T> items);
	
	boolean put(T items);
	
	Collection<T> getItems();
	
	<E> Attribute<T,E> getAttribute(String attributeName);
}
