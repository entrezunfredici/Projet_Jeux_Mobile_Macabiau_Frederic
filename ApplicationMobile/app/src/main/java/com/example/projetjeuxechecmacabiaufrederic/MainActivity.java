package com.example.projetjeuxechecmacabiaufrederic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bNew=findViewById(R.id.btnCreateNewParty);
        Button bRefresh=findViewById(R.id.btnRefreshPartyList);
        ListView partyList = (ListView)findViewById(R.id.partyListLV);
        ArrayList<PartySelecter> psPartyList = new ArrayList();
        int iParty=5;
        for(int i=1; i<iParty+1; i++){
            PartySelecter ps = new PartySelecter();
            ps.setTexte("party"+i);
            psPartyList.add(ps);
        }
        CustomAdapter partyAdapter = new CustomAdapter(this, psPartyList);
        partyList.setAdapter(partyAdapter);
        bNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    //creation d'une partie
                    setContentView(R.layout.activity_game);
                    Intent intent = new Intent(getApplicationContext(), GameActivity.class);

                }
            }
        );
        bRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}