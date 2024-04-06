package com.example.pda.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.pda.MainActivity;

public class MagazijnDatabaseHandler {

    Context context;
    public MagazijnDatabaseHandler(Context context){
        this.context = context;
    }

    public boolean CheckIfProductExists(String ean){
        String QUERY = "SELECT * FROM " + DatabaseContract.Product.TABLE_NAME + " " +
                "WHERE " + DatabaseContract.Product.COLUMN_NAME_EAN + " = " + ean;

        SQLiteDatabase db = MainActivity.databaseHelper.instanceOfDB(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        if (cursor.getCount() > 0){
            return true;
        }

        return false;
    }

    public void PlaatsProductInSchap(String ean, String schapnummer){
        // Check if combination exists
        if (!CheckIfComboExists(ean, schapnummer)){
            // If not, put in new combination
            PlaatsCombo(ean, schapnummer);
        }
        else {
            Toast.makeText(context, "Product al in schap", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean CheckIfComboExists(String ean, String schapnummer){
        String QUERY = "SELECT * FROM " + DatabaseContract.Magazijnlocatie.TABLE_NAME + " " +
                "WHERE " + DatabaseContract.Magazijnlocatie.COLUMN_NAME_EAN + " = '" + ean + "' " +
                "AND " + DatabaseContract.Magazijnlocatie.COLUMN_NAME_LOCATIE + " = '" + schapnummer + "' ";

        SQLiteDatabase db = MainActivity.databaseHelper.instanceOfDB(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        if (cursor.getCount() > 0){
            return true;
        }

        return false;
    }

    public void PlaatsCombo(String ean, String schapnummer){

        String PLAATS_QUERY = "INSERT INTO " + DatabaseContract.Magazijnlocatie.TABLE_NAME + "( " +
                DatabaseContract.Magazijnlocatie.COLUMN_NAME_LOCATIE + ", " +
                DatabaseContract.Magazijnlocatie.COLUMN_NAME_EAN +
                " ) " +
                "VALUES ('" + schapnummer + "', '" + ean + "') ";
        SQLiteDatabase db = MainActivity.databaseHelper.instanceOfDB(context).getReadableDatabase();
        db.execSQL(PLAATS_QUERY);

        Toast.makeText(context, "Product geplaatst!", Toast.LENGTH_SHORT).show();

    }
}
