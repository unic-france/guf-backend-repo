package com.unic.fr.controller.finances.implementation;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.guf.batch.data.entity.Assignment;
import com.guf.batch.data.entity.Customerinvoice;
import com.guf.batch.data.entity.Customerinvoicedetail;
import com.guf.batch.data.entity.Customerinvoicestatushistory;
import com.guf.batch.data.entity.Flagcustomerinvoice;
import com.guf.batch.data.entity.Gridpourcentagerepartitionpartner;
import com.guf.batch.data.entity.Groupcompany;
import com.guf.batch.data.entity.Partnernetwork;
import com.guf.batch.data.entity.Refworkingday;
import com.unic.fr.exception.TechnicalException;
import com.unic.fr.pdf.CustomerInvoicePdfGenerator;
import com.unic.fr.repository.AssignmentRepository;
import com.unic.fr.repository.CustomerinvoiceRepository;
import com.unic.fr.repository.CustomerinvoicestatushistoryRepository;
import com.unic.fr.repository.FlagcustomerinvoiceRepository;
import com.unic.fr.repository.GroupcompanyRepository;
import com.unic.fr.repository.PartnerRepository;
import com.unic.fr.repository.RefworkingdaysRepository;
import com.unic.fr.utils.AppProperties;

@Transactional
@Configuration
public class CreateCustomerInvoiceBean {
	
	private MultipartFile craFileUpload;

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
	CustomerInvoicePdfGenerator customerInvoicePdfGenerator;
	
	@Autowired
	RefworkingdaysRepository refworkingdaysRepository;
	

	public ResponseEntity<String> createCustomerInvoiceWithoutCra(String assignmentreferencenumber, String periodParam, BigDecimal numberofdaysworked) throws TechnicalException, IOException {
		
		return createCustomerInvoice(assignmentreferencenumber, periodParam, numberofdaysworked) ;
	}
	
	public ResponseEntity<String> createCustomerInvoiceWithCra( String assignmentreferencenumber, String periodParam, BigDecimal numberofdaysworked, MultipartFile craFileUpload) throws TechnicalException, IOException {
		
		this.craFileUpload = craFileUpload;
		
		return createCustomerInvoice(assignmentreferencenumber, periodParam, numberofdaysworked) ;
	}
	
	
	public ResponseEntity<String> createCustomerInvoice( String assignmentreferencenumber, String periodParam, BigDecimal numberofdaysworked) throws TechnicalException, IOException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date period = null;
		
		try {period = sdf.parse(periodParam);} catch (java.text.ParseException e) { System.out.println(e.getMessage()); }
	
		Assignment assignment = assignmentRepository.getAssignmentByAssignmentreferencenumber(assignmentreferencenumber);
		if (null == assignment) throw new TechnicalException("Aucune facture pour cette référence : "+assignmentreferencenumber);
		
		Customerinvoice customerinvoice = new Customerinvoice();
	
		if (null == assignment.getCustomerinvoices()) {
			assignment.setCustomerinvoices(new ArrayList<Customerinvoice>());
		}
		assignment.getCustomerinvoices().add(customerinvoice);
		customerinvoice.setAssignment(assignment);
		
		Groupcompany groupcompany = groupCompanyRepository.findByActiveTrue();
		if (null == groupcompany) throw new TechnicalException("Aucun groupCompany actif");
		
		customerinvoice.setGroupcompany(groupcompany);
		if (null == groupcompany.getCustomerinvoices()) {
			groupcompany.setCustomerinvoices(new ArrayList<Customerinvoice>());
		}
		groupcompany.getCustomerinvoices().add(customerinvoice);
		
		Customerinvoicedetail customerinvoicedetail = assignment.getCustomerinvoicedetail();
		if (null == customerinvoicedetail) throw new TechnicalException("Aucune customerinvoicedetail pour cette mission : "+assignment.getIdassignment());
		
		customerinvoice.setCustomerinvoicedetail(customerinvoicedetail);
		if (null == customerinvoicedetail.getCustomerinvoices()) {
			customerinvoicedetail.setCustomerinvoices(new ArrayList<Customerinvoice>());
		}
		customerinvoicedetail.getCustomerinvoices().add(customerinvoice);
		
		Gridpourcentagerepartitionpartner grouppourcentagerepport = getGridPourcentRepartitionPartner(assignment);
		if (null == grouppourcentagerepport) throw new TechnicalException("Aucune grouppourcentagerepport pour cette mission : "+assignment.getIdassignment());
		customerinvoice.setGridpourcentagerepartitionpartner(grouppourcentagerepport);
		if (null == grouppourcentagerepport.getCustomerinvoices()) {
			grouppourcentagerepport.setCustomerinvoices(new ArrayList<Customerinvoice>());
		}
		grouppourcentagerepport.getCustomerinvoices().add(customerinvoice);
		
		Partnernetwork partnernetwork = getCurrentPartnernetwork(assignment);
		if (null == partnernetwork) throw new TechnicalException("Aucune partnernetwork pour cette mission : "+assignment.getIdassignment());
		customerinvoice.setPartnernetwork(partnernetwork);
		if (null == partnernetwork.getCustomerinvoices()) {
			partnernetwork.setCustomerinvoices(new ArrayList<Customerinvoice>());
		}
		partnernetwork.getCustomerinvoices().add(customerinvoice);
		
		Flagcustomerinvoice flagcustomerinvoice = new Flagcustomerinvoice();
		flagcustomerinvoice.setFlagrepartitioninvoice(false);
		flagcustomerinvoice.setFlagcheckrepartitioninvoice("todo");
		flagcustomerinvoice.setFlagrepartitioninvoicepaid(false);
		flagcustomerinvoice.setCustomerinvoices(new ArrayList<Customerinvoice>());
		flagcustomerinvoice.getCustomerinvoices().add(customerinvoice);
		customerinvoice.setFlagcustomerinvoice(flagcustomerinvoice);
		
		Customerinvoicestatushistory customerinvoicestatushistory = new Customerinvoicestatushistory();
		customerinvoicestatushistory.setStatus(appProperties.getPartnerCreation());
		customerinvoicestatushistory.setDatecreation(new Timestamp(System.currentTimeMillis()));
		customerinvoicestatushistory.setNote(null);
		if (null == customerinvoice.getCustomerinvoicestatushistories()) {
			customerinvoice.setCustomerinvoicestatushistories(new ArrayList<Customerinvoicestatushistory>());
		}
		customerinvoice.getCustomerinvoicestatushistories().add(customerinvoicestatushistory);
		customerinvoicestatushistory.setCustomerinvoice(customerinvoice);
		
		//Mise à jour des champs du customerInvoice
		customerinvoice.setDaypricewithouttax(assignment.getDaypricewithouttax());
		customerinvoice.setPeriod(period);
		customerinvoice.setDuedate(getDueDate());
		customerinvoice.setCustomerinvoicereferencenumber(getPersonalProductionInvoiceReferenceNumber(appProperties.getPersonalProductionInvoiceReferenceNumber()));
		
		customerinvoice.setTaxrate(appProperties.getTaxrate());
		customerinvoice.setTotalamountwithouttax(calculateTotalMountWithoutTax(assignment.getDaypricewithouttax(), numberofdaysworked));
		customerinvoice.setTotalamountwithtax(getTotalMountWithTax(customerinvoice.getTotalamountwithouttax(), customerinvoice.getTaxrate()));
		customerinvoice.setTaxamount(getTotalAmount(customerinvoice));
		customerinvoice.setCurrency(appProperties.getCurrency());
		customerinvoice.setDueamount(customerinvoice.getTotalamountwithtax());
		customerinvoice.setNumberofdaysworked(numberofdaysworked);
		
		if (null != this.craFileUpload && !this.craFileUpload.isEmpty()) {
			customerinvoice.setActivityreportdocument(this.craFileUpload.getBytes());
			customerinvoice.setActivityreportdocumentname(this.craFileUpload.getName());
		}
		
		//Génération de la facture
		this.customerInvoicePdfGenerator.init(customerinvoice);
		ResponseEntity<String> reponse = this.customerInvoicePdfGenerator.generateCustomerInvoice(false);
		
		return reponse;
	}
	
	private BigDecimal calculateTotalMountWithoutTax(BigDecimal tjm, BigDecimal numberofdaysworked) {
		
		return tjm.multiply(numberofdaysworked);
	}

	private Partnernetwork getCurrentPartnernetwork(Assignment assignment) {
		
		Partnernetwork partner = null;
		List<Partnernetwork> partnerworkList = assignment.getPartner1().getPartnernetworks();
		
		for (Partnernetwork pnk: partnerworkList) {
			if (pnk.getCurrent()) {
				partner = pnk;
				break;
			}
		}
		
		return partner;
	}

	private Gridpourcentagerepartitionpartner getGridPourcentRepartitionPartner(Assignment assignment) {
		
		Gridpourcentagerepartitionpartner grouppourcentagerepport = null;
		List<Gridpourcentagerepartitionpartner> groupPourcentageRepportList = assignment.getPartner1().getGridpourcentagerepartitionpartners();
		
		for (Gridpourcentagerepartitionpartner gcp : groupPourcentageRepportList) {
			if (gcp.getCurrent()) {
				grouppourcentagerepport = gcp;
				break;
			}
		}
		return grouppourcentagerepport;
	}
	
	private Date getDueDate() {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 30);
		return cal.getTime();
	}

	private BigDecimal getTotalAmount(Customerinvoice customerinvoice) {
		return customerinvoice.getTotalamountwithtax().subtract(customerinvoice.getTotalamountwithouttax());
	}

	public String getPersonalProductionInvoiceReferenceNumber(String pAssignmentreferencenumber) {
		return new StringBuilder().append("PPR").append(pAssignmentreferencenumber).append(new Timestamp(System.currentTimeMillis()).toString()).toString();
	}
	
	public BigDecimal getTotalMountWithTax(BigDecimal totalMountWithoutTax, BigDecimal tax) {
	
		BigDecimal cent = BigDecimal.valueOf((long)100);
		BigDecimal taxPourcent = tax.divide(cent);
		BigDecimal un = BigDecimal.valueOf((long)1);
		
		BigDecimal pourcent = un.add(taxPourcent);
		
		return totalMountWithoutTax.multiply(pourcent);
	}

	public ResponseEntity<String> checkCustomerInvoiceParameters(String assignmentreferencenumber, String periodParam, int numberofdaysworked) throws TechnicalException {
		
		ResponseEntity<String> response = null;
		String message = null;
		String checkPeriodErr = null;
		String checkNumDays = null;
		Assignment assignment = this.assignmentRepository.getAssignmentByAssignmentreferencenumber(assignmentreferencenumber);
		
		checkPeriodErr = checkPeriod(periodParam, assignment);
		
		if (null != checkPeriodErr && !"".equalsIgnoreCase(checkPeriodErr)) {
			System.out.println("checkPeriod : "+checkPeriodErr);
			message = checkPeriodErr;
		}
		
		checkNumDays = checkNumberDays(numberofdaysworked, periodParam);
		if (null != checkNumDays && !"".equalsIgnoreCase(checkNumDays)) {
			System.out.println("checkNumDays : "+checkNumDays);
			message = message+checkNumDays;
			System.out.println("Message : "+message);
		}
		

		if (null != message && !"".equalsIgnoreCase(message)) {
			System.out.println("Message final : "+message);
			response = new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}

	private String checkNumberDays(int numberofdaysworked, String periodParam) throws TechnicalException {
		
		String message = null;
		String month = null;
		String year = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		try {
			
			cal.setTime(sdf.parse(periodParam));
			
			month = Integer.toString(cal.get(Calendar.MONTH));
			year = Integer.toString(cal.get(Calendar.YEAR));
			
			Refworkingday ref = refworkingdaysRepository.getOneByMonthAndYear(month, year);
			
			if (numberofdaysworked > ref.getWorkingdays()) {
				message="Le nombre de jour saisie est supérieur au nombre de jour ouvré pour ce mois ci."+"\n"+
						"Merci de modifier le nombre de jour ou contacter le support en cas de problème (contact@unic-france.com)";
			}
		
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			
			message = "Problème technique, merci de reprendre dans quelques minutes"+"\n"
						+"Contacter le support si le problème persiste";
			throw new TechnicalException(message); 
		}
				
		return message;
	}

	private String checkPeriod(String periodParam, Assignment assignment) throws TechnicalException {
		
		String message = null;
		
		List<Customerinvoice> customerInvoices = assignment.getCustomerinvoices();
		
		for (Customerinvoice custInv : customerInvoices) {
			
			if (comparePeriod(custInv.getPeriod(), periodParam)) {
				message="Il existe déjà une facture pour cette période."+"\n"+
						"Merci de modifier la période ou contacter le support en cas de problème (contact@unic-france.com)";
				break;
			}
		}
		
		return message;
	}

	private boolean comparePeriod(Date period, String periodParam) throws TechnicalException {
		
		boolean equal = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calPeriod = Calendar.getInstance();
		Calendar calParam = Calendar.getInstance();
		
		try {
			
			calPeriod.setTime(period);
			calParam.setTime(sdf.parse(periodParam));
			
			int yearPeriod = calPeriod.get(Calendar.YEAR);
			int yearParam = calParam.get(Calendar.YEAR);
			
			if (yearPeriod == yearParam) {
				
				int monthPeriod = calPeriod.get(Calendar.MONTH);
				int monthParam = calParam.get(Calendar.MONTH);
				
				if (monthPeriod == monthParam) {
					equal = true;
				}
			}
		
		
		} catch (ParseException e) {
			
			String message = "Problème de parsing de la période en paramètre";
			System.out.println(message);
			throw new TechnicalException(message);
		}
	
		return equal;
	}

		
}
