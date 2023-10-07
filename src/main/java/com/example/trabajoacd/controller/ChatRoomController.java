package com.example.trabajoacd.controller;

import com.example.trabajoacd.App;
import com.example.trabajoacd.model.DAO.ChatsDAO;
import com.example.trabajoacd.model.domain.Session;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class ChatRoomController {
    @FXML
    private ListView<String> chatListView;

    @FXML
    private TextField messageField;
    @FXML
    private Button back;

    private String selectedRoom;

    private final ChatsDAO dao = new ChatsDAO();

    private final ObservableList<String> chatMessages = FXCollections.observableArrayList();


    public void initialize() {
        updateChat();

        chatListView.setItems(chatMessages);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), e -> updateChat()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

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
            dao.saveMessageForRoom(roomName, senderNickname, messageContent, timestamp);

            String formattedMessage = roomName + ": " + timestamp + ": " + senderNickname + ": " + messageContent;

            // Agregar cada parte del mensaje como un elemento separado
            chatMessages.add(roomName);
            chatMessages.add(timestamp.toString());
            chatMessages.add(senderNickname);
            chatMessages.add(messageContent);

            // Limpia el campo de mensaje
            messageField.clear();
        } catch (Exception e) {
            System.err.println("Error al guardar el mensaje en el XML: " + e.getMessage());
        }
    }

    public void updateChat() {
        String messagesAsString = String.valueOf(dao.loadMessagesAsString());
        String[] messageArray = messagesAsString.split("\n");

        chatMessages.clear();

        for (String message : messageArray) {
            String[] parts = message.split(":");
            if (parts.length >= 2) {
                String roomName = parts[0].trim();
                if (selectedRoom.equals(roomName)) {
                    chatMessages.add(message);
                }
            }
        }

        if (!chatMessages.isEmpty()) {
            chatListView.scrollTo(chatMessages.size() - 1);
        }
    }

    public void initData(String selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    public void backtoroom(ActionEvent event) throws IOException {
        App.setRoot("HomePage");
    }
}
