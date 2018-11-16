package com.example.hoon.borisdiary;

import android.app.Application;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

public class DiaryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));

    }
}
