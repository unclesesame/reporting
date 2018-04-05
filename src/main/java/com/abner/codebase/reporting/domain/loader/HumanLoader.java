package com.abner.codebase.reporting.domain.loader;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Named;

import com.abner.codebase.reporting.domain.Employee;
import com.abner.codebase.reporting.domain.Human;
import com.abner.codebase.reporting.domain.Student;
import com.abner.codebase.reporting.domain.impl.HumanImpl;

@Named
public class HumanLoader extends AbstractLoader<Human>{
	@Override
	protected List<Human> stitch() {
		Collection<Student> students = cacheManager.getCache().get(Student.class);
		Collection<Employee> employees = cacheManager.getCache().get(Employee.class);
		return Stream.concat(students.stream().map(this::mapStudent), 
				employees.stream().map(this::mapEmployee)).collect(Collectors.toList());
	}
	
	private Human mapStudent(Student student) {
		HumanImpl human = new HumanImpl();
		human.setAge(student.getAge());
		human.setName(student.getName());
		human.setSex(student.getSex());
		return human;
	}
	
	private Human mapEmployee(Employee employee) {
		HumanImpl human = new HumanImpl();
		human.setAge(employee.getAge());
		human.setName(employee.getName());
		human.setSex(employee.getSex());
		return human;
	}
}
