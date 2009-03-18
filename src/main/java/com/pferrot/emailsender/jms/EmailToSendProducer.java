package com.pferrot.emailsender.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class EmailToSendProducer {
	
    private JmsTemplate jmsTemplate;
    private Destination destination;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public void sendMessage() {
		MessageCreator creator = new MessageCreator() 
		{
			public Message createMessage(Session session)
			{
				TextMessage message = null;
				try 
				{
					message = session.createTextMessage();
					message.setStringProperty("text", "Hello World");
					System.out.println("Produced hello world...");
				}
				catch (JMSException e)
				{
					e.printStackTrace();
				}
				return message;
			}
		};
		jmsTemplate.send(destination, creator);
	}

}
