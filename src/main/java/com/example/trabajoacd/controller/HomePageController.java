package com.example.trabajoacd.controller;

import com.example.trabajoacd.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode; // Importa esta clase

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class HomePageController {
    @FXML
    private Button button1;
    @FXML
    private ComboBox<?> cmBox;
    @FXML
    private Button btn_room;
    @FXML
    private ListView<String> listView;

    @FXML
    private void initialize() {
        // Ruta al archivo XML que deseas leer
        String filePath = "chatRoom.xml";

        try {
            // Crear un analizador DOM
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(filePath));

            // Obtener la lista de elementos "room"
            NodeList roomNodes = doc.getElementsByTagName("name");

            // Agregar las salas a la lista
            for (int i = 0; i < roomNodes.getLength(); i++) {
                Element roomElement = (Element) roomNodes.item(i);
                String roomName = roomElement.getTextContent();
                listView.getItems().add(roomName);
            }

            // Configurar el modo de selección de ListView
            listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    @FXML
    private void handleRoomSelection(ActionEvent event) {
        // Verifica si se ha seleccionado una sala en el ListView
        String selectedRoom = listView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            // Si hay una sala seleccionada, navega a la página "chatRooms.fxml"
            navigateToChatRoom();
        } else {
            // Si no hay una sala seleccionada, muestra un mensaje o realiza alguna acción
            System.out.println("Por favor, selecciona una sala antes de continuar.");
        }
    }
*/
    private void navigateToChatRoom() {
        try {
            App.setRoot("ChatRoom");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleRoomSelection(javafx.event.ActionEvent event) {
        // Verifica si se ha seleccionado una sala en el ListView
        String selectedRoom = listView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            // Si hay una sala seleccionada, navega a la página "chatRooms.fxml"
            navigateToChatRoom();
        } else {
            // Si no hay una sala seleccionada, muestra un mensaje o realiza alguna acción
            System.out.println("Por favor, selecciona una sala antes de continuar.");
        }
    }

    public void addChat(javafx.event.ActionEvent event) {
    }
}
