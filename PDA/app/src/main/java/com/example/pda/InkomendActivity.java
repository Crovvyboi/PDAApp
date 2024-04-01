package com.example.pda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class InkomendActivity extends AppCompatActivity {

    private ConstraintLayout toVracht, toPlaatsen;
    private Button backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inkomend_scherm);

        InitWidgets();
    }

    public void InitWidgets(){
        backButton = findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        toVracht = findViewById(R.id.toBestelling);
        toPlaatsen = findViewById(R.id.toPlaatsen);

        toVracht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create intent to vracht_scherm
                Intent intent = new Intent(getApplicationContext(), VrachtActivity.class);
                startActivity(intent);
            }
        });

        toPlaatsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create intent to plaatsen_scherm
                Intent intent = new Intent(getApplicationContext(), PlaatsenActivity.class);
                startActivity(intent);

            }
        });
    }
}
