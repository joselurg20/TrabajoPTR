package com.example.trabajoacd.controller;

import com.example.trabajoacd.App;
import com.example.trabajoacd.conexion.XmlManager;
import com.example.trabajoacd.model.domain.User;
import com.example.trabajoacd.model.domain.Users;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.example.trabajoacd.model.DAO.UserDAO.getCurrentUser;

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


    public void setConnectedUsersComboBox(ComboBox<String> comboBox) {
        this.connectedUsersComboBox = comboBox;
    }
    @FXML
    private void initialize() {
        String chatRoomFilePath = "chatRoom.xml";
        cargarSalasDesdeXml(chatRoomFilePath);

        // Configura el ComboBox para permitir múltiples líneas
        connectedUsersComboBox.setStyle("-fx-pref-width: 150; -fx-pref-height: 50;");
        connectedUsersComboBox.setMinHeight(ComboBox.USE_PREF_SIZE);

        // Programa una tarea para actualizar el ComboBox cada 5 segundos
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                // Borra y vuelve a agregar la lista de usuarios
                connectedUsersComboBox.getItems().clear();
                connectedUsersComboBox.getItems().add(""); // Agrega un espacio en blanco

                // Obtén la lista de nombres de usuarios y divídela
                List<String> connectedUsers = Arrays.asList(UserManager.getConnectedUsersNames().split(", "));

                // Agrega los nombres de usuarios uno debajo del otro
                connectedUsersComboBox.getItems().addAll(connectedUsers);
            });
        }, 0, 5, TimeUnit.SECONDS);

        Users loadedUsers = XmlManager.loadConnectedUsersFromXml();
        List<User> userList = loadedUsers.getUsers();
        UserManager.setConnectedUsers(userList);
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

    private void navigateToChatRoom(String selectedRoom) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trabajoacd/ChatRoom.fxml"));
            Parent root = loader.load();

            ChatRoomController chatRoomController = loader.getController();
            chatRoomController.initData(selectedRoom);

            Scene scene = new Scene(root);
            Stage stage = (Stage) btn_room.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRoomSelection(ActionEvent event) {
        String selectedRoom = listView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            navigateToChatRoom(selectedRoom);
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
        User user = getCurrentUser();

        if (user != null && UserManager.getConnectedUsers().contains(user)) {
            // Desconectar al usuario
            UserManager.removeUser(user);

            // Eliminar al usuario del archivo XML
            XmlManager.removeUserFromXml(String.valueOf(user));
        }

        // Actualizar la lista de usuarios conectados
        Users loadedUsers = XmlManager.loadConnectedUsersFromXml();
        List<User> userList = loadedUsers.getUsers();
        UserManager.setConnectedUsers(userList);

        App.setRoot("User");
    }


}

