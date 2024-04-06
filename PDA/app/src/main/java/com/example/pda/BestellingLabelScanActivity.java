package com.example.pda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pda.Adapters.BestellingLabelAdapter;
import com.example.pda.Adapters.BestellingProductAdapter;
import com.example.pda.Bestelling.Bestelling;
import com.example.pda.Database.BestellingDatabaseHandler;
import com.example.pda.Parceel.Parceel;
import com.example.pda.Parceel.Transporteur;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class BestellingLabelScanActivity extends AppCompatActivity {

    private Integer labelcount;
    private String bestellingID;

    ArrayList<Parceel> parceels = new ArrayList<>();

    private Button backbutton, homebutton, afmeldbutton;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestellinglabelscan_scherm);

        Intent intent = getIntent();
        labelcount = intent.getIntExtra("Aantal", 0);
        bestellingID = intent.getStringExtra("BARCODE");

        InitWidgets();
    }

    private void InitWidgets() {
        backbutton = findViewById(R.id.backbutton);
        homebutton = findViewById(R.id.homeButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back
                Intent intent = new Intent(getApplicationContext(), BestellingActivity.class);
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

        listView = findViewById(R.id.listview);

        Bestelling bestelling = new Bestelling();
        bestelling.setBestellingID(bestellingID);

        Transporteur transporteur = new Transporteur();
        // Get transporteur from db
        Random random = new Random();
        switch (random.nextInt(2)){
            case 0:
                transporteur.setTransporteurID(1);
                transporteur.setTransporteurVoorvoegsel("3S");
                break;
            case 1:
                transporteur.setTransporteurID(2);
                transporteur.setTransporteurVoorvoegsel("JVG");
                break;
            default:
                transporteur.setTransporteurID(1);
                transporteur.setTransporteurVoorvoegsel("3S");
                break;
        }


        DateTimeFormatter localDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();

        ArrayList<String> labels = new ArrayList<>();
        parceels = new ArrayList<>();
        for (int i = 0; i <= labelcount; i++) {
            Parceel parceel = new Parceel();
            parceel.setPerceelID(transporteur.getTransporteurVoorvoegsel() + bestellingID + i);
            parceel.setBestelling(bestelling);
            parceel.setTransporteur(transporteur);
            parceel.setDatumAanmaak(localDateTime.format(now));
            parceels.add(parceel);

            labels.add(transporteur.getTransporteurVoorvoegsel() + bestellingID + i);
        }

        BestellingLabelAdapter bestellingLabelAdapter = new BestellingLabelAdapter(getApplicationContext(), labels);
        listView.setAdapter(bestellingLabelAdapter);

        afmeldbutton = findViewById(R.id.afmeldButton);
        afmeldbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Put new parcels in database
                BestellingDatabaseHandler bestellingProductAdapter = new BestellingDatabaseHandler(getApplicationContext());
                for (Parceel parceel:
                     parceels) {
                    bestellingProductAdapter.AddParcelToDB(parceel);
                }

                bestellingProductAdapter.UpdateBestellingStatus(bestellingID);

                Intent intent = new Intent(getApplicationContext(), BestellingActivity.class);
                startActivity(intent);
            }
        });

    }
}
