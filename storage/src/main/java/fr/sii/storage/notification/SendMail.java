package fr.sii.storage.notification;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import fr.sii.storage.exception.NotificationException;


public class SendMail implements Notification {


	private String email;
	
	public SendMail(String email) {
		super();
		this.email = email;
	}


	@Override
	public void send(String mes) throws NotificationException {
		// prepare message
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", "localhost");
		Session session = Session.getDefaultInstance(properties);
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("test@sii.fr"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject(mes);
			message.setText(mes);
			// Send message
			Transport.send(message);
		} catch (MessagingException e) {
			throw new NotificationException("failed to send email", e);
		}
	}

}
