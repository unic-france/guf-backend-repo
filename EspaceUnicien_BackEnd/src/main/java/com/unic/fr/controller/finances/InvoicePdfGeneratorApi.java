package com.unic.fr.controller.finances;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unic.fr.exception.TechnicalException;
import com.unic.fr.pdf.CustomerInvoicePdfGenerator;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class InvoicePdfGeneratorApi {
	
	@Autowired
	CustomerInvoicePdfGenerator customerInvoicePdfGenerator;
	
	@PostMapping("/api/invoicePdf/generator")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> invoicePdfGenerator(@RequestParam("customerinvoicereferencenumber") String customerinvoicereferencenumber,
													  @RequestParam("forcedGeneration") boolean forcedGeneration) {
		ResponseEntity<String> response = null;
		
		System.out.println("CUSTOMER INVOICE PDF");
		
		customerInvoicePdfGenerator.init(customerinvoicereferencenumber);
		
		try {
			response =  customerInvoicePdfGenerator.generateCustomerInvoice(forcedGeneration);
			
		} catch(TechnicalException e) {
			System.out.println(e.getMessage());
			response = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		} finally {
			
			System.out.println(response.getBody());
		}
		return response;
	}
}
