package com.unic.fr.dto;

import java.sql.Timestamp;

public class PersonalproductioninvoicestatushistoryDto {
	
	private int idppinvoicestatushistory;
	
	private Timestamp datecreation;
	
	private String status;

	public int getIdppinvoicestatushistory() {
		return idppinvoicestatushistory;
	}

	public void setIdppinvoicestatushistory(int idppinvoicestatushistory) {
		this.idppinvoicestatushistory = idppinvoicestatushistory;
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
