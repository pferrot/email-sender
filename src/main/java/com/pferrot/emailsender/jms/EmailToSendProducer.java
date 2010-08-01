package com.pferrot.emailsender.jms;

import java.util.Iterator;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.pferrot.emailsender.Utils;

public class EmailToSendProducer {
	
//	private final static Log log = LogFactory.getLog(EmailToSendProducer.class);
	
	public static final String SENDER_NAME_KEY = "senderName";
	public static final String SENDER_ADDRESS_KEY = "senderAddress";
	public static final String TO_KEY = "to";
	public static final String NB_INLINE_RESOURCES_KEY = "inlineResources";
	public static final String INLINE_RESOURCES_KEY = "inlineResources";	
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
			final String bodyHtml,
			final Map<String, String> inlineResources) throws JMSException {
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
				
				if (inlineResources != null) {
					mapMessage.setInt(NB_INLINE_RESOURCES_KEY, inlineResources.size());
					
					Iterator<String> ite = inlineResources.keySet().iterator();
					int counter = 0;
					while (ite.hasNext()) {
						String resourceId = ite.next();
						String path = inlineResources.get(resourceId);
						mapMessage.setString(INLINE_RESOURCES_KEY + counter, Utils.encodeInlineResource(resourceId, path));
						counter++;
					}
				}
				else {
					mapMessage.setInt(NB_INLINE_RESOURCES_KEY, 0);
				}
			
				return mapMessage;
			}
		};
		jmsTemplate.send(destination, creator);
	}

}
