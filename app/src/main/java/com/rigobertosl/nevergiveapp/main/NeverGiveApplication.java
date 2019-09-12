package com.rigobertosl.nevergiveapp.main;

import android.app.Application;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.distribute.Distribute;

public class NeverGiveApplication extends Application {

    @Override
    public void onCreate() {

        AppCenter.start(this, "{cbfe040e-2a15-4f9d-ab42-b110a9c59eb8}", Distribute.class);
        super.onCreate();
    }
}
