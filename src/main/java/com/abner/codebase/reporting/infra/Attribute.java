package com.abner.codebase.reporting.infra;

public interface Attribute<T,E> {
	String getAttributeName();
	
	E getValue(T instance);
	
	boolean index();
}
