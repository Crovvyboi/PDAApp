package com.example.pda.Bestelling;

import com.example.pda.Product.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;

public class Bestelling {
    private String bestellingID;
    private String bestellingStatus;
    private Klant klant;
    private String plaatsingDatum;
    private ArrayList<BestellingProduct> besteldeProducten;
    private Adres verzendAdres;

    public String getBestellingID() {
        return bestellingID;
    }

    public String getBestellingStatus() {
        return bestellingStatus;
    }

    public Klant getKlant() {
        return klant;
    }

    public String  getPlaatsingDatum() {
        return plaatsingDatum;
    }

    public void setBestellingID(String bestellingID) {
        this.bestellingID = bestellingID;
    }

    public void setBestellingStatus(String bestellingStatus) {
        this.bestellingStatus = bestellingStatus;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    public void setPlaatsingDatum(String plaatsingDatum) {
        this.plaatsingDatum = plaatsingDatum;
    }

    public Adres getVerzendAdres() {
        return verzendAdres;
    }

    public void setVerzendAdres(Adres verzendAdres) {
        this.verzendAdres = verzendAdres;
    }
}
