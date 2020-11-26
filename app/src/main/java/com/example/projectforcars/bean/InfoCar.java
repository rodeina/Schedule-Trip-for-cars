package com.example.projectforcars.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InfoCar implements Serializable {


    @SerializedName("idInfosCar")
    private String idInfosCar;
    @SerializedName("idCar")
    private String idCar;
    @SerializedName("Zieladresse")
    private String destination;
    @SerializedName("Datum")
    private String date;
    @SerializedName("Zeit")
    private String temps;
    @SerializedName("Kilometerstand_starten")
    private String firstMileage;
    @SerializedName("Letzter_Kilometerstand")
    private String endMileage;
    @SerializedName("N_Reise")
    private String nTrip;
    public String getIdInfosCar() {
        return idInfosCar;
    }
    public void setIdInfosCar(String idInfosCar) {
        this.idInfosCar = idInfosCar;
    }

    public String getIdCar() {
        return idCar;
    }

    public void setIdCar(String idCar) {
        this.idCar = idCar;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public String getFirstMileage() {
        return firstMileage;
    }

    public void setFirstMileage(String firstMileage) {
        this.firstMileage = firstMileage;
    }

    public String getEndMileage() {
        return endMileage;
    }

    public void setEndMileage(String endMileage) {
        this.endMileage = endMileage;
    }

    public String getnTrip() {
        return nTrip;
    }

    public void setnTrip(String nTrip) {
        this.nTrip = nTrip;
    }
}
