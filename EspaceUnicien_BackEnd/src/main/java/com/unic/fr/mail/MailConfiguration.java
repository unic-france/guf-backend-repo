package com.unic.fr.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfiguration {
	
	
	@Value("${spring.mail.host}")
	private String host;
	
	
	@Value("{spring.mail.port}")
	private String port;
	
	@Value("${spring.mail.username}") 
	private String username;
	
	
	@Value("${spring.mail.password}") 
	private String password;
	
	@Value("${spring.mail.properties.mail.smtp.auth}") 
	private boolean isSmtpAuth;
	
	@Value("${spring.mail.properties.mail.smtp.starttls.enable}") 
	private boolean isStarttlsEenable;
	
	@Value("${mail.transport.protocol}") 
	private String transportProtocol;
	
	@Value("${mail.smtp.debug}") 
	private boolean mailDebug;

	public JavaMailSender getMailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost(host);
		mailSender.setPort(Integer.valueOf(port));
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", isStarttlsEenable);
        javaMailProperties.put("mail.smtp.auth", isSmtpAuth);
        javaMailProperties.put("mail.transport.protocol", transportProtocol);
        javaMailProperties.put("mail.debug", mailDebug);
 
        mailSender.setJavaMailProperties(javaMailProperties);
		
		
		return mailSender;
	}
}
