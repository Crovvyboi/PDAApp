package com.example.pda.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pda.Product.Product;
import com.example.pda.R;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    TextView naam, beschrijving, voorraad, ean, type, merk;

    public ProductAdapter(@NonNull Context context, @NonNull List<Product> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell_product, parent,false);
        }

        naam = convertView.findViewById(R.id.productNaam);
        beschrijving = convertView.findViewById(R.id.productBeschrijving);
        voorraad = convertView.findViewById(R.id.actueleVoorraad);
        ean = convertView.findViewById(R.id.ean);
        type = convertView.findViewById(R.id.productType);
        merk = convertView.findViewById(R.id.merk);

        naam.setText(product.getNaam().getNaam());
        beschrijving.setText(product.getNaam().getProductBeschrijving());
        voorraad.setText(String.valueOf(product.getActueleVoorraad()));
        ean.setText("EAN: " + String.valueOf(product.getEan()));
        type.setText(product.getNaam().getProductType());
        merk.setText(product.getNaam().getMerkNaam());

        return convertView;
    }
}
