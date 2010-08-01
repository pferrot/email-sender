package com.pferrot.emailsender.manager;

import java.util.Map;

import org.springframework.mail.MailException;

public interface MailManager {
	
	/**
	 * Creates a JMS message, i.e. it is async.
	 * 
	 * @param senderName
	 * @param senderAddress
	 * @param to: key = name, value = email address
	 * @param cc: key = name, value = email address
	 * @param bcc: key = name, value = email address
	 * @param subject
	 * @param mergeObjects
	 * @param templateLocation
	 * @throws MailException
	 */
	void send(String senderName, 
			  String senderAddress,
			  Map<String, String> to,  
			  Map<String, String> cc, 
			  Map<String, String> bcc,
			  String subject, 
			  Map mergeObjects,
			  String templateLocation) throws MailException;

	/**
	 * Creates a JMS message, i.e. it is async.
	 * 
	 * @param senderName
	 * @param senderAddress
	 * @param to: key = name, value = email address
	 * @param cc: key = name, value = email address
	 * @param bcc: key = name, value = email address
	 * @param subject
	 * @param mergeObjects
	 * @param templateLocation
	 * @param inlineResources: key = contentId, value = path (in the classpath)
	 * @throws MailException
	 */
	void send(String senderName, 
			  String senderAddress,
			  Map<String, String> to,  
			  Map<String, String> cc, 
			  Map<String, String> bcc,
			  String subject, 
			  Map mergeObjects, 
			  String templateLocation,
			  Map<String, String> inlineResources) throws MailException;
	
	/**
	 * Creates a JMS message, i.e. it is async.
	 * 
	 * @param senderName
	 * @param senderAddress
	 * @param to: key = name, value = email address
	 * @param cc: key = name, value = email address
	 * @param bcc: key = name, value = email address
	 * @param subject
	 * @param bodyText
	 * @param bodyHtml
	 * @param inlineResources: key = contentId, value = path (in the classpath)
	 * @throws MailException
	 */
	void send(String senderName, 
			  String senderAddress,
			  Map<String, String> to,  
			  Map<String, String> cc, 
			  Map<String, String> bcc, 
			  String subject, 
			  String bodyText, 
		      String bodyHtml,
		      Map<String, String> inlineResources) throws MailException;	
	
	/**
	 * Send the email strait away, i.e. it is sync.
	 * 
	 * @param senderName
	 * @param senderAddress
	 * @param to: key = name, value = email address
	 * @param cc: key = name, value = email address
	 * @param bcc: key = name, value = email address
	 * @param subject
	 * @param mergeObjects
	 * @param templateLocation
	 * @param inlineResources: key = contentId, value = path (in the classpath)
	 * @throws MailException
	 */
	void sendSync(String senderName, 
			  String senderAddress,
			  Map<String, String> to,  
			  Map<String, String> cc, 
			  Map<String, String> bcc,
			  String subject, 
			  Map mergeObjects, 
			  String templateLocation,
			  Map<String, String> inlineResources) throws MailException;	
	
	/**
     * Send the email strait away, i.e. it is sync.
     * 
	 * @param senderName
	 * @param senderAddress
	 * @param to: key = name, value = email address
	 * @param cc: key = name, value = email address
	 * @param bcc: key = name, value = email address
	 * @param subject
	 * @param bodyText
	 * @param bodyHtml
	 * @param inlineResources: key = contentId, value = path (in the classpath)
	 * @throws MailException
	 */
	void sendSync(String senderName, 
			  String senderAddress,
			  Map<String, String> to,  
			  Map<String, String> cc, 
			  Map<String, String> bcc, 
			  String subject, 
			  String bodyText, 
		      String bodyHtml,
		      Map<String, String> inlineResources) throws MailException;	
	
//	String getText(Map mergeObjects, String templateLocation) throws MailException;
//	
//	String getHtml(Map mergeObjects, String templateLocation) throws MailException;
}
