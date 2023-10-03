package com.example.trabajoacd.controller;

import com.example.trabajoacd.App;
import com.example.trabajoacd.model.DAO.ChatsDAO;
import com.example.trabajoacd.model.domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Arrays;



public class ChatRoomController {

    @FXML
    private ListView<String> chatListView;

    @FXML
    private TextField messageField;

    private User currentUser;

    @FXML
    private Button btn;

    private final ChatsDAO dao = new ChatsDAO();

    private final ObservableList<String> chatMessages = FXCollections.observableArrayList();

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void initialize() {
        chatListView.setItems(chatMessages);
    }

    @FXML
    void backToRoom(ActionEvent event) throws IOException {
        App.setRoot("HomePage");
    }

    @FXML
    void sendMessage(ActionEvent event) {
        String messageContent = messageField.getText();
        if (!messageContent.isEmpty() && currentUser != null) {
            String senderNickname = currentUser.getNickname();
            sendMessage(senderNickname, messageContent);
        }
    }

    private void sendMessage(String senderNickname, String messageContent) {
        try {
            dao.saveMessage(senderNickname + ": " + messageContent, "message.xml");

            // Agregar el mensaje al ListView
            String formattedMessage = senderNickname + ": " + messageContent;
            chatMessages.add(formattedMessage);

            messageField.clear();
        } catch (Exception e) {
            System.err.println("Error al guardar el mensaje en el XML: " + e.getMessage());
        }
    }

    public void updateChat() {
        String messagesAsString = dao.loadMessagesAsString();
        String[] messageArray = messagesAsString.split("\n");

        // Limpiamos la lista antes de actualizarla
        chatMessages.clear();

        // Añadimos los mensajes a la lista
        chatMessages.addAll(Arrays.asList(messageArray));
    }
}
