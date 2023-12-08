package com.example.projetjeuxechecmacabiaufrederic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //context=getApplicationContext();
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
                    /*Intent game = new Intent(getApplicationContext(), GameActivity.class);
                    startActivity(game);*/
                    /*GameActivity game = new GameActivity();
                    game.onCreate(savedInstanceState);*/
                    //normalement dans une autre classe
                    int[] casesColor1 = {255, 67, 47, 20};
                    int[] casesColor2 = {255, 232, 220, 202};
                    Cases[][] echiquier = new Cases[9][9];
                    setContentView(R.layout.activity_game);
                    Button bDiscontinued = findViewById(R.id.btnDiscontinuedParty);
                    Log.v("log","ready top play");
                    for (int i = 1; i < 9; i++) {
                        for (int k = 1; k < 9; k++) {
                            String identifier = "Case" + i;
                            switch (k) {
                                case 1:
                                    identifier = identifier + "A";
                                    break;
                                case 2:
                                    identifier = identifier + "B";
                                    break;
                                case 3:
                                    identifier = identifier + "C";
                                    break;
                                case 4:
                                    identifier = identifier + "D";
                                    break;
                                case 5:
                                    identifier = identifier + "E";
                                    break;
                                case 6:
                                    identifier = identifier + "F";
                                    break;
                                case 7:
                                    identifier = identifier + "G";
                                    break;
                                default:
                                    identifier = identifier + "H";
                                    break;
                            }
                            echiquier[i][k] = findViewById(getResources().getIdentifier(identifier, "id", getPackageName()));
                            if ((i%2)==0) {
                                if ((k%2)==0) {
                                    echiquier[i][k].defColor(casesColor1);
                                } else {
                                    echiquier[i][k].defColor(casesColor2);
                                }
                            } else {
                                if ((k%2)==0) {
                                    echiquier[i][k].defColor(casesColor2);
                                } else {
                                    echiquier[i][k].defColor(casesColor1);
                                }
                            }
                        }
                    }
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