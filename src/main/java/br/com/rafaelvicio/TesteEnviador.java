package br.com.rafaelvicio;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.apache.activemq.Message;

public class TesteEnviador {
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();

		// imports do package javax.jms
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila);

		consumer.setMessageListener(new MessageListener(){

			@Override
			public void onMessage(javax.jms.Message message) {
				TextMessage textMessage  = (TextMessage)message;
		        try{
		            System.out.println(textMessage.getText());
		        } catch(JMSException e){
		            e.printStackTrace();
		        }   
			}

		});

		new Scanner(System.in).nextLine();

		session.close();
		connection.close();
		context.close();

	}
}