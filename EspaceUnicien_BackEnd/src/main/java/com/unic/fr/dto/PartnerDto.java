package com.unic.fr.dto;

import java.util.List;

public class PartnerDto {
	
	private Integer idpartner;

	private String puid;
	
	private List<PartnernetworkDto> partnernetworks;
	
	private PartnerprofileDto partnerprofile;
	
	private PartnercompanyDto partnercompany;
	
	//private List<AssignmentDto> assignments1;
	
	//private List<AssignmentDto> assignments2;
	
	private List<PartnerDto> filleuls1;
	
	private List<PartnerDto> filleuls2;
	
	private List<PartnerDto> filleuls3;
	
	private List<PartnerDto> filleuls4;
	
	private List<PartnerDto> filleuls5;

	public Integer getIdpartner() {
		return idpartner;
	}

	public void setIdpartner(Integer idpartner) {
		this.idpartner = idpartner;
	}

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

	public PartnercompanyDto getPartnercompany() {
		return partnercompany;
	}

	public void setPartnercompany(PartnercompanyDto partnercompany) {
		this.partnercompany = partnercompany;
	}

	/*public List<AssignmentDto> getAssignments1() {
		return assignments1;
	}

	public void setAssignments1(List<AssignmentDto> assignments1) {
		this.assignments1 = assignments1;
	}

	public List<AssignmentDto> getAssignments2() {
		return assignments2;
	}

	public void setAssignments2(List<AssignmentDto> assignments2) {
		this.assignments2 = assignments2;
	}
*/
	public List<PartnerDto> getFilleuls1() {
		return filleuls1;
	}

	public void setFilleuls1(List<PartnerDto> filleuls1) {
		this.filleuls1 = filleuls1;
	}

	public List<PartnerDto> getFilleuls2() {
		return filleuls2;
	}

	public void setFilleuls2(List<PartnerDto> filleuls2) {
		this.filleuls2 = filleuls2;
	}

	public List<PartnerDto> getFilleuls3() {
		return filleuls3;
	}

	public void setFilleuls3(List<PartnerDto> filleuls3) {
		this.filleuls3 = filleuls3;
	}

	public List<PartnerDto> getFilleuls4() {
		return filleuls4;
	}

	public void setFilleuls4(List<PartnerDto> filleuls4) {
		this.filleuls4 = filleuls4;
	}

	public List<PartnerDto> getFilleuls5() {
		return filleuls5;
	}

	public void setFilleuls5(List<PartnerDto> filleuls5) {
		this.filleuls5 = filleuls5;
	}

	
	public List<PartnernetworkDto> getPartnernetworks() {
		return partnernetworks;
	}

	public void setPartnernetworks(List<PartnernetworkDto> partnernetworks) {
		this.partnernetworks = partnernetworks;
	}

	public PartnerprofileDto getPartnerprofile() {
		return partnerprofile;
	}

	public void setPartnerprofile(PartnerprofileDto partnerprofile) {
		this.partnerprofile = partnerprofile;
	}

}
