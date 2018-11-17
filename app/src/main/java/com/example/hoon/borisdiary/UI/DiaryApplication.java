package com.example.hoon.borisdiary.UI;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.example.hoon.borisdiary.DB.DBHelper;

public class DiaryApplication extends Application {
    private static DBHelper DB;
    public static DBHelper getDB() {
        return DB;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DB = new DBHelper(getApplicationContext());
    }

    public static void DiaryDialog(Context context, String message, DialogInterface.OnClickListener nogative, DialogInterface.OnClickListener positive){
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setNegativeButton("취소", nogative)
                .setPositiveButton("확인", positive)
                .show();
    }

}
