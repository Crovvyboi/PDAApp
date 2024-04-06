package com.example.pda.Bestelling;

public class Adres {
    private Integer adresID;
    private String postcode;
    private String straatNaamNummer;
    private String plaats;

    public String getPostcode() {
        return postcode;
    }

    public String getStraatNaamNummer() {
        return straatNaamNummer;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setStraatNaamNummer(String straatNaamNummer) {
        this.straatNaamNummer = straatNaamNummer;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }
}
