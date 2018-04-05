package com.abner.codebase.reporting.domain;

import com.abner.codebase.reporting.infra.Domain;

public interface Employee extends Domain{
	String getName();
	Integer getAge();
	String getSex();
}
