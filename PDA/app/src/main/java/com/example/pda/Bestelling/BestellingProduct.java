package com.example.pda.Bestelling;

import android.content.Context;
import android.widget.Toast;

import com.example.pda.Product.Product;

import java.util.ArrayList;

public class BestellingProduct {
    private Product product;
    private Integer aantal;
    private Integer verzameld = 0;
    private ArrayList<String> locaties;

    public void AddToVerzameld(Context context){
        if (verzameld + 1 <= aantal){
            verzameld += 1;
        }
        else {
            Toast.makeText(context, "Teveel verzameld.", Toast.LENGTH_SHORT);
        }
    }

    public boolean CheckIfComplete(){
        if (verzameld == aantal){
            return true;
        }
        return false;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAantal() {
        return aantal;
    }

    public void setAantal(Integer aantal) {
        this.aantal = aantal;
    }

    public ArrayList<String> getLocaties() {
        return locaties;
    }

    public void setLocaties(ArrayList<String> locaties) {
        this.locaties = locaties;
    }

    public Integer getVerzameld() {
        return verzameld;
    }

    public void setVerzameld(Integer verzameld) {
        this.verzameld = verzameld;
    }
}
