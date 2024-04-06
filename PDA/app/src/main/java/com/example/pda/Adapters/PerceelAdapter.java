package com.example.pda.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pda.Parceel.Parceel;

import com.example.pda.R;

import java.util.List;

public class PerceelAdapter extends ArrayAdapter<Parceel> {
    TextView id, datum, transporteur, naam, straat, postcode, plaats;

    public PerceelAdapter(@NonNull Context context, List<Parceel> parceels) {
        super(context, 0, parceels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Parceel parceel = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell_perceel, parent,false);
        }

        id = convertView.findViewById(R.id.perceelID);
        datum = convertView.findViewById(R.id.datum);
        transporteur = convertView.findViewById(R.id.transporteur);
        naam = convertView.findViewById(R.id.klantNaam);
        straat = convertView.findViewById(R.id.straat);
        postcode = convertView.findViewById(R.id.postcode);
        plaats = convertView.findViewById(R.id.plaats);

        id.setText(String.valueOf(parceel.getPerceelID()));
        datum.setText(parceel.getDatumAanmaak());
        transporteur.setText(parceel.getTransporteur().getTransporteurNaam());
        naam.setText(parceel.getBestelling().getKlant().getKlantNaam());
        straat.setText(parceel.getBestelling().getVerzendAdres().getStraatNaamNummer());
        postcode.setText(parceel.getBestelling().getVerzendAdres().getPostcode());
        plaats.setText(parceel.getBestelling().getVerzendAdres().getPlaats());

        return convertView;
    }
}
