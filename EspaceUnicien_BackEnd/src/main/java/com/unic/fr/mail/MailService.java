package com.unic.fr.mail;

import com.unic.fr.exception.TechnicalException;

public interface MailService {
	
	public void send(Mail mail) throws TechnicalException;

}
