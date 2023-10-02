package com.example.trabajoacd.model.threads;

import com.example.trabajoacd.controller.UserManager;
import com.example.trabajoacd.controller.XmlManager;
import com.example.trabajoacd.model.domain.User;
import com.example.trabajoacd.model.domain.Users;
import javafx.scene.control.ComboBox;

import java.util.List;

public class UpdateUsersThread extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                List<User> connectedUsers = UserManager.getConnectedUsers();
                Users users = new Users(connectedUsers); // Crear un objeto Users

                XmlManager.saveConnectedUsersToXml(users);

                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}


