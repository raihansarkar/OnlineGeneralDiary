package com.example.raihansarkar.onlinegeneraldiary;

/**
 * Created by raiha on 1/11/2018.
 */

public class PoliceStation {
    private String name;
    private String number;

    public PoliceStation(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
