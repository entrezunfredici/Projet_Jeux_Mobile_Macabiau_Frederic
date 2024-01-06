package com.example.projetjeuxechecmacabiaufrederic;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class clockSystem {
    private GameActivity.GameActivityCallBack gameActivityCallBack=null;
    private FireBaseController.FireBaseCallBack fireBaseCallBack=null;
    public clockSystem(GameActivity.GameActivityCallBack initGameActivityCallBack) {
        super();
        this.gameActivityCallBack=initGameActivityCallBack;
    }
    public clockSystem(FireBaseController.FireBaseCallBack initfireBaseCallBack) {
        super();
        this.fireBaseCallBack=initfireBaseCallBack;
    }
    static int digit;
    //public void initClock(GameActivity.GameActivityCallBack initGameActivityCallBack){
    public void initClock(){
        //gameActivityCallBack=initGameActivityCallBack;
        new Thread(new Runnable() {
            public void run() {
                final Runnable timers = new Runnable() {
                    @Override
                    public void run() {
                        digit++;
                        if(gameActivityCallBack!=null){
                            gameActivityCallBack.timerCall();
                        }
                        if(fireBaseCallBack!=null){
                            fireBaseCallBack.timerCall();
                        }
                    }
                };
                final ScheduledExecutorService timerExecutor = Executors.newSingleThreadScheduledExecutor();
                timerExecutor.scheduleAtFixedRate(timers, 0, 1, TimeUnit.SECONDS);
            }
        }).start();

    }
}
