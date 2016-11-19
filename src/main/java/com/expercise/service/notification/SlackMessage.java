package com.expercise.service.notification;

public class SlackMessage {

    private String channel;

    private String text;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SlackMessage{" +
                "channel='" + channel + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

}
