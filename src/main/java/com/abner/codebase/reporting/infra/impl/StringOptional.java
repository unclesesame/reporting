package com.abner.codebase.reporting.infra.impl;

import org.apache.commons.lang3.StringUtils;
import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class StringOptional extends ConvertNullTo{
	public StringOptional(final CellProcessor next) {
		super(StringUtils.EMPTY, next);
	}
}
