package com.example.trabajoacd.model.DAO;

import com.example.trabajoacd.controller.MessageXmlManager;
import com.example.trabajoacd.model.domain.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatsDAO {

    public void saveMessageForRoom(String roomName, String message) {
        // Cargar mensajes desde el XML
        Message chatRoom = MessageXmlManager.loadMessagesFromXml();

        // Agregar el nuevo mensaje
        chatRoom.add(roomName, message);

        // Guardar los mensajes actualizados
        MessageXmlManager.saveMessagesToXml(chatRoom);
    }

    public List<String> loadMessagesAsString() {
        List<String> messages = new ArrayList<>();
        Message message = MessageXmlManager.loadMessagesFromXml();
        for (String msg : message.getMessages()) {
            messages.add(msg);
        }
        return messages;
    }
}











