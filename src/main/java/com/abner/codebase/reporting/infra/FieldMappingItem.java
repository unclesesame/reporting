package com.abner.codebase.reporting.infra;

import org.supercsv.cellprocessor.ift.CellProcessor;

public interface FieldMappingItem {
	FieldMappingItem to(String fileName);
	
	FieldMappingItem asInteger();
	
	FieldMappingItem asDouble();
	
	FieldMappingItem asString();
	
	FieldMappingItem asBoolean();
	
	FieldMappingItem asDoubleString(String format);
	
	FieldMappingItem asBoolString();
	
	String getFieldName();
	
	CellProcessor getCellProcessor();
}
