package com.example.pda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pda.Database.DatabaseHelper;
import com.example.pda.Database.VrachtDatabaseHandler;
import com.example.pda.Product.Product;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class VrachtActivity extends AppCompatActivity {
    private int LAUNCH_SECOND_ACTIVITY = 1;
    private Button backbutton, homebutton;
    private ConstraintLayout vrachtButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vracht_scherm);

        InitWidgets();
    }

    private void InitWidgets() {
        backbutton = findViewById(R.id.backbutton);
        homebutton = findViewById(R.id.homeButton);
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

        vrachtButton = findViewById(R.id.vrachtButton);
        vrachtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open camera to scan barcode
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                intent.putExtra("TARGET_VARIABLE", "vrachtnummer");
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == this.RESULT_OK){
                String result=data.getStringExtra("BARCODE_DATA");

                // Start verwerken vracht in database
                VerwerkVracht(result);

            }
            if (resultCode == this.RESULT_CANCELED) {
                // Write your code if there's no result
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void VerwerkVracht(String code){
        // Check of de code in de vracht database bestaat
        VrachtDatabaseHandler vrachtDatabaseHandler = new VrachtDatabaseHandler(this);
        boolean check = vrachtDatabaseHandler.CheckIfVrachtExists(code);
        if (check){
            // Haal de producten op uit de vracht database
            ArrayList<Product> vrachtProducten = new ArrayList<>();
            vrachtProducten = vrachtDatabaseHandler.GetVrachtProducten(code);

            // Begin met plaatsen van producten in systeem database
            // Check voor elk product of het product al bestaat
            for (Product product:
                 vrachtProducten) {
                if (vrachtDatabaseHandler.CheckIfProductExists(product)){
                    // Ja, voeg het aantal toe aan de voorraad van het product
                    vrachtDatabaseHandler.AddToExistingProduct(product);
                }
                else{
                    // Nee, voeg de nieuwe producten toe aan de database
                    vrachtDatabaseHandler.AddNewProduct(product);
                }
            }

        }
        else {
            // Geef melding
            Toast.makeText(this, "Vracht niet gevonden.", Toast.LENGTH_LONG).show();
        }


    }
}
