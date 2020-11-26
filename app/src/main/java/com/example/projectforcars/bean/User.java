package com.example.projectforcars.bean;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("status")
    private String status;

    @SerializedName("result_code")
    private int resultCode;

    @SerializedName("name")
    private String name;

    @SerializedName("user_name")
    private String emai;

    @SerializedName("password")
    private String password;

    @SerializedName("user_id")
    private String idUser;
    @SerializedName("email")
    private String email;
    @SerializedName("passwort")

     private String passwort;
    public String getStatus() {
        return status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmai() {
        return emai;
    }

    public String getPassword() {
        return password;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswort() {
        return passwort;
    }
}

