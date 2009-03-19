package com.pferrot.emailsender.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailException;

import com.pferrot.emailsender.manager.MailManager;

public class EmailToSendConsumer implements MessageListener {
	
	private final static Log log = LogFactory.getLog(EmailToSendConsumer.class);
	
	private MailManager mailManager;
	
	public void setMailManager(MailManager mailManager) {
		this.mailManager = mailManager;
	}

	public void onMessage(Message message) {
		if (log.isDebugEnabled()) {
			log.debug("Message received: " + message);
		}
		
		if (message instanceof MapMessage)
        {
			MapMessage mapMessage = (MapMessage)message;
            try
            {
            	final String senderName = mapMessage.getString(EmailToSendProducer.SENDER_NAME_KEY);
            	final String senderAddress = mapMessage.getString(EmailToSendProducer.SENDER_ADDRESS_KEY);
            	final String to = mapMessage.getString(EmailToSendProducer.TO_KEY);
            	final Map<String, String> toMap = new HashMap<String, String>();
            	toMap.put(to, to);
            	final String subject = mapMessage.getString(EmailToSendProducer.SUBJECT_KEY);
            	final String bodyText = mapMessage.getString(EmailToSendProducer.BODY_TEXT_KEY);
            	final String bodyHtml = mapMessage.getString(EmailToSendProducer.BODY_HTML_KEY);
            	mailManager.send(senderName, senderAddress, toMap, null, null, subject, bodyText, bodyHtml);
            }
            catch (JMSException jmse)
            {
            	// TODO
            	throw new RuntimeException(jmse);
            }
            catch (MailException me) {
            	throw new RuntimeException(me);
            }
       }
		else {
			throw new RuntimeException("Not a map message!!!");
		}
		
		
	}
}
