package com.example.projetjeuxechecmacabiaufrederic;

import static com.example.projetjeuxechecmacabiaufrederic.GameActivity.connectToAnHost;
import static com.example.projetjeuxechecmacabiaufrederic.GameActivity.setHost;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public interface MainActivityCallBack{
        void onButtonClicked(int partyNumber);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDataBase menuDataBase= new MyDataBase(this);
        Button bNew=findViewById(R.id.btnCreateNewParty);
        Button bRefresh=findViewById(R.id.btnRefreshPartyList);
        ArrayList<PartySelecter> psPartyList = new ArrayList();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference online = database.getReference("partyList");
        online.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int nParty = dataSnapshot.getValue(int.class);
                menuDataBase.insertData(nParty+"");
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });
        for(int i = 0; i<parseInt(menuDataBase.readData()); i++){
             //DatabaseReference partyStatus = database.getReference("host"+i+"status");
             PartySelecter ps = new PartySelecter("Rejoindre",R.layout.activity_main,"party"+(i+1));
             ps.setIParty(i+1);
             psPartyList.add(ps);
        }
        CustomAdapter partyAdapter = new CustomAdapter(this, psPartyList, new MainActivityCallBack() {
            @Override
            public void onButtonClicked(int partyNumber) {
                Intent game = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(game);
                connectToAnHost("host"+partyNumber, getApplicationContext());
            }
        });
        ListView partyList = (ListView)findViewById(R.id.partyListLV);
        partyList.setAdapter(partyAdapter);

        bNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                int iParty=parseInt(menuDataBase.readData())+1;
                online.setValue(iParty);
                //creation d'une partie
                Intent game = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(game);
                setHost("host"+iParty, getApplicationContext());
            }
        });

        bRefresh.setOnClickListener(new View.OnClickListener() {
            int iParty = 0;
            @Override
            public void onClick(View v) {
                online.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        int nParty = dataSnapshot.getValue(int.class);
                        menuDataBase.insertData(nParty+"");
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("APPX", "Failed to read value.", error.toException());
                    }
                });
                for(int i = 0; i<parseInt(menuDataBase.readData()); i++){
                    PartySelecter ps = new PartySelecter("Rejoindre",R.layout.activity_game,"party"+(i+1));
                    psPartyList.add(ps);
                }
            }
        });
    }
}
