package com.yq.domain;

public class Message {
    private String message;
    private String username;
    private String province;

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getProvince () {
        return province;
    }

    public void setProvince (String province) {
        this.province = province;
    }

    @Override
    public String toString () {
        return "Message{" + "message='" + message + '\'' + ", username='" + username + '\'' + ", province='" + province + '\'' + '}';
    }
}
