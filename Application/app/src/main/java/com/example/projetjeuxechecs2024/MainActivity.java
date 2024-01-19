package com.example.projetjeuxechecs2024;

import static com.example.projetjeuxechecs2024.GameActivity.connectToAnHost;
import static com.example.projetjeuxechecs2024.GameActivity.setHost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.projetjeuxechecs2024.utility.FireBaseController;
import com.example.projetjeuxechecs2024.utility.clockSystem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public interface MainActivityCallBack{
        void onButtonClicked(int partyNumber);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        clockSystem clock = new clockSystem(1000);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bNew=findViewById(R.id.btnCreateNewParty);
        Button bRefresh=findViewById(R.id.btnRefreshPartyList);
        ArrayList<PartySelecter> psPartyList = new ArrayList();
        FireBaseController firebaseDatabase = new FireBaseController(this);
        firebaseDatabase.getPartyList();
        for(int i = 0; i<firebaseDatabase.getPartyList(); i++){
            PartySelecter ps = new PartySelecter("Rejoindre",R.layout.activity_main,"party"+(i+1));
            ps.setHost(i+1);
            psPartyList.add(ps);
        }
        CustomAdapter partyAdapter = new CustomAdapter(this, psPartyList, new MainActivityCallBack() {
            @Override
            public void onButtonClicked(int partyNumber) {
                Intent game = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(game);
                connectToAnHost("host"+partyNumber, getApplicationContext(), clock);
            }
        });
        ListView partyList = (ListView)findViewById(R.id.partyListLV);
        partyList.setAdapter(partyAdapter);
        bNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                firebaseDatabase.addNewParty();
                //creation d'une partie
                Intent game = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(game);
                int n=firebaseDatabase.getPartyList()+1;
                setHost("host"+n, getApplicationContext(), clock);
            }
        });
        Context context=this;
        bRefresh.setOnClickListener(new View.OnClickListener() {
            int iParty = 0;
            @Override
            public void onClick(View v) {
                psPartyList.clear();
                for(int i = 0; i<firebaseDatabase.getPartyList(); i++){
                    PartySelecter ps = new PartySelecter("Rejoindre",R.layout.activity_main,"party"+(i+1));
                    ps.setHost(i+1);
                    psPartyList.add(ps);
                }
                ListView partyList = (ListView)findViewById(R.id.partyListLV);
                partyList.setAdapter(partyAdapter);
            }
        });
    }
}