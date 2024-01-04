package com.example.projetjeuxechecmacabiaufrederic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<PartySelecter> {

    private MainActivity.MainActivityCallBack mainActivityCallBack;
    Context selecterContext;
    public CustomAdapter(Context context, ArrayList<PartySelecter> games, MainActivity.MainActivityCallBack mainActivityCallBack) {
        super(context, 0, games);
        this.mainActivityCallBack=mainActivityCallBack;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.game_field, parent, false);
        }
        PartySelecter ps = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.game_field, parent, false);
        }
        TextView partyName = convertView.findViewById(R.id.partyName);
        Button btnJoinParty= convertView.findViewById(R.id.btnJoinParty);
        btnJoinParty.setText(ps.getDefaultButton());
        partyName.setText(ps.getText());
        btnJoinParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //connection à une partie
                mainActivityCallBack.onButtonClicked(ps.getHost());
            }
        });
        return convertView;
    }
}
