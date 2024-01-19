package com.example.projetjeuxechecs2024.utility;

import android.util.Log;

import com.example.projetjeuxechecs2024.GameActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class clockSystem {
    private GameActivity.GameActivityCallBack gameActivityCallBack=null;
    private FireBaseController.FireBaseCallBack fireBaseCallBack=null;
    public clockSystem(int delay) {
        super();
        new Thread(new Runnable() {
            public void run() {
                final Runnable timers = new Runnable() {
                    @Override
                    public void run() {
                        Log.d("surcharge du timer", "je suis le timer");
                        if(gameActivityCallBack!=null){
                            gameActivityCallBack.timerCall();
                        }
                        if(fireBaseCallBack!=null){
                            fireBaseCallBack.timerCall();
                        }
                    }
                };
                final ScheduledExecutorService timerExecutor = Executors.newSingleThreadScheduledExecutor();
                timerExecutor.scheduleAtFixedRate(timers, 0, delay, TimeUnit.MILLISECONDS);
            }
        }).start();
    }
    public void initGameCallBAck(GameActivity.GameActivityCallBack initGameActivityCallBack) {
        gameActivityCallBack=initGameActivityCallBack;
    }
    public void initFireBaseCallBack(FireBaseController.FireBaseCallBack initfireBaseCallBack) {
        fireBaseCallBack=initfireBaseCallBack;
    }
}
