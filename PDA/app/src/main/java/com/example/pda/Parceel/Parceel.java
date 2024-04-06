package com.example.pda.Parceel;

import com.example.pda.Bestelling.Adres;
import com.example.pda.Bestelling.Bestelling;

import java.util.Date;

public class Parceel {
    private String perceelID;
    private String datumAanmaak;
    private Bestelling bestelling;
    private Transporteur transporteur;

    public String getPerceelID() {
        return perceelID;
    }

    public String getDatumAanmaak() {
        return datumAanmaak;
    }

    public Bestelling getBestelling() {
        return bestelling;
    }

    public Transporteur getTransporteur() {
        return transporteur;
    }


    public void setPerceelID(String perceelID) {
        this.perceelID = perceelID;
    }

    public void setDatumAanmaak(String datumAanmaak) {
        this.datumAanmaak = datumAanmaak;
    }

    public void setBestelling(Bestelling bestelling) {
        this.bestelling = bestelling;
    }

    public void setTransporteur(Transporteur transporteur) {
        this.transporteur = transporteur;
    }
}
