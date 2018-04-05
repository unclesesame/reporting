package com.abner.codebase.reporting.infra.impl;

import javax.inject.Named;

import org.supercsv.cellprocessor.ift.CellProcessor;

import com.abner.codebase.reporting.infra.CellProcessorProvider;
@Named
public class EmptyCellProcessorProvider implements CellProcessorProvider{

	@Override
	public CellProcessor inputProcessor() {
		return null;
	}

	@Override
	public CellProcessor outputProcessor() {
		return null;
	}

}
