package com.abner.codebase.reporting.infra;

import org.supercsv.cellprocessor.ift.CellProcessor;

public interface CellProcessorProvider {
	CellProcessor inputProcessor();
	CellProcessor outputProcessor();
}
