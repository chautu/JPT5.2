package mailer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {
	private static MailSender instance;
	private Message mimeMessage;

	public static MailSender getInstance() {
		if (instance == null) {
			instance = new MailSender();
		}
		return instance;
	}

	public MailSender() {
	}

	public Properties smtpProperties(String username, String password) {
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.port", String.valueOf(587));
		properties.setProperty("mail.user", username);
		properties.setProperty("mail.password", password);
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.auth", "true");

		return properties;
	}

	private Session createSession() {
		Properties properties = smtpProperties(Account.getInstance().getUsername(),
				Account.getInstance().getPassword());
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Account.getInstance().getUsername(),
						Account.getInstance().getPassword());
			}
		});
		return session;
	}

	public void sendMail(ArrayList<String> toList, ArrayList<String> ccList, 
			ArrayList<String> bccList, String subject,
			String content, File[] attachFiles) throws MessagingException, IOException {
		Session session = createSession();
		if (session != null)
			mimeMessage = new MimeMessage(session);
		prepare(toList, ccList, bccList, subject, content, attachFiles);
		Transport.send(mimeMessage);
	}

	public void prepare(ArrayList<String> toList, ArrayList<String> ccList, ArrayList<String> bccList, String subject,
			String content, File[] attachFiles) throws AddressException, MessagingException, IOException {
		mimeMessage.setFrom(new InternetAddress(Account.getInstance().getUsername()));

		setRecipientType(toList, Message.RecipientType.TO);

		if (ccList != null) {
			setRecipientType(ccList, Message.RecipientType.CC);
		}
		if (bccList != null) {
			setRecipientType(bccList, Message.RecipientType.BCC);
		}
	
		mimeMessage.setSubject(subject);
		mimeMessage.setSentDate(new Date());

		addContent(content, attachFiles);

	}

	private void setRecipientType(ArrayList<String> list, javax.mail.Message.RecipientType to)
			throws MessagingException {
		InternetAddress[] toAddresses = new InternetAddress[list.size()];
		for (int i = 0; i < list.size(); i++) {
			toAddresses[i] = new InternetAddress(list.get(i).trim());
		}

		for (int i = 0; i < list.size(); i++) {
			mimeMessage.addRecipient(to, toAddresses[i]);
		}
	}

	public void addContent(String content, File[] attachFiles) throws MessagingException, IOException {
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(content, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		if (attachFiles != null && attachFiles.length > 0) {
			for (File aFile : attachFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();

				try {
					attachPart.attachFile(aFile);
				} catch (IOException ex) {
					throw ex;
				}

				multipart.addBodyPart(attachPart);
			}
		}
		mimeMessage.setContent(multipart);
	}

}
