package com.unic.fr.dto;

import java.util.Date;
import java.math.BigDecimal;

public class CustomerinvoiceDto {
	
	private Integer idcustomerinvoice;

	private byte[] activityreportdocument;

	private String currency;

	private String customerinvoicereferencenumber;

	private byte[] customerinvoicedocument;

	private BigDecimal dueamount;

	private Date duedate;

	private BigDecimal numberofdaysworked;

	private String period;

	private BigDecimal taxamount;

	private BigDecimal taxrate;

	private BigDecimal totalamountwithouttax;

	private BigDecimal totalamountwithtax;

	public Integer getIdcustomerinvoice() {
		return idcustomerinvoice;
	}

	public void setIdcustomerinvoice(Integer idcustomerinvoice) {
		this.idcustomerinvoice = idcustomerinvoice;
	}

	public byte[] getActivityreportdocument() {
		return activityreportdocument;
	}

	public void setActivityreportdocument(byte[] activityreportdocument) {
		this.activityreportdocument = activityreportdocument;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCustomerinvoicereferencenumber() {
		return customerinvoicereferencenumber;
	}

	public void setCustomerinvoicereferencenumber(String customerinvoicereferencenumber) {
		this.customerinvoicereferencenumber = customerinvoicereferencenumber;
	}

	public byte[] getCustomerinvoicedocument() {
		return customerinvoicedocument;
	}

	public void setCustomerinvoicedocument(byte[] customerinvoicedocument) {
		this.customerinvoicedocument = customerinvoicedocument;
	}

	public BigDecimal getDueamount() {
		return dueamount;
	}

	public void setDueamount(BigDecimal dueamount) {
		this.dueamount = dueamount;
	}

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public BigDecimal getNumberofdaysworked() {
		return numberofdaysworked;
	}

	public void setNumberofdaysworked(BigDecimal numberofdaysworked) {
		this.numberofdaysworked = numberofdaysworked;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public BigDecimal getTaxamount() {
		return taxamount;
	}

	public void setTaxamount(BigDecimal taxamount) {
		this.taxamount = taxamount;
	}

	public BigDecimal getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(BigDecimal taxrate) {
		this.taxrate = taxrate;
	}

	public BigDecimal getTotalamountwithouttax() {
		return totalamountwithouttax;
	}

	public void setTotalamountwithouttax(BigDecimal totalamountwithouttax) {
		this.totalamountwithouttax = totalamountwithouttax;
	}

	public BigDecimal getTotalamountwithtax() {
		return totalamountwithtax;
	}

	public void setTotalamountwithtax(BigDecimal totalamountwithtax) {
		this.totalamountwithtax = totalamountwithtax;
	}

}
