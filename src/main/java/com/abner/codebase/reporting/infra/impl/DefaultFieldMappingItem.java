package com.abner.codebase.reporting.infra.impl;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.supercsv.cellprocessor.FmtNumber;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.abner.codebase.reporting.infra.FieldMappingItem;

public class DefaultFieldMappingItem implements FieldMappingItem{

	private String fieldName;
	
	private CellProcessor cellProcessor;
	private static final HashMap<Class<?>, Method> PARSED_METHOD_CACHE = new HashMap<>();
	
	@Override
	public FieldMappingItem to(String fileName) {
		this.fieldName = fieldName;
		return this;
	}

	@Override
	public FieldMappingItem asInteger() {
		this.cellProcessor = new ParseInt();
		return this;
	}

	@Override
	public FieldMappingItem asDouble() {
		this.cellProcessor = new ParseDouble();
		return this;
	}

	@Override
	public FieldMappingItem asString() {
		this.cellProcessor = new StringOptional(new Trim());
		return this;
	}

	@Override
	public FieldMappingItem asBoolean() {
		this.cellProcessor = new ParseBool();
		return this;
	}

	@Override
	public String getFieldName() {
		return this.fieldName;
	}

	@Override
	public CellProcessor getCellProcessor() {
		return this.cellProcessor;
	}

	@Override
	public FieldMappingItem asDoubleString(String format) {
		this.cellProcessor = new Optional(new FmtNumber(format));
		return this;
	}

	@Override
	public FieldMappingItem asBoolString() {
		this.cellProcessor = new FmtBool ("Y","N");
		return this;
	}
	
}
