package com.unic.fr.controller.finances;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.guf.batch.data.entity.Partner;
import com.unic.fr.controller.finances.implementation.CreateCustomerInvoiceBean;
import com.unic.fr.controller.finances.implementation.PersonalProductionInvoiceBean;
import com.unic.fr.dto.PersonalproductioninvoiceDto;
import com.unic.fr.exception.TechnicalException;
import com.unic.fr.repository.AssignmentRepository;
import com.unic.fr.repository.CustomerinvoiceRepository;
import com.unic.fr.repository.CustomerinvoicestatushistoryRepository;
import com.unic.fr.repository.FlagcustomerinvoiceRepository;
import com.unic.fr.repository.GroupcompanyRepository;
import com.unic.fr.repository.PartnerRepository;
import com.unic.fr.repository.RefworkingdaysRepository;
import com.unic.fr.security.services.UserPrinciple;
import com.unic.fr.utils.AppProperties;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CustomerInvoiceRestApi {
	
//private static final Logger logger = LoggerFactory.getLogger(CustomerInvoiceRestApi.class);
	
	@Autowired
	PartnerRepository partnerRepository;
	
	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired
	GroupcompanyRepository groupCompanyRepository;
	
	@Autowired
	CustomerinvoiceRepository customerInvoiceRepository;
	
	@Autowired
	FlagcustomerinvoiceRepository flagcustomerinvoiceRepository;
	
	@Autowired
	CustomerinvoicestatushistoryRepository customerinvoicestatushistoryRepository;
	
	@Autowired
	AppProperties appProperties;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	CreateCustomerInvoiceBean createCustomerInvoiceBean;
	
	@Autowired
	PersonalProductionInvoiceBean ppInvoiceBean;
	
	@Autowired
	RefworkingdaysRepository refworkingdaysRepository;
	
	@PostMapping("/api/createCustomerInvoiceWithCra/")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> createCustomerInvoice(@RequestParam() String assignmentreferencenumber, 
														@RequestParam() String periodParam, 
														@RequestParam() int numberofdaysworked,
														@RequestParam() MultipartFile craFileUpload) {
		
		ResponseEntity<String> response = null;
		
		try {
			
			response = checkPrerequis(assignmentreferencenumber, periodParam, numberofdaysworked);
			System.out.println("Check prerequis response : "+response);
			
			if (null == response) {
				
				response = createCustomerInvoiceBean.createCustomerInvoiceWithCra(assignmentreferencenumber, periodParam, BigDecimal.valueOf(numberofdaysworked), craFileUpload);
			}
		
		} catch (TechnicalException e) {
			
			System.out.println(e.getMessage());
			response = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			response = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} finally {
			
			//System.out.println(response.getBody());
		}
			
		return response;
	}
	
	@PostMapping("/api/createCustomerInvoiceWithoutCra/")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> createCustomerInvoiceWithoutCra(@RequestParam() String assignmentreferencenumber, 
																  @RequestParam() String periodParam, 
																  @RequestParam() int numberofdaysworked) {

		ResponseEntity<String> response = null;
		
		try {
			
			response = checkPrerequis(assignmentreferencenumber, periodParam, numberofdaysworked);
			System.out.println("Check prerequis response : "+response);
			
			if (null == response) {
				
				response = createCustomerInvoiceBean.createCustomerInvoiceWithoutCra(assignmentreferencenumber, periodParam, BigDecimal.valueOf(numberofdaysworked));
			}
		
		} catch (TechnicalException e) {
			
			System.out.println(e.getMessage());
			response = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			response = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		} finally {
			
			//System.out.println(response.getBody());
		}
			
		return response;
		
	}
	
	ResponseEntity<String> checkPrerequis (String assignmentreferencenumber, String periodParam, int numberofdaysworked) throws TechnicalException{
	
		ResponseEntity<String> response = null;
		
		response = createCustomerInvoiceBean.checkCustomerInvoiceParameters(assignmentreferencenumber, periodParam, numberofdaysworked);
			
		return response;
	}
	
	
	@GetMapping("/api/getPersonalproductioninvoices/")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> getPersonalproductioninvoices() {

		ResponseEntity<?> responseEntity = null;
		
		List<PersonalproductioninvoiceDto> personalproductioninvoices = null;
		
		try {
			
			UserPrinciple user =  (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			Partner partner = partnerRepository.getByPuid(user.getUuid());
			
			personalproductioninvoices = ppInvoiceBean.getPersonelProdCustomerInvoices(partner);
		
		} catch(TechnicalException e) {
			
			System.out.println("TechnicalException : "+e.getMessage());
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		} catch(Exception e) {
			
			System.out.println("Exception : "+e.getMessage());
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		} finally {
			
			if (null != responseEntity) {
				responseEntity = ResponseEntity.ok(personalproductioninvoices);
			}
		}
		
		return responseEntity;
	}
}
