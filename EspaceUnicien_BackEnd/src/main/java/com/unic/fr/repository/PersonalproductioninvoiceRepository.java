package com.unic.fr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.guf.batch.data.entity.Personalproductioninvoice;
import java.util.List;

public interface PersonalproductioninvoiceRepository extends JpaRepository<Personalproductioninvoice, Integer> {
	
	@Query("SELECT pp FROM Personalproductioninvoice pp JOIN pp.personalprodpinvoicestatushistories pph WHERE pp.idissuer=:pIdPartner and MAX(pph.status)=:pStatus")
	public List<Personalproductioninvoice> findPersonalProductionInvoices(@Param("pIdPartner") Integer pIdPartner,@Param("pStatus") String pStatus);
}
