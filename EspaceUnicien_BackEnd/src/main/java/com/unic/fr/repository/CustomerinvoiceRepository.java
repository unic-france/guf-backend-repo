package com.unic.fr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guf.batch.data.entity.Customerinvoice;

public interface CustomerinvoiceRepository extends JpaRepository<Customerinvoice, Long> {
	
	public Customerinvoice getCustomerinvoiceBycustomerinvoicereferencenumber(String customerinvoicereferencenumber);
	
}
