package com.example.pda.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pda.R;

import java.util.List;

public class BestellingLabelAdapter extends ArrayAdapter<String> {


    public BestellingLabelAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String label = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell_bestellinglabel, parent,false);
        }

        TextView labeltext = convertView.findViewById(R.id.labelText);
        labeltext.setText(label);

        return convertView;
    }
}
