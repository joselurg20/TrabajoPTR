package com.example.trabajoacd.controller;

import com.example.trabajoacd.App;
import com.example.trabajoacd.model.domain.Session;
import com.example.trabajoacd.model.domain.User;

import com.example.trabajoacd.model.domain.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserController {

    @FXML
    private TextField usernameField;

    @FXML
    private Button buttonPass;

    @FXML
    void pass(ActionEvent event) throws IOException {
        String nickname = usernameField.getText();

        if (userExistsInXML(nickname)) {
            Session.currentUser = new User(nickname);
            UserManager.addUser(Session.currentUser);

            System.out.println("Bienvenido, " + nickname + "!");
            App.setRoot("HomePage");
        } else {
            System.out.println("El usuario con el nickname '" + nickname + "' no existe en el XML.");
            // Aquí puedes mostrar un mensaje de error al usuario si lo deseas.
        }
    }


    private boolean userExistsInXML(String nickname) {
        try {
            // Crear un objeto DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Crear un objeto DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parsear el archivo XML y cargarlo en un objeto Document
            Document document = builder.parse(new File("user.xml"));

            // Obtener todos los elementos "nickname" del documento
            NodeList nicknameNodes = document.getElementsByTagName("nickname");

            // Verificar si se encontró al menos un elemento "nickname"
            if (nicknameNodes.getLength() > 0) {
                for (int i = 0; i < nicknameNodes.getLength(); i++) {
                    Node nicknameNode = nicknameNodes.item(i);
                    String nicknameFromXML = nicknameNode.getTextContent();
                    // Comparar el nickname del XML con el que se pasó como argumento
                    if (nicknameFromXML.equals(nickname)) {
                        return true; // El nickname existe en el XML
                    }
                }
                return false; // El nickname no coincide con ninguno en el XML.
            } else {
                return false; // No se encontraron elementos "nickname" en el XML.
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Error al leer el archivo XML.
        }
    }


    private List<User> loadUsersFromXML() {
        List<User> userList = new ArrayList<>();

        try {
            File file = new File("user.xml");

            if (file.exists()) {
                JAXBContext context = JAXBContext.newInstance(Users.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Users users = (Users) unmarshaller.unmarshal(file);
                userList = users.getUsers();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return userList;
    }

}