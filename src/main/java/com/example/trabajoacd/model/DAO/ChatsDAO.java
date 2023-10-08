package com.example.trabajoacd.model.DAO;

import com.example.trabajoacd.controller.MessageXmlManager;
import com.example.trabajoacd.model.domain.Message;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatsDAO {

    public void saveMessageForRoom(String roomName, String sender, String message, Date timestamp) {
        // Cargar mensajes desde el XML
        Message chatRoom = MessageXmlManager.loadMessagesFromXml();

        // Agregar el nuevo mensaje
        chatRoom.add(roomName, sender, message, timestamp);

        // Guardar los mensajes actualizados
        MessageXmlManager.saveMessagesToXml(chatRoom);
    }

    public List<String> loadMessagesForRoom(String roomName) {
        List<String> messages = new ArrayList<>();
        Message message = MessageXmlManager.loadMessagesFromXml();

        for (String msg : message.getMessages()) {
            String[] parts = msg.split(":");
            if (parts.length >= 1) {
                String currentRoom = parts[0].trim();
                if (roomName.equals(currentRoom)) {
                    messages.add(msg);
                }
            }
        }

        return messages;
    }
}











