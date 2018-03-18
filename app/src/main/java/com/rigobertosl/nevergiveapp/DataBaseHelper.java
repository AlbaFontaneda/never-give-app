package com.rigobertosl.nevergiveapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dataBase.db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //aquí creamos la tabla de ejercicios (nombre, dias)
        db.execSQL("CREATE TABLE tabla_ejercicios" +
                "(dias text," +
                "nombre text)");
    }

    public void onUpgrade(SQLiteDatabase db, int version1, int version2) {

        db.execSQL("drop table if exists tabla_ejercicios");
        db.execSQL("CREATE TABLE tabla_ejercicios" +
                "(dias text," +
                "nombre text)");
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
