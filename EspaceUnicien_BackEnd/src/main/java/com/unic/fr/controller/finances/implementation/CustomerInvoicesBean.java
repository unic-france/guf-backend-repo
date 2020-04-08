package com.unic.fr.controller.finances.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.guf.batch.data.entity.Customerinvoice;
import com.unic.fr.repository.CustomerinvoiceRepository;

@Configuration
public class CustomerInvoicesBean {
	
	@Autowired
	CustomerinvoiceRepository customerInvoiceRepository;
	
	public List<Customerinvoice> getCustomerInvoices (int assignmentreferencenumber){
		
		
		return null;
	}

}
