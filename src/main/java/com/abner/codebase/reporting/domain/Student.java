package com.abner.codebase.reporting.domain;

import com.abner.codebase.reporting.infra.Domain;

public interface Student extends Domain{
	String getName();
	Integer getAge();
	String getSex();
}
