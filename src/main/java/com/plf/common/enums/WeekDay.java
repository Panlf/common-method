package com.plf.common.enums;

public enum WeekDay {
	
	SUN("星期日"),MON("星期一"),TUE("星期二"),WED("星期三"),THI("星期四"),FRI("星期五"),SAT("星期六");
	
	WeekDay(){}
	
	private String name;
	
	WeekDay(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}	
}
