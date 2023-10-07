package com.example.trabajoacd.controller;

import com.example.trabajoacd.App;
import com.example.trabajoacd.model.DAO.ChatsDAO;
import com.example.trabajoacd.model.domain.Message;
import com.example.trabajoacd.model.domain.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class ChatRoomController {
    @FXML
    private ListView<String> chatListView;

    @FXML
    private TextField messageField;
    @FXML
    private Button btn;
    private String selectedRoom;

    private final ChatsDAO dao = new ChatsDAO();

    private final ObservableList<String> chatMessages = FXCollections.observableArrayList();

    @FXML
    void backToRoom(ActionEvent event) throws IOException {
        App.setRoot("HomePage");
    }

    public void initialize() {
        // Cargar mensajes anteriores desde el XML
        updateChat();

        chatListView.setItems(chatMessages);
    }

    // Método para establecer la sala seleccionada desde la página anterior
    public void setSelectedRoom(String room) {
        selectedRoom = room;
    }

    @FXML
    void sendMessage(ActionEvent event) {
        String messageContent = messageField.getText();

        if (!messageContent.isEmpty() && Session.currentUser != null && selectedRoom != null) {
            String senderNickname = Session.currentUser.getNickname();
            Date timestamp = new Date();

            sendMessage(senderNickname, messageContent, timestamp, selectedRoom);

            messageField.clear();
        }
    }

    private void sendMessage(String senderNickname, String messageContent, Date timestamp, String roomName) {
        try {
            dao.saveMessageForRoom(roomName, timestamp + ":" + senderNickname + ": " + messageContent);

            String formattedMessage = timestamp + ":" + senderNickname + ": " + messageContent;
            chatMessages.add(formattedMessage);

            messageField.clear();
        } catch (Exception e) {
            System.err.println("Error al guardar el mensaje en el XML: " + e.getMessage());
        }
    }

    public void updateChat() {
        String messagesAsString = String.valueOf(dao.loadMessagesAsString());
        String[] messageArray = messagesAsString.split("\n");

        // Limpiamos la lista antes de actualizarla
        chatMessages.clear();

        // Añadimos los mensajes a la lista
        chatMessages.addAll(Arrays.asList(messageArray));
    }


    public void initData(String selectedRoom) {
        this.selectedRoom = selectedRoom;
        // Haz lo que necesites con el nombre de la sala
    }
}
