package com.kodility.service.email.model;

public class EmailBuilder {

    private String to;
    private String from;
    private String subject;
    private String text;

    public EmailBuilder to(String to) {
        this.to = to;
        return this;
    }

    public EmailBuilder from(String from) {
        this.from = from;
        return this;
    }

    public EmailBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailBuilder text(String text) {
        this.text = text;
        return this;
    }

    public Email build() {
        Email email = new Email();
        email.setTo(to);
        email.setFrom(from);
        email.setSubject(subject);
        email.setText(text);
        return email;
    }

}
