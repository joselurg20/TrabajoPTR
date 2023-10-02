package com.example.trabajoacd.controller;

import com.example.trabajoacd.model.domain.User;
import com.example.trabajoacd.model.domain.Users;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static List<User> connectedUsers = new ArrayList<>();

    public static void addUser(User user) {
        connectedUsers.add(user);
        Users users = new Users(connectedUsers);
        XmlManager.saveConnectedUsersToXml(users);
    }

    public static List<User> getConnectedUsers() {
        return connectedUsers;
    }

    public static String getConnectedUsersNames() {
        List<String> usernames = new ArrayList<>();
        for (User user : connectedUsers) {
            usernames.add(user.getNickname());
        }
        return String.join(", ", usernames);
    }

    public static void setConnectedUsers(List<User> users) {
        connectedUsers = users;
    }
}



