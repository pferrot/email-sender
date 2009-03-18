package com.pferrot.emailsender.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;

public class EmailToSendConsumer implements MessageListener {

	public void onMessage(Message message) {
		System.out.println("Message received !!!");
		if (message instanceof TextMessage)
        {
			TextMessage textMessage = (TextMessage)message;
            try
            {
                System.out.println(textMessage.getStringProperty("text"));
            }
            catch (JMSException e)
            {
                e.printStackTrace();
            }
       }
		else {
			throw new RuntimeException("Not a test message!!!");
		}
		
		
	}
}
