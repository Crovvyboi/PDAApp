package com.example.pda.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pda.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper databaseHelper;

    private static Context c;
    public static final String DB_NAME = "PDADB";
    public static final int DB_VERSION = 1;

    private static String CREATE_TABLE = "";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION );
        c = context;

        GetCreateString();
    }

    public static DatabaseHelper instanceOfDB(Context context){
        if (databaseHelper == null){
            databaseHelper = new DatabaseHelper(context);
        }

        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.i("Database", "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldversion, int newversion) {
        onCreate(sqLiteDatabase);
    }

    public void GetCreateString(){
        InputStream inputStream = c.getResources().openRawResource(R.raw.dbscript);

        CREATE_TABLE = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
        Log.d("Table script", CREATE_TABLE);
        
    }
}
