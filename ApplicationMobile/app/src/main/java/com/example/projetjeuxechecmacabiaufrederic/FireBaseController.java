package com.example.projetjeuxechecmacabiaufrederic;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FireBaseController {
    static MyDataBase hostDataBase = null;
    static DatabaseReference partyListReference = null;
    static DatabaseReference partyHostStatusReference = null;
    static FirebaseDatabase database;
    static DatabaseReference host;
    static DatabaseReference host_piece_color;
    static DatabaseReference Player_piece_color;
    static DatabaseReference host_status;
    static DatabaseReference players_status;
    static Context context;
    public static void initControler(Context initContext){
        context=initContext;
        database = FirebaseDatabase.getInstance();
        partyListReference = database.getReference("partyList");
        hostDataBase = new MyDataBase(context);
    }

    public static void addNewParty(){
        partyListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int nParty = dataSnapshot.getValue(int.class);
                hostDataBase.insertData(MyDataBase.COL1,nParty+"");
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });
        int partyNumber=parseInt(hostDataBase.readData(MyDataBase.COL1));
        partyNumber++;
        partyListReference.setValue(partyNumber);
    }

    public static void addHost(String hostName, String HostPieceColor, String PlayerPieceColor){
        Log.d("HostName",hostName);
        host = database.getReference(hostName);
        host_piece_color = database.getReference(hostName+"HostPieceColor");
        host_piece_color.setValue(HostPieceColor);
        Player_piece_color = database.getReference(hostName+"PlayerPieceColor");
        Player_piece_color.setValue(PlayerPieceColor);
        host_status = database.getReference(hostName+"status");
        host_status.setValue("Waiting_Players");
        players_status = database.getReference(hostName+"players");
        players_status.setValue(1);
        hostDataBase.insertData(MyDataBase.COL3,"1");
        Log.d("Serveure","server clock");
        Thread DatabaseThread;
        new Thread(new Runnable() {
            public void run() {
                final Runnable readDatabase = new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Serveure",hostDataBase.readData(MyDataBase.COL2));
                        if((parseInt(hostDataBase.readData(MyDataBase.COL3)))<=1){
                            players_status.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // This method is called once with the initial value and again
                                    // whenever data at this location is updated.
                                    int nbPlayers = dataSnapshot.getValue(int.class);
                                    hostDataBase.insertData(MyDataBase.COL3,nbPlayers+"");
                                }
                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    Log.w("APPX", "Failed to read value.", error.toException());
                                }
                            });
                        }else{
                            if(hostDataBase.readData(MyDataBase.COL2)=="Waiting_Players"){
                                hostDataBase.insertData(MyDataBase.COL3,"Ready_To_Play");
                                host_status.setValue("Ready_To_Play");
                            }
                        }
                        Log.d("Serveure",hostDataBase.readData(MyDataBase.COL2));
                    }
                };
                final ScheduledExecutorService firebaseExecutor = Executors.newSingleThreadScheduledExecutor();
                firebaseExecutor.scheduleAtFixedRate(readDatabase, 0, 1, TimeUnit.SECONDS);
            }
        }).start();
    }

    public static void connectToAnHost(String hostName){
        players_status = database.getReference(hostName+"players");
        players_status.setValue(2);
    }

    public static int getPartyList(){
        partyListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int nParty = dataSnapshot.getValue(int.class);
                hostDataBase.insertData(MyDataBase.COL1,nParty+"");
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });
        Log.d("Mes_couilles_en_ski",hostDataBase.readData(MyDataBase.COL1));
        int returned = parseInt(hostDataBase.readData(MyDataBase.COL1));
        return returned;
    }
    public static int getFreeHost(int iParty){
        int iHost;
        for(iHost=1; hostDataBase.readData(MyDataBase.COL2)!="Waiting_Players"; iHost++) {
            partyHostStatusReference = database.getReference("host"+iHost+"status");
            partyHostStatusReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String text = dataSnapshot.getValue(String.class);
                    hostDataBase.insertData(MyDataBase.COL2,text);
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("APPX", "Failed to read value.", error.toException());
                }
            });
        }
        return iHost;
    }
    public static int getPlayerSatus(){
        return parseInt(hostDataBase.readData(MyDataBase.COL3));
    }
    public static String getHostSatus(){
        return hostDataBase.readData(MyDataBase.COL2);
    }

}
