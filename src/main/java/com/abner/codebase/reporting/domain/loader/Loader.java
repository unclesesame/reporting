package com.abner.codebase.reporting.domain.loader;

import java.util.List;

import com.abner.codebase.reporting.infra.Domain;

public interface Loader<T extends Domain> {
	List<T> load();
}
