package com.plf.common.reflect;

import java.util.Date;

public class Employee {

	@JsonIgnore
	private Integer id;
	
	@JsonProperty("username")
	private String name;
	

	private Double salary;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date workDate;
	
	public Employee() {
	}

	public Employee(Integer id, String name, Double salary, Date workDate) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.workDate = workDate;
	}



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Date getWorkDate() {
		return workDate;
	}
	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", workDate=" + workDate + "]";
	}
	
	
}
