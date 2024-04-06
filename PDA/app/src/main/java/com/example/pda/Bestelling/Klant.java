package com.example.pda.Bestelling;

public class Klant {
    private String email;
    private String klantNaam;
    private String tel;
    private Adres thuisAdres;

    public String getKlantNaam() {
        return klantNaam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setKlantNaam(String klantNaam) {
        this.klantNaam = klantNaam;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Adres getThuisAdres() {
        return thuisAdres;
    }

    public void setThuisAdres(Adres thuisAdres) {
        this.thuisAdres = thuisAdres;
    }
}
