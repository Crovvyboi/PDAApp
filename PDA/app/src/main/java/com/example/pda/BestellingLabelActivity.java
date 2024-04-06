package com.example.pda;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pda.Adapters.BestellingLabelAdapter;

import java.util.ArrayList;

public class BestellingLabelActivity extends AppCompatActivity {
    private Integer selectedPos = -1;
    private String bestellingID;
    private Button backbutton, homebutton, selectbutton;
    private TextView selectedLabel;
    private ListView recyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestellinglabel_scherm);

        Intent intent = getIntent();
        bestellingID = intent.getStringExtra("BARCODE");

        InitWidgets();
    }

    private void InitWidgets() {
        backbutton = findViewById(R.id.backbutton);
        homebutton = findViewById(R.id.homeButton);
        selectbutton = findViewById(R.id.selectButton);
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

        selectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send to next screen
                Intent intent = new Intent(getApplicationContext(), BestellingLabelScanActivity.class);
                intent.putExtra("Aantal", selectedPos);
                intent.putExtra("BARCODE", bestellingID);
                startActivity(intent);
            }
        });

        recyclerview = findViewById(R.id.recyclerview);
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            labels.add(i + " LABELS");
        }
        BestellingLabelAdapter bestellingLabelAdapter = new BestellingLabelAdapter(getApplicationContext(), labels);
        recyclerview.setAdapter(bestellingLabelAdapter);

        recyclerview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPos = i;
                selectedLabel.setText((i + 1) + " labels geselecteerd.");
            }
        });

        selectedLabel = findViewById(R.id.selectedLabel);
        selectedLabel.setText("Nog geen labels geselecteerd.");

    }
}
