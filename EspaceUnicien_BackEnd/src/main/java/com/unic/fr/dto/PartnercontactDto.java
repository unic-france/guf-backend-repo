package com.unic.fr.dto;

public class PartnercontactDto {

	private Integer idpartnercontact;

	private String emailgroup;

	private String emailpartner;

	private String mobilephonenumber;

	private String phonenumber;

	public Integer getIdpartnercontact() {
		return idpartnercontact;
	}

	public void setIdpartnercontact(Integer idpartnercontact) {
		this.idpartnercontact = idpartnercontact;
	}

	public String getEmailgroup() {
		return emailgroup;
	}

	public void setEmailgroup(String emailgroup) {
		this.emailgroup = emailgroup;
	}

	public String getEmailpartner() {
		return emailpartner;
	}

	public void setEmailpartner(String emailpartner) {
		this.emailpartner = emailpartner;
	}

	public String getMobilephonenumber() {
		return mobilephonenumber;
	}

	public void setMobilephonenumber(String mobilephonenumber) {
		this.mobilephonenumber = mobilephonenumber;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
}
