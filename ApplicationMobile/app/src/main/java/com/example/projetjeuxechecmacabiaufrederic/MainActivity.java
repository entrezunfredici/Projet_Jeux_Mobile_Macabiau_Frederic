package com.example.projetjeuxechecmacabiaufrederic;

import static java.lang.Boolean.TRUE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context=getApplicationContext();
        Button bNew=findViewById(R.id.btnCreateNewParty);
        Button bRefresh=findViewById(R.id.btnRefreshPartyList);
        ListView partyList = (ListView)findViewById(R.id.partyListLV);
        ArrayList<PartySelecter> psPartyList = new ArrayList();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Online = database.getReference("partyList");
        int iParty=0;
        Online.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int iParty = dataSnapshot.getValue(int.class);
                Log.v("Number_of_party",iParty+" ");

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });
        for(int i = 1; i<(iParty+1); i++){
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
                Cases[][] echiquier = new Cases[8][8];
                Online.setValue(iParty+1);
                String partyID="party"+iParty;
                Online.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        int value = dataSnapshot.getValue(int.class);
                        Log.v("Number_of_party",value+" ");

                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("APPX", "Failed to read value.", error.toException());
                    }
                });
                setContentView(R.layout.activity_game);
                Button bDiscontinued = findViewById(R.id.btnDiscontinuedParty);
                EditText player = findViewById(R.id.player);
                Log.v("log","ready top play");
                for (int i = 0; i < 8; i++) {
                    for (int k = 0; k < 8; k++) {
                        String identifier = "Case" + (i+1);
                        switch ((k+1)) {
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
                new Thread(new Runnable(){
                    public void run(){
                        DatabaseReference partyName = database.getReference(partyID);
                        Boolean white=TRUE;
                        if(white){
                            partyName.setValue("white");
                        }else{
                            partyName.setValue("black");
                        }
                    }
                });
            }
        });
        bRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}