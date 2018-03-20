package com.rigobertosl.nevergiveapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Creamos esta clase para establecer el esquemas de las tablas
 */
public class DataBaseContract {
    private DataBaseContract() {}

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "dbNeverGiveApp.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    /*Establecemos contenido de las tablas*/
    public static class DataBaseEntryTrain implements BaseColumns {
        public static final String TABLE_NAME = "tabla_ejercicios";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DAYS = "days";
        public static final String COLUMN_SERIES = "series";
        public static final String COLUMN_REPETICIONES = "repeticiones";
        public static final String COLUMN_DESCANSO = "descanso";

        private static final String SQL_CREATE_ENTRIES_TRAIN =
                "CREATE TABLE " + DataBaseContract.DataBaseEntryTrain.TABLE_NAME + " (" +
                        DataBaseContract.DataBaseEntryTrain._ID + " INTEGER PRIMARY KEY," +
                        DataBaseContract.DataBaseEntryTrain.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                        DataBaseContract.DataBaseEntryTrain.COLUMN_DAYS + TEXT_TYPE + COMMA_SEP +
                        DataBaseContract.DataBaseEntryTrain.COLUMN_SERIES + TEXT_TYPE + COMMA_SEP +
                        DataBaseContract.DataBaseEntryTrain.COLUMN_REPETICIONES + TEXT_TYPE + COMMA_SEP +
                        DataBaseContract.DataBaseEntryTrain.COLUMN_DESCANSO + TEXT_TYPE + " )";

        private static final String SQL_DELETE_ENTRIES_TRAIN =
                "DROP TABLE IF EXISTS " + DataBaseContract.DataBaseEntryTrain.TABLE_NAME;
    }

    public static class DataBaseEntryFoods implements BaseColumns {
        public static final String TABLE_NAME = "tabla_comidas";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DAYS = "days";

        private static final String SQL_CREATE_ENTRIES_FOODS =
                "CREATE TABLE " + DataBaseContract.DataBaseEntryFoods.TABLE_NAME + " (" +
                        DataBaseContract.DataBaseEntryFoods._ID + " INTEGER PRIMARY KEY," +
                        DataBaseContract.DataBaseEntryFoods.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                        DataBaseContract.DataBaseEntryFoods.COLUMN_DAYS + TEXT_TYPE + " )";

        private static final String SQL_DELETE_ENTRIES_FOODS =
                "DROP TABLE IF EXISTS " + DataBaseContract.DataBaseEntryFoods.TABLE_NAME;
    }

    private DataBaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    public static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            //aquí creamos la tabla de ejercicios utilizando lo anterior
            db.execSQL(DataBaseEntryTrain.SQL_CREATE_ENTRIES_TRAIN);
            db.execSQL(DataBaseEntryFoods.SQL_CREATE_ENTRIES_FOODS);
        }

        public void onUpgrade(SQLiteDatabase db, int version1, int version2) {
            db.execSQL(DataBaseEntryTrain.SQL_DELETE_ENTRIES_TRAIN);
            db.execSQL(DataBaseEntryFoods.SQL_DELETE_ENTRIES_FOODS);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
