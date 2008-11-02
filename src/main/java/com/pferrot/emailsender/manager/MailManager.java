package com.pferrot.emailsender.manager;

import java.util.Map;

import org.springframework.mail.MailException;

public interface MailManager {
	
	void send(String senderName, 
			  String senderAddress,
			  Map<String, String> to,  
			  Map<String, String> cc, 
			  Map<String, String> bcc, 
			  String subject, 
			  Map mergeObjects, 
			  String templateLocation) throws MailException; 	
}
