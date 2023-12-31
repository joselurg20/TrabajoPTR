package com.example.trabajoacd.conexion;

import com.example.trabajoacd.model.domain.User;
import com.example.trabajoacd.model.domain.Users;

import javax.xml.bind.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlManager {

    private static final String XML_FILE_PATH = "connectedUsers.xml";

    public static void saveConnectedUsersToXml(Users users) {
        try {
            JAXBContext context = JAXBContext.newInstance(Users.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File file = new File(XML_FILE_PATH);

            // Si el archivo existe, cargamos los usuarios existentes
            Users existingUsers = new Users();
            if (file.exists()) {
                existingUsers = loadConnectedUsersFromXml();
            }

            // Verificamos si users y su lista no son null
            if (users != null && users.getUsers() != null) {
                // Verificamos si existingUsers y su lista no son null
                if (existingUsers.getUsers() != null) {
                    existingUsers.getUsers().addAll(users.getUsers());
                } else {
                    // Si la lista en existingUsers es null, la inicializamos con una nueva lista
                    existingUsers.setUsers(new ArrayList<>(users.getUsers()));
                }
            }

            marshaller.marshal(existingUsers, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static Users loadConnectedUsersFromXml() {
        try {
            JAXBContext context = JAXBContext.newInstance(Users.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            File file = new File(XML_FILE_PATH);
            if (file.exists()) {
                return (Users) unmarshaller.unmarshal(file);
            }
            return new Users();
        } catch (JAXBException e) {
            e.printStackTrace();
            return new Users();
        }
    }

    public static void removeUserFromXml(String username) {
        try {
            JAXBContext context = JAXBContext.newInstance(Users.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File file = new File(XML_FILE_PATH);

            Users existingUsers = new Users();
            if (file.exists()) {
                existingUsers = (Users) unmarshaller.unmarshal(file);
            }

            List<User> users = existingUsers.getUsers();
            for (User user : users) {
                if (user.getNickname().equals(username)) {
                    users.remove(user);
                    break;
                }
            }

            // Guardar la lista actualizada en el archivo XML
            marshaller.marshal(existingUsers, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
