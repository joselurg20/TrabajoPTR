package com.example.trabajoacd.controller;

import com.example.trabajoacd.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.example.trabajoacd.model.domain.User;
import com.example.trabajoacd.model.domain.Users;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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
        String nickname= usernameField.getText();
        // Verificar si el usuario existe en el archivo XML de usuarios
        if (userExistsInXML(nickname)) {
            // Permitir que el usuario entre
            System.out.println("Bienvenido, " + nickname + "!");
            App.setRoot("HomePage");
        } else {
            // Mensaje de error
            System.out.println("El usuario " + nickname + " no existe en el sistema. No puede entrar.");
        }
    }
    private boolean userExistsInXML(String nickname) {
        List<User> users = loadUsersFromXML();
        for (User user : users) {
            if (user.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
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
