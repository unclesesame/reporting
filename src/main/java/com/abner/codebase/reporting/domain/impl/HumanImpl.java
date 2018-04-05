package com.abner.codebase.reporting.domain.impl;

import com.abner.codebase.reporting.domain.Human;

public class HumanImpl implements Human{
	private String name;
	private String sex;
	private Integer age;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getAge() {
		return age;
	}

	@Override
	public String getSex() {
		return sex;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
