package com.pferrot.emailsender.manager.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.pferrot.emailsender.manager.MailManager;

public class MailManagerImpl implements MailManager {
	
	private final static Log log = LogFactory.getLog(MailManagerImpl.class);
	
	private static final String HTML_TEMPLATE_SUFFIX = "html.vm";
	private static final String TEXT_TEMPLATE_SUFFIX = "text.vm";
	
	private JavaMailSender javaMailSender;
	private VelocityEngine velocityEngine;
	
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	public void send(String senderName, String senderAddress, 
			 Map<String, String> to,
	         Map<String, String> cc, 
	         Map<String, String> bcc, 
	         String subject, 
	         String bodyText, 
	         String bodyHtml)
	throws MailException {
		
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			if (log.isDebugEnabled()) {
				log.debug("Sender: " + senderName + " (" + senderAddress + ")");
			}
			helper.setFrom(senderAddress, senderName);
			
			if (to != null && !to.isEmpty()) {
				Iterator<String> ite = to.keySet().iterator();
				while (ite.hasNext()) {
					String name = ite.next();
					String recipient = to.get(name);
					if (log.isDebugEnabled()) {
						log.debug("TO: " + name + " (" + recipient + ")");
					}						
					helper.addTo(recipient, name);
				}
			}			
			
			if (cc != null && !cc.isEmpty()) {
				Iterator<String> ite = cc.keySet().iterator();
				while (ite.hasNext()) {
					String name = ite.next();
					String recipient = cc.get(name);
					if (log.isDebugEnabled()) {
						log.debug("CC: " + name + " (" + recipient + ")");
					}					
					helper.addCc(recipient, name);
				}
			}
			
			if (bcc != null && !bcc.isEmpty()) {
				Iterator<String> ite = bcc.keySet().iterator();
				while (ite.hasNext()) {
					String name = ite.next();
					String recipient = bcc.get(name);
					if (log.isDebugEnabled()) {
						log.debug("BCC: " + name + " (" + recipient + ")");
					}						
					helper.addBcc(recipient, name);
				}
			}
			
			helper.setText(bodyText, bodyHtml);
			
			if (log.isDebugEnabled()) {
				log.debug("Subject: " + subject);
			}
			helper.setSubject(subject);
			
			// TODO: attachements if needed.
		
			javaMailSender.send(helper.getMimeMessage());
		}
		catch (MailException e) {
			throw e;
		}
		catch (Exception e) {
			throw new MailPreparationException(e);
		}		
	}

	public void send(String senderName, String senderAddress, 
					 Map<String, String> to,
			         Map<String, String> cc, 
			         Map<String, String> bcc, 
			         String subject, 
			         Map mergeObjects, 
			         String templateLocation)
			throws MailException {
		
		try {
			send(senderName, senderAddress, to, cc, bcc, subject, getText(mergeObjects, templateLocation), getHtml(mergeObjects, templateLocation));
		}
		catch (MailException e) {
			throw e;
		}
		catch (Exception e) {
			throw new MailPreparationException(e);
		}		
	}
	
	public String getText(Map mergeObjects, String templateLocation) throws MailException {
		try {
			StringWriter text = new StringWriter();
			VelocityEngineUtils.mergeTemplate(velocityEngine, templateLocation + "/" + TEXT_TEMPLATE_SUFFIX, mergeObjects, text);
			text.close();
			
			if (log.isDebugEnabled()) {
				log.debug("Text message: \n" + text.toString());
			}
			
			return text.toString();
		}
		catch (IOException e) {
			throw new MailPreparationException(e);
		}
	}
	
	public String getHtml(Map mergeObjects, String templateLocation) throws MailException {
		try {
			StringWriter html = new StringWriter();
			VelocityEngineUtils.mergeTemplate(velocityEngine, templateLocation + "/" + HTML_TEMPLATE_SUFFIX, mergeObjects, html);
			html.close();
			
			if (log.isDebugEnabled()) {
				log.debug("HTML message: \n" + html.toString());
			}
			
			return html.toString();
		}
		catch (IOException e) {
			throw new MailPreparationException(e);
		}		
	}

}
