package com.abner.codebase.reporting.infra.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.supercsv.cellprocessor.ift.CellProcessor;

import com.abner.codebase.reporting.infra.FieldMapping;
import com.abner.codebase.reporting.infra.FieldMappingItem;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class DefaultFieldMapping implements FieldMapping<Integer> {

	private Map<Integer, FieldMappingItem> fieldMappingItems = new TreeMap<>();
	
	private List<String> fields = new ArrayList<>();
	
	private List<CellProcessor> cellProcessors = new ArrayList<>();
	
	private Integer columnCount;
	
	public DefaultFieldMapping(Integer columnCount) {
		this.columnCount = columnCount;
	}
	
	public DefaultFieldMapping(List<String> fields, List<CellProcessor> cellProcessors, Integer columnCount) {
		this.fields = fields;
		this.cellProcessors = cellProcessors;
		this.columnCount =  columnCount;
	}
	
	@Override
	public FieldMappingItem map(Integer index) {
		FieldMappingItem fieldMappingItem =  new DefaultFieldMappingItem();
		fieldMappingItems.put(index, fieldMappingItem);
		return fieldMappingItem;
	}

	@Override
	public String[] fields() {
		if(fields.isEmpty())
			populateFieldsConfig();
		return fields.toArray(new String[columnCount]);
	}

	@Override
	public Integer[] columns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CellProcessor[] cellProcessors() {
		if(fields.isEmpty()) {
			populateFieldsConfig();
		}
		return cellProcessors.toArray(new CellProcessor[columnCount]);
	}
	
	private void populateFieldsConfig() {
		for(Integer i=0; i<columnCount;i++){
			FieldMappingItem fieldMappingItem = fieldMappingItems.get(i);
			CellProcessor cellProcessor = fieldMappingItem == null ? null : fieldMappingItem.getCellProcessor();
			String fieldName = fieldMappingItem == null ? null : fieldMappingItem.getFieldName();
			cellProcessors.add(cellProcessor);
			fields.add(fieldName);
		}
	}
	 
}
