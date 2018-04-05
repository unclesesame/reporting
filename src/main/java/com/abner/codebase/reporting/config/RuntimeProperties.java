package com.abner.codebase.reporting.config;

public interface RuntimeProperties {
	<T> T getValue();
	String getKey();
	String getLongKey();
	String getDescription();
}
