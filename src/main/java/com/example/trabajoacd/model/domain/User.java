package com.example.trabajoacd.model.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {
        private  String nickname;

        public User() {}

        public User(String nickname) {
            this.nickname = nickname;
        }

        @XmlElement
        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

    }


