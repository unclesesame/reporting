package com.abner.codebase.reporting.infra;

import java.lang.reflect.Field;

import com.abner.codebase.reporting.infra.impl.FieldMappings;

public interface AutoMapping {
	FieldMappings getDefaultFieldMappings(Field[] fields);
}
