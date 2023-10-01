package com.example.trabajoacd.controller;

import com.example.trabajoacd.model.DAO.ChatsDAO;
import com.example.trabajoacd.model.domain.ChatRoom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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

        ChatsDAO dao = new ChatsDAO();
        dao.saveChatRoom(room, "chatRoom.xml");
    }
}
