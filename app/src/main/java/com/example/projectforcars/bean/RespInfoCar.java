package com.example.projectforcars.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RespInfoCar {
    @SerializedName("status")
    private String status;
    @SerializedName("result_code")
    private int resultCode;

    @SerializedName("cars")
    List<InfoCar> infoCarList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public List<InfoCar> getInfoCarList() {
        return infoCarList;
    }

    public void setInfoCarList(List<InfoCar> infoCarList) {
        this.infoCarList = infoCarList;
    }
}
