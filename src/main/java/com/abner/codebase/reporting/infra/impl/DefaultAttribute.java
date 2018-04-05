package com.abner.codebase.reporting.infra.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import javax.activation.FileDataSource;

import org.springframework.cglib.core.MethodWrapper;

import com.abner.codebase.reporting.infra.Attribute;

public class DefaultAttribute<T,E> implements Attribute<T, E> {
	private final String attributeName;
	private Optional<Field> field = Optional.empty();
	private Optional<Method> method = Optional.empty();
	private boolean index;
	
	public DefaultAttribute(String attributeName) {
		this.attributeName =  attributeName;
	}
	
	@Override
	public String getAttributeName() {
		return this.attributeName;
	}
	
	public void setIndex(boolean index) {
		this.index = index;
	}
	
	public void setField(Field field) {
		this.field = Optional.of(field);
	}
	
	public void setMethod(Method method) {
		this.method = Optional.of(method);
	}

	@Override
	public E getValue(T instance) {
		if(field.isPresent())
			return getValue(field.get(),instance);
		if(method.isPresent())
			return getValue(method.get(),instance);
		throw new RuntimeException("can not read value [" + this.attributeName + "] from instance" + instance);
	}
	
	private E getValue(Field field, T instance) {
		try{
			return (E) field.get(instance);
		} catch(IllegalAccessException e) {
			throw new RuntimeException("Unable to read value for instance" + instance + " and field" + field, e);
		}
	}
	
	private E getValue(Method method, T instance) {
		try{
			return (E) method.invoke(instance);
		} catch (InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException("Unable to read value for instance" + instance + " and method" + method, e);
		}
	}

	@Override
	public boolean index() {
		return this.index;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributeName == null) ? 0 : attributeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultAttribute other = (DefaultAttribute) obj;
		if (attributeName == null) {
			if (other.attributeName != null)
				return false;
		} else if (!attributeName.equals(other.attributeName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DefaultAttribute [attributeName=" + attributeName + ", index=" + index + "]";
	}

}
