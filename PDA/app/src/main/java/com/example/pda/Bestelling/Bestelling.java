package com.example.pda.Bestelling;

import com.example.pda.Product.Product;
import com.example.pda.Product.ProductType;

import java.util.Date;
import java.util.Dictionary;
import java.util.List;

public class Bestelling {
    private Integer bestellingID;
    private BestellingStatus bestellingStatus;
    private Klant klant;
    private Date plaatsingDatum;
    private Dictionary<Product, Integer> besteldeProducten; // Product en aantal
}
