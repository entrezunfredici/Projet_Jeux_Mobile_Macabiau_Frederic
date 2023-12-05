package com.example.projetjeuxechecmacabiaufrederic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bNew=findViewById(R.id.btnCreateNewParty);
        Button bRefresh=findViewById(R.id.btnRefreshPartyList);
        ListView list = (ListView)findViewById(R.id.partyListLV);
        ArrayAdapter<String> tableau =
                new ArrayAdapter<String>(list.getContext(),
                        R.layout.game_field,
                        R.id.partyName);
        int iParty=3;
        for (int i=0; i<iParty; i++){
            tableau.add("party"+i);
        }
        list.setAdapter(tableau);
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