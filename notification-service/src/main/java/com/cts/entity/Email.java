package com.cts.entity;

public class Email {

	private String name;
	public Email() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Email(String name, String to, String subject, String body) {
		super();
		this.name = name;
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	private String to;
	private String subject;
	private String body;
	
}
