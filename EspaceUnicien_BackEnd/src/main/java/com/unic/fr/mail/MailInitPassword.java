package com.unic.fr.mail;

public class MailInitPassword extends Mail {
	
	private String mailTo;
	private String password;
	private String mailFrom;
	
	private String mailSubject;
	private String mailContent;

	
	public MailInitPassword(String mailTo, String password, String subject) {
		
		super();
		this.mailTo = mailTo;
		this.password = password;
		this.mailSubject = subject;
		this.mailContent = getContent(this.password);
		
	}

	private String getContent(String password) {
		
		String content = "Vous trouverez ci-dessous un mot de passe temporaire (valable 48h). Connectez-vous sur votre espace (www.unic-france.com/espaceUnicien) pour le modifier";
		String warning = "En cas de problème, contactez le support : contact@unic-france.com";
		
		String result = content+"\n\n"+password+"\n\n"+warning;
		return result;
		
	}
	

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
