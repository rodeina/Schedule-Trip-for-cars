package com.example.projectforcars.conf;

import com.example.projectforcars.bean.Car;
import com.example.projectforcars.bean.User;

import java.util.ArrayList;

public class Factory {

    private static Factory instance;

    private User user;

    private ArrayList<Car> listCar = new ArrayList<>();

    public static Factory getInstance(){

        if(instance == null){
            instance = new Factory();
        }

        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Car> getListCar() {
        return listCar;
    }

}
