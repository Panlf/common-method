package com.plf.excel;

/**
 * 技能信息模型
 * @author plf 2017年6月12日下午9:45:24
 *
 */
public class SkillInfo {
	private String company;
	private String year;
	private String info;
	
	public SkillInfo(String company, String year, String info) {
		super();
		this.company = company;
		this.year = year;
		this.info = info;
	}
	
	
	
	public SkillInfo() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
}