package com.abner.codebase.reporting.infra;

import java.util.List;

public interface CsvReader<T> {
	List<T> read();
}
