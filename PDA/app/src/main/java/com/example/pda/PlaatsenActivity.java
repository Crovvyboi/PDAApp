package com.example.pda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class PlaatsenActivity extends AppCompatActivity {

    private Button backbutton, homebutton;
    private ConstraintLayout productButton, schapButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plaatsen_scherm);

        InitWidgets();
    }

    private void InitWidgets() {
        backbutton = findViewById(R.id.backbutton);
        homebutton = findViewById(R.id.homeButton);
        productButton = findViewById(R.id.productButton);
        schapButton = findViewById(R.id.schapButton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back
                Intent intent = new Intent(getApplicationContext(), InkomendActivity.class);
                startActivity(intent);
            }
        });

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to home
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        productButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open camera to scan barcode

            }
        });

        schapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open camera to scan barcode

            }
        });

    }
}
