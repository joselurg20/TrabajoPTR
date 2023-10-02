package com.example.trabajoacd.controller;

import com.example.trabajoacd.App;
import com.example.trabajoacd.model.DAO.ChatsDAO;
import com.example.trabajoacd.model.domain.ChatRoom;
import com.example.trabajoacd.model.domain.ChatRooms;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CreateRoomController {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    void saveRoom(ActionEvent event) {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();

        ChatRoom room = new ChatRoom();
        room.setId(id);
        room.setName(name);

        // Cargar salas existentes desde el archivo XML
        List<ChatRoom> existingRooms = loadExistingChatRooms("chatRoom.xml");

        // Agregar la nueva sala
        existingRooms.add(room);

        // Guardar todas las salas, incluida la nueva, en el archivo XML
        saveAllChatRooms(existingRooms, "chatRoom.xml");
    }

    @FXML
    void back(ActionEvent event) {
        try {
            App.setRoot("HomePage");
        } catch (Exception e) {
            e.printStackTrace(); // Manejar errores apropiadamente en tu aplicación
        }
    }


    private List<ChatRoom> loadExistingChatRooms(String filePath) {
        try {
            // Cargar las salas existentes desde el archivo XML
            File file = new File(filePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(ChatRooms.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ChatRooms chatRooms = (ChatRooms) jaxbUnmarshaller.unmarshal(file);
            return chatRooms.getChatRooms();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveAllChatRooms(List<ChatRoom> chatRooms, String filePath) {
        try {
            // Guardar todas las salas en el archivo XML
            ChatRooms rooms = new ChatRooms();
            rooms.setChatRooms(chatRooms);

            File file = new File(filePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(ChatRooms.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(rooms, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
