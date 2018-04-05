package com.abner.codebase.reporting.infra.impl;

import java.lang.reflect.Field;
import java.text.FieldPosition;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

import com.abner.codebase.reporting.config.FileConfigurationProvider;
import com.abner.codebase.reporting.infra.AutoMapping;
import com.abner.codebase.reporting.infra.CellProcessorProvider;
import com.abner.codebase.reporting.infra.Column;
import com.abner.codebase.reporting.infra.Domain;
import com.abner.codebase.reporting.infra.FieldMapping;
import com.sun.beans.finder.ClassFinder;
import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;
import com.sun.xml.internal.bind.v2.model.annotation.FieldLocatable;
import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;

import sun.swing.MenuItemLayoutHelper.ColumnAlignment;

enum FieldMappingType implements AutoMapping{

	FIELD_ORDER {
		@Override
		public FieldMappings getDefaultFieldMappings(Field[] fields) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	
	ANNOTATION {
		
		@Override
		public FieldMappings getDefaultFieldMappings(Field[] fields) {
			return mapToAnnotation(fields);
		}
		
		private FieldMappings mapToAnnotation(Field[] fields){
			int columnCount = getColumnCount(fields);
			FieldMapping<Integer> indexMapping = new DefaultFieldMapping(columnCount);
			FieldMapping<String> headerMapping = new HeaderFieldMapping();
			FieldMappings result = new FieldMappings(indexMapping, headerMapping);
			if(columnCount == 0){
				return result;
			}
			for(Field field : fields){
				Column column = field.getAnnotation(Column.class);
				if(column == null)
					continue;
				Integer index = column.index();
				String fieldName = field.getName();
				String header = column.header().isEmpty() ? fieldName : column.header();
				//use customized processors if they are present
				if(checkCellProcessorProvider(column)) {
					CellProcessorProvider cellProcessorProvider = applicationContext.
				}
				
				
			}
			return result;
			
		}
		
		private int getColumnCount(Field[] fields) {
			Class<?> klass = fields[0].getDeclaringClass();
			Class<? extends Domain> domainClass = (Class<? extends Domain>) klass;
			//Class<? extends Domain> domain = getInterface(domainClass);
			return FileConfigurationProvider.getFileConfiguration().getFileDescriber(domainClass).getColumnCount();
		}
		
		private boolean checkCellProcessorProvider(Column column) {
			return column.cellProcessorProvider() != EmptyCellProcessorProvider.class;
		}
	};
	
	private static ApplicationContext applicationContext;
	
	static void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}
	
	private static void mapFieldWithIndex(FieldMappings fieldMappings, Field field, Integer index, String header) {
		Class<?> type = field.getType();
		String fieldName = field.getName();
		if(String.class.equals(type)) {
			fieldMappings.indexMapping.map(index).to(fieldName).asString();
			fieldMappings.headerMapping.map(header).to(fieldName).asString();
		}else if(Integer.class.equals(type) || int.class.equals(type)) {
			fieldMappings.indexMapping.map(index).to(fieldName).asInteger();
			fieldMappings.headerMapping.map(header).to(fieldName).asString();
		}else if(Double.class.equals(type) || double.class.equals(type)) {
			fieldMappings.indexMapping.map(index).to(fieldName).asDouble();
			fieldMappings.headerMapping.map(header).to(fieldName).asDoubleString("#0.00##########");
		}else if(Boolean.class.equals(type) || boolean.class.equals(type)) {
			fieldMappings.indexMapping.map(index).to(fieldName).asBoolean();
			fieldMappings.headerMapping.map(header).to(fieldName).asBoolString();
		}else{
			throw new RuntimeException(type.toString() + " not been implemented in " + field.getDeclaringClass());
		}
	}
	
	private static void mapFieldWithIndex(FieldMappings fieldMappings, Field field, Integer index) {
		mapFieldWithIndex(fieldMappings, field, index, field.getName());
	}
}
