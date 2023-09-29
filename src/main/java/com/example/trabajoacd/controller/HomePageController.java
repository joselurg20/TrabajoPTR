package com.example.trabajoacd.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

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
    void add(ActionEvent event) throws IOException {
        App.setRoot("CreateRoom");
    }

    @FXML
    private ListView<String> listView;

//    @FXML
  //  private GridPane gridP;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
