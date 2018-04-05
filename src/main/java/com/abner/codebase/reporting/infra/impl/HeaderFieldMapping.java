package com.abner.codebase.reporting.infra.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.supercsv.cellprocessor.ift.CellProcessor;

import com.abner.codebase.reporting.infra.FieldMapping;
import com.abner.codebase.reporting.infra.FieldMappingItem;

public class HeaderFieldMapping implements FieldMapping<String> {

	private Map<String, FieldMappingItem> fieldMappingItems = new LinkedHashMap<>();
	private List<String> columns = new ArrayList<>();
	private List<String> fields = new ArrayList<>();
	private List<CellProcessor> cellProcessors = new ArrayList<>();
	
	@Override
	public FieldMappingItem map(String header) {
		FieldMappingItem fieldMappingItem = new DefaultFieldMappingItem();
		fieldMappingItems.put(header, fieldMappingItem);
		return fieldMappingItem;
	}

	@Override
	public String[] fields() {
		if(fields.isEmpty()) {
			populateFieldsConfig();
		}
		return fields.toArray(new String[fieldMappingItems.size()]);
	}

	@Override
	public String[] columns() {
		if(fields.isEmpty()) {
			populateFieldsConfig();
		}
		return columns.toArray(new String[fieldMappingItems.size()]);
	}

	@Override
	public CellProcessor[] cellProcessors() {
		if(fields.isEmpty()) {
			populateFieldsConfig();
		}
		return cellProcessors.toArray(new CellProcessor[fieldMappingItems.size()]);
	}
	
	private void populateFieldsConfig() {
		fieldMappingItems.entrySet().forEach(entry -> {
			columns.add(entry.getKey());
			fields.add(entry.getValue().getFieldName());
			cellProcessors.add(entry.getValue().getCellProcessor());
		});
	}
	
}
