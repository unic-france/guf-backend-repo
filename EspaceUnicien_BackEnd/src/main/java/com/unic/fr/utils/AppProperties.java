package com.unic.fr.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.math.BigDecimal;

@Configuration
@PropertySource("classpath:unic.properties")
public class AppProperties {
	
	@Value("${customerinvoice.ppirn}")
	private String personalProductionInvoiceReferenceNumber;
	
	
	@Value("#{T(java.lang.Float).parseFloat('${customerinvoice.taxrate}')}")
	private BigDecimal taxrate;
	
	@Value("${customerinvoice.currency}") 
	private String currency;
	
	
	@Value("${customerinvoicestatushistory.partnerCreation}") 
	private String partnerCreation;
	
	@Value("${customerinvoicestatushistory.groupvalidationongoing}") 
	private String groupvalidationongoing;
	
	
	//GETTER and SETTER
	
	public String getPersonalProductionInvoiceReferenceNumber() {
		return personalProductionInvoiceReferenceNumber;
	}

	public BigDecimal getTaxrate() {
		return taxrate;
	}

	public String getCurrency() {
		return currency;
	}

	public String getPartnerCreation() {
		return partnerCreation;
	}

	public String getGroupvalidationongoing() {
		return groupvalidationongoing;
	}

}
