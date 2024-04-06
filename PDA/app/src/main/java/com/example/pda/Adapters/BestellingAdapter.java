package com.example.pda.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pda.Bestelling.Bestelling;
import com.example.pda.R;

import java.util.List;

public class BestellingAdapter extends ArrayAdapter<Bestelling> {
    public BestellingAdapter(@NonNull Context context, List<Bestelling> bestellingList) {
        super(context,0, bestellingList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Bestelling bestelling = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell_bestelling, parent,false);
        }

        TextView bestellingID = convertView.findViewById(R.id.bestellingID);
        TextView status = convertView.findViewById(R.id.bestellingStatus);
        TextView datum = convertView.findViewById(R.id.plaatsingDatum);
        TextView klant = convertView.findViewById(R.id.klantnaam);

        bestellingID.setText(String.valueOf(bestelling.getBestellingID()));
        status.setText(bestelling.getBestellingStatus());
        datum.setText(bestelling.getPlaatsingDatum());
        klant.setText(bestelling.getKlant().getKlantNaam());


        return convertView;
    }
}
