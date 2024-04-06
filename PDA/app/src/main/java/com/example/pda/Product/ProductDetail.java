package com.example.pda.Product;

public class ProductDetail {
    private String naam;
    private String productBeschrijving;
    private String merkNaam;
    private String productType;

    public String getProductBeschrijving() {
        return productBeschrijving;
    }

    public String getMerkNaam() {
        return merkNaam;
    }

    public String getProductType() {
        return productType;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setProductBeschrijving(String productBeschrijving) {
        this.productBeschrijving = productBeschrijving;
    }

    public void setMerkNaam(String merkNaam) {
        this.merkNaam = merkNaam;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
