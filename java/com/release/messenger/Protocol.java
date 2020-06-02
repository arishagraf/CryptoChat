package com.release.messenger;

import com.google.gson.Gson;

public class Protocol {
    public final static int USER_STATUS = 1;
    public final static int MESSAGE = 2;
    public final static int USER_NAME = 3;

    static class Message {
        public final static int GROUP_CHAT = 1;
        private long sender;
        private long receiver = GROUP_CHAT;
        private String encodedText;

        public Message (String encodedText) {
            this.encodedText = encodedText;
        }

        public long getSender() {
            return sender;
        }

        public Message setSender(long sender) {
            this.sender = sender;
            return this;
        }

        public long getReceiver() {
            return receiver;
        }

        public Message setReceiver(long receiver) {
            this.receiver = receiver;
            return this;
        }

        public String getEncodedText() {
            return encodedText;
        }

        public Message setEncodedText(String encodedText) {
            this.encodedText = encodedText;
            return this;
        }
    }

    static class User {
        private long id;
        private String name;

        public User () {}

        public long getId() {
            return id;
        }

        public User setId(long id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public User setName(String name) {
            this.name = name;
            return this;
        }
    }

    static class UserStatus {
        private User user;
        private boolean connected;

        public User getUser() {
            return user;
        }

        public UserStatus setUser(User user) {
            this.user = user;
            return this;
        }

        public boolean isConnected() {
            return connected;
        }

        public UserStatus setConnected(boolean connected) {
            this.connected = connected;
            return this;
        }
    }

    static class UserName {
        private String name;

        public UserName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public UserName setName(String name) {
            this.name = name;
            return this;
        }
    }

    public static int getType(String json) {
        if (json == null || json.length() == 0) {
            return -1;
        }
        return Integer.valueOf(json.substring(0,1));
    }

    public static UserStatus unpackStatus(String json) {
        Gson g = new Gson();
        return g.fromJson(json.substring(1), UserStatus.class);
    }

    public static Message unpackMessage(String json) {
        Gson g = new Gson();
        return g.fromJson(json.substring(1), Message.class);
    }

    public static String packMessage(Message m) {
        Gson g = new Gson();
        return MESSAGE + g.toJson(m);
    }

    public static String packName(UserName n) {
        Gson g = new Gson();
        return USER_NAME + g.toJson(n);
    }
}
