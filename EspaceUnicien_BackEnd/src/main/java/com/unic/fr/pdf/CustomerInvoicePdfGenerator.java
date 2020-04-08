package com.unic.fr.pdf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.guf.batch.data.entity.Assignment;
import com.guf.batch.data.entity.Customerinvoice;
import com.guf.batch.data.entity.Customerinvoicestatushistory;
import com.guf.batch.data.entity.Groupcompany;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.unic.fr.exception.TechnicalException;
import com.unic.fr.repository.CustomerinvoiceRepository;
import com.unic.fr.repository.CustomerinvoicestatushistoryRepository;


@Configuration
@PropertySource("classpath:unic.properties")
public class CustomerInvoicePdfGenerator {
	
	private Assignment assignment;
	private Groupcompany groupCompany;
	private Customerinvoice customerInvoice;
	private Document document;
	private List<Customerinvoicestatushistory> customerinvoicestatusHistoryList;
		
	@Value("${customerinvoice.invoiceTemplate}")
	private String invoiceTemplace;
	
	@Value("${customerinvoice.invoicePdfPath}")
	private String invoicePdfPath;
	
	@Autowired
	CustomerinvoiceRepository customerinvoiceRepository;
	
	@Autowired
	CustomerinvoicestatushistoryRepository customerinvoicestatusHistoryRepository;
	
	public void init(String customerinvoicereferencenumber) {
		this.customerInvoice = customerinvoiceRepository.getCustomerinvoiceBycustomerinvoicereferencenumber(customerinvoicereferencenumber);
		this.assignment = customerInvoice.getAssignment();
		this.groupCompany = customerInvoice.getGroupcompany();
		this.customerinvoicestatusHistoryList = customerinvoicestatusHistoryRepository.getByCustomerinvoice(this.customerInvoice);
		
	}
	
	public void init(Customerinvoice customerinvoice) {
		this.customerInvoice = customerinvoice;
		this.assignment = customerInvoice.getAssignment();
		this.groupCompany = customerInvoice.getGroupcompany();
		this.customerinvoicestatusHistoryList = this.customerInvoice.getCustomerinvoicestatushistories();
		//this.customerinvoicestatusHistoryList = customerinvoicestatusHistoryRepository.getByCustomerinvoice(this.customerInvoice);
	}
	
	public ResponseEntity<String> generateCustomerInvoice(boolean forcedGeneration) throws TechnicalException{
		
		try {
			//Step1 : Vérifier si un fichier existe déjà en base et sur le serveur
			if (null != this.customerInvoice.getCustomerinvoicedocument() && !forcedGeneration) {
				return new ResponseEntity<String>("Un fichier pdf existe déjà en base",
	                    HttpStatus.BAD_REQUEST);
			}
			
			//Step2 : Vérifier si les prérequis (champs) minimum du customers invoice sont présent
			String minimumValue = checkMinimumValue();
			
			if (null != minimumValue && !minimumValue.isEmpty()) {
				return new ResponseEntity<String>(minimumValue,
	                    HttpStatus.BAD_REQUEST);
			}
			
			//Step3 : Créer le File à partir du template word et le remplir
			ResponseEntity<String> retour = generateCustomerInvoicePdf();
			if (HttpStatus.BAD_REQUEST == retour.getStatusCode()) {
				return retour;
			}
			
			//Step4 : Créer le pdf, insérer en base + stockage du pdf sur le disque
			ResponseEntity<String> retourSave = saveFileOnServerAndBase();
			if (HttpStatus.BAD_REQUEST == retourSave.getStatusCode()) {
				return retourSave;
			}
		} catch (Exception e) {
			throw new TechnicalException(e.getMessage());
		}
		return new ResponseEntity<String>("Un fichier pdf enregistré avec succès", HttpStatus.OK);
	}
	
	private ResponseEntity<String> saveFileOnServerAndBase() {
		
		ResponseEntity<String> response = null;
		byte[] invoice = null;
		Exception exception = null;
		
		String invoiceName="Facture-"+this.customerInvoice.getCustomerinvoicereferencenumber();
		String directoryOriginalPath = getDirectoryPath("Original");
		
		File directoryInvoices = new File (directoryOriginalPath);
		File fileOut = null;
		
		try {
			createDirectories(directoryInvoices, fileOut, invoiceName);
			
			this.document.isUpdateFields(true);
		
			this.document.saveToFile(directoryInvoices+File.separator+invoiceName+".pdf", FileFormat.PDF);
			
			File generatedFile = new File (directoryInvoices+File.separator+invoiceName+".pdf");
			invoice = Files.readAllBytes(generatedFile.toPath());
			
			this.customerInvoice.setCustomerinvoicedocument(invoice);

			try {
				//throw new Exception("Simulation problème MAJ Database");
				this.customerinvoiceRepository.save(this.customerInvoice);
				this.customerinvoiceRepository.flush();
			
			} catch(Exception e) {
				
				System.out.println("Exception : "+e.getMessage());
				exception = e;
				response = new ResponseEntity<String>("L'insertion en base a échouée",
	                    HttpStatus.BAD_REQUEST);
				
				if(!generatedFile.delete()) {
					response = new ResponseEntity<String>("L'insertion en base a échouée, merci de supprimer le fichier sur le disque.",
		                    HttpStatus.BAD_REQUEST);
				} else {
					response = new ResponseEntity<String>("L'insertion en base a échouée, la suppression du fichier généré aussi.",
		                    HttpStatus.BAD_REQUEST);
				}
			}
			finally {
				
				response = new ResponseEntity<String>("L'insertion en base a échouée",
	                    HttpStatus.BAD_REQUEST);
				
				if(!generatedFile.delete()) {
					response = new ResponseEntity<String>("L'insertion en base a échouée, merci de supprimer le fichier sur le disque.",
		                    HttpStatus.BAD_REQUEST);
				} else {
					response = new ResponseEntity<String>("L'insertion en base a échouée, la suppression du fichier généré aussi.",
		                    HttpStatus.BAD_REQUEST);
				}
				
			}
			
		} catch (IOException ioE) {
			
			System.out.println("Exception : "+ioE.getMessage());
			exception = ioE;
			response = new ResponseEntity<String>("La creation du répertoire invoices a échouée",
                    HttpStatus.BAD_REQUEST);
			
		} catch(Exception e) {
			System.out.println("Exception : "+e.getMessage());
			exception = e;
			response = new ResponseEntity<String>("La sauvegarde en base a échouée",
                    HttpStatus.BAD_REQUEST);
			
		
		} finally {
			if (null == exception) {
				response = new ResponseEntity<String>("La sauvegarde en base est effectuée",
	                    HttpStatus.OK);
			}
		}
		
		return response;
	}

	private String getValue(String value) {
		String retour = " ";
		
		if(null != value) {
			retour = value;
		}
		return retour;
	}
	
	private String getPeriod(Date period) {
		
		String periode = null;
		
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM yyyy");
	    
	    periode = dateFormat.format(period);
	    
		return periode;
	}

	private String checkMinimumValue() {
		
		String error = null;
		
		if (null == this.groupCompany.getGroupcompanyname() 
			|| this.groupCompany.getGroupcompanyname().isEmpty()) {
			error = error+"groupCompanyName";
		}
			
		if (null == this.assignment.getPartner1().getPartnercompany().getCompanyname()
			|| this.assignment.getPartner1().getPartnercompany().getCompanyname().isEmpty()) {
			error = error+"getPartnercompanyName";
			
		}
			
		if (null == this.assignment.getPartner1().getPartnercompany().getAddress1().getLine1recipient()
			|| this.assignment.getPartner1().getPartnercompany().getAddress1().getLine1recipient().isEmpty()) {
			error = error+"getPartnercompanyAdresseLine1";
		}
			
		if (null == this.assignment.getPartner1().getPartnercompany().getAddress1().getLine4streetnumberandstreetlabel()
		|| this.assignment.getPartner1().getPartnercompany().getAddress1().getLine4streetnumberandstreetlabel().isEmpty()){
			error = error+"getPartnercompanyAdresseLine4";
		}
		
		if (null == this.assignment.getPartner1().getPartnercompany().getAddress1().getLine6zipcodelocalization()
		|| this.assignment.getPartner1().getPartnercompany().getAddress1().getLine6zipcodelocalization().isEmpty()){
			error = error+"getPartnercompanyAdresseLine6";
		}
		
		if (null == this.assignment.getPartner1().getPartnercompany().getAddress1().getLine7country()
		|| this.assignment.getPartner1().getPartnercompany().getAddress1().getLine7country().isEmpty()){
			error = error+"getPartnercompanyAdresseLine7";
		}
		
		if (null == this.assignment.getPartner1().getPartnercompany().getSiretnumber()
		|| this.assignment.getPartner1().getPartnercompany().getSiretnumber().isEmpty()){
			error = error+"getPartnerSiretNumber";
		}
		
		if (null == this.assignment.getPartner1().getPartnercompany().getTvanumber()
		|| this.assignment.getPartner1().getPartnercompany().getTvanumber().isEmpty()){
			error = error+"getPartnertvanumber";
		}
		
		if (null == this.assignment.getCustomer1().getCompanyname()
		|| this.assignment.getCustomer1().getCompanyname().isEmpty()){
			error = error+"assignmentPartnerCompanyname";
		}
		
		if (null == this.assignment.getCustomerinvoicedetail().getAddress().getLine1recipient()
		|| this.assignment.getCustomerinvoicedetail().getAddress().getLine1recipient().isEmpty()){
			error = error+"CustomerinvoiceDetailAdresseLine1";
		}
		
		if (null == this.assignment.getCustomerinvoicedetail().getAddress().getLine4streetnumberandstreetlabel()
		|| this.assignment.getCustomerinvoicedetail().getAddress().getLine4streetnumberandstreetlabel().isEmpty()){
			error = error+"CustomerinvoiceDetailAdresseLine4";
		}
		
		if (null == this.assignment.getCustomerinvoicedetail().getAddress().getLine6zipcodelocalization()
		|| this.assignment.getCustomerinvoicedetail().getAddress().getLine6zipcodelocalization().isEmpty()){
			error = error+"CustomerinvoiceDetailAdresseLine6";
		}
		
		if (null == this.assignment.getCustomerinvoicedetail().getAddress().getLine7country()
		|| this.assignment.getCustomerinvoicedetail().getAddress().getLine7country().isEmpty()){
			error = error+"CustomerinvoiceDetailAdresseLine7";
		}
		
		if (null == this.assignment.getCustomerinvoicedetail().getInvoiceassignmentreferencenumber()
		|| this.assignment.getCustomerinvoicedetail().getInvoiceassignmentreferencenumber().isEmpty()){
			error = error+"AssignmentCustomerinvoiceReferenceNumber";
		}
		
		if (null == this.customerInvoice.getCustomerinvoicereferencenumber()
		|| this.customerInvoice.getCustomerinvoicereferencenumber().isEmpty()){
			error = error+"CustomerinvoiceReferenceNumber";
		}
		
		if (null == this.customerInvoice.getPeriod()) {
			error = error+"CustomerinvoicePeriod";
		}
		
		if (null == this.assignment.getDescriptionshort()
		|| this.assignment.getDescriptionshort().isEmpty()){
			error = error+"AssignmentDescriptionshort";
		}
		
		if (null == this.groupCompany.getGroupbillingcontact().getGroupcompanies()
		|| this.groupCompany.getGroupbillingcontact().getGroupcompanies().isEmpty()){
			error = error+"GroupCompanyGroupbillingContactGroupcompanies";
		}
		
		if (null == this.groupCompany.getGroupbillingcontact().getGroupbillingphonecontact()
		|| this.groupCompany.getGroupbillingcontact().getGroupbillingphonecontact().isEmpty()){
			error = error+"GroupCompanyGroupbillingGroupbillingphonecontact";
		}
		
		if (null == this.groupCompany.getGroupbillingcontact().getGroupbillingemailcontact()
		|| this.groupCompany.getGroupbillingcontact().getGroupbillingemailcontact().isEmpty()){
			error = error+"GroupCompanyGroupbillingGroupbillingEmailcontact";
		}
		
		if (null == this.customerinvoicestatusHistoryList
		|| this.customerinvoicestatusHistoryList.isEmpty()){
			error = error+"customerinvoicestatusHistoryList";
		}
		
		if (BigDecimal.valueOf((long)0) == this.customerInvoice.getNumberofdaysworked()) {
			error = error+"customerInvoiceNumberofdaysworked";
		}
		
		if (BigDecimal.valueOf((long)0) == this.assignment.getDaypricewithouttax()) {
			error = error+"assignmentDaypricewithouttax";
		}
		
		if (BigDecimal.valueOf((long)0) == this.customerInvoice.getTaxrate()) {
			error = error+"customerInvoiceTaxrate";
		}
		
		if (BigDecimal.valueOf((long)0) == this.customerInvoice.getTotalamountwithouttax()) {
			error = error+"customerInvoiceTotalamountwithouttax";
		}
		
		if (null == this.customerInvoice.getDuedate()) {
			error = error+"customerInvoiceDuedate";
		}
		
		
		if (BigDecimal.valueOf((long)0) == this.customerInvoice.getTaxamount()) {
			error = error+"customerInvoiceTaxamount";
		}
		
		
		if (BigDecimal.valueOf((long)0) == this.customerInvoice.getTotalamountwithtax()) {
			error = error+"customerInvoiceTotalamountwithtax";
		}
		
		if (null == this.groupCompany.getGroupbankingdetail().getIbannumber()
		|| this.groupCompany.getGroupbankingdetail().getIbannumber().isEmpty()){
			error = error+"groupCompanyGroupbankingdetailIbannumber";
		}
		
		if (null == this.groupCompany.getGroupbankingdetail().getBiccode()
		|| this.groupCompany.getGroupbankingdetail().getBiccode().isEmpty()){
			error = error+"groupCompanyGroupbankingdetailBiccode";
		}
		
		if (null == this.groupCompany.getSiretnumber()
		|| this.groupCompany.getSiretnumber().isEmpty()){
			error = error+"groupCompanySiretnumber";
		}
		
		if (null == this.groupCompany.getTvanumber()
		|| this.groupCompany.getTvanumber().isEmpty()){
			error = error+"groupCompanyTvanumber";
		}
		
		if (null == this.groupCompany.getAddress1().getLine1recipient()
		|| this.groupCompany.getAddress1().getLine1recipient().isEmpty()){
			error = error+"groupCompanyAddressLine1";
		}
		
		if (null == this.groupCompany.getAddress1().getLine4streetnumberandstreetlabel()
		|| this.groupCompany.getAddress1().getLine4streetnumberandstreetlabel().isEmpty()){
			error = error+"groupCompanyAddressLine4";
		}
		
		if (null == this.groupCompany.getAddress1().getLine6zipcodelocalization()
		|| this.groupCompany.getAddress1().getLine6zipcodelocalization().isEmpty()){
			error = error+"groupCompanyAddressLine6";
		}
		
		if (null == this.groupCompany.getAddress1().getLine7country()
		|| this.groupCompany.getAddress1().getLine7country().isEmpty()){
			error = error+"groupCompanyAddressLine7";
		} 

		
		return error;
	}
	
	public String getDirectoryPath(String originalOrDuplicata) {
		
		String separator = File.separator;
		
		return this.invoicePdfPath
				+separator+
				this.assignment.getPartner1().getIdpartner()
				+separator+
				this.assignment.getIdassignment()
				+separator+
				this.customerInvoice.getPeriod()
				+separator+
				originalOrDuplicata+separator;
	}
	
	public ResponseEntity<String> generateCustomerInvoicePdf() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		Document doc = new Document();
		System.out.println(this.invoiceTemplace);
		
		doc.loadFromFile(this.invoiceTemplace);
		
		//Bloc 1 (à gauche) - Nom du partner
		doc.replace("#GroupName", this.groupCompany.getGroupcompanyname(),  true,true);
		
		//Bloc 2 (à gauche) - Partner
        doc.replace("#PartnerCompany", this.assignment.getPartner1().getPartnercompany().getCompanyname(),true ,true);
        doc.replace("#PartnerAddressLine1", getValue(this.assignment.getPartner1().getPartnercompany().getAddress1().getLine1recipient()), true, true);
		doc.replace("#PartnerAddressLine2", getValue(this.assignment.getPartner1().getPartnercompany().getAddress1().getLine2deliverypoint()), true, true);
		doc.replace("#PartnerAddressLine3", getValue(this.assignment.getPartner1().getPartnercompany().getAddress1().getLine3additionallocalizationinfo()), true, true);
		doc.replace("#PartnerAddressLine4", getValue(this.assignment.getPartner1().getPartnercompany().getAddress1().getLine4streetnumberandstreetlabel()), true, true);
		doc.replace("#PartnerAddressLine5", getValue(this.assignment.getPartner1().getPartnercompany().getAddress1().getLine5additionalstreetinfo()), true, true);
		doc.replace("#PartnerAddressLine6", getValue(this.assignment.getPartner1().getPartnercompany().getAddress1().getLine6zipcodelocalization()), true, true);
		doc.replace("#PartnerAddressLine7", getValue(this.assignment.getPartner1().getPartnercompany().getAddress1().getLine7country()), true, true);
		doc.replace("#PartnerSiretnumber", getValue(this.assignment.getPartner1().getPartnercompany().getSiretnumber()), true , true);
		doc.replace("#PartnerTvanumber", getValue(this.assignment.getPartner1().getPartnercompany().getTvanumber()), true , true);
		
		//Bloc 3 (à droite) - 
		doc.replace("#CustomerCompanyname", getValue(this.assignment.getCustomer1().getCompanyname()), true , true);
		doc.replace("#CustomerAddressLine1", getValue(this.assignment.getCustomerinvoicedetail().getAddress().getLine1recipient()), true , true);
		doc.replace("#CustomerAddressLine2", getValue(this.assignment.getCustomerinvoicedetail().getAddress().getLine2deliverypoint()), true , true);
		doc.replace("#CustomerAddressLine3", getValue(this.assignment.getCustomerinvoicedetail().getAddress().getLine3additionallocalizationinfo()), true , true);
		doc.replace("#CustomerAddressLine4", getValue(this.assignment.getCustomerinvoicedetail().getAddress().getLine4streetnumberandstreetlabel()), true , true);
		doc.replace("#CustomerAddressLine5", getValue(this.assignment.getCustomerinvoicedetail().getAddress().getLine5additionalstreetinfo()), true , true);
		doc.replace("#CustomerAddressLine6", getValue(this.assignment.getCustomerinvoicedetail().getAddress().getLine6zipcodelocalization()), true , true);
		doc.replace("#CustomerAddressLine7", getValue(this.assignment.getCustomerinvoicedetail().getAddress().getLine7country()), true , true);
		
		//Bloc 4 (à gauche) - Invoice Reference
		doc.replace("#AssignmentInvoicerefnumber", getValue(this.assignment.getCustomerinvoicedetail().getInvoiceassignmentreferencenumber()), true , true);
		doc.replace("#CustomerInvoicerefnumber", getValue(this.customerInvoice.getCustomerinvoicereferencenumber()), true , true);
		
		//Bloc 5 (à droite) - Date de facturation
		Date dateCreation = null;
		
		for (Customerinvoicestatushistory cus : customerinvoicestatusHistoryList) {
			if (null != cus.getDatecreation()) {
				dateCreation = cus.getDatecreation();
				break;
			}
		}
		doc.replace("#InvoiceCreationDate", getValue(sdf.format(dateCreation)), true, true);
		
		//Bloc 6 (à gauche) - Designation
		doc.replace("#Customerinvoiceperiod", getPeriod(this.customerInvoice.getPeriod()), true, true);
		doc.replace("#AssignmentDescription", getValue(this.assignment.getDescriptionshort()), true, true);
		doc.replace("#CustomerInvoicerefnumber", getValue(this.customerInvoice.getCustomerinvoicereferencenumber()), true , true);
		
		doc.replace("#Groupbillingcontact", getValue(this.groupCompany.getGroupbillingcontact().getGroupcompanies().iterator().next().getGroupcompanyname()), true, true);
		doc.replace("#Groupbillingphone", getValue(this.groupCompany.getGroupbillingcontact().getGroupbillingphonecontact()), true, true);
		doc.replace("#Groupbillingemail", getValue(this.groupCompany.getGroupbillingcontact().getGroupbillingemailcontact()), true, true);
		
		// Bloc 7Bis - Nombre de jours
		doc.replace("#NumberOfDay", getValue(String.valueOf(this.customerInvoice.getNumberofdaysworked())), true, true);
		
		//Bloc 7 - TJM
		doc.replace("#Tjm", getValue(String.valueOf(this.assignment.getDaypricewithouttax())), true, true);
		
		//Bloc 8 - TVA
		doc.replace("#Customerinvoicetax", getValue(String.valueOf(this.customerInvoice.getTaxrate())), true, true);
		
		//Bloc 9 - MONTANT HT
		doc.replace("#Customerinvoicetotalwithouttax", getValue(String.valueOf(this.customerInvoice.getTotalamountwithouttax())), true, true);
		
		//Bloc 10 - DueDate
		doc.replace("#CustomerDueDate", getValue(sdf.format(this.customerInvoice.getDuedate())), true, true);
		
		//Bloc 11 - MONTANT HT + Tax + TTC
		doc.replace("#Customerinvoicetotalwithouttax", getValue(String.valueOf(this.customerInvoice.getTotalamountwithouttax())), true, true);
		doc.replace("#CustomerinvoiceAmount", getValue(String.valueOf(this.customerInvoice.getTaxamount())), true, true);
		doc.replace("#Customerinvoicetotalwithtax", getValue(String.valueOf(this.customerInvoice.getTotalamountwithtax())), true, true);
		
		//Bloc 12 - Référence à rappeler
		doc.replace("#Customerinvoicereferencenumber", getValue(this.customerInvoice.getCustomerinvoicereferencenumber()), true , true);
		doc.replace("#Customerinvoicetotalwithtax", getValue(String.valueOf(this.customerInvoice.getTotalamountwithtax())), true, true);
		
		//Bloc 13 - RIB
		doc.replace("#Groupcompanyibannumber", getValue(this.groupCompany.getGroupbankingdetail().getIbannumber()), true, true);
		doc.replace("#Groupcompanyibannumber", getValue(this.groupCompany.getGroupbankingdetail().getBiccode()), true, true);
		
		//Bloc 14 - Pied de page
		doc.replace("#GroupCompany", getValue(this.groupCompany.getGroupcompanyname()),true ,true);
		doc.replace("#GroupCompanySiretnumber", getValue(this.groupCompany.getSiretnumber()), true , true);
		doc.replace("#GroupCompanyTvanumber", getValue(this.groupCompany.getTvanumber()), true , true);
		doc.replace("#GroupheadofficeLine1", getValue(this.groupCompany.getAddress1().getLine1recipient()), true , true);
		doc.replace("#GroupheadofficeLine2", getValue(this.groupCompany.getAddress1().getLine2deliverypoint()), true , true);
		doc.replace("#GroupheadofficeLine3", getValue(this.groupCompany.getAddress1().getLine3additionallocalizationinfo()), true , true);
		doc.replace("#GroupheadofficeLine4", getValue(this.groupCompany.getAddress1().getLine4streetnumberandstreetlabel()), true , true);
		doc.replace("#GroupheadofficeLine5", getValue(this.groupCompany.getAddress1().getLine5additionalstreetinfo()), true , true);
		doc.replace("#GroupheadofficeLine6", getValue(this.groupCompany.getAddress1().getLine6zipcodelocalization()), true , true);
		doc.replace("#GroupheadofficeLine7", getValue(this.groupCompany.getAddress1().getLine7country()), true , true);
		
		this.document = doc;
		
		return new ResponseEntity<String>("Création du template OK",
                HttpStatus.OK);
	}
	
	private void createDirectories(File directoryInvoices, File fileOut, String invoiceName) throws IOException {
		
		if (!directoryInvoices.exists()) {
			
			if (directoryInvoices.mkdirs()) {
				System.out.println("Directory is created");
				
			} else{
				
				System.out.println("Failed to create directory");
			}
			
		} else {
			System.out.println("Directory already exists");
		}
			
		fileOut = new File(directoryInvoices.getAbsolutePath()+File.separatorChar+invoiceName+".pdf");
		
		if(fileOut.createNewFile()){
			
		  	System.out.println("File is created");
		  	System.out.println("File Path : "+directoryInvoices.getAbsolutePath());
		  	
		} else {
		
			System.out.println("Failed to create file");
			System.out.println("File Path : "+directoryInvoices.getAbsolutePath());
		}
			
	}

}
