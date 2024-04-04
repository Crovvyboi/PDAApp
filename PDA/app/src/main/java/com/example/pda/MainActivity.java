package com.example.pda;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pda.Database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout toUitgaand, toInkomend, toOverzicht;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       loadFromDBToMemory();

        InitWidgets();
    }

    public void InitWidgets(){
        toUitgaand = findViewById(R.id.toUitgaand);
        toInkomend = findViewById(R.id.toInkomend);
        toOverzicht = findViewById(R.id.toOverzicht);

        toUitgaand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create intent to uitgaand_scherm
                Intent intent = new Intent(getApplicationContext(), UitgaandActivity.class);
                startActivity(intent);
            }
        });

        toInkomend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create intent to inkomend_scherm
                Intent intent = new Intent(getApplicationContext(), InkomendActivity.class);
                startActivity(intent);
            }
        });

        toOverzicht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create intent to overzicht_scherm
                Intent intent = new Intent(getApplicationContext(), OverzichtActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadFromDBToMemory(){
        DatabaseHelper databaseHelper = DatabaseHelper.instanceOfDB(this);
        databaseHelper.onCreate(databaseHelper.getWritableDatabase());
    }

}