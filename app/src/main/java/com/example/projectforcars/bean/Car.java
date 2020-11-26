package com.example.projectforcars.bean;

import com.google.gson.annotations.SerializedName;

public class Car {
    @SerializedName("carRegistration")
    private String registration;

    @SerializedName("carName")
    private String name;

    @SerializedName("idCar")
    private String idCar;

    @SerializedName("id")
    private String idUser;



    public Car(String registration, String name, String idUser) {
        this.registration = registration;
        this.name = name;
        this.idUser = idUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getRegistration() {
        return registration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getIdCar() {
        return idCar;
    }

    public void setIdCar(String idCar) {
        this.idCar = idCar;
    }


}
