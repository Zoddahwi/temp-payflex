package com.fintechedge.userservice.auth.service;

public class SmsRequest {
    private String from;
    private String to;
    private String text;

    public SmsRequest(String from, String to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
    }

    // Getter and setter methods

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
