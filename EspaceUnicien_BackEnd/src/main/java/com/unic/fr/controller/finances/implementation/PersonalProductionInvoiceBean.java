package com.unic.fr.controller.finances.implementation;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import com.guf.batch.data.entity.Partner;
import com.guf.batch.data.entity.Personalproductioninvoice;
import com.unic.fr.dto.PersonalproductioninvoiceDto;
import com.unic.fr.exception.TechnicalException;
import com.unic.fr.repository.PersonalproductioninvoiceRepository;
import com.unic.fr.utils.AppProperties;

public class PersonalProductionInvoiceBean {
	
	@Autowired
	AppProperties prop;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	PersonalproductioninvoiceRepository personalproductioninvoiceRepository;
	
	public List<PersonalproductioninvoiceDto> getPersonelProdCustomerInvoices (Partner partner) throws TechnicalException {
		
		List<PersonalproductioninvoiceDto> personalProdInvoicesDto = null;
		List<Personalproductioninvoice> personalProdInvoices = null;
		
		try {
			
			personalProdInvoices = personalproductioninvoiceRepository.findPersonalProductionInvoices(partner.getIdpartner(),prop.getPpCustomersent());
			
			personalProdInvoicesDto = modelMapper.map(personalProdInvoices, new TypeToken<List<PersonalproductioninvoiceDto>>(){}.getType());
			
		} catch(Exception e) {
			
			throw new TechnicalException(e.getMessage());
		}
		
		return personalProdInvoicesDto;
	}
}
