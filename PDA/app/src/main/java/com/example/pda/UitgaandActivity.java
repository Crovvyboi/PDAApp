package com.example.pda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class UitgaandActivity extends AppCompatActivity {
    private ConstraintLayout toBestelling;
    private Button backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uitgaand_scherm);

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


        toBestelling = findViewById(R.id.toBestelling);

        toBestelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create intent to bestelling_scherm
                Intent intent = new Intent(getApplicationContext(), BestellingActivity.class);
                startActivity(intent);
            }
        });

    }
}
