package com.example.projetjeuxechecs2024;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetjeuxechecs2024.container.container;
import com.example.projetjeuxechecs2024.objects.Cases;
import com.example.projetjeuxechecs2024.objects.Pieces;
import com.example.projetjeuxechecs2024.utility.FireBaseController;
import com.example.projetjeuxechecs2024.utility.clockSystem;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity{
    public interface GameActivityCallBack{
        void timerCall();
    }
    static clockSystem clock = new clockSystem(1000);
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
            {0,1,2,3,4,2,1,0},
            {5,5,5,5,5,5,5,5}
    };
    String[] piecesNames={
            "tour","cavalier","fou","reine","roi","pion",
    };
    int[] piecesApparences={
            R.drawable.black_tour,
            R.drawable.black_cavalier,
            R.drawable.black_fou,
            R.drawable.black_reine,
            R.drawable.black_roi,
            R.drawable.black_pion,
    };
    String[][] piecesMovements={
            {"TOP","BOTTOM","RIGHT","LEFT", null, null, null, null},
            {null, null, null, null,"RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {null, null, null, null,"RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {"TOP","BOTTOM","RIGHT","LEFT","RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {"TOP","BOTTOM","RIGHT","LEFT","RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {"TOP", null, null, null, null, null, null, null}
    };
    int[] piecesMinimalMove={1,2,1,1,1,1};
    int[] piecesMaximalMove={-1,2,-1,-1,1,2};
    String[][] piecesCaptures={
            {"TOP","BOTTOM","RIGHT","LEFT", null, null, null, null},
            {null, null, null, null,"RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {null, null, null, null,"RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {"TOP","BOTTOM","RIGHT","LEFT","RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {"TOP","BOTTOM","RIGHT","LEFT","RIGHT TOP","LEFT TOP","RIGHT BOTTOM","LEFT BOTTOM"},
            {null, null, null, null,"RIGHT TOP","LEFT TOP", null, null}
    };
    int[] piecesMinimalCapture={1,2,1,1,1,1};
    int[] piecesMaximalCapture={-1,2,-1,-1,1,1};

    static FireBaseController fireBaseController;

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
        startParty[0] = TRUE;

        clockSystem clock = new clockSystem(1000);
        clock.initGameCallBAck(new GameActivityCallBack() {
            @Override
            public void timerCall() {
                Log.v("player status",fireBaseController.getHostSatus());
                if (fireBaseController.getHostSatus() == "Waiting_Players") {
                    startParty[0] = TRUE;
                }
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
        new Thread(new Runnable() {
            container piecesContainer = new container();
            public void run() {
                Log.d("container positions cases","test");
                for(int i=0; i<2; i++){
                    for(int j=0; j<8; j++){
                        String name=piecesNames[piecesPlacement[i][j]];
                        int initApparence=piecesApparences[piecesPlacement[i][j]];
                        String[] movements=piecesMovements[piecesPlacement[i][j]];
                        int initPosX=j;
                        int initPosY=i;
                        int initMinimalMove=piecesMinimalMove[piecesPlacement[i][j]];
                        int initMaximalMove=piecesMaximalMove[piecesPlacement[i][j]];
                        String[] captures=piecesCaptures[piecesPlacement[i][j]];
                        int initMinimalCapture=piecesMinimalCapture[piecesPlacement[i][j]];
                        int initMaximalCapture=piecesMaximalCapture[piecesPlacement[i][j]];
                        Pieces newPiece=new Pieces(
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
                        piecesContainer.containerPushBack(newPiece);
                        Pieces tailPiece=piecesContainer.ContainerGetTail();
                        int posY=tailPiece.GetPosY();
                        int posX=tailPiece.GetPosX();
                        echiquier[posY][posX].setApparence(piecesApparences[piecesPlacement[posY][posX]]);
                        echiquier[posY][posX].setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                float x,y;
                                int [][] movePositions= {
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                };
                                /*for(int n=0; n<16; n++){
                                    Pieces thisPiece=piecesContainer.ContainerParse(n);
                                    if((thisPiece.GetPosX()==posX)&(thisPiece.GetPosY()==posY)){
                                        movePositions=thisPiece.GetMovePositions();
                                    }
                                }*/
                                x=v.getX();
                                y=v.getY();
                                for (int i = 0; i < 8; i++) {
                                    String line="(";
                                    for (int j = 0; j < 8; j++) {
                                        line=line+movePositions[j][i]+"";
                                        if(movePositions[j][i]==1){
                                            echiquier[j][i].defColor(casesColorGreen);
                                        }else if(movePositions[j][i]==2){
                                            echiquier[j][i].defColor(casesColorPieceSelected);
                                        }

                                    }
                                    Log.d("lines",line+")");
                                }
                                return false;
                            }
                        });
                    }
                }
                Log.d("container positions cases","test");
                Pieces head=piecesContainer.ContainerGetHead();
                Pieces tail=piecesContainer.ContainerGetTail();
                String message = "head"+head.GetPosX()+";"+head.GetPosY()+"; tail"+tail.GetPosX()+";"+tail.GetPosY();
                Log.d("container positions cases",message);
                for(int n=0; n<8; n++){
                   Pieces elem=piecesContainer.ContainerParse(n);
                   int x=elem.GetPosX();
                   int y=elem.GetPosY();
                   String thismessage=""+x+";"+y;
                    Log.d("text positions cases elem",thismessage);
                }
                //container debug
                container textContainer = new container();
                for(int n=0; n<8; n++){
                    String var=n+"";
                    textContainer.containerPushBack(var);
                }
                String var3="";
                var3=textContainer.ContainerGetTail();
                Log.d("text conteneur tail",var3);
                var3=textContainer.ContainerGetHead();
                Log.d("text conteneur head",var3);
                for(int n=0; n<8; n++){
                    var3=textContainer.ContainerParse(n);
                    Log.d("text conteneur elem",var3);
                }
            }
        }).start();
    }
    protected Context getContext(){
        return getApplicationContext();
    }

    static public void setHost(String hostName, Context context, clockSystem initClock){
        clock=initClock;
        fireBaseController = new FireBaseController(context);
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

        new Thread(new Runnable() {
            public void run() {
                fireBaseController.addHost(hostName, finalPiecesColor[0], finalPiecesColor[1]);
            }
        }).start();
    }

    static public void connectToAnHost(String hostName, Context context, clockSystem initClock){
        clock=initClock;
        fireBaseController = new FireBaseController(context);
    }
}
