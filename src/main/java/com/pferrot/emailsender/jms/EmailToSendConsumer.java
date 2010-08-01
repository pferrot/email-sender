package com.pferrot.emailsender.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.mail.MailException;

import com.pferrot.emailsender.Utils;
import com.pferrot.emailsender.manager.MailManager;

public class EmailToSendConsumer implements SessionAwareMessageListener {
	
	private final static Log log = LogFactory.getLog(EmailToSendConsumer.class);
	
	private MailManager mailManager;
	
	public void setMailManager(MailManager mailManager) {
		this.mailManager = mailManager;
	}

	/**
	 * TODO: since this processing happens in a transaction (see applicationContext-email-sender.xml,
	 * sessionTransacted = true), what if a message always fails? Should we purge the queue after
	 * some time? But maybe the problem is with our server...
	 */
	public void onMessage(final Message message, final Session session) throws JMSException {
		if (log.isDebugEnabled()) {
			log.debug("Message received: " + message);
		}
		
		if (message instanceof MapMessage) {
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
            	final Integer nbInlineResources = mapMessage.getInt(EmailToSendProducer.NB_INLINE_RESOURCES_KEY);
            	Map<String, String> inlineResourcesMap = null;
            	if (nbInlineResources > 0) {
            		inlineResourcesMap =  new HashMap<String, String>();
	            	for (int i = 0; i < nbInlineResources; i++) {
	            		final String inlineResourceCode = mapMessage.getString(EmailToSendProducer.INLINE_RESOURCES_KEY + i);
	            		final String[] idAndPath = Utils.decodeInlineResource(inlineResourceCode);
	            		inlineResourcesMap.put(idAndPath[0], idAndPath[1]);
	            	}
            	}
            	mailManager.sendSync(senderName, senderAddress, toMap, null, null, subject, bodyText, bodyHtml, inlineResourcesMap);
            }
            catch (JMSException jmse)
            {
            	if (log.isWarnEnabled()) {
            		log.warn("Email could not be sent out (JMSException): " + jmse.getMessage());
            	}
            	throw jmse;
            }
            catch (MailException me) {
            	final JMSException jmsException = new JMSException("Mail exception.");
            	jmsException.setLinkedException(me);
            	if (log.isWarnEnabled()) {
            		log.warn("Email could not be sent out (MailException): " + me.getMessage());
            	}
            	throw jmsException;
            }
        }
		// Not a map message, ignore it.
		else {
			if (log.isWarnEnabled()) {
				log.warn("Email not sent, not a map message: " + message);
			}
		}		
	}
}
