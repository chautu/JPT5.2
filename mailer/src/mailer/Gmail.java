package mailer;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class Gmail {
	private String subject;
	private String content;
	private String from;
	private Date date;
	private ArrayList<String> cc;
	private ArrayList<String> bcc;
	private ArrayList<String> to;
	private ArrayList<File> attachFiles;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ArrayList<String> getCc() {
		return cc;
	}

	public void setCc(ArrayList<String> cc) {
		this.cc = cc;
	}

	public ArrayList<String> getBcc() {
		return bcc;
	}

	public void setBcc(ArrayList<String> bcc) {
		this.bcc = bcc;
	}

	public ArrayList<String> getTo() {
		return to;
	}

	public void setTo(ArrayList<String> to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ArrayList<File> getAttachFiles() {
		return attachFiles;
	}

	public void setAttachFiles(ArrayList<File> attachFiles) {
		this.attachFiles = attachFiles;
	}
}
