package com.pferrot.emailsender.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class EmailToSendProducer {
	
//	private final static Log log = LogFactory.getLog(EmailToSendProducer.class);
	
	public static final String SENDER_NAME_KEY = "senderName";
	public static final String SENDER_ADDRESS_KEY = "senderAddress";
	public static final String TO_KEY = "to";
	public static final String SUBJECT_KEY = "subject";
	public static final String BODY_TEXT_KEY = "bodyText";
	public static final String BODY_HTML_KEY = "bodyHtml";
	
    private JmsTemplate jmsTemplate;
    private Destination destination;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	// TODO: multiple to / cc / bcc
	public void sendMessage(final String senderName, 
			final String senderAddress,
			final String to,
//			  Map<String, String> to,  
//			  Map<String, String> cc, 
//			  Map<String, String> bcc, 
			final String subject, 
			final String bodyText, 
			final String bodyHtml) throws JMSException {
		MessageCreator creator = new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = null;
				
				mapMessage = session.createMapMessage();
				mapMessage.setString(SENDER_NAME_KEY, senderName);
				mapMessage.setString(SENDER_ADDRESS_KEY, senderAddress);
				mapMessage.setString(TO_KEY, to);
				mapMessage.setString(SUBJECT_KEY, subject);
				mapMessage.setString(BODY_TEXT_KEY, bodyText);
				mapMessage.setString(BODY_HTML_KEY, bodyHtml);
			
				return mapMessage;
			}
		};
		jmsTemplate.send(destination, creator);
	}

}
