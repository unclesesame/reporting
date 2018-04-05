package com.abner.codebase.reporting.domain.dumper;

import java.util.Collection;

import com.abner.codebase.reporting.infra.Domain;

public interface FileDumper<T extends Domain> {
	void dumpToFile(Collection<T> instances);
}
