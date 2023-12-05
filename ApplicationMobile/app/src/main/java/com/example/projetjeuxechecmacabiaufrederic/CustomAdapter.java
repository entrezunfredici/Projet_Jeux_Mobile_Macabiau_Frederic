package com.example.projetjeuxechecmacabiaufrederic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<PartySelecter> {
    public CustomAdapter(Context context, ArrayList<PartySelecter> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PartySelecter ps = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.game_field, parent, false);
        }
        TextView partyName = convertView.findViewById(R.id.partyName);
        Button btnJoinParty= convertView.findViewById(R.id.btnJoinParty);

        partyName.setText(ps.getTexte());
        btnJoinParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //connection à une partie
                //setContentView(R.layout.activity_game);
                //Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            }
        });
        return convertView;
    }
}
