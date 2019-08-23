package com.rigobertosl.nevergiveapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.rigobertosl.nevergiveapp.firedatabase.AppFiredatabase;

public class SplashActivity  extends AppFiredatabase {

    private ObjectAnimator fadeIn,lftToRgt ;
    private ImageView logoApp, nameApp;
    private  AnimatorSet animatorSet;
    private int width;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        logoApp = (ImageView)this.findViewById(R.id.logoApp);
        nameApp = (ImageView)this.findViewById(R.id.nameApp);
        animatorSet = new AnimatorSet();

        Display display = getWindowManager().getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);
        width = point.x; // screen width

        animateScreen();
    }

    private void animateScreen() {
        fadeIn = ObjectAnimator.ofFloat(logoApp, "alpha", 0, 1f);
        fadeIn.setDuration(1500);

        lftToRgt = ObjectAnimator.ofFloat(nameApp,"translationX",width,0f )
                .setDuration(1500); // to animate right to left;

        lftToRgt.start();
        lftToRgt.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                logoApp.setVisibility(View.VISIBLE);
                fadeIn.start();
            }
        });

        fadeIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(autoLogin()){
                    startNewActivity(SplashActivity.this, MainActivity.class);
                }else{
                    startNewActivity(SplashActivity.this, LoginActivity.class);
                }
            }
        });

    }
}
