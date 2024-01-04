package com.example.projetjeuxechecmacabiaufrederic;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class clockSystem {
    private GameActivity.GameActivityCallBack gameActivityCallBack;
    static int digit;
    public void initClock(GameActivity.GameActivityCallBack initGameActivityCallBack){
        gameActivityCallBack=initGameActivityCallBack;
        new Thread(new Runnable() {
            public void run() {
                final Runnable timers = new Runnable() {
                    @Override
                    public void run() {
                        digit++;
                        gameActivityCallBack.timerCall();
                    }
                };
                final ScheduledExecutorService timerExecutor = Executors.newSingleThreadScheduledExecutor();
                timerExecutor.scheduleAtFixedRate(timers, 0, 1, TimeUnit.SECONDS);
            }
        }).start();

    }
}
