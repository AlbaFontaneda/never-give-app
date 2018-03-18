package com.rigobertosl.nevergiveapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "dbNeverGiveApp.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String COMMA_SEP = ",";

    /** Metodos para crear o eliminar entradas de las tablas **/
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DataBaseContract.DataBaseEntry.TABLE_NAME + " (" +
                    DataBaseContract.DataBaseEntry._ID + " INTEGER PRIMARY KEY," +
                    DataBaseContract.DataBaseEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.DataBaseEntry.COLUMN_DAYS + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.DataBaseEntry.COLUMN_SERIES + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.DataBaseEntry.COLUMN_REPETICIONES + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.DataBaseEntry.COLUMN_DESCANSO + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DataBaseContract.DataBaseEntry.TABLE_NAME;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //aquí creamos la tabla de ejercicios utilizando lo anterior
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int version1, int version2) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
