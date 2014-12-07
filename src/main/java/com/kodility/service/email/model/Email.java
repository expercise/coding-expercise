package com.kodility.service.email.model;

public class Email {

    private String to;
    private String from;
    private String subject;
    private String text;

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
