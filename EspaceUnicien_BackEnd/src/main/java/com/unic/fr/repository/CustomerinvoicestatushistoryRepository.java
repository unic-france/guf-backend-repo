package com.unic.fr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guf.batch.data.entity.Customerinvoice;
import com.guf.batch.data.entity.Customerinvoicestatushistory;

public interface CustomerinvoicestatushistoryRepository extends JpaRepository<Customerinvoicestatushistory, Long> {
	
	List<Customerinvoicestatushistory> getByCustomerinvoice(Customerinvoice customerinvoice);
}
