package com.example.projectforcars.bean;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("result_code")
    private int resultCode;
    @SerializedName("name")
    private String name;
    @SerializedName("user_id")
    private String idUser;


    public String getStatus() {
        return status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getName() {
        return name;
    }

    public String getIdUser() {
        return idUser;
    }

}
