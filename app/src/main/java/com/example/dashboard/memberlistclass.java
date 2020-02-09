package com.example.dashboard;

public class memberlistclass {
    String name,hosp,disease,severe,longi,lati;

    public memberlistclass(String name, String hosp, String disease, String severe, String longi, String lati) {
        this.name = name;
        this.hosp = hosp;
        this.disease = disease;
        this.severe = severe;
        this.longi = longi;
        this.lati = lati;
    }

    public String getName() {
        return name;
    }

    public String getHosp() {
        return hosp;
    }

    public String getDisease() {
        return disease;
    }

    public String getSevere() {
        return severe;
    }

    public String getLongi() {
        return longi;
    }

    public String getLati() {
        return lati;
    }
}
