package com.example.pda.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pda.Bestelling.Bestelling;
import com.example.pda.Bestelling.BestellingProduct;
import com.example.pda.MainActivity;
import com.example.pda.Parceel.Parceel;
import com.example.pda.Product.Product;
import com.example.pda.Product.ProductDetail;

import java.util.ArrayList;

public class BestellingDatabaseHandler {
    Context context;
    public BestellingDatabaseHandler(Context context){
        this.context = context;
    }

    public ArrayList<BestellingProduct> GetBestellingProducten(String code){
        ArrayList<BestellingProduct> products = new ArrayList<>();

        String QUERY = "SELECT * FROM " + DatabaseContract.Bestellingproduct.TABLE_NAME + " " +
                "INNER JOIN " + DatabaseContract.Product.TABLE_NAME + " " +
                "ON " + DatabaseContract.Bestellingproduct.TABLE_NAME + "." + DatabaseContract.Bestellingproduct.COLUMN_NAME_EAN + " = " + DatabaseContract.Product.TABLE_NAME + "." + DatabaseContract.Product.COLUMN_NAME_EAN + " " +
                "INNER JOIN " + DatabaseContract.Productdetail.TABLE_NAME + " " +
                "ON "+ DatabaseContract.Product.TABLE_NAME + "." + DatabaseContract.Product.COLUMN_NAME_NAAM + " = " + DatabaseContract.Productdetail.TABLE_NAME + "." + DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTNAAM + " " +
                "WHERE " + DatabaseContract.Bestellingproduct.TABLE_NAME + "." + DatabaseContract.Bestellingproduct.COLUMN_NAME_BESTELLINGID + " = " + code ;

        SQLiteDatabase db = MainActivity.databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        try{
            if (cursor.moveToFirst()){
                do{
                    BestellingProduct bestellingProduct = new BestellingProduct();

                    Product product = new Product();
                    product.setEan(String.valueOf(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Product.COLUMN_NAME_EAN))));
                    product.setActueleVoorraad(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Product.COLUMN_NAME_ACTUELEVOORRAAD)));

                    ProductDetail productDetail = new ProductDetail();
                    productDetail.setNaam(cursor.getString(cursor.getColumnIndex(DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTNAAM)));
                    productDetail.setProductBeschrijving(cursor.getString(cursor.getColumnIndex(DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTBESCHRIJVING)));
                    productDetail.setMerkNaam(cursor.getString(cursor.getColumnIndex(DatabaseContract.Productdetail.COLUMN_NAME_MERKNAAM)));
                    productDetail.setProductType(cursor.getString(cursor.getColumnIndex(DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTTYPE)));
                    product.setNaam(productDetail);

                    bestellingProduct.setProduct(product);
                    bestellingProduct.setAantal(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Bestellingproduct.COLUMN_NAME_AANTAL)));

                    products.add(bestellingProduct);
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

        for (BestellingProduct product:
             products) {
            GetLocaties(product);
        }

        return products;
    }

    public BestellingProduct GetLocaties(BestellingProduct product){
        ArrayList<String> locaties = new ArrayList<>();

        String QUERY = "SELECT * FROM " + DatabaseContract.Product.TABLE_NAME + " " +
                "INNER JOIN " + DatabaseContract.Magazijnlocatie.TABLE_NAME + " " +
                "ON " + DatabaseContract.Product.TABLE_NAME + "." + DatabaseContract.Product.COLUMN_NAME_EAN + " = " + DatabaseContract.Magazijnlocatie.TABLE_NAME + "." + DatabaseContract.Magazijnlocatie.COLUMN_NAME_EAN + " ";

        SQLiteDatabase db = MainActivity.databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        try{
            if (cursor.moveToFirst()){
                do{
                    locaties.add(cursor.getString(cursor.getColumnIndex(DatabaseContract.Magazijnlocatie.COLUMN_NAME_LOCATIE)));
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

        product.setLocaties(locaties);

        return product;
    }

    public void AddParcelToDB(Parceel parceel){
        String PLAATS_QUERY = "INSERT INTO " + DatabaseContract.Perceel.TABLE_NAME + "( " +
                DatabaseContract.Perceel.COLUMN_NAME_PERCEELID + ", " +
                DatabaseContract.Perceel.COLUMN_NAME_BESTELLINGID + ", " +
                DatabaseContract.Perceel.COLUMN_NAME_TRANSPORTEURID + ", " +
                DatabaseContract.Perceel.COLUMN_NAME_DATUMAANMAAK +
                " ) " +
                "VALUES ('" + parceel.getPerceelID() + "', '" + parceel.getBestelling().getBestellingID() + "', '" + parceel.getTransporteur().getTransporteurID() + "', '" + parceel.getDatumAanmaak() + "') ";
        SQLiteDatabase db = MainActivity.databaseHelper.instanceOfDB(context).getReadableDatabase();
        db.execSQL(PLAATS_QUERY);
    }

    public void UpdateBestellingStatus(String code){
        String PLAATS_QUERY = "UPDATE " + DatabaseContract.Bestelling.TABLE_NAME + " " +
                "SET " + DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGSTATUS + " = 'Verwerkt' " +
                "WHERE " + DatabaseContract.Bestelling.COLUMN_NAME_BESTELLINGID + " = '" + code + "'";
        SQLiteDatabase db = MainActivity.databaseHelper.instanceOfDB(context).getReadableDatabase();
        db.execSQL(PLAATS_QUERY);
    }

}
