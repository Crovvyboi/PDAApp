package com.example.pda.Parceel;

public class Transporteur {
    private Integer transporteurID;
    private String transporteurNaam;
    private String transporteurVoorvoegsel;

    public String getTransporteurNaam() {
        return transporteurNaam;
    }

    public void setTransporteurNaam(String transporteurNaam) {
        this.transporteurNaam = transporteurNaam;
    }

    public Integer getTransporteurID() {
        return transporteurID;
    }

    public void setTransporteurID(Integer transporteurID) {
        this.transporteurID = transporteurID;
    }

    public String getTransporteurVoorvoegsel() {
        return transporteurVoorvoegsel;
    }

    public void setTransporteurVoorvoegsel(String transporteurVoorvoegsel) {
        this.transporteurVoorvoegsel = transporteurVoorvoegsel;
    }
}
