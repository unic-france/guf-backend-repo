package com.unic.fr.dto;

import java.math.BigDecimal;

public class RefworkingdaysDto {

	private String month;
	private String year;
	private BigDecimal workingdays;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public BigDecimal getWorkingdays() {
		return workingdays;
	}
	public void setWorkingdays(BigDecimal workingdays) {
		this.workingdays = workingdays;
	}
}
