package com.unic.fr.dto;

import java.util.Date;
import java.util.List;

public class PartnerprofileDto {
	
	private Integer idpartnerprofile;

	private byte[] cv;

	private Date dateofbirth;

	private String firstname;

	private String linkedinurl;

	private String name;

	private byte[] picture;

	private String summary;

	private String title;

	private List<PartnerpreferenceDto> partnerpreferences;

	private List<SkillDto> skills;
	
	private PartnercontactDto partnerContact;

	public Integer getIdpartnerprofile() {
		return idpartnerprofile;
	}

	public void setIdpartnerprofile(Integer idpartnerprofile) {
		this.idpartnerprofile = idpartnerprofile;
	}

	public byte[] getCv() {
		return cv;
	}

	public void setCv(byte[] cv) {
		this.cv = cv;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLinkedinurl() {
		return linkedinurl;
	}

	public void setLinkedinurl(String linkedinurl) {
		this.linkedinurl = linkedinurl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<PartnerpreferenceDto> getPartnerpreferences() {
		return partnerpreferences;
	}

	public void setPartnerpreferences(List<PartnerpreferenceDto> partnerpreferences) {
		this.partnerpreferences = partnerpreferences;
	}

	public List<SkillDto> getSkills() {
		return skills;
	}

	public void setSkills(List<SkillDto> skills) {
		this.skills = skills;
	}

	public PartnercontactDto getPartnerContact() {
		return partnerContact;
	}

	public void setPartnerContact(PartnercontactDto partnerContact) {
		this.partnerContact = partnerContact;
	}
}
