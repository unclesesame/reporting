package com.abner.codebase.reporting.infra.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;

import com.abner.codebase.reporting.infra.Attribute;
import com.abner.codebase.reporting.infra.AttributeParser;
import com.abner.codebase.reporting.infra.Index;

public class DefaultAttributeParser implements AttributeParser {

	@Override
	public <T> Map<String, Attribute<T, ?>> parse(Class<? extends T> domain) {
		Map<String, Attribute<T,?>> result = new HashMap<>();
		Field[] fields = domain.getDeclaredFields(); 
		Method[] methods = domain.getDeclaredMethods();
		for(Field field : fields) {
			if(Modifier.isStatic(field.getModifiers()))
				continue;
			Attribute<T, ?> attribute = createAttribute(field);
			result.put(attribute.getAttributeName(), attribute);
		}
		for(Method method : methods) {
			if(method.getAnnotation(Index.class) == null)
				continue;
			method.setAccessible(true);
			Attribute<T, ?> attribute = createAttribute(method);
		}
		return null;
	}
	
	private <T> Attribute<T, ?> createAttribute(Field field) {
		String attributeName = field.getName();
		boolean indexed = true;
		Index index;
		if((index = field.getAnnotation(Index.class)) != null) {
			attributeName = StringUtils.isEmpty(index.name()) ? attributeName : index.name();
			indexed = true;
		}
		DefaultAttribute<T, ?> attribute = new DefaultAttribute<>(attributeName);
		attribute.setIndex(indexed);
		attribute.setField(field);
		return attribute;
	}
	
	private <T> Attribute<T, ?> createAttribute(Method method) {
		String attributeName = method.getName();
		boolean indexed = true;
		Index index;
		if((index = method.getAnnotation(Index.class)) != null) {
			attributeName = StringUtils.isEmpty(index.name()) ? attributeName : index.name();
			indexed = true;
		}
		DefaultAttribute<T, ?> attribute = new DefaultAttribute<>(attributeName);
		attribute.setIndex(indexed);
		attribute.setMethod(method);
		return attribute;
	}
	
}
