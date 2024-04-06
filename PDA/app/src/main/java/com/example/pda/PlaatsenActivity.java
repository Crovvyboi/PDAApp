package com.example.pda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pda.Database.MagazijnDatabaseHandler;
import com.example.pda.Database.VrachtDatabaseHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaatsenActivity extends AppCompatActivity {

    private int LAUNCH_SECOND_ACTIVITY = 1;
    private Button backbutton, homebutton;
    private ConstraintLayout productButton, schapButton;
    private TextView prodText, schapText;

    private String productnummer, schapnummer;

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
        prodText = findViewById(R.id.prodText);
        schapText = findViewById(R.id.schapText);

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
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                intent.putExtra("TARGET_VARIABLE", "productnummer");
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }
        });

        schapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productnummer != null && productnummer != "")
                {
                    // Open camera to scan barcode
                    Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                    intent.putExtra("TARGET_VARIABLE", "schapnummer");
                    startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Scan eerst een product!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == this.RESULT_OK){
                String result = data.getStringExtra("BARCODE_DATA");

                switch (data.getStringExtra("TARGET_VARIABLE")){
                    case "productnummer":
                        GetProductnummer(result);
                        break;
                    case "schapnummer":
                        GetSchapnummer(result);
                        break;
                    default:
                        break;
                }
            }
            if (resultCode == this.RESULT_CANCELED) {
                // Write your code if there's no result
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void GetProductnummer(String productnummer){
        // Check if Product exists
        MagazijnDatabaseHandler magazijnDatabaseHandler = new MagazijnDatabaseHandler(getApplicationContext());
        if (magazijnDatabaseHandler.CheckIfProductExists(productnummer)){
            this.productnummer = productnummer;
            this.prodText.setText(productnummer);
            this.schapnummer = "";
            this.schapText.setText("Scan de barcode op het schap");
        }
        else {
            Toast.makeText(getApplicationContext(), "Product niet gevonden.", Toast.LENGTH_SHORT).show();
        }
    }

    private void GetSchapnummer(String schapnummer){
        // Check if Schapnummer is in correct format
        if (SchapRegex(schapnummer)){
            this.schapnummer = schapnummer;
            this.schapText.setText(schapnummer);

            // Check if there is both a Product and Schap scanned
            if (productnummer != null && productnummer != ""){
                // Put new combination in database
                StartPlaatsen();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Scan een schap.", Toast.LENGTH_SHORT).show();
        }

    }

    public void StartPlaatsen(){
        MagazijnDatabaseHandler magazijnDatabaseHandler = new MagazijnDatabaseHandler(getApplicationContext());
        magazijnDatabaseHandler.PlaatsProductInSchap(this.productnummer, this.schapnummer);
    }

    public static boolean SchapRegex(final String input) {
        // Compile regular expression
        final Pattern pattern = Pattern.compile("\\d\\d-\\d\\d-\\d\\d", Pattern.CASE_INSENSITIVE);
        // Match regex against input
        final Matcher matcher = pattern.matcher(input);
        // Use results...
        return matcher.matches();
    }
}
