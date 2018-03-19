package com.rigobertosl.nevergiveapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "dbNeverGiveApp.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    /** Metodos para crear o eliminar entradas de las tablas **/
    private static final String SQL_CREATE_ENTRIES_TRAIN =
            "CREATE TABLE " + DataBaseContract.DataBaseEntryTrain.TABLE_NAME + " (" +
                    DataBaseContract.DataBaseEntryTrain._ID + " INTEGER PRIMARY KEY," +
                    DataBaseContract.DataBaseEntryTrain.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.DataBaseEntryTrain.COLUMN_DAYS + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.DataBaseEntryTrain.COLUMN_SERIES + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.DataBaseEntryTrain.COLUMN_REPETICIONES + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.DataBaseEntryTrain.COLUMN_DESCANSO + TEXT_TYPE + " )";
    private static final String SQL_CREATE_ENTRIES_FOODS =
            "CREATE TABLE " + DataBaseContract.DataBaseEntryFoods.TABLE_NAME + " (" +
                    DataBaseContract.DataBaseEntryFoods._ID + " INTEGER PRIMARY KEY," +
                    DataBaseContract.DataBaseEntryFoods.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    DataBaseContract.DataBaseEntryFoods.COLUMN_DAYS + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES_TRAIN =
            "DROP TABLE IF EXISTS " + DataBaseContract.DataBaseEntryTrain.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES_FOODS =
            "DROP TABLE IF EXISTS " + DataBaseContract.DataBaseEntryFoods.TABLE_NAME;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //aquí creamos la tabla de ejercicios utilizando lo anterior
        db.execSQL(SQL_CREATE_ENTRIES_TRAIN);
        db.execSQL(SQL_CREATE_ENTRIES_FOODS);
    }

    public void onUpgrade(SQLiteDatabase db, int version1, int version2) {
        db.execSQL(SQL_DELETE_ENTRIES_TRAIN);
        db.execSQL(SQL_DELETE_ENTRIES_FOODS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
