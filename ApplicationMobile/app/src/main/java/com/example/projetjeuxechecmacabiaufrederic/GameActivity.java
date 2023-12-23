package com.example.projetjeuxechecmacabiaufrederic;

import static java.lang.Boolean.FALSE;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {
    private static final int[] casesColor1 = {255, 67, 47, 20};
    private static final int[] casesColor2 = {255, 232, 220, 202};
    private static final int[] casesColorGreen = {255, 9, 106, 9};
    private static final int[] casesColorRed = {255, 240, 0, 32};
    private Cases[][] echiquier = new Cases[8][8];
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
        MyDataBase partyDB= new MyDataBase(this);
        TextView gameDuration = findViewById(R.id.GameDuration);
        TextView timeRemainingToPlay = findViewById(R.id.timeRemainingToPlay);
        final boolean[] check={FALSE};
        final int[] checktime={30,0};
        new Thread(new Runnable() {
            public void run() {
                final int[] seconds = {0,0};
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
                        if(check[0] && (checktime[1]>0 || checktime[0]>0)){
                            if(checktime[1]==0){
                                checktime[0]--;
                                checktime[1]+=60;
                            }else{
                                checktime[1]--;
                            }
                            if(checktime[0] >9 & checktime[1] >9){
                                timeRemainingToPlay.setText(checktime[0] +":"+ checktime[1]);
                            }else if(checktime[0] >9){
                                timeRemainingToPlay.setText(checktime[0] +":0"+ seconds[0]);
                            }else if(checktime[1] >9){
                                timeRemainingToPlay.setText("0"+ checktime[0] +":"+ checktime[1]);
                            }else{
                                timeRemainingToPlay.setText("0"+ checktime[0] +":0"+ checktime[1]);
                            }
                        }
                    }
                };
                final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                final Runnable task = new Runnable() {
                    @Override
                    public void run() {

                    }
                };
                final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
            }
        }).start();
    }
}
