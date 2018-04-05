package com.abner.codebase.reporting.service;

import java.util.List;

import com.abner.codebase.reporting.infra.Domain;

public interface ReportingService {
	<T extends Domain> List<T> generateReport(String domainName);
	
	<T extends Domain> List<T> generateReport(Class<? extends T> domain);
}
