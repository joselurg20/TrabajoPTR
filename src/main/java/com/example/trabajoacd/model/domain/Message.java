package com.example.trabajoacd.model.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "message")
public class Message {
    private String sender;
    private Date timestamp;
    private String content;
    private List<String> messages = new ArrayList<>(); // Lista de mensajes

    public Message() {}

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.timestamp = new Date();
    }

    @XmlElement
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @XmlElement
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @XmlElement
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @XmlElement(name = "message")
    public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String messageContent) {
        messages.add(messageContent);
    }

    @Override
    public String toString() {
        return timestamp + " " + sender + ": " + content;
    }
}

