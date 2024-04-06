package com.example.pda.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.pda.Bestelling.Adres;
import com.example.pda.Bestelling.Bestelling;
import com.example.pda.Bestelling.Klant;
import com.example.pda.MainActivity;
import com.example.pda.Parceel.Parceel;
import com.example.pda.Parceel.Transporteur;
import com.example.pda.Product.Product;
import com.example.pda.Product.ProductDetail;

import java.util.ArrayList;

public class VrachtDatabaseHandler {
    Context context;
    public VrachtDatabaseHandler(Context context){
        this.context = context;
    }

    public boolean CheckIfVrachtExists(String code){
        String QUERY = "SELECT * FROM " + DatabaseContract.Vrachtlevering.TABLE_NAME + " " +
                "WHERE " + DatabaseContract.Vrachtlevering.COLUMN_NAME_BARCODE + " = " + code;

        SQLiteDatabase db = MainActivity.databaseHelper.instanceOfDB(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        try{
            if (cursor.moveToFirst()){
                return true;
            }
        } catch (Exception e) {
            Log.d("Error", "Error getting Vracht from db");
        }
        finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return false;
    }

    public ArrayList<Product> GetVrachtProducten(String code){
        ArrayList<Product> vrachtProducten = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DatabaseContract.Vrachtlevering.TABLE_NAME + " " +
                "INNER JOIN " + DatabaseContract.Vrachtproduct.TABLE_NAME + " " +
                "ON " + DatabaseContract.Vrachtlevering.TABLE_NAME + "." + DatabaseContract.Vrachtlevering.COLUMN_NAME_EAN + " = " + DatabaseContract.Vrachtproduct.TABLE_NAME + "." + DatabaseContract.Vrachtproduct.COLUMN_NAME_EAN + " " +
                "WHERE " + DatabaseContract.Vrachtlevering.COLUMN_NAME_BARCODE + " = " + code;

        SQLiteDatabase db = MainActivity.databaseHelper.instanceOfDB(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        try{
            if (cursor.moveToFirst()){
                do{
                    Product product = new Product();
                    product.setEan(cursor.getString(cursor.getColumnIndex(DatabaseContract.Vrachtproduct.COLUMN_NAME_EAN)));
                    product.setActueleVoorraad(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Vrachtlevering.COLUMN_NAME_AANTAL)));

                    ProductDetail productDetail = new ProductDetail();
                    productDetail.setNaam(cursor.getString(cursor.getColumnIndex(DatabaseContract.Vrachtproduct.COLUMN_NAME_NAAM)));
                    productDetail.setProductBeschrijving(cursor.getString(cursor.getColumnIndex(DatabaseContract.Vrachtproduct.COLUMN_NAME_PRODUCTBESCHRIJVING)));
                    productDetail.setMerkNaam(cursor.getString(cursor.getColumnIndex(DatabaseContract.Vrachtproduct.COLUMN_NAME_MERKNAAM)));
                    productDetail.setProductType(cursor.getString(cursor.getColumnIndex(DatabaseContract.Vrachtproduct.COLUMN_NAME_PRODUCTTYPE)));
                    product.setNaam(productDetail);

                    vrachtProducten.add(product);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("Error", "Error getting Vracht from db");
        }
        finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }


        return vrachtProducten;
    }

    public boolean CheckIfProductExists(Product product){
        String QUERY = "SELECT * FROM " + DatabaseContract.Product.TABLE_NAME + " " +
                "WHERE " + DatabaseContract.Product.COLUMN_NAME_EAN + " = " + product.getEan();

        SQLiteDatabase db = MainActivity.databaseHelper.instanceOfDB(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        if (cursor.getCount() > 0){
            return true;
        }

        return false;

    }

    public void AddToExistingProduct(Product product){
        Product existingProduct = new Product();
        // Get existing product
        existingProduct = GetExistingProduct(product.getEan());
        if (existingProduct != null){
            Integer newVoorraad = product.getActueleVoorraad() + existingProduct.getActueleVoorraad();

            SQLiteDatabase db = MainActivity.databaseHelper.instanceOfDB(context).getReadableDatabase();
            String VOORRAAD_QUERY = "UPDATE " + DatabaseContract.Product.TABLE_NAME + " " +
                    "SET " + DatabaseContract.Product.COLUMN_NAME_ACTUELEVOORRAAD + " = " + newVoorraad + " " +
                    "WHERE " + DatabaseContract.Product.COLUMN_NAME_EAN + " = " + product.getEan();
            ;
            db.execSQL(VOORRAAD_QUERY);
            Toast.makeText(context, "Added to Voorraad.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "An error has occured whilst adding to Voorraad.", Toast.LENGTH_SHORT).show();
        }
    }

    public void AddNewProduct(Product product){
        SQLiteDatabase db = MainActivity.databaseHelper.instanceOfDB(context).getReadableDatabase();

        if (!CheckIfMerkExists(product.getNaam().getMerkNaam())){
            // Insert new Merk
            String MERK_QUERY = "INSERT INTO " + DatabaseContract.Merk.TABLE_NAME + " (" +
                    DatabaseContract.Merk.COLUMN_NAME_MERKNAAM +
                    ") " +
                    "VALUES ('" +
                    product.getNaam().getMerkNaam() +
                    "')";
            db.execSQL(MERK_QUERY);
        }
        if (!CheckIfTypeExists(product.getNaam().getProductType())){
            // Insert new Producttype
            String TYPE_QUERY = "INSERT INTO " + DatabaseContract.Producttype.TABLE_NAME + " (" +
                    DatabaseContract.Producttype.COLUMN_NAME_PRODUCTTYPE +
                    ") " +
                    "VALUES ('" +
                    product.getNaam().getProductType() +
                    "')";
            db.execSQL(TYPE_QUERY);
        }

        // Insert Productdetail
        String DETAIL_QUERY = "INSERT INTO " + DatabaseContract.Productdetail.TABLE_NAME+ " (" +
                DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTNAAM + ", " +
                DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTBESCHRIJVING + ", " +
                DatabaseContract.Productdetail.COLUMN_NAME_MERKNAAM + ", " +
                DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTTYPE + " " +
                ")" +
                "VALUES ('" +
                product.getNaam().getNaam() + "', '" +
                product.getNaam().getProductBeschrijving() + "', '" +
                product.getNaam().getMerkNaam() + "', '" +
                product.getNaam().getProductType() + "' " +
                ")";

        db.execSQL(DETAIL_QUERY);

        // Insert product
        String PRODUCT_QUERY = "INSERT INTO " + DatabaseContract.Product.TABLE_NAME + " (" +
                DatabaseContract.Product.COLUMN_NAME_EAN + ", " +
                DatabaseContract.Product.COLUMN_NAME_NAAM + ", " +
                DatabaseContract.Product.COLUMN_NAME_ACTUELEVOORRAAD + ", " +
                DatabaseContract.Product.COLUMN_NAME_VERKOOPPRIJS + " " +
                ")" +
                "VALUES ('" +
                product.getEan() + "', '" +
                product.getNaam().getNaam() + "', " +
                product.getActueleVoorraad() + ", " +
                product.getPrijs() + " " +
                ")";

        db.execSQL(PRODUCT_QUERY);

        Toast.makeText(context, "Added new Product.", Toast.LENGTH_SHORT).show();
    }

    public Product GetExistingProduct(String ean){
        String QUERY = "SELECT * FROM " + DatabaseContract.Product.TABLE_NAME + " " +
                "INNER JOIN " + DatabaseContract.Productdetail.TABLE_NAME + " " +
                "ON " + DatabaseContract.Product.TABLE_NAME + "." + DatabaseContract.Product.COLUMN_NAME_NAAM + " = " + DatabaseContract.Productdetail.TABLE_NAME + "." + DatabaseContract.Productdetail.COLUMN_NAME_PRODUCTNAAM;

        SQLiteDatabase db = MainActivity.databaseHelper.instanceOfDB(context).getReadableDatabase();
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

                    return product;
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

        return null;
    }

    public boolean CheckIfMerkExists(String merk){
        String QUERY = "SELECT * FROM " + DatabaseContract.Merk.TABLE_NAME + " " +
                "WHERE " + DatabaseContract.Merk.COLUMN_NAME_MERKNAAM + " = '" + merk + "' ";

        SQLiteDatabase db = MainActivity.databaseHelper.instanceOfDB(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        if (cursor.getCount() > 0){
            return true;
        }

        return false;
    }

    public boolean CheckIfTypeExists(String type){
        String QUERY = "SELECT * FROM " + DatabaseContract.Producttype.TABLE_NAME + " " +
                "WHERE " + DatabaseContract.Producttype.COLUMN_NAME_PRODUCTTYPE + " = '" + type+ "' ";

        SQLiteDatabase db = MainActivity.databaseHelper.instanceOfDB(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        if (cursor.getCount() > 0){
            return true;
        }

        return false;
    }
}
