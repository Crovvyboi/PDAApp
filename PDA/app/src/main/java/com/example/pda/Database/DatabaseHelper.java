package com.example.pda.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pda.Bestelling.Adres;
import com.example.pda.Bestelling.Bestelling;
import com.example.pda.Bestelling.Klant;
import com.example.pda.Parceel.Parceel;
import com.example.pda.Parceel.Transporteur;
import com.example.pda.Product.Product;
import com.example.pda.Product.ProductDetail;
import com.example.pda.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper databaseHelper;

    private static Context c;
    public static final String DB_NAME = "PDADB.db";
    public static final int DB_VERSION = 1;

    private static final String SQL_CREATE_ADRES = "CREATE TABLE " + DatabaseContract.Adres.TABLE_NAME + " (" +
            DatabaseContract.Adres.COLUMN_NAME_ADRESID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.Adres.COLUMN_NAME_POSTCODE + " TEXT, " +
            DatabaseContract.Adres.COLUMN_NAME_STRAATNAAMNUMMER + " TEXT, " +
            DatabaseContract.Adres.COLUMN_NAME_PLAATS + " TEXT " +
            ") ";
    private static final String SQL_CREATE_BESTELLING = "CREATE TABLE " + DatabaseContract.Bestelling.TABLE_NAME + " (" +
            DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGID + " TEXT PRIMARY KEY, " +
            DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGSTATUS + " TEXT, " +
            DatabaseContract.Bestelling.COLUMN_NAME_EMAIL + " TEXT, " +
            DatabaseContract.Bestelling.COLUMN_NAME_PLAATSINGDATUM + " TEXT, " +
            DatabaseContract.Bestelling.COLUMN_NAME_ADRESID + " INTEGER, " +

            " FOREIGN KEY (" + DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGSTATUS + ") " +
            " REFERENCES " + DatabaseContract.Bestellingstatus.TABLE_NAME  + " " +
            "(" + DatabaseContract.Bestellingstatus.COLUMN_NAME_BESTELLINGSTATUS   + "), " +

            " FOREIGN KEY (" + DatabaseContract.Bestelling.COLUMN_NAME_ADRESID + ") " +
            " REFERENCES " + DatabaseContract.Adres.TABLE_NAME + " " +
            "(" + DatabaseContract.Adres.COLUMN_NAME_ADRESID  + "), " +

            " FOREIGN KEY (" + DatabaseContract.Bestelling.COLUMN_NAME_EMAIL + ") " +
            " REFERENCES " + DatabaseContract.Klant.TABLE_NAME  + " " +
            " (" + DatabaseContract.Klant.COLUMN_NAME_EMAIL  + ") " +
            ") ";
    private static final String SQL_CREATE_BESTELLINGPRODUCT = "CREATE TABLE " + DatabaseContract.Bestellingproduct.TABLE_NAME + " (" +
            DatabaseContract.Bestellingproduct.COLUMN_NAME_BPID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.Bestellingproduct.COLUMN_NAME_BESTELLINGID + " TEXT, " +
            DatabaseContract.Bestellingproduct.COLUMN_NAME_EAN + " TEXT, " +
            DatabaseContract.Bestellingproduct.COLUMN_NAME_AANTAL + " INTEGER, " +

            " FOREIGN KEY (" + DatabaseContract.Bestellingproduct.COLUMN_NAME_EAN + ") " +
            " REFERENCES " + DatabaseContract.Product.TABLE_NAME + " " +
            " (" + DatabaseContract.Product.COLUMN_NAME_EAN  + "), " +

            " FOREIGN KEY (" + DatabaseContract.Bestellingproduct.COLUMN_NAME_BESTELLINGID + ") " +
            " REFERENCES " + DatabaseContract.Bestelling.TABLE_NAME + " " +
            " (" + DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGID  + ") " +
            ")";
    private static final String SQL_CREATE_BESTELLINGSTATUS = "CREATE TABLE " + DatabaseContract.Bestellingstatus.TABLE_NAME + " (" +
            DatabaseContract.Bestellingstatus.COLUMN_NAME_BESTELLINGSTATUS + " TEXT PRIMARY KEY " +
            ")";
    private static final String SQL_CREATE_KLANT = "CREATE TABLE " + DatabaseContract.Klant.TABLE_NAME + " (" +
            DatabaseContract.Klant.COLUMN_NAME_EMAIL + " TEXT PRIMARY KEY, " +
            DatabaseContract.Klant.COLUMN_NAME_KLANTNAAM + " TEXT, " +
            DatabaseContract.Klant.COLUMN_NAME_TEL + " TEXT, " +
            DatabaseContract.Klant.COLUMN_NAME_ADRESID + " INTEGER, " +

            " FOREIGN KEY (" + DatabaseContract.Klant.COLUMN_NAME_ADRESID + ") " +
            " REFERENCES " + DatabaseContract.Adres.TABLE_NAME + " " +
            "(" + DatabaseContract.Adres.COLUMN_NAME_ADRESID  + ")" +
            ")";
    private static final String SQL_CREATE_MAGAZIJNLOCATIE = "CREATE TABLE " + DatabaseContract.Magazijnlocatie.TABLE_NAME + " (" +
            DatabaseContract.Magazijnlocatie.COLUMN_NAME_LOCATIEID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.Magazijnlocatie.COLUMN_NAME_LOCATIE + " TEXT, " +
            DatabaseContract.Magazijnlocatie.COLUMN_NAME_EAN + " TEXT, " +

            " FOREIGN KEY (" + DatabaseContract.Magazijnlocatie.COLUMN_NAME_EAN + ") " +
            " REFERENCES " + DatabaseContract.Product.TABLE_NAME + " " +
            "(" + DatabaseContract.Product.COLUMN_NAME_EAN  + ")" +
            ")";
    private static final String SQL_CREATE_MERK = "CREATE TABLE " + DatabaseContract.Merk.TABLE_NAME + " (" +
            DatabaseContract.Merk.COLUMN_NAME_MERKNAAM + " TEXT PRIMARY KEY " +
            ")";
    private static final String SQL_CREATE_PERCEEL = "CREATE TABLE " + DatabaseContract.Perceel.TABLE_NAME + " (" +
            DatabaseContract.Perceel.COLUMN_NAME_PERCEELID + " TEXT PRIMARY KEY , " +
            DatabaseContract.Perceel.COLUMN_NAME_DATUMAANMAAK + " TEXT, " +
            DatabaseContract.Perceel.COLUMN_NAME_BESTELLINGID + " INTEGER," +
            DatabaseContract.Perceel.COLUMN_NAME_TRANSPORTEURID + " INTEGER, " +

            " FOREIGN KEY (" + DatabaseContract.Perceel.COLUMN_NAME_BESTELLINGID + ") " +
            " REFERENCES " + DatabaseContract.Bestelling.TABLE_NAME + " " +
            "(" + DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGID  + "), " +

            " FOREIGN KEY (" + DatabaseContract.Perceel.COLUMN_NAME_TRANSPORTEURID + ") " +
            " REFERENCES " + DatabaseContract.Transporteur.TABLE_NAME + " " +
            "(" + DatabaseContract.Transporteur.COLUMN_NAME_TRANSPORTEURID + ")" +
            ")";
    private static final String SQL_CREATE_PRODUCT = "CREATE TABLE " + DatabaseContract.Product.TABLE_NAME + " (" +
            DatabaseContract.Product.COLUMN_NAME_EAN + " INTEGER PRIMARY KEY, " +
            DatabaseContract.Product.COLUMN_NAME_NAAM + " TEXT, " +
            DatabaseContract.Product.COLUMN_NAME_ACTUELEVOORRAAD + " INTEGER, " +
            DatabaseContract.Product.COLUMN_NAME_VERKOOPPRIJS + " NUMERIC, " +

            " FOREIGN KEY (" + DatabaseContract.Product.COLUMN_NAME_NAAM + ") " +
            " REFERENCES " + DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTNAAM + " " +
            "(" + DatabaseContract.Productdetail.TABLE_NAME  + ")" +
            ")";
    private static final String SQL_CREATE_PRODUCTDETAIL = "CREATE TABLE " + DatabaseContract.Productdetail.TABLE_NAME + " (" +
            DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTNAAM + " TEXT PRIMARY KEY, " +
            DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTBESCHRIJVING + " TEXT, " +
            DatabaseContract.Productdetail.COLUMN_NAME_MERKNAAM + " TEXT, " +
            DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTTYPE + " TEXT, " +

            " FOREIGN KEY (" + DatabaseContract.Productdetail.COLUMN_NAME_MERKNAAM + ") " +
            " REFERENCES " + DatabaseContract.Producttype.TABLE_NAME + " " +
            "(" + DatabaseContract.Producttype.COLUMN_NAME_PRODUCTTYPE  + "), " +

            " FOREIGN KEY (" + DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTTYPE + ") " +
            " REFERENCES " + DatabaseContract.Merk.TABLE_NAME + " " +
            "(" + DatabaseContract.Merk.COLUMN_NAME_MERKNAAM  + ")" +
            ")";
    private static final String SQL_CREATE_PRODUCTTYPE = "CREATE TABLE " + DatabaseContract.Producttype.TABLE_NAME + " (" +
            DatabaseContract.Producttype.COLUMN_NAME_PRODUCTTYPE + " TEXT PRIMARY KEY " +
            ")";
    private static final String SQL_CREATE_TRANSPORTEUR = "CREATE TABLE " + DatabaseContract.Transporteur.TABLE_NAME + " (" +
            DatabaseContract.Transporteur.COLUMN_NAME_TRANSPORTEURID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.Transporteur.COLUMN_NAME_TRANSPORTEURNAAM + " TEXT, " +
            DatabaseContract.Transporteur.COLUMN_NAME_TRANSPORTEURVOORVOEGSEL + " TEXT " +
            ")";
    private static final String SQL_CREATE_VRACHTPRODUCT = "CREATE TABLE " + DatabaseContract.Vrachtproduct.TABLE_NAME + " (" +
            DatabaseContract.Vrachtproduct.COLUMN_NAME_EAN + " TEXT PRIMARY KEY, " +
            DatabaseContract.Vrachtproduct.COLUMN_NAME_NAAM + " TEXT, " +
            DatabaseContract.Vrachtproduct.COLUMN_NAME_PRODUCTBESCHRIJVING + " TEXT, " +
            DatabaseContract.Vrachtproduct.COLUMN_NAME_MERKNAAM + " TEXT, " +
            DatabaseContract.Vrachtproduct.COLUMN_NAME_PRODUCTTYPE + " TEXT " +
            ")";
    private static final String SQL_CREATE_VRACHTLEVERING = "CREATE TABLE " + DatabaseContract.Vrachtlevering.TABLE_NAME + " (" +
            DatabaseContract.Vrachtlevering.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseContract.Vrachtlevering.COLUMN_NAME_BARCODE + " TEXT, " +
            DatabaseContract.Vrachtlevering.COLUMN_NAME_EAN + " TEXT, " +
            DatabaseContract.Vrachtlevering.COLUMN_NAME_AANTAL + " TEXT, " +

            " FOREIGN KEY (" + DatabaseContract.Vrachtlevering.COLUMN_NAME_EAN + ") " +
            " REFERENCES " + DatabaseContract.Vrachtproduct.TABLE_NAME + " " +
            "(" + DatabaseContract.Vrachtproduct.COLUMN_NAME_EAN  + ")" +
            ")";

    private static final String[] TABLE_NAMES = {
            DatabaseContract.Vrachtlevering.TABLE_NAME,
            DatabaseContract.Vrachtproduct.TABLE_NAME
    };

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION );
        c = context;
    }

    public DatabaseHelper instanceOfDB(Context context){
        if (databaseHelper == null){
            databaseHelper = new DatabaseHelper(context);
            this.onCreate(databaseHelper.getWritableDatabase());
        }

        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        dropTables(sqLiteDatabase);

        initVracht(sqLiteDatabase);
        initProduct(sqLiteDatabase);
        initBestelling(sqLiteDatabase);
        initPerceel(sqLiteDatabase);

        populateTables(sqLiteDatabase);

        Log.i("Database", "Database created");
    }

    private void initVracht(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(SQL_CREATE_VRACHTPRODUCT);
        sqLiteDatabase.execSQL(SQL_CREATE_VRACHTLEVERING);
    }

    private void initProduct(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCTTYPE);
        sqLiteDatabase.execSQL(SQL_CREATE_MERK);
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCTDETAIL);
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCT);
        sqLiteDatabase.execSQL(SQL_CREATE_MAGAZIJNLOCATIE);
    }

    private void initBestelling(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(SQL_CREATE_BESTELLINGSTATUS);
        sqLiteDatabase.execSQL(SQL_CREATE_ADRES);
        sqLiteDatabase.execSQL(SQL_CREATE_KLANT);
        sqLiteDatabase.execSQL(SQL_CREATE_BESTELLING);
        sqLiteDatabase.execSQL(SQL_CREATE_BESTELLINGPRODUCT);
    }

    private void initPerceel(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(SQL_CREATE_TRANSPORTEUR);
        sqLiteDatabase.execSQL(SQL_CREATE_PERCEEL);
    }

    private void dropTables(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Vrachtlevering.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Vrachtproduct.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Perceel.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Transporteur.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Bestellingproduct.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Bestelling.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Klant.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Adres.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Bestellingstatus.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Product.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Magazijnlocatie.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Productdetail.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Merk.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Producttype.TABLE_NAME);
    }

    private void populateTables(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("INSERT INTO " + DatabaseContract.Vrachtproduct.TABLE_NAME + "( " +
                DatabaseContract.Vrachtproduct.COLUMN_NAME_EAN + ", " +
                DatabaseContract.Vrachtproduct.COLUMN_NAME_NAAM + ", " +
                DatabaseContract.Vrachtproduct.COLUMN_NAME_PRODUCTBESCHRIJVING + ", " +
                DatabaseContract.Vrachtproduct.COLUMN_NAME_MERKNAAM + ", " +
                DatabaseContract.Vrachtproduct.COLUMN_NAME_PRODUCTTYPE +
                " )" +
                "VALUES ( '4739331313449', 'GeForce RTX 4060Ti', 'Krachtige nieuwe videokaart uit de 40xx series.', 'Gigabyte', 'Videokaart' )," +
                "( '74969913221', 'Regular Slinky 3 Pack', 'De klassieke Ernie Ball snaren voor elektrische gitaar.', 'Ernie Ball', 'Gitaarsnaren' ), " +
                "( '74969911235', 'Beefy Slinky 3 Pack', 'De klassieke Ernie Ball snaren voor drop tuning.', 'Ernie Ball', 'Gitaarsnaren' ), " +
                "( '8718907783521', 'AH huismerk cola regular blik', 'Cola met suiker.', 'AH', 'Cola' ), " +
                "( '9008703000779', 'Bullit energy sugarfree', 'Hoe kan dit vloeibare suiker zijn als er geen suiker in zit?', 'Bullit', 'Energy drink' ), " +
                "( '8718907763998', 'AH energy', 'Vloeibare suiker.', 'AH', 'Energy drink' ) " );

        sqLiteDatabase.execSQL("INSERT INTO " + DatabaseContract.Vrachtlevering.TABLE_NAME + "( " +
                DatabaseContract.Vrachtlevering.COLUMN_NAME_BARCODE + ", " +
                DatabaseContract.Vrachtlevering.COLUMN_NAME_EAN + ", " +
                DatabaseContract.Vrachtlevering.COLUMN_NAME_AANTAL +
                " )" +
                "VALUES ( '8085718894631', '8718907783521', '3' ), " +
                "( '8085718894631', '9008703000779', '3' ), " +
                "( '8085718894631', '8718907763998', '3' ), " +
                "( '6430830889126', '74969913221', '2' ), " +
                "( '6430830889126', '74969911235', '2' ), " +
                "( '4068680117797', '4739331313449', '5' )");

        sqLiteDatabase.execSQL("INSERT INTO " + DatabaseContract.Bestellingstatus.TABLE_NAME + "( " +
                DatabaseContract.Bestellingstatus.COLUMN_NAME_BESTELLINGSTATUS +
                " )" +
                "VALUES ( 'Geplaatst' )," +
                "( 'Verwerkt' )");

        sqLiteDatabase.execSQL("INSERT INTO " + DatabaseContract.Merk.TABLE_NAME + "( " +
                DatabaseContract.Merk.COLUMN_NAME_MERKNAAM +
                " )" +
                "VALUES ( 'Gigabyte' ), " +
                "( 'AH' )");

        sqLiteDatabase.execSQL("INSERT INTO " + DatabaseContract.Producttype.TABLE_NAME + "( " +
                DatabaseContract.Producttype.COLUMN_NAME_PRODUCTTYPE +
                " )" +
                "VALUES ( 'Videokaart' )," +
                "( 'Cola' )");

        sqLiteDatabase.execSQL("INSERT INTO " + DatabaseContract.Productdetail.TABLE_NAME + "( " +
                DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTNAAM + ", " +
                DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTBESCHRIJVING + ", " +
                DatabaseContract.Productdetail.COLUMN_NAME_MERKNAAM + ", " +
                DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTTYPE +
                " )" +
                "VALUES ( 'GeForce RTX 4060Ti', 'Krachtige nieuwe videokaart uit de 40xx series.', 'Gigabyte', 'Videokaart' ), " +
                "( 'AH huismerk cola regular blik', 'Cola met suiker.', 'AH', 'Cola' ) ");

        sqLiteDatabase.execSQL("INSERT INTO " + DatabaseContract.Product.TABLE_NAME + "( " +
                DatabaseContract.Product.COLUMN_NAME_EAN + ", " +
                DatabaseContract.Product.COLUMN_NAME_NAAM + ", " +
                DatabaseContract.Product.COLUMN_NAME_VERKOOPPRIJS + ", " +
                DatabaseContract.Product.COLUMN_NAME_ACTUELEVOORRAAD +
                " )" +
                "VALUES ( '4739331313449', 'GeForce RTX 4060Ti', '399.00', '5' )," +
                "( '8718907783521', 'AH huismerk cola regular blik', '0.39', '12' )");

        sqLiteDatabase.execSQL("INSERT INTO " + DatabaseContract.Magazijnlocatie.TABLE_NAME + "( " +
                DatabaseContract.Magazijnlocatie.COLUMN_NAME_LOCATIE + ", " +
                DatabaseContract.Magazijnlocatie.COLUMN_NAME_EAN +
                " )" +
                "VALUES ( '11-02-15', '4739331313449' ), " +
                "('11-02-15', '8718907783521' ), " +
                "('15-04-06', '8718907783521' )");

        sqLiteDatabase.execSQL("INSERT INTO " + DatabaseContract.Adres.TABLE_NAME + "( " +
                DatabaseContract.Adres.COLUMN_NAME_POSTCODE + ", " +
                DatabaseContract.Adres.COLUMN_NAME_STRAATNAAMNUMMER + ", " +
                DatabaseContract.Adres.COLUMN_NAME_PLAATS +
                " )" +
                "VALUES ('2771 PH', 'Pippeling 127', 'Boskoop' ), " +
                "('1504 NG', 'Poelenburg 117', 'Zaandam'  ), " +
                "('7241 GR', 'Zwiepseweg 156', 'Lochem'  )");

        sqLiteDatabase.execSQL("INSERT INTO " + DatabaseContract.Klant.TABLE_NAME + "( " +
                DatabaseContract.Klant.COLUMN_NAME_EMAIL + ", " +
                DatabaseContract.Klant.COLUMN_NAME_KLANTNAAM + ", " +
                DatabaseContract.Klant.COLUMN_NAME_TEL + ", " +
                DatabaseContract.Klant.COLUMN_NAME_ADRESID +
                " )" +
                "VALUES ('jsloot3@johnsonenterprisesunlimited.com', 'Jan Sloot', '0617239245 ', '1' ), " +
                "('karen1476@24mail.top', 'Karen Sloot', '0637619500 ', '1' ), " +
                "('mjets@otpku.com', 'Mark Jets', '0666496059 ', '2' )");

        sqLiteDatabase.execSQL("INSERT INTO " + DatabaseContract.Bestelling.TABLE_NAME + "( " +
                DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGID + ", " +
                DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGSTATUS + ", " +
                DatabaseContract.Bestelling.COLUMN_NAME_EMAIL + ", " +
                DatabaseContract.Bestelling.COLUMN_NAME_PLAATSINGDATUM + ", " +
                DatabaseContract.Bestelling.COLUMN_NAME_ADRESID +
                " )" +
                "VALUES ( '8969698924666', 'Geplaatst', 'karen1476@24mail.top', '4-4-2024', '1' ), " +
                "( '1316460054927', 'Verwerkt', 'karen1476@24mail.top', '1-4-2024', '2' ), " +
                "( '8944794311909', 'Geplaatst', 'mjets@otpku.com', '3-4-2024', '3' )");

        sqLiteDatabase.execSQL("INSERT INTO " + DatabaseContract.Bestellingproduct.TABLE_NAME + "( " +
                DatabaseContract.Bestellingproduct.COLUMN_NAME_BESTELLINGID + ", " +
                DatabaseContract.Bestellingproduct.COLUMN_NAME_EAN + ", " +
                DatabaseContract.Bestellingproduct.COLUMN_NAME_AANTAL +
                " )" +
                "VALUES ( '8969698924666', '8718907763998', '1' ), " +
                "( '8969698924666', '9008703000779', '2' ), " +
                "( '1316460054927', '74969913221', '3' ), " +
                "( '8944794311909', '4739331313449', '2' )");

        sqLiteDatabase.execSQL("INSERT INTO " + DatabaseContract.Transporteur.TABLE_NAME + "( " +
                DatabaseContract.Transporteur.COLUMN_NAME_TRANSPORTEURNAAM + ", " +
                DatabaseContract.Transporteur.COLUMN_NAME_TRANSPORTEURVOORVOEGSEL +
                " )" +
                "VALUES ( 'PostNL', '3S' )," +
                "( 'DHL', 'JVG' )");

        sqLiteDatabase.execSQL("INSERT INTO " + DatabaseContract.Perceel.TABLE_NAME + "( " +
                DatabaseContract.Perceel.COLUMN_NAME_PERCEELID + ", " +
                DatabaseContract.Perceel.COLUMN_NAME_DATUMAANMAAK + ", " +
                DatabaseContract.Perceel.COLUMN_NAME_BESTELLINGID + ", " +
                DatabaseContract.Perceel.COLUMN_NAME_TRANSPORTEURID + " " +
                " )" +
                "VALUES ('3S1316460054927', '2-4-2024', '1316460054927', '1' )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldversion, int newversion) {
        onCreate(sqLiteDatabase);
    }


    public ArrayList<Product> GetProductenFromDB(){
        ArrayList<Product> producten = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DatabaseContract.Product.TABLE_NAME + " " +
                "INNER JOIN " + DatabaseContract.Productdetail.TABLE_NAME + " " +
                "ON " + DatabaseContract.Product.COLUMN_NAME_NAAM + " = " + DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTNAAM;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        try{
            if (cursor.moveToFirst()){
                do{
                    Product product = new Product();
                    product.setEan(String.valueOf(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Product.COLUMN_NAME_EAN))));
                    product.setActueleVoorraad(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Product.COLUMN_NAME_ACTUELEVOORRAAD)));

                    ProductDetail productDetail = new ProductDetail();
                    productDetail.setNaam(cursor.getString(cursor.getColumnIndex(DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTNAAM)));
                    productDetail.setProductBeschrijving(cursor.getString(cursor.getColumnIndex(DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTBESCHRIJVING)));
                    productDetail.setMerkNaam(cursor.getString(cursor.getColumnIndex(DatabaseContract.Productdetail.COLUMN_NAME_MERKNAAM)));
                    productDetail.setProductType(cursor.getString(cursor.getColumnIndex(DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTTYPE)));
                    product.setNaam(productDetail);

                    producten.add(product);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("Error", "Error getting Products from db");
        }
        finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return producten;
    }

    public ArrayList<Bestelling> GetBestellingenFromDB(){
        ArrayList<Bestelling> bestellingen = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DatabaseContract.Bestelling.TABLE_NAME + " " +
                "LEFT JOIN " + DatabaseContract.Klant.TABLE_NAME + " " +
                "ON " + DatabaseContract.Bestelling.TABLE_NAME + "." + DatabaseContract.Bestelling.COLUMN_NAME_EMAIL + " = " + DatabaseContract.Klant.TABLE_NAME + "." + DatabaseContract.Klant.COLUMN_NAME_EMAIL + " ";


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        try{
            if (cursor.moveToFirst()){
                do{
                    Bestelling bestelling = new Bestelling();
                    bestelling.setBestellingID(cursor.getString(cursor.getColumnIndex(DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGID)));
                    bestelling.setBestellingStatus(cursor.getString(cursor.getColumnIndex(DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGSTATUS)));
                    bestelling.setPlaatsingDatum(cursor.getString(cursor.getColumnIndex(DatabaseContract.Bestelling.COLUMN_NAME_PLAATSINGDATUM)));

                    Klant klant = new Klant();
                    klant.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseContract.Klant.COLUMN_NAME_EMAIL)));
                    klant.setTel(cursor.getString(cursor.getColumnIndex(DatabaseContract.Klant.COLUMN_NAME_TEL)));
                    klant.setKlantNaam(cursor.getString(cursor.getColumnIndex(DatabaseContract.Klant.COLUMN_NAME_KLANTNAAM)));
                    bestelling.setKlant(klant);

                    bestellingen.add(bestelling);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("Error", "Error getting Bestelling from db");
        }
        finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return bestellingen;
    }

    public ArrayList<Parceel> GetParcelenFromDB(){
        ArrayList<Parceel> parceels = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DatabaseContract.Perceel.TABLE_NAME + " " +
                "LEFT JOIN " + DatabaseContract.Transporteur.TABLE_NAME + " " +
                "ON " + DatabaseContract.Perceel.TABLE_NAME + "." + DatabaseContract.Perceel.COLUMN_NAME_TRANSPORTEURID + " = " + DatabaseContract.Transporteur.TABLE_NAME + "." + DatabaseContract.Transporteur.COLUMN_NAME_TRANSPORTEURID + " " +
                "LEFT JOIN " + DatabaseContract.Bestelling.TABLE_NAME + " " +
                "ON " + DatabaseContract.Perceel.TABLE_NAME + "." + DatabaseContract.Perceel.COLUMN_NAME_BESTELLINGID + " = " + DatabaseContract.Bestelling.TABLE_NAME + "." + DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGID + " " +
                "LEFT JOIN " + DatabaseContract.Klant.TABLE_NAME + " " +
                "ON " + DatabaseContract.Bestelling.TABLE_NAME + "." + DatabaseContract.Bestelling.COLUMN_NAME_EMAIL + " = " + DatabaseContract.Klant.TABLE_NAME + "." + DatabaseContract.Klant.COLUMN_NAME_EMAIL + " " +
                "LEFT JOIN " + DatabaseContract.Adres.TABLE_NAME + " " +
                "ON " + DatabaseContract.Bestelling.TABLE_NAME + "." + DatabaseContract.Bestelling.COLUMN_NAME_ADRESID + " = " + DatabaseContract.Adres.TABLE_NAME + "." + DatabaseContract.Adres.COLUMN_NAME_ADRESID + " ";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        try{
            if (cursor.moveToFirst()){
                do{
                    Parceel parceel = new Parceel();
                    parceel.setPerceelID(cursor.getString(cursor.getColumnIndex(DatabaseContract.Perceel.COLUMN_NAME_PERCEELID)));
                    parceel.setDatumAanmaak(cursor.getString(cursor.getColumnIndex(DatabaseContract.Perceel.COLUMN_NAME_DATUMAANMAAK)));

                    Transporteur transporteur = new Transporteur();
                    transporteur.setTransporteurNaam(cursor.getString(cursor.getColumnIndex(DatabaseContract.Transporteur.COLUMN_NAME_TRANSPORTEURNAAM)));
                    parceel.setTransporteur(transporteur);

                    Adres adres = new Adres();
                    adres.setPlaats(cursor.getString(cursor.getColumnIndex(DatabaseContract.Adres.COLUMN_NAME_PLAATS)));
                    adres.setPostcode(cursor.getString(cursor.getColumnIndex(DatabaseContract.Adres.COLUMN_NAME_POSTCODE)));
                    adres.setStraatNaamNummer(cursor.getString(cursor.getColumnIndex(DatabaseContract.Adres.COLUMN_NAME_STRAATNAAMNUMMER)));

                    Bestelling bestelling = new Bestelling();
                    Klant klant = new Klant();
                    klant.setKlantNaam(cursor.getString(cursor.getColumnIndex(DatabaseContract.Klant.COLUMN_NAME_KLANTNAAM)));
                    bestelling.setKlant(klant);
                    bestelling.setVerzendAdres(adres);
                    parceel.setBestelling(bestelling);

                    parceels.add(parceel);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("Error", "Error getting Parceel from db");
        }
        finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return parceels;
    }



}
