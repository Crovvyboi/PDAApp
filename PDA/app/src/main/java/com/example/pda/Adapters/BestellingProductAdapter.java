package com.example.pda.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pda.Bestelling.BestellingProduct;
import com.example.pda.Product.Product;
import com.example.pda.R;

import java.util.List;

public class BestellingProductAdapter extends ArrayAdapter<BestellingProduct> {

    TextView naam, totaal, verzameld, ean, locatie;

    public BestellingProductAdapter(@NonNull Context context, List<BestellingProduct> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BestellingProduct product = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell_bestellingproduct, parent,false);
        }

        naam = convertView.findViewById(R.id.productNaamLabel);
        totaal = convertView.findViewById(R.id.totaalProductenLabel);
        verzameld = convertView.findViewById(R.id.verzameldProductenLabel);
        ean = convertView.findViewById(R.id.eanLabel);
        locatie = convertView.findViewById(R.id.locatieLabel);

        naam.setText(product.getProduct().getNaam().getNaam());
        totaal.setText(String.valueOf(product.getAantal()));
        verzameld.setText(String.valueOf(product.getVerzameld()));
        ean.setText(product.getProduct().getEan());
        locatie.setText(product.getLocaties().get(0));

        return convertView;
    }
}
