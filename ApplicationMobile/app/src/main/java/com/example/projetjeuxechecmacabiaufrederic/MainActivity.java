package com.example.projetjeuxechecmacabiaufrederic;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
                Intent game = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(game);
                /*GameActivity game = new GameActivity();
                game.onCreate(savedInstanceState);*/
                //gameActivity();
>>>>>>> Test_Intent
            }
        });

        bRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    void gameActivity(){
        int[] casesColor1 = {255, 67, 47, 20};
        int[] casesColor2 = {255, 232, 220, 202};
        int[] casesColorGreen = {255, 9, 106, 9};
        int[] casesColorRed = {255, 240, 0, 32};
        Cases[][] echiquier = new Cases[8][8];

        setContentView(R.layout.activity_game);
        Button bDiscontinued = findViewById(R.id.btnDiscontinuedParty);
        TextView player = findViewById(R.id.player);
        Log.v("log","ready top play");
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                String identifier = "Case" + (i + 1);
                switch (k + 1) {
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
                if ((i % 2) == 0) {
                    if ((k % 2) == 0) {
                        echiquier[i][k].defColor(casesColor1);
                    } else {
                        echiquier[i][k].defColor(casesColor2);
                    }
                } else {
                    if ((k % 2) == 0) {
                        echiquier[i][k].defColor(casesColor2);
                    } else {
                        echiquier[i][k].defColor(casesColor1);
                    }
                }
            }
        }
        DataBase partyDB= new DataBase(this);
        TextView gameDuration = findViewById(R.id.GameDuration);
        new Thread(new Runnable() {
            public void run() {
                final int[] seconds = {0};
                final int[] minutes = {0};
                final Runnable task = new Runnable() {

                    @Override
                    public void run() {
                        if(seconds[0] ==60){
                            seconds[0] =0;
                            minutes[0]++;
                        }
                        if(minutes[0] >9 & seconds[0] >9){
                            gameDuration.setText(minutes[0] +":"+ seconds[0]);
                        }else if(minutes[0] >9){
                            gameDuration.setText(minutes[0] +":0"+ seconds[0]);
                        }else if(seconds[0] >9){
                            gameDuration.setText("0"+ minutes[0] +":"+ seconds[0]);
                        }else{
                            gameDuration.setText("0"+ minutes[0] +":0"+ seconds[0]);
                        }
                        seconds[0]++;
                    }
                };
                final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {

            }
        }).start();
    }
}
