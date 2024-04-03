package com.example.pda.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pda.Bestelling.Adres;
import com.example.pda.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper databaseHelper;

    private static Context c;
    public static final String DB_NAME = "PDADB.db";
    public static final int DB_VERSION = 1;

    private static final String SQL_CREATE_ADRES = "CREATE TABLE " + DatabaseContract.Adres.TABLE_NAME + "(" +
            DatabaseContract.Adres.COLUMN_NAME_ADRESID + "INTEGER PRIMARY KEY AUTOINCREMENT" +
            DatabaseContract.Adres.COLUMN_NAME_POSTCODE + "TEXT" +
            DatabaseContract.Adres.COLUMN_NAME_STRAATNAAMNUMMER + "TEXT" +
            DatabaseContract.Adres.COLUMN_NAME_PLAATS + "TEXT" +
            ")";
    private static final String SQL_CREATE_BESTELLING = "CREATE TABLE " + DatabaseContract.Bestelling.TABLE_NAME + "(" +
            DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGID + "INTEGER PRIMARY KEY AUTOINCREMENT" +
            DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGSTATUS + "TEXT" +
            DatabaseContract.Bestelling.COLUMN_NAME_EMAIL + "TEXT" +
            DatabaseContract.Bestelling.COLUMN_NAME_PLAATSINGDATUM + "TEXT" +

            "FOREIGN KEY (" + DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGSTATUS + ") " +
            "REFERENCES " + DatabaseContract.Bestellingstatus.TABLE_NAME  + "" +
            "(" + DatabaseContract.Bestellingstatus.COLUMN_NAME_BESTELLINGSTATUS   + ")" +

            "FOREIGN KEY (" + DatabaseContract.Bestelling.COLUMN_NAME_EMAIL + ") " +
            "REFERENCES " + DatabaseContract.Klant.TABLE_NAME  + "" +
            "(" + DatabaseContract.Klant.COLUMN_NAME_EMAIL  + ")" +
            ")";
    private static final String SQL_CREATE_BESTELLINGPRODUCT = "CREATE TABLE " + DatabaseContract.Bestellingproduct.TABLE_NAME + "(" +
            DatabaseContract.Bestellingproduct.COLUMN_NAME_BPID + "INTEGER PRIMARY KEY AUTOINCREMENT" +
            DatabaseContract.Bestellingproduct.COLUMN_NAME_BESTELLINGID + "TEXT" +
            DatabaseContract.Bestellingproduct.COLUMN_NAME_EAN + "TEXT" +
            DatabaseContract.Bestellingproduct.COLUMN_NAME_AANTAL + "INTEGER" +

            "FOREIGN KEY (" + DatabaseContract.Bestellingproduct.COLUMN_NAME_EAN + ") " +
            "REFERENCES " + DatabaseContract.Product.TABLE_NAME + "" +
            "(" + DatabaseContract.Product.COLUMN_NAME_EAN  + ")" +

            "FOREIGN KEY (" + DatabaseContract.Bestellingproduct.COLUMN_NAME_BESTELLINGID + ") " +
            "REFERENCES " + DatabaseContract.Bestelling.TABLE_NAME + "" +
            "(" + DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGID  + ")" +
            ")";
    private static final String SQL_CREATE_BESTELLINGSTATUS = "CREATE TABLE " + DatabaseContract.Bestellingstatus.TABLE_NAME + "(" +
            DatabaseContract.Bestellingstatus.COLUMN_NAME_BESTELLINGSTATUS + "TEXT PRIMARY KEY" +
            ")";
    private static final String SQL_CREATE_KLANT = "CREATE TABLE " + DatabaseContract.Klant.TABLE_NAME + "(" +
            DatabaseContract.Klant.COLUMN_NAME_EMAIL + "TEXT PRIMARY KEY" +
            DatabaseContract.Klant.COLUMN_NAME_KLANTNAAM + "TEXT" +
            DatabaseContract.Klant.COLUMN_NAME_TEL + "TEXT" +
            DatabaseContract.Klant.COLUMN_NAME_ADRESID + "INTEGER" +

            "FOREIGN KEY (" + DatabaseContract.Klant.COLUMN_NAME_ADRESID + ") " +
            "REFERENCES " + DatabaseContract.Adres.TABLE_NAME + "" +
            "(" + DatabaseContract.Adres.COLUMN_NAME_ADRESID  + ")" +
            ")";
    private static final String SQL_CREATE_MAGAZIJNLOCATIE = "CREATE TABLE " + DatabaseContract.Magazijnlocatie.TABLE_NAME + "(" +
            DatabaseContract.Magazijnlocatie.COLUMN_NAME_LOCATIEID + "INTEGER PRIMARY KEY AUTOINCREMENT" +
            DatabaseContract.Magazijnlocatie.COLUMN_NAME_LOCATIE + "TEXT" +
            DatabaseContract.Magazijnlocatie.COLUMN_NAME_EAN + "TEXT" +

            "FOREIGN KEY (" + DatabaseContract.Magazijnlocatie.COLUMN_NAME_EAN + ") " +
            "REFERENCES " + DatabaseContract.Product.TABLE_NAME + "" +
            "(" + DatabaseContract.Product.COLUMN_NAME_EAN  + ")" +
            ")";
    private static final String SQL_CREATE_MERK = "CREATE TABLE " + DatabaseContract.Merk.TABLE_NAME + "(" +
            DatabaseContract.Merk.COLUMN_NAME_MERKNAAM + "TEXT PRIMARY KEY" +
            ")";
    private static final String SQL_CREATE_PERCEEL = "CREATE TABLE " + DatabaseContract.Perceel.TABLE_NAME + "(" +
            DatabaseContract.Perceel.COLUMN_NAME_PERCEELID + "INTEGER PRIMARY KEY AUTOINCREMENT" +
            DatabaseContract.Perceel.COLUMN_NAME_DATUMAANMAAK + "TEXT" +
            DatabaseContract.Perceel.COLUMN_NAME_BESTELLINGID + "INTEGER" +
            DatabaseContract.Perceel.COLUMN_NAME_ADRESID + "INTEGER" +
            DatabaseContract.Perceel.COLUMN_NAME_TRANSPORTEURID + "INTEGER" +

            "FOREIGN KEY (" + DatabaseContract.Perceel.COLUMN_NAME_BESTELLINGID + ") " +
            "REFERENCES " + DatabaseContract.Bestelling.TABLE_NAME + "" +
            "(" + DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGID  + ")" +

            "FOREIGN KEY (" + DatabaseContract.Perceel.COLUMN_NAME_ADRESID + ") " +
            "REFERENCES " + DatabaseContract.Adres.TABLE_NAME + "" +
            "(" + DatabaseContract.Adres.COLUMN_NAME_ADRESID  + ")" +

            "FOREIGN KEY (" + DatabaseContract.Perceel.COLUMN_NAME_TRANSPORTEURID + ") " +
            "REFERENCES " + DatabaseContract.Transporteur.TABLE_NAME + "" +
            "(" + DatabaseContract.Transporteur.COLUMN_NAME_TRANSPORTEURID + ")" +
            ")";
    private static final String SQL_CREATE_PRODUCT = "CREATE TABLE " + DatabaseContract.Product.TABLE_NAME + "(" +
            DatabaseContract.Product.COLUMN_NAME_EAN + "INTEGER PRIMARY KEY" +
            DatabaseContract.Product.COLUMN_NAME_NAAM + "TEXT" +
            DatabaseContract.Product.COLUMN_NAME_ACTUELEVOORRAAD + "INTEGER" +
            DatabaseContract.Product.COLUMN_NAME_VERKOOPPRIJS + "NUMERIC" +

            "FOREIGN KEY (" + DatabaseContract.Product.COLUMN_NAME_NAAM + ") " +
            "REFERENCES " + DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTNAAM + "" +
            "(" + DatabaseContract.Productdetail.TABLE_NAME  + ")" +
            ")";
    private static final String SQL_CREATE_PRODUCTDETAIL = "CREATE TABLE " + DatabaseContract.Productdetail.TABLE_NAME + "(" +
            DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTNAAM + "TEXT PRIMARY KEY" +
            DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTBESCHRIJVING + "TEXT" +
            DatabaseContract.Productdetail.COLUMN_NAME_MERKNAAM + "TEXT" +
            DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTTYPE + "TEXT" +

            "FOREIGN KEY (" + DatabaseContract.Productdetail.COLUMN_NAME_MERKNAAM + ") " +
            "REFERENCES " + DatabaseContract.Producttype.TABLE_NAME + "" +
            "(" + DatabaseContract.Producttype.COLUMN_NAME_PRODUCTTYPE  + ")" +

            "FOREIGN KEY (" + DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTTYPE + ") " +
            "REFERENCES " + DatabaseContract.Merk.TABLE_NAME + "" +
            "(" + DatabaseContract.Merk.COLUMN_NAME_MERKNAAM  + ")" +
            ")";
    private static final String SQL_CREATE_PRODUCTTYPE = "CREATE TABLE " + DatabaseContract.Producttype.TABLE_NAME + "(" +
            DatabaseContract.Producttype.COLUMN_NAME_PRODUCTTYPE + "TEXT PRIMARY KEY" +
            ")";
    private static final String SQL_CREATE_TRANSPORTEUR = "CREATE TABLE " + DatabaseContract.Transporteur.TABLE_NAME + "(" +
            DatabaseContract.Transporteur.COLUMN_NAME_TRANSPORTEURID + "INTEGER PRIMARY KEY AUTOINCREMENT" +
            DatabaseContract.Transporteur.COLUMN_NAME_TRANSPORTEURNAAM + "TEXT" +
            DatabaseContract.Transporteur.COLUMN_NAME_TRANSPORTEURVOORVOEGSEL + "TEXT" +
            ")";
    private static final String SQL_CREATE_VRACHTPRODUCT = "CREATE TABLE " + DatabaseContract.Vrachtproduct.TABLE_NAME + "(" +
            DatabaseContract.Vrachtproduct.COLUMN_NAME_EAN + "TEXT PRIMARY KEY" +
            DatabaseContract.Vrachtproduct.COLUMN_NAME_NAAM + "TEXT" +
            DatabaseContract.Vrachtproduct.COLUMN_NAME_PRODUCTBESCHRIJVING + "TEXT" +
            DatabaseContract.Vrachtproduct.COLUMN_NAME_MERKNAAM + "TEXT" +
            DatabaseContract.Vrachtproduct.COLUMN_NAME_PRODUCTTYPE + "TEXT" +
            ")";
    private static final String SQL_CREATE_VRACHTLEVERING = "CREATE TABLE " + DatabaseContract.Vrachtlevering.TABLE_NAME + "(" +
            DatabaseContract.Vrachtlevering.COLUMN_NAME_BARCODE + "TEXT PRIMARY KEY" +
            DatabaseContract.Vrachtlevering.COLUMN_NAME_EAN + "TEXT" +
            DatabaseContract.Vrachtlevering.COLUMN_NAME_AANTAL + "TEXT" +

            "FOREIGN KEY (" + DatabaseContract.Vrachtlevering.COLUMN_NAME_EAN + ") " +
            "REFERENCES " + DatabaseContract.Vrachtproduct.TABLE_NAME + "" +
            "(" + DatabaseContract.Vrachtproduct.COLUMN_NAME_EAN  + ")" +
            ")";
    private static final String SQL_ALTER_CONSTRAINTS = "";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION );
        c = context;
    }

    public static DatabaseHelper instanceOfDB(Context context){
        if (databaseHelper == null){
            databaseHelper = new DatabaseHelper(context);
        }

        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.i("Database", "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldversion, int newversion) {
        onCreate(sqLiteDatabase);
    }
}
