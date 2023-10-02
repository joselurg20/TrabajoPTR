package com.example.trabajoacd.controller;

import com.example.trabajoacd.model.domain.Users;

import javax.xml.bind.*;
import java.io.File;
import java.io.IOException;

public class XmlManager {

    private static final String XML_FILE_PATH = "connectedUsers.xml";

    public static void saveConnectedUsersToXml(Users users) {
        try {
            JAXBContext context = JAXBContext.newInstance(Users.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(users, new File(XML_FILE_PATH));
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
}
