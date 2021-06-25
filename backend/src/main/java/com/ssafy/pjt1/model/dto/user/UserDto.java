package com.ssafy.pjt1.model.dto.user;

public class UserDto {
    private String user_id;
    private String user_email;
    private String user_password;
    private String user_nickname;
    private String user_location;
    private int user_generation;
    private String user_image;
    private int user_auth;
    private String user_authKey;
    private int is_used;

    public int getIs_used() {
        return this.is_used;
    }

    public void setIs_used(int is_used) {
        this.is_used = is_used;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return this.user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return this.user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_nickname() {
        return this.user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_location() {
        return this.user_location;
    }

    public void setUser_location(String user_location) {
        this.user_location = user_location;
    }

    public int getUser_generation() {
        return this.user_generation;
    }

    public void setUser_generation(int user_generation) {
        this.user_generation = user_generation;
    }

    public String getUser_image() {
        return this.user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public int getUser_auth() {
        return this.user_auth;
    }

    public void setUser_auth(int user_auth) {
        this.user_auth = user_auth;
    }

    public String getUser_authKey() {
        return user_authKey;
    }

    public void setUser_authKey(String user_authKey) {
        this.user_authKey = user_authKey;
    }

}
