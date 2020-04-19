package mailer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import javax.swing.JOptionPane;

public class MailReceiver {
	private Store store;
	private Folder folder;
	private Session session;
	private Properties properties;
	private static MailReceiver instance;
	private ArrayList<MimeMultipart> mimeMultiparts;
	private Message[] messages;

	public static MailReceiver getInstance() {
		if (instance == null) {
			instance = new MailReceiver();
		}
		return instance;
	}

	public MailReceiver() {
		mimeMultiparts = new ArrayList<>();
	}

	private Properties getServerProperties(String protocol, String host, String port) {
		Properties properties = new Properties();

		// server setting
		properties.put(String.format("mail.%s.host", protocol), host);
		properties.put(String.format("mail.%s.port", protocol), port);

		// SSL setting
		properties.setProperty(String.format("mail.%s.socketFactory.class", protocol),
				"javax.net.ssl.SSLSocketFactory");
		properties.setProperty(String.format("mail.%s.socketFactory.fallback", protocol), "false");
		properties.setProperty(String.format("mail.%s.socketFactory.port", protocol), 
				String.valueOf(port));

		return properties;
	}

	private void openInbox(String arg) {
		try {
			folder = store.getFolder(arg);
			folder.open(Folder.READ_ONLY);
		} catch (MessagingException e) {
		}
	}
        // public ArrayList<Gmail> getAllMail() {
	// try {
	// openInbox("Inbox");
	// Message messages[] = folder.search(new FlagTerm(new Flags(Flag.SEEN),
	// false));
	// fetchProfile(messages);
	// ArrayList<Gmail> list = fetchMail(messages);
	// return list;
	//
	// } catch (MessagingException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	private void fetchProfile(Message[] messages) {
		FetchProfile fp = new FetchProfile();
		fp.add(FetchProfile.Item.ENVELOPE);
		fp.add(FetchProfile.Item.CONTENT_INFO);
		try {
			folder.fetch(messages, fp);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Gmail> findSpamMail() {
		try {
			openInbox("[Gmail]/Spam");
			messages = folder.getMessages();
			fetchProfile(messages);
			ArrayList<Gmail> list = fetchMail(messages);
			return list;

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Gmail> findSentMail() {
		try {
			openInbox("[Gmail]/Sent Mail");
			messages = folder.getMessages();
			fetchProfile(messages);
			ArrayList<Gmail> list = fetchMail(messages);
			return list;

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Gmail> findInboxMail() {
		try {
			openInbox("INBOX");
			messages = folder.getMessages();
			fetchProfile(messages);
			ArrayList<Gmail> list = fetchMail(messages);
			return list;

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Gmail> findTrashMail() {
		try {
			openInbox("[Gmail]/Trash");
			messages = folder.getMessages();
			fetchProfile(messages);
			ArrayList<Gmail> list = fetchMail(messages);
			return list;

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Gmail> findDraftMail() {
		setPropertiesWithImap();
		try {
			openInbox("[Gmail]/Drafts");
			messages = folder.getMessages();
			fetchProfile(messages);
			ArrayList<Gmail> list = fetchMail(messages);
			return list;
		} catch (MessagingException e) {
		}
		return null;
	}

	private ArrayList<Gmail> fetchMail(Message[] messages) {
		ArrayList<Gmail> list = new ArrayList<>();
		for (Message message : messages) {
			Gmail gmail = new Gmail();
			try {
				gmail.setFrom(parseAddressFrom(message.getFrom()[0].toString()));
				gmail.setSubject(message.getSubject());
				gmail.setDate(message.getSentDate());
				if (message.isMimeType("text/plain")) {
					gmail.setContent(message.getContent().toString());
				} else {
					if (message.getContent() instanceof String) {
						gmail.setContent(message.getContent().toString());
					} else if (message.getContent() instanceof Multipart) {
						MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
						mimeMultiparts.add(mimeMultipart);
						String result = getTextFromMimeMultipart(mimeMultipart);
						gmail.setContent(result);
					}
				}
				list.add(gmail);
			} catch (MessagingException | IOException e) {

			}
		}
		return list;
	}

	private void setPropertiesWithImap() {
		properties = getServerProperties(Constant.PROTOCOL_IMAP, Constant.HOST_IMAP, Constant.PORT_IMAP);
		session = Session.getInstance(properties);

		try {
			store = session.getStore(Constant.PROTOCOL_IMAP);
			store.connect(Account.getInstance().getUsername(), Account.getInstance().getPassword());
		} catch (MessagingException e) {

		}
	}

	public boolean checkLogin() {
		properties = getServerProperties(Constant.PROTOCOL_IMAP, Constant.HOST_IMAP, Constant.PORT_IMAP);
		session = Session.getInstance(properties);
		try {
			store = session.getStore(Constant.PROTOCOL_IMAP);
			store.connect(Account.getInstance().getUsername(), Account.getInstance().getPassword());
			return true;
		} catch (MessagingException e) {

			return false;
		}
	}

	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			MimeBodyPart bodyPart = (MimeBodyPart) mimeMultipart.getBodyPart(i);

			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break;
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

	public void download(int index) throws MessagingException, IOException {
		MimeMultipart mimeMultipart = (MimeMultipart) messages[index].getContent();
		String contentType = messages[index].getContentType();
		if (contentType.contains("multipart")) {
			int count = mimeMultipart.getCount();
			for (int i = 0; i < count; i++) {
				MimeBodyPart bodyPart = (MimeBodyPart) mimeMultipart.getBodyPart(i);
				if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
					JOptionPane.showMessageDialog(null, "Dowload thành công");
					String fileName = bodyPart.getFileName();
					bodyPart.saveFile(Constant.SAVE_DIRECTORY + File.separator + fileName);
				}
			}
		} 
	}

	private String parseAddressFrom(String from) {
		if (from.contains("<"))
			return from.split("<")[1].split(">")[0];
		return from;
	}
}
