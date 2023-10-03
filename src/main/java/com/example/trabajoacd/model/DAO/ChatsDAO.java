package com.example.trabajoacd.model.DAO;

import com.example.trabajoacd.model.domain.ChatRoom;
import com.example.trabajoacd.controller.MessageXmlManager;

import java.util.List;

public class ChatsDAO {

    public void saveMessage(String messageContent, String s) {
        ChatRoom chatRoom = MessageXmlManager.loadMessagesFromXml();
        chatRoom.addMessage(messageContent);
        MessageXmlManager.saveMessagesToXml(chatRoom);
    }

    public String loadMessagesAsString() {
        ChatRoom chatRoom = MessageXmlManager.loadMessagesFromXml();
        List<String> messageList = chatRoom.getMessages();
        StringBuilder messages = new StringBuilder();
        for (String message : messageList) {
            messages.append(message).append("\n");
        }
        return messages.toString();
    }
}

