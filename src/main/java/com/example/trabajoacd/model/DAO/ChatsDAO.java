package com.example.trabajoacd.model.DAO;

import com.example.trabajoacd.model.domain.ChatRoom;

import javax.xml.bind.JAXB;
import java.io.File;

public class ChatsDAO {

    public void saveChatRoom(ChatRoom chatRoom, String filePath) {
        try {
            // Guardar el objeto ChatRoom en un archivo XML
            JAXB.marshal(chatRoom, new File(filePath));
        } catch (Exception e) {
            e.printStackTrace(); // Manejar errores apropiadamente en tu aplicación
        }
    }


}
