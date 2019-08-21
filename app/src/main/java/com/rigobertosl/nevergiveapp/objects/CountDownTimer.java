package com.rigobertosl.nevergiveapp.objects;

import android.os.Handler;

/**
 * Código cogido de StackOverflow.
 * Autor: Ocanal
 * Answered Jan 13 '12 at 23:24
 * Edited Mar 27 '12 at 12:47
 * Consultado a día 10/04/2018
 * Link: https://stackoverflow.com/questions/8857590/android-countdowntimer-skips-last-ontick/8858608
**/

public class CountDownTimer {

    private long START_TIMER;
    private long millisInFuture;
    private long countDownInterval;
    private boolean status, reset;

    public CountDownTimer(long pMillisInFuture, long pCountDownInterval) {
        this.START_TIMER = pMillisInFuture;
        this.millisInFuture = pMillisInFuture;
        this.countDownInterval = pCountDownInterval;
        status = false;
        reset = false;
        Initialize();
    }

    public void pause() {
        status = false;
    }

    public void reset(){
        reset = true;
    }

    public long getCurrentTime() {
        return millisInFuture;
    }

    public void start() {
        status = true;
        reset = false;
    }
    public void Initialize() {
        final Handler handler = new Handler();
        final Runnable counter = new Runnable(){

            public void run(){
                if (reset){
                    millisInFuture = START_TIMER;
                    handler.postDelayed(this, countDownInterval);
                }else{
                    if(status) {
                        if(millisInFuture <= 0) {
                            millisInFuture = START_TIMER;
                            handler.postDelayed(this, countDownInterval);
                            status = false;
                        } else {
                            millisInFuture -= countDownInterval;
                            handler.postDelayed(this, countDownInterval);
                        }
                    } else {
                        if (reset){
                            millisInFuture = START_TIMER;
                        }
                        handler.postDelayed(this, countDownInterval);
                    }
                }
            }
        };
        handler.postDelayed(counter, countDownInterval);
    }
}
