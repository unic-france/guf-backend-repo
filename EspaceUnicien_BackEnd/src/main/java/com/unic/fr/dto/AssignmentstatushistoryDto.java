package com.unic.fr.dto;

import java.sql.Timestamp;

public class AssignmentstatushistoryDto {

	private Integer idassignmentstatushistory;

	private Timestamp datecreation;

	private String status;

	public Integer getIdassignmentstatushistory() {
		return idassignmentstatushistory;
	}

	public void setIdassignmentstatushistory(Integer idassignmentstatushistory) {
		this.idassignmentstatushistory = idassignmentstatushistory;
	}

	public Timestamp getDatecreation() {
		return datecreation;
	}

	public void setDatecreation(Timestamp datecreation) {
		this.datecreation = datecreation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
