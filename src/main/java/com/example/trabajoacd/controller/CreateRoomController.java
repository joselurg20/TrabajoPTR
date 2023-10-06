package com.example.trabajoacd.controller;

import com.example.trabajoacd.App;
import com.example.trabajoacd.model.domain.ChatRoom;
import com.example.trabajoacd.model.domain.ChatRooms;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CreateRoomController {

    @FXML
    private TextField nameField;

    @FXML
    private Label labelTxt;

    @FXML
    void saveRoom(ActionEvent event) {
        String name = nameField.getText();

        ChatRoom room = new ChatRoom();
        room.setName(name);

        // Generar un ID único para la sala
        int id = generateUniqueID();
        room.setId(id);

        // Cargar salas existentes desde el archivo XML
        List<ChatRoom> existingRooms = loadExistingChatRooms("chatRoom.xml");

        // Agregar la nueva sala
        existingRooms.add(room);

        // Guardar todas las salas, incluida la nueva, en el archivo XML
        saveAllChatRooms(existingRooms, "chatRoom.xml");

        // Mostrar un mensaje en el Label
        labelTxt.setText("La sala se ha creado correctamente.");

        // Limpiar el campo de nombre después de guardar
        nameField.clear();
    }

    @FXML
    void back(ActionEvent event) {
        try {
            App.setRoot("HomePage");
        } catch (Exception e) {
            e.printStackTrace(); // Manejar errores apropiadamente en tu aplicación
        }
    }

    private int generateUniqueID() {
        // Implementa lógica para generar un ID único según tus necesidades
        // En este ejemplo, puedes simplemente incrementar un contador
        return getNextAvailableID();
    }

    private int getNextAvailableID() {
        // Implementa la lógica para encontrar el siguiente ID disponible
        // Puedes utilizar un contador, buscar el máximo en la lista actual, etc.
        // Por ejemplo, puedes recorrer las salas existentes para encontrar el siguiente ID disponible.
        List<ChatRoom> existingRooms = loadExistingChatRooms("chatRoom.xml");
        int maxID = 0;
        for (ChatRoom room : existingRooms) {
            if (room.getId() > maxID) {
                maxID = room.getId();
            }
        }
        return maxID + 1; // Devuelve el siguiente ID disponible
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
