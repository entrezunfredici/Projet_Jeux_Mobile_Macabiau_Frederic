package com.example.projetjeuxechecs2024.utility;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    static clockSystem clock = new clockSystem(1000);
    public FireBaseController(Context initContext){
        super();
        context=initContext;
        database = FirebaseDatabase.getInstance();
        partyListReference = database.getReference("partyList");
        hostDataBase = new MyDataBase(context);
    }
    public interface FireBaseCallBack{
        void timerCall();
    }
    public static void addNewParty(){
        partyListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int nParty = dataSnapshot.getValue(int.class);
                hostDataBase.insertIntData(MyDataBase.COL1,nParty);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });
        int partyNumber=hostDataBase.readIntData(MyDataBase.COL1);
        Log.d("controlPartyNumber",partyNumber+"");
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
        hostDataBase.insertData(MyDataBase.COL2,"Waiting_Players");
        players_status = database.getReference(hostName+"players");
        players_status.setValue("1");
        hostDataBase.insertData(MyDataBase.COL3,"1");
        final String[] value = {hostDataBase.readData(MyDataBase.COL3)};
        clock.initFireBaseCallBack(new FireBaseCallBack() {
            @Override
            public void timerCall() {
                if(value[0]==null){
                    value[0]="1";
                }
                Log.d("nbJoueurs",value[0]);
                switch(value[0]){
                    case "1":
                        Log.d("nbJoueurs","si un joueur");
                        players_status.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // This method is called once with the initial value and again
                                // whenever data at this location is updated.
                                String nbPlayers = dataSnapshot.getValue(String.class);
                                hostDataBase.insertData(MyDataBase.COL3,nbPlayers);
                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Log.w("APPX", "Failed to read value.", error.toException());
                            }
                        });
                        if(hostDataBase.readData(MyDataBase.COL3)!=null){
                            value[0] = hostDataBase.readData(MyDataBase.COL3);
                        }
                        break;
                    case "2":
                        Log.d("nbJoueurs","si deux joueurs");
                        hostDataBase.insertData(MyDataBase.COL2,"Ready_To_Play");
                        host_status.setValue("Ready_To_Play");

                        break;
                    default:
                        Log.d("nbJoueurs","étrange");
                }
            }
        });
    }

    public static void connectToAnHost(String hostName){
        //clockSystem clock = new clockSystem(1000);
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
                hostDataBase.insertIntData(MyDataBase.COL1, nParty);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });
        Log.d("nbParties","n="+hostDataBase.readIntData(MyDataBase.COL1));
        return hostDataBase.readIntData(MyDataBase.COL1);
    }
    public static int getPlayerSatus(){
        return parseInt(hostDataBase.readData(MyDataBase.COL3));
    }
    public static String getHostSatus(){return hostDataBase.readData(MyDataBase.COL2);}

}
