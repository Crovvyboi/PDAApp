package com.example.pda.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DatabaseHandler {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHandler (Context c){
        context = c;
    }

    public DatabaseHandler open() throws SQLException{
        dbHelper = new DatabaseHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }


}
