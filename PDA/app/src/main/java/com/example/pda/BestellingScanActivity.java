package com.example.pda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pda.Adapters.BestellingProductAdapter;
import com.example.pda.Adapters.ProductAdapter;
import com.example.pda.Bestelling.BestellingProduct;
import com.example.pda.Database.BestellingDatabaseHandler;
import com.example.pda.Database.MagazijnDatabaseHandler;
import com.example.pda.Database.VrachtDatabaseHandler;
import com.example.pda.Product.Product;

import java.util.ArrayList;

public class BestellingScanActivity extends AppCompatActivity {
    private int LAUNCH_SECOND_ACTIVITY = 1;
    private String code;
    private ArrayList<BestellingProduct> bestellingProducten = new ArrayList<>();
    private Button backbutton, homebutton, scanbutton;
    private ListView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestellingscan_scherm);

        Intent intent = getIntent();
        code = intent.getStringExtra("BARCODE");

        GetBestellingProducten(code);

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

        recyclerView = findViewById(R.id.recyclerView);
        SetAdapter();

        scanbutton = findViewById(R.id.scanButton);
        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }
        });

    }

    public void SetAdapter(){
        BestellingProductAdapter bestellingProductAdapter = new BestellingProductAdapter(getApplicationContext(), bestellingProducten);
        recyclerView.setAdapter(bestellingProductAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == this.RESULT_OK){
                String barcode = data.getStringExtra("BARCODE_DATA");
                // Check if product is on list
                CheckScannedProduct(barcode);
            }
            if (resultCode == this.RESULT_CANCELED) {
                // Write your code if there's no result
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void GetBestellingProducten(String code){
        BestellingDatabaseHandler bestellingDatabaseHandler = new BestellingDatabaseHandler(getApplicationContext());
        bestellingProducten = bestellingDatabaseHandler.GetBestellingProducten(code);

        if (bestellingProducten.size() < 1 ){
            Toast.makeText(getApplicationContext(), "Producten voor bestelling niet op voorraad.", Toast.LENGTH_SHORT);
            Intent intent = new Intent(getApplicationContext(), BestellingActivity.class);
            startActivity(intent);
        }
    }

    private void CheckScannedProduct(String code){
        // Check if product exists
        MagazijnDatabaseHandler magazijnDatabaseHandler = new MagazijnDatabaseHandler(getApplicationContext());
        if (magazijnDatabaseHandler.CheckIfProductExists(code)){
            // Yes, get product
            VrachtDatabaseHandler vrachtDatabaseHandler = new VrachtDatabaseHandler(getApplicationContext());
            Product product  = new Product();
            product = vrachtDatabaseHandler.GetExistingProduct(code);

            // Yes, check if product is in list
            Integer productIndex = CheckIfInList(product);
            if (productIndex != -1){
                // Add +1 to entry
                bestellingProducten.get(productIndex).AddToVerzameld(getApplicationContext());

                // Check if list is complete
                if (CheckIfCollected()){
                    // Yes, go to bestellinglabel_scherm
                    Intent intent = new Intent(getApplicationContext(), BestellingLabelActivity.class);
                    intent.putExtra("BARCODE", this.code);
                    startActivity(intent);
                }
                else{
                    // Update view
                    SetAdapter();
                }
            }
        }


    }

    private Integer CheckIfInList(Product product){
        // Check for each item if product ean  = product ean
        for (BestellingProduct prod:
             bestellingProducten) {
            // Yes, return index
            if (prod.getProduct().getEan().equals(product.getEan())){
                return bestellingProducten.indexOf(prod);
            }
        }
        return -1;
    }

    private boolean CheckIfCollected(){
        // Check for each item if aantal = verzameld
        for (BestellingProduct product:
             bestellingProducten) {
            if (!product.CheckIfComplete()){
                return false;
            }
        }
        return true;
    }

}
