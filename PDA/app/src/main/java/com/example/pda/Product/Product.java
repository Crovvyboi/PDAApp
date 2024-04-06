package com.example.pda.Product;

public class Product {
    private String ean;
    private ProductDetail naam;
    private Integer actueleVoorraad;
    private Double prijs;

    public String getEan() {
        return ean;
    }

    public ProductDetail getNaam() {
        return naam;
    }

    public Integer getActueleVoorraad() {
        return actueleVoorraad;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public void setNaam(ProductDetail naam) {
        this.naam = naam;
    }

    public void setActueleVoorraad(Integer actueleVoorraad) {
        this.actueleVoorraad = actueleVoorraad;
    }

    public Double getPrijs() {
        return prijs;
    }

    public void setPrijs(Double prijs) {
        this.prijs = prijs;
    }
}
