package com.example.pda.Database;

import android.provider.BaseColumns;

public final class DatabaseContract {
    private DatabaseContract(){}
    public static class Adres implements BaseColumns{
        public static final String TABLE_NAME = "Adres";
        public static  final String COLUMN_NAME_ADRESID = "adresID";
        public static  final String COLUMN_NAME_POSTCODE = "postcode";
        public static  final String COLUMN_NAME_STRAATNAAMNUMMER = "straatNaamNummer";
        public static  final String COLUMN_NAME_PLAATS = "plaats";
    }

    public static class Klant implements BaseColumns{
        public static  final String TABLE_NAME = "Klant";
        public static  final String COLUMN_NAME_EMAIL = "email";
        public static  final String COLUMN_NAME_KLANTNAAM = "klantNaam";
        public static  final String COLUMN_NAME_TEL = "tel";
        public static  final String COLUMN_NAME_ADRESID = "adresID";
    }

    public static class Bestelling implements BaseColumns{
        public static  final String TABLE_NAME = "Bestelling";
        public static  final String COLUMN_NAME_BESTELLINGID = "bestellingID";
        public static  final String COLUMN_NAME_BESTELLINGSTATUS = "bestellingStatus";
        public static  final String COLUMN_NAME_EMAIL = "email";
        public static  final String COLUMN_NAME_PLAATSINGDATUM = "plaatsingDatum";
    }

    public static class Perceel implements BaseColumns{
        public static  final String TABLE_NAME = "Perceel";
        public static  final String COLUMN_NAME_PERCEELID = "perceelID";
        public static  final String COLUMN_NAME_DATUMAANMAAK = "datumAanmaak";
        public static  final String COLUMN_NAME_BESTELLINGID = "bestellingID";
        public static  final String COLUMN_NAME_TRANSPORTEURID = "transporteurID";
        public static  final String COLUMN_NAME_ADRESID = "adresID";
    }

    public static class Transporteur implements BaseColumns{
        public static  final String TABLE_NAME = "Transporteur";
        public static  final String COLUMN_NAME_TRANSPORTEURID = "transporteurID";
        public static  final String COLUMN_NAME_TRANSPORTEURNAAM = "transporteurNaam";
        public static  final String COLUMN_NAME_TRANSPORTEURVOORVOEGSEL = "transporteurVoorvoegsel";
    }

    public static class Bestellingstatus implements BaseColumns{
        public static  final String TABLE_NAME = "BestellingStatus";
        public static  final String COLUMN_NAME_BESTELLINGSTATUS = "bestellingStatus";
    }

    public static class Bestellingproduct implements BaseColumns{
        public static  final String TABLE_NAME = "BestellingProduct";
        public static  final String COLUMN_NAME_BPID = "bpID";
        public static  final String COLUMN_NAME_BESTELLINGID = "bestellingID";
        public static  final String COLUMN_NAME_EAN = "ean";
        public static  final String COLUMN_NAME_AANTAL = "aantal";
    }

    public static class Product implements BaseColumns{
        public static  final String TABLE_NAME = "Product";
        public static  final String COLUMN_NAME_EAN = "ean";
        public static  final String COLUMN_NAME_NAAM = "naam";
        public static  final String COLUMN_NAME_ACTUELEVOORRAAD = "actueleVoorraad";
        public static  final String COLUMN_NAME_VERKOOPPRIJS = "verkoopprijs";
    }

    public static class Magazijnlocatie implements BaseColumns{
        public static  final String TABLE_NAME = "MagazijnLocatie";
        public static  final String COLUMN_NAME_LOCATIEID = "locatieID";
        public static  final String COLUMN_NAME_LOCATIE = "locatie";
        public static  final String COLUMN_NAME_EAN = "ean";
    }

    public static class Productdetail implements BaseColumns{
        public static  final String TABLE_NAME = "ProductDetail";
        public static  final String COLUMN_NAME_PRODUCTNAAM = "productNaam";
        public static  final String COLUMN_NAME_PRODUCTBESCHRIJVING = "productBeschrijving";
        public static  final String COLUMN_NAME_MERKNAAM = "merkNaam";
        public static  final String COLUMN_NAME_PRODUCTTYPE = "productType";
    }

    public static class Merk implements BaseColumns{
        public static  final String TABLE_NAME = "Merk";
        public static  final String COLUMN_NAME_MERKNAAM = "merkNaam";

    }

    public static class Producttype implements BaseColumns{
        public static  final String TABLE_NAME = "Producttype";
        public static  final String COLUMN_NAME_PRODUCTTYPE = "producttype";

    }

    // Deze tabellen staan los van het systeem en horen in een aparte API te staan, voor testredenen wordt deze meegenomen in het systeem
    public static class Vrachtproduct implements BaseColumns{
        public static  final String TABLE_NAME = "VrachtProduct";
        public static  final String COLUMN_NAME_EAN = "ean";
        public static  final String COLUMN_NAME_NAAM = "naam";
        public static  final String COLUMN_NAME_PRODUCTBESCHRIJVING = "productBeschrijving";
        public static  final String COLUMN_NAME_MERKNAAM = "merkNaam";
        public static  final String COLUMN_NAME_PRODUCTTYPE = "productType";
    }

    public static class Vrachtlevering implements BaseColumns{
        public static  final String TABLE_NAME = "VrachtLevering";
        public static  final String COLUMN_NAME_BARCODE = "barcode";
        public static  final String COLUMN_NAME_EAN = "ean";
        public static  final String COLUMN_NAME_AANTAL = "aantal";
    }
}
