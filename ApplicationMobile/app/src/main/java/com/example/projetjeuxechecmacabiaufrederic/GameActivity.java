package com.example.projetjeuxechecmacabiaufrederic;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Integer.parseInt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import android.view.MotionEvent;

public class GameActivity extends AppCompatActivity {
    public interface GameActivityCallBack{
        void timerCall();
    }
    private static final int[] casesColor1 = {255, 67, 47, 20};
    private static final int[] casesColor2 = {255, 232, 220, 202};
    private static final int[] casesColorGreen = {255, 9, 106, 9};
    private static final int[] casesColorRed = {255, 240, 0, 32};
    private static final int[] casesColorPieceSelected = {255, 183, 181, 181};

    private final Cases[][] echiquier = new Cases[8][8];
    private FrameLayout[][] touchCases = new FrameLayout[8][8];

    private static final boolean [] startParty = {FALSE};

    private final int [] timer = {0,0,0};
    private final String [] timerPrinter = {"00","00","00"};

    private static final boolean [] startCheck = {FALSE};

    private static final int[] checkTime = {0,20,0};

    int piecesNumber=16;
    int[][] piecesPlacement={
            {1,2,3,4,5,3,2,1},
            {6,6,6,6,6,6,6,6}
    };
    String[] piecesNames={
            "tour","cavalier","fou","reine","roi","pion",
    };
    int[] piecesApparences={
            0,
            0,
            0,
            0,
            0,
            R.drawable.black_pion
    };
    String[][] piecesMovements={
            {"TOP","BOTTOM","RIGHT","LEFT", null, null, null, null},
            {null, null, null, null,"RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {null, null, null, null,"RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {"TOP","BOTTOM","RIGHT","RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {"TOP","BOTTOM","RIGHT","RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {"TOP", null, null, null, null, null, null, null, null}
    };
    int[] piecesMinimalMove={1,2,1,1,1,1};
    int[] piecesMaximalMove={-1,2,-1,-1,1,2};
    String[][] piecesCaptures={
            {"TOP","BOTTOM","RIGHT","LEFT", null, null, null, null},
            {null, null, null, null,"RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {null, null, null, null,"RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {"TOP","BOTTOM","RIGHT","RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {"TOP","BOTTOM","RIGHT","RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {null, null, null, null,"RIGHT TOP","LEFT TOP", null, null}
    };
    int[] piecesMinimalCapture={1,2,1,1,1,1};
    int[] piecesMaximalCapture={-1,2,-1,-1,1,1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        TextView gameDuration = findViewById(R.id.GameDuration);
        TextView timeRemainingToPlay = findViewById(R.id.timeRemainingToPlay);
        clockSystem clock = new clockSystem(new GameActivityCallBack() {
            @Override
            public void timerCall() {
                if(startParty[0]){
                    if(timer[0]==60){
                        timer[0]=0;
                        timer[1]++;
                    }
                    if(timer[1]==60) {
                        timer[1]=0;
                        timer[2]++;
                    }
                    for(int i=0; i<3; i++){
                        if(timer[i]<10){
                            timerPrinter[i]="0"+timer[i];
                        }else{
                            timerPrinter[i]=""+timer[i];
                        }
                    }
                    gameDuration.setText(timerPrinter[2]+":"+timerPrinter[1] +":"+ timerPrinter[0]);
                    timer[0]++;
                }
                if(startCheck[0] && (checkTime[0]>=0 || checkTime[1]>=0)){
                    if(checkTime[0]==-1){
                        checkTime[1]--;
                        checkTime[0]+=60;
                    }
                    for(int i=0; i<3; i++){
                        if(timer[i]<10){
                            timerPrinter[i]="0"+checkTime[i];
                        }else{
                            timerPrinter[i]=""+checkTime[i];
                        }
                    }
                    timeRemainingToPlay.setText(timerPrinter[1] +":"+ timerPrinter[0]);
                    checkTime[0]--;
                }
            }
        });
        clock.initClock();
        new Thread(new Runnable() {
            public void run() {
                Pieces[] myPieces={};
                /*for(int i=0; i<2; i++){
                    for(int j=0; j<8; j++){
                        String name=piecesNames[piecesPlacement[j][i]];
                        int initApparence=piecesApparences[piecesPlacement[j][i]];
                        String[] movements=piecesMovements[piecesPlacement[j][i]];
                        int initPosX=i;
                        int initPosY=j;
                        int initMinimalMove=piecesMinimalMove[piecesPlacement[j][i]];
                        int initMaximalMove=piecesMaximalMove[piecesPlacement[j][i]];
                        String[] captures=piecesCaptures[piecesPlacement[j][i]];
                        int initMinimalCapture=piecesMinimalCapture[piecesPlacement[j][i]];
                        int initMaximalCapture=piecesMaximalCapture[piecesPlacement[j][i]];
                        Pieces myPiece=new Pieces(
                                name,
                                initApparence,
                                "North",
                                initPosX, initPosY,
                                movements,
                                initMinimalMove,
                                initMaximalMove,
                                captures,
                                initMinimalCapture,
                                initMaximalCapture
                        );
                        int piecePosX=myPiece.GetPosX();
                        int piecePosY=myPiece.GetPosY();
                        echiquier[piecePosY][piecePosX].setApparence(myPiece.GetApparence());
                        echiquier[piecePosY][piecePosX].setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                int [][] movePositions=myPiece.GetMovePositions();
                                for (int i = 0; i < 8; i++) {
                                    for (int j = 0; j < 8; j++) {
                                        if(movePositions[j][i]==1){
                                            echiquier[j][i].defColor(casesColorGreen);
                                        }else if(movePositions[j][i]==2){
                                            echiquier[j][i].defColor(casesColorPieceSelected);
                                        }
                                    }
                                }
                                return false;
                            }
                        });
                    }
                }*/
                String name="pion";
                String[] movements={"TOP", null, null, null, null, null, null, null, null};
                String[] captures={"RIGHT TOP","LEFT TOP", null, null, null, null, null, null, null};
                int initPosX=0;
                int initPosY=1;
                int initMinimalMove=1;
                int initMaximalMove=2;
                int initMinimalCapture=1;
                int initMaximalCapture=1;
                Pieces myPiece=new Pieces(
                        name,
                        R.drawable.black_pion,
                        "North",
                        initPosX, initPosY,
                        movements,
                        initMinimalMove,
                        initMaximalMove,
                        captures,
                        initMinimalCapture,
                        initMaximalCapture
                );
                int piecePosX=myPiece.GetPosX();
                int piecePosY=myPiece.GetPosY();
                echiquier[piecePosY][piecePosX].setApparence(myPiece.GetApparence());
                echiquier[piecePosY][piecePosX].setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int [][] movePositions=myPiece.GetMovePositions();
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                if(movePositions[j][i]==1){
                                    echiquier[j][i].defColor(casesColorGreen);
                                }else if(movePositions[j][i]==2){
                                    echiquier[j][i].defColor(casesColorPieceSelected);
                                }
                            }
                        }
                        return false;
                    }
                });
            }

        }).start();
    }
    protected Context getContext(){
        return getApplicationContext();
    }
    protected void downloadImage(String url, ImageView imagePiece, RequestQueue queue, String name, String Object){
        new Thread(new Runnable(){
            public void run(){
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jObj = new JSONObject(response);
                                    JSONObject jObjCurrent = jObj.getJSONObject(name);
                                    String icon = jObjCurrent.getString(Object);
                                    Picasso.get().load(icon).into(imagePiece);
                                }catch(JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("comment","didn't work!");
                    }
                });
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        }).start();
    }
    static public void setHost(String hostName, Context context){
        FireBaseController fireBaseController = new FireBaseController(context);
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        String[] piecesColor={null,null};
        if(Math.random()<0.5){
            piecesColor[0]="white";
            piecesColor[1]="black";
        }else{
            piecesColor[0]="black";
            piecesColor[1]="white";
        }
        String[] finalPiecesColor = piecesColor;
        MyDataBase partyDatabase= new MyDataBase(context);

        new Thread(new Runnable() {
            public void run() {
                //DatabaseReference host = database.getReference(hostName);
                fireBaseController.addHost(hostName, finalPiecesColor[0], finalPiecesColor[1]);
                final Runnable readDatabase = new Runnable() {
                    @Override
                    public void run() {
                        int nPlayers = fireBaseController.getPlayerSatus();
                        if (fireBaseController.getPlayerSatus() <= 1) {

                        } else {
                            if (fireBaseController.getHostSatus() == "Waiting_Players") {
                                startParty[0] = TRUE;
                            }

                        }
                    }
                };
                final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.scheduleAtFixedRate(readDatabase, 0, 200, TimeUnit.MILLISECONDS);
            }
        }).start();
    }

    static public void connectToAnHost(String hostName, Context context){
        FireBaseController fireBaseController = new FireBaseController(context);
        new Thread(new Runnable() {
            public void run() {
                fireBaseController.connectToAnHost(hostName);
                if(startCheck[0]){

                }else{
                    final Runnable readDatabase = new Runnable() {
                        @Override
                        public void run() {

                        }
                    };
                    final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                    executor.scheduleAtFixedRate(readDatabase, 0, 200, TimeUnit.MILLISECONDS);
                }
            }
        }).start();
    }
}
