package com.kodility.service.email.model;

public class Email {

    private String to;

    private String from;

    private String subject;

    private String text;

    public String getTo() {
        return to;
    }

    public Email setTo(String to) {
        this.to = to;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public Email setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Email setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getText() {
        return text;
    }

    public Email setText(String text) {
        this.text = text;
        return this;
    }

}
