package com.kodility.service.email.model;

public class Email {

    private String to;

    private String from;

    private String subject;

    private String content;

    private String subjectKey;

    private String templateName;

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

    public String getContent() {
        return content;
    }

    public Email setContent(String content) {
        this.content = content;
        return this;
    }

    public String getSubjectKey() {
        return subjectKey;
    }

    public Email setSubjectKey(String subjectKey) {
        this.subjectKey = subjectKey;
        return this;
    }

    public String getTemplateName() {
        return templateName;
    }

    public Email setTemplateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

}
