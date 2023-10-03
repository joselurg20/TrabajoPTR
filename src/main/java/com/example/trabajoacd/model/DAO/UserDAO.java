package com.example.trabajoacd.model.DAO;

import com.example.trabajoacd.model.domain.User;

public class UserDAO {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
