package com.example.trabajoacd.controller;

import com.example.trabajoacd.model.domain.ChatRoom;
import com.example.trabajoacd.model.domain.Message;

import javax.xml.bind.*;
import java.io.File;

public class MessageXmlManager {

    private static final String XML_FILE_PATH = "message.xml";

    public static void saveMessagesToXml(Message chatRoom) {
        try {
            JAXBContext context = JAXBContext.newInstance(Message.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(chatRoom, new File(XML_FILE_PATH));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static Message loadMessagesFromXml() {
        try {
            JAXBContext context = JAXBContext.newInstance(Message.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            File file = new File(XML_FILE_PATH);
            if (file.exists()) {
                return (Message) unmarshaller.unmarshal(file);
            }
            return new Message();
        } catch (JAXBException e) {
            e.printStackTrace();
            return new Message();
        }
    }
}
