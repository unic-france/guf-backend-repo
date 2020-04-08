package com.unic.fr.dto;

import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

public class AssignmentDto {
	
	private Integer idassignment;

	private Boolean activityreportcustomercompulsory;

	private String assignmentreferencenumber;

	private Boolean businessopportunitybroughtactive;

	private String countrycode;

	private String currency;

	private Date dateofbeginning;

	private Date dateofend;

	private BigDecimal daypricewithouttax;

	private String descriptionfull;

	private String descriptionshort;

	private String functionalscope;

	private Boolean portage;

	private String position;

	private Boolean tacitrenewal;
	
	private AddressDto address;
	
	private CustomerDto customer1;
	
	private List<CustomerinvoiceDto> customerInvoices;
	
	//private 
	
	private List<AssignmentstatushistoryDto> assignmentStatusHistories;
	
	public Integer getIdassignment() {
		return idassignment;
	}

	public void setIdassignment(Integer idassignment) {
		this.idassignment = idassignment;
	}

	public Boolean getActivityreportcustomercompulsory() {
		return activityreportcustomercompulsory;
	}

	public void setActivityreportcustomercompulsory(Boolean activityreportcustomercompulsory) {
		this.activityreportcustomercompulsory = activityreportcustomercompulsory;
	}

	public String getAssignmentreferencenumber() {
		return assignmentreferencenumber;
	}

	public void setAssignmentreferencenumber(String assignmentreferencenumber) {
		this.assignmentreferencenumber = assignmentreferencenumber;
	}

	public Boolean getBusinessopportunitybroughtactive() {
		return businessopportunitybroughtactive;
	}

	public void setBusinessopportunitybroughtactive(Boolean businessopportunitybroughtactive) {
		this.businessopportunitybroughtactive = businessopportunitybroughtactive;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getDateofbeginning() {
		return dateofbeginning;
	}

	public void setDateofbeginning(Date dateofbeginning) {
		this.dateofbeginning = dateofbeginning;
	}

	public Date getDateofend() {
		return dateofend;
	}

	public void setDateofend(Date dateofend) {
		this.dateofend = dateofend;
	}

	public BigDecimal getDaypricewithouttax() {
		return daypricewithouttax;
	}

	public void setDaypricewithouttax(BigDecimal daypricewithouttax) {
		this.daypricewithouttax = daypricewithouttax;
	}

	public String getDescriptionfull() {
		return descriptionfull;
	}

	public void setDescriptionfull(String descriptionfull) {
		this.descriptionfull = descriptionfull;
	}

	public String getDescriptionshort() {
		return descriptionshort;
	}

	public void setDescriptionshort(String descriptionshort) {
		this.descriptionshort = descriptionshort;
	}

	public String getFunctionalscope() {
		return functionalscope;
	}

	public void setFunctionalscope(String functionalscope) {
		this.functionalscope = functionalscope;
	}

	public Boolean getPortage() {
		return portage;
	}

	public void setPortage(Boolean portage) {
		this.portage = portage;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Boolean getTacitrenewal() {
		return tacitrenewal;
	}

	public void setTacitrenewal(Boolean tacitrenewal) {
		this.tacitrenewal = tacitrenewal;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}

	public CustomerDto getCustomer1() {
		return customer1;
	}

	public void setCustomer1(CustomerDto customer1) {
		this.customer1 = customer1;
	}

	public List<CustomerinvoiceDto> getCustomerInvoices() {
		return customerInvoices;
	}

	public void setCustomerInvoices(List<CustomerinvoiceDto> customerInvoices) {
		this.customerInvoices = customerInvoices;
	}

	public List<AssignmentstatushistoryDto> getAssignmentStatusHistories() {
		return assignmentStatusHistories;
	}

	public void setAssignmentStatusHistories(List<AssignmentstatushistoryDto> assignmentStatusHistories) {
		this.assignmentStatusHistories = assignmentStatusHistories;
	}

}
