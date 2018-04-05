package com.abner.codebase.reporting.infra;

import org.supercsv.cellprocessor.ift.CellProcessor;

public interface FieldMapping<T> {
	
	FieldMappingItem map(T index);
	String[] fields();
	T[] columns();
	CellProcessor[] cellProcessors();
}
