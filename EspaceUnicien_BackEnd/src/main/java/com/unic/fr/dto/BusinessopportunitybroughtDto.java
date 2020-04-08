package com.unic.fr.dto;

import java.math.BigDecimal;
import java.util.List;

import com.guf.batch.data.entity.Businessopportunitybroughtinvoice;
import com.guf.batch.data.entity.Businessopportunitybroughtstatushistory;

public class BusinessopportunitybroughtDto {
	
	private int idbusinessopportunitybrought;
	
	private BigDecimal amountwithouttax;

	private BigDecimal amountwithtax;

	private String currency;

	private BigDecimal taxamount;

	private BigDecimal taxrate;

	private PartnerDto partner;
	  
	private List<Businessopportunitybroughtinvoice> businessopportunitybroughtinvoices;

	private List<Businessopportunitybroughtstatushistory> businessopportunitybroughtstatushistories;

	private List<CustomerinvoiceDto> customerinvoices;

	public int getIdbusinessopportunitybrought() {
		return idbusinessopportunitybrought;
	}

	public void setIdbusinessopportunitybrought(int idbusinessopportunitybrought) {
		this.idbusinessopportunitybrought = idbusinessopportunitybrought;
	}

	public BigDecimal getAmountwithouttax() {
		return amountwithouttax;
	}

	public void setAmountwithouttax(BigDecimal amountwithouttax) {
		this.amountwithouttax = amountwithouttax;
	}

	public BigDecimal getAmountwithtax() {
		return amountwithtax;
	}

	public void setAmountwithtax(BigDecimal amountwithtax) {
		this.amountwithtax = amountwithtax;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public PartnerDto getPartner() {
		return partner;
	}

	public void setPartner(PartnerDto partner) {
		this.partner = partner;
	}

	public List<Businessopportunitybroughtinvoice> getBusinessopportunitybroughtinvoices() {
		return businessopportunitybroughtinvoices;
	}

	public void setBusinessopportunitybroughtinvoices(
			List<Businessopportunitybroughtinvoice> businessopportunitybroughtinvoices) {
		this.businessopportunitybroughtinvoices = businessopportunitybroughtinvoices;
	}

	public List<Businessopportunitybroughtstatushistory> getBusinessopportunitybroughtstatushistories() {
		return businessopportunitybroughtstatushistories;
	}

	public void setBusinessopportunitybroughtstatushistories(
			List<Businessopportunitybroughtstatushistory> businessopportunitybroughtstatushistories) {
		this.businessopportunitybroughtstatushistories = businessopportunitybroughtstatushistories;
	}

	public List<CustomerinvoiceDto> getCustomerinvoices() {
		return customerinvoices;
	}

	public void setCustomerinvoices(List<CustomerinvoiceDto> customerinvoices) {
		this.customerinvoices = customerinvoices;
	}

}
