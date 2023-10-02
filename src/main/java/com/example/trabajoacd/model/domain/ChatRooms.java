package com.example.trabajoacd.model.domain;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "chatRooms")
public class ChatRooms {
    private List<ChatRoom> chatRooms;

    @XmlElement(name = "chatRoom")
    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(List<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }
}
