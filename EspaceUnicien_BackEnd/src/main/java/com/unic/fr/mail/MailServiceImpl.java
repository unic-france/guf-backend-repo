package com.unic.fr.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.unic.fr.exception.TechnicalException;

@Service("mailService")
public class MailServiceImpl implements MailService {
	
	@Autowired
	JavaMailSender mailSender;
	
	@Value("${mail.from.emailgroup}") 
	private String from;

	@Override
	public void send(Mail mail) throws TechnicalException {
		
		mail.setMailFrom(from);
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		String missingArgument = checkMissingArgument(mail);
		
		if (null != missingArgument && !missingArgument.isEmpty()) {
			throw new TechnicalException(missingArgument);
		}
		
		try {
			
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			
			mimeMessageHelper.setSubject(mail.getMailSubject());
			mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom(),"unic-france.com"));
			mimeMessageHelper.setTo(mail.getMailTo());
			mimeMessageHelper.setText(mail.getMailContent());
			
			mailSender.send(mimeMessageHelper.getMimeMessage());
			
		} catch(MessagingException e) {
			throw new TechnicalException(e.getMessage());
			
		} catch (UnsupportedEncodingException e) {
			throw new TechnicalException(e.getMessage());
			
		}
	}

	private String checkMissingArgument(Mail mail) {
		
		String missingArgument = null;
		
		if (null == mail.getMailSubject() || mail.getMailSubject().isEmpty()){
			
			missingArgument = missingArgument+"\n"+"Mail Subject";
		}
		    
		if( null == mail.getMailFrom() || mail.getMailFrom().isEmpty()){
			
			missingArgument = missingArgument+"\n"+"Mail From";
		}
	 
		if (null == mail.getMailTo() || mail.getMailTo().isEmpty()) {
		 
			missingArgument = missingArgument+"\n"+"Mail To";
		}
	 
		if (null == mail.getMailContent()  || mail.getMailContent().isEmpty()) {
		
			missingArgument = missingArgument+"\n"+"Mail Content";
		}
		
		if (null != missingArgument && !missingArgument.isEmpty()) {
			
			missingArgument = "Missing Argument(s) : "+missingArgument;
		}
			
		return missingArgument;
	}

}
