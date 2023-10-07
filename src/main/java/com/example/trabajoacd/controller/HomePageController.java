package com.example.trabajoacd.controller;

import com.example.trabajoacd.App;
import com.example.trabajoacd.model.domain.User;
import com.example.trabajoacd.model.domain.Users;
import com.example.trabajoacd.model.threads.UpdateUsersThread;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HomePageController {

    @FXML
    private Button button1;

    @FXML
    private ComboBox<String> connectedUsersComboBox;

    @FXML
    private Button btn_room;

    @FXML
    private ListView<String> listView;

    private String selectedRoom;


    @FXML
    private void initialize() {
        String chatRoomFilePath = "chatRoom.xml";
        cargarSalasDesdeXml(chatRoomFilePath);

        try {
            UpdateUsersThread updateThread = new UpdateUsersThread();
            updateThread.start();

            ObservableList<String> connectedUsers = FXCollections.observableArrayList();
            connectedUsersComboBox.setItems(connectedUsers);

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> {
                Platform.runLater(() -> {
                    connectedUsers.clear();
                    connectedUsers.addAll(UserManager.getConnectedUsersNames());
                });
            }, 0, 5, TimeUnit.SECONDS);

            Users loadedUsers = XmlManager.loadConnectedUsersFromXml();
            List<User> userList = loadedUsers.getUsers();
            UserManager.setConnectedUsers(userList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*   private void navigateToChatRoom() {
        try {
            App.setRoot("ChatRoom");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
 /*private void navigateToChatRoom() {
     try {
         ChatRoomController chatRoomController = (ChatRoomController);
         App.setRoot("ChatRoom");
         chatRoomController.setSelectedRoom(selectedRoom);
     } catch (IOException e) {
         e.printStackTrace();
     }
 }*/
 private void navigateToChatRoom(String selectedRoom) {
     try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trabajoacd/ChatRoom.fxml"));
         Parent root = loader.load();

         // Obtener el controlador de la nueva escena
         ChatRoomController chatRoomController = loader.getController();

         // Pasar el nombre de la sala
         chatRoomController.initData(selectedRoom);

         Scene scene = new Scene(root);
         Stage stage = (Stage) btn_room.getScene().getWindow();
         stage.setScene(scene);
     } catch (IOException e) {
         e.printStackTrace();
     }
 }

    public void handleRoomSelection(javafx.event.ActionEvent event) {
        String selectedRoom = listView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            navigateToChatRoom(selectedRoom); // Pasar el nombre de la sala seleccionada
        } else {
            System.out.println("Por favor, selecciona una sala antes de continuar.");
        }
    }


    @FXML
    void addChat(ActionEvent event) throws IOException {
        App.setRoot("CreateRoom");
    }
    @FXML
    void login(ActionEvent event) throws IOException {
        App.setRoot("User");
    }

    private void cargarSalasDesdeXml(String filePath) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(filePath));

            NodeList roomNodes = doc.getElementsByTagName("name");

            for (int i = 0; i < roomNodes.getLength(); i++) {
                Element roomElement = (Element) roomNodes.item(i);
                String roomName = roomElement.getTextContent();
                listView.getItems().add(roomName);
            }
            listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

