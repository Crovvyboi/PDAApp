package com.example.pda;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pda.Adapters.BestellingAdapter;
import com.example.pda.Adapters.PerceelAdapter;
import com.example.pda.Adapters.ProductAdapter;
import com.example.pda.Bestelling.Bestelling;
import com.example.pda.Database.DatabaseHelper;
import com.example.pda.Parceel.Parceel;
import com.example.pda.Product.Product;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class OverzichtActivity extends AppCompatActivity {


    private ArrayList<Product> producten = new ArrayList<>();
    private ArrayList<Bestelling> bestellingen = new ArrayList<>();
    private ArrayList<Parceel> parceels = new ArrayList<>();

    private Button backButton;
    private TabLayout tabLayout;
    private ListView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overzicht_scherm);

        InitWidgets();
    }

    private void InitWidgets() {
        backButton = findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);

        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        Log.d("Tab switch", "Switched to bestelling");
                        GetBestellingListFromDB();
                        SetAdapterToBestelling();
                        break;
                    case 1:
                        Log.d("Tab switch", "Switched to product");
                        GetProductListFromDB();
                        SetAdaptertoProduct();
                        break;
                    case 2:
                        Log.d("Tab switch", "Switched to parceel");
                        GetPerceelListFromDB();
                        SetAdaptertoPerceel();
                        break;
                    default:
                        GetBestellingListFromDB();
                        SetAdapterToBestelling();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        Log.d("Tab switch", "Switched to bestelling");
                        GetBestellingListFromDB();
                        SetAdapterToBestelling();
                        break;
                    case 1:
                        Log.d("Tab switch", "Switched to product");
                        GetProductListFromDB();
                        SetAdaptertoProduct();
                        break;
                    case 2:
                        Log.d("Tab switch", "Switched to parceel");
                        GetPerceelListFromDB();
                        SetAdaptertoPerceel();
                        break;
                    default:
                        GetBestellingListFromDB();
                        SetAdapterToBestelling();
                        break;
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                switch (tab.getPosition()){
                    case 0:
                        Log.d("Tab switch", "Switched to bestelling");
                        GetBestellingListFromDB();
                        SetAdapterToBestelling();
                        break;
                    case 1:
                        Log.d("Tab switch", "Switched to product");
                        GetProductListFromDB();
                        SetAdaptertoProduct();
                        break;
                    case 2:
                        Log.d("Tab switch", "Switched to parceel");
                        GetPerceelListFromDB();
                        SetAdaptertoPerceel();
                        break;
                    default:
                        GetBestellingListFromDB();
                        SetAdapterToBestelling();
                        break;
                }

            }
        });

        tabLayout.selectTab(tabLayout.getTabAt(0));
    }

    public void GetProductListFromDB(){
        DatabaseHelper databaseHelper = MainActivity.databaseHelper.instanceOfDB(this);
        producten = databaseHelper.GetProductenFromDB();
    }

    public void GetBestellingListFromDB(){
        DatabaseHelper databaseHelper = MainActivity.databaseHelper.instanceOfDB(this);
        bestellingen = databaseHelper.GetBestellingenFromDB();
    }

    public void GetPerceelListFromDB(){
        DatabaseHelper databaseHelper = MainActivity.databaseHelper.instanceOfDB(this);
        parceels = databaseHelper.GetParcelenFromDB();
    }

    public void SetAdaptertoProduct(){
        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), producten);
        recyclerView.setAdapter(productAdapter);
    }

    public void SetAdapterToBestelling(){
        BestellingAdapter bestellingAdapter = new BestellingAdapter(getApplicationContext(), bestellingen);
        recyclerView.setAdapter(bestellingAdapter);
    }

    public void SetAdaptertoPerceel(){
        PerceelAdapter perceelAdapter = new PerceelAdapter(getApplicationContext(), parceels);
        recyclerView.setAdapter(perceelAdapter);
    }


}
