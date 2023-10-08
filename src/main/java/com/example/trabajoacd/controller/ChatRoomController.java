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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatRoomController {

    @FXML
    private ListView<String> chatListView;

    @FXML
    private TextField messageField;

    @FXML
    private Button back;

    private String selectedRoom;

    private final ChatsDAO dao = new ChatsDAO();

    private final Set<String> chatMessagesSet = new HashSet<>();

    private final ObservableList<String> chatMessages = FXCollections.observableArrayList();

    private int lastMessageIndex = -1;

    public void initialize() {
        chatListView.setItems(chatMessages);
        chatMessagesSet.addAll(chatMessages);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> updateChat()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void initData(String selectedRoom) {
        this.selectedRoom = selectedRoom;
        chatMessages.clear();
        updateChat();
        List<String> messages = dao.loadMessagesForRoom(selectedRoom);
        chatMessages.addAll(messages);
    }

    public void setSelectedRoom(String room) {
        selectedRoom = room;
        updateChat();
    }

    @FXML
    void sendMessage(ActionEvent event) {
        String messageContent = messageField.getText();

        if (!messageContent.isEmpty() && Session.currentUser != null && selectedRoom != null) {
            String senderNickname = Session.currentUser.getNickname();
            Date timestamp = new Date();

            if (sendMessage(senderNickname, messageContent, timestamp, selectedRoom)) {
                messageField.clear();
            }
        }
    }

    private boolean sendMessage(String senderNickname, String messageContent, Date timestamp, String roomName) {
        try {
            dao.saveMessageForRoom(roomName, senderNickname, messageContent, timestamp);

            String formattedMessage = roomName + ": " + timestamp + ": " + senderNickname + ": " + messageContent;

            chatMessages.add(formattedMessage);
            chatMessagesSet.add(formattedMessage);

            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar el mensaje en el XML: " + e.getMessage());
            return false;
        }
    }


    public void updateChat() {
        if (selectedRoom == null) return;

        List<String> messages = dao.loadMessagesForRoom(selectedRoom);

        for (int i = lastMessageIndex + 1; i < messages.size(); i++) {
            String message = messages.get(i);
            chatMessages.add(message);
            chatMessagesSet.add(message);
        }

        lastMessageIndex = messages.size() - 1;

        if (!chatMessages.isEmpty()) {
            chatListView.scrollTo(chatMessages.size() - 1);
        }
    }

    public void backtoroom(ActionEvent event) throws IOException {
        App.setRoot("HomePage");
    }
}
