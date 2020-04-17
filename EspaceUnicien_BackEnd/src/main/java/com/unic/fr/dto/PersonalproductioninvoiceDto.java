package com.unic.fr.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PersonalproductioninvoiceDto {
	
	private String currency;
	
	private byte[] personalproductioninvoicedocument;
	
	private String personalproductioninvoicedocumentname;
	
	private Date duedate;
	
	private String personalproductioninvoicereferencenumber;

	private BigDecimal taxamount;
	
	private BigDecimal taxrate;
	
	private BigDecimal totalamountwithouttax;
	
	private BigDecimal totalamountwithtax;
	
	private List<PersonalproductioninvoicestatushistoryDto> personalproductioninvoicestatushistories;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public byte[] getPersonalproductioninvoicedocument() {
		return personalproductioninvoicedocument;
	}

	public void setPersonalproductioninvoicedocument(byte[] personalproductioninvoicedocument) {
		this.personalproductioninvoicedocument = personalproductioninvoicedocument;
	}

	public String getPersonalproductioninvoicedocumentname() {
		return personalproductioninvoicedocumentname;
	}

	public void setPersonalproductioninvoicedocumentname(String personalproductioninvoicedocumentname) {
		this.personalproductioninvoicedocumentname = personalproductioninvoicedocumentname;
	}

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public String getPersonalproductioninvoicereferencenumber() {
		return personalproductioninvoicereferencenumber;
	}

	public void setPersonalproductioninvoicereferencenumber(String personalproductioninvoicereferencenumber) {
		this.personalproductioninvoicereferencenumber = personalproductioninvoicereferencenumber;
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

	public List<PersonalproductioninvoicestatushistoryDto> getPersonalproductioninvoicestatushistories() {
		return personalproductioninvoicestatushistories;
	}

	public void setPp(List<PersonalproductioninvoicestatushistoryDto> personalproductioninvoicestatushistories) {
		this.personalproductioninvoicestatushistories = personalproductioninvoicestatushistories;
	}
	
	

}
