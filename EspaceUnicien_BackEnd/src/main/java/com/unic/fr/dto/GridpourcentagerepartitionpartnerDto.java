package com.unic.fr.dto;

import java.util.Date;
import java.util.List;

import com.guf.batch.data.entity.Groupmodel;
import com.guf.batch.data.entity.Recurrencegroup;

public class GridpourcentagerepartitionpartnerDto {
	
	private boolean current;
	
	private Date date;
	
	private List<BusinessopportunitybroughtDto> businessopportunitybroughts;
	
	private List<CustomerinvoiceDto> customerinvoices;

	private GridpourcentagerepartitionDto gridpourcentagerepartition;

	private PartnerDto partner;

	private List<Groupmodel> groupmodels;

	private List<Recurrencegroup> recurrencegroups;

	private List<Recurrencegroup> recurrencelevel1s;

	private List<Recurrencegroup> recurrencelevel2s;

	private List<Recurrencegroup> recurrencelevel3s;

	private List<Recurrencegroup> recurrencelevel4s;

	private List<Recurrencegroup> recurrencelevel5s;

	public boolean isCurrent() {
		
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<BusinessopportunitybroughtDto> getBusinessopportunitybroughts() {
		return businessopportunitybroughts;
	}

	public void setBusinessopportunitybroughts(List<BusinessopportunitybroughtDto> businessopportunitybroughts) {
		this.businessopportunitybroughts = businessopportunitybroughts;
	}

	public List<CustomerinvoiceDto> getCustomerinvoices() {
		return customerinvoices;
	}

	public void setCustomerinvoices(List<CustomerinvoiceDto> customerinvoices) {
		this.customerinvoices = customerinvoices;
	}

	public GridpourcentagerepartitionDto getGridpourcentagerepartition() {
		return gridpourcentagerepartition;
	}

	public void setGridpourcentagerepartition(GridpourcentagerepartitionDto gridpourcentagerepartition) {
		this.gridpourcentagerepartition = gridpourcentagerepartition;
	}

	public PartnerDto getPartner() {
		return partner;
	}

	public void setPartner(PartnerDto partner) {
		this.partner = partner;
	}

	public List<Groupmodel> getGroupmodels() {
		return groupmodels;
	}

	public void setGroupmodels(List<Groupmodel> groupmodels) {
		this.groupmodels = groupmodels;
	}

	public List<Recurrencegroup> getRecurrencegroups() {
		return recurrencegroups;
	}

	public void setRecurrencegroups(List<Recurrencegroup> recurrencegroups) {
		this.recurrencegroups = recurrencegroups;
	}

	public List<Recurrencegroup> getRecurrencelevel1s() {
		return recurrencelevel1s;
	}

	public void setRecurrencelevel1s(List<Recurrencegroup> recurrencelevel1s) {
		this.recurrencelevel1s = recurrencelevel1s;
	}

	public List<Recurrencegroup> getRecurrencelevel2s() {
		return recurrencelevel2s;
	}

	public void setRecurrencelevel2s(List<Recurrencegroup> recurrencelevel2s) {
		this.recurrencelevel2s = recurrencelevel2s;
	}

	public List<Recurrencegroup> getRecurrencelevel3s() {
		return recurrencelevel3s;
	}

	public void setRecurrencelevel3s(List<Recurrencegroup> recurrencelevel3s) {
		this.recurrencelevel3s = recurrencelevel3s;
	}

	public List<Recurrencegroup> getRecurrencelevel4s() {
		return recurrencelevel4s;
	}

	public void setRecurrencelevel4s(List<Recurrencegroup> recurrencelevel4s) {
		this.recurrencelevel4s = recurrencelevel4s;
	}

	public List<Recurrencegroup> getRecurrencelevel5s() {
		return recurrencelevel5s;
	}

	public void setRecurrencelevel5s(List<Recurrencegroup> recurrencelevel5s) {
		this.recurrencelevel5s = recurrencelevel5s;
	}
	  
}
