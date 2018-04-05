package com.abner.codebase.reporting.infra;

import com.abner.codebase.reporting.service.Service;

public interface DomainParser extends Service{
	<T extends Domain> DomainDescriber<T> parser(Class<T> domain);
}
