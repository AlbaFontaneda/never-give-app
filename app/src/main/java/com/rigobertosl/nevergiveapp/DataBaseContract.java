package com.rigobertosl.nevergiveapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.sql.RowId;

/**
 * Creamos esta clase para establecer el esquemas de las tablas
 */
public class DataBaseContract {
    public DataBaseContract(Context context) {
        this.context = context;
    }

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

    private final Context context;

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

    public DataBaseContract open() throws SQLException {
        mDbHelper = new DataBaseHelper(context);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    /** Crear tabla_ejercicos en la base de datos **/
    public long createTableTraining(String name, String days){
        ContentValues values = new ContentValues();
        values.put(DataBaseEntryTrain.COLUMN_NAME, name);
        values.put(DataBaseEntryTrain.COLUMN_DAYS, name);

        return mDb.insert(DataBaseEntryTrain.TABLE_NAME, null, values);
    }

    /** Crear tabla_comidas en la base de datos **/
    public long createTableFoods(String name, String days){
        ContentValues values = new ContentValues();
        values.put(DataBaseEntryFoods.COLUMN_NAME, name);
        values.put(DataBaseEntryFoods.COLUMN_DAYS, name);

        return mDb.insert(DataBaseEntryFoods.TABLE_NAME, null, values);
    }

    //TODO: Cuando se implementen las opciones, crear funciones para eliminar tablas

    /** Devolver todas las filas de la tabla_ejercicios **/
    //TODO: Devolver todos los datos del entrenamiento
    public Cursor fetchAllRowsTraining() {
        return mDb.query(DataBaseEntryTrain.TABLE_NAME, new String[] {
                        DataBaseEntryTrain._ID, DataBaseEntryTrain.COLUMN_NAME, DataBaseEntryTrain.COLUMN_DAYS},
                null, null, null, null, null);
    }

    /** Devolver una unica fila de la tabla_ejercicios **/
    public Cursor fetchRowTraining(long rowId) throws SQLException {
        Cursor mCursor = mDb.query(true, DATABASE_NAME, new String[] {
                        DataBaseEntryTrain._ID, DataBaseEntryTrain.COLUMN_NAME, DataBaseEntryTrain.COLUMN_DAYS},
                DataBaseEntryTrain._ID + "=" + rowId, null, null, null, null, null);
        if(mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /** Actualizar la ultima fila de la tabla_ejercicios **/
    public boolean updateLastRowTraining(long rowId, String series, String repeticiones, String descanso) {
        ContentValues values = new ContentValues();
        values.put(DataBaseEntryTrain.COLUMN_SERIES, series);
        values.put(DataBaseEntryTrain.COLUMN_REPETICIONES, repeticiones);
        values.put(DataBaseEntryTrain.COLUMN_SERIES, descanso);

        return mDb.update(DataBaseEntryTrain.TABLE_NAME, values, DataBaseEntryTrain._ID + "=" + rowId, null) > 0;
    }

    /** Devolver todas las filas de la tabla_comidas **/
    public Cursor fetchAllRowsFoods() {
        return mDb.query(DATABASE_NAME, new String[] {
                        DataBaseEntryFoods._ID, DataBaseEntryFoods.COLUMN_NAME, DataBaseEntryFoods.COLUMN_DAYS},
                null, null, null, null, null);
    }

    /** Devolver una unica fila de la tabla_comidas **/
    public Cursor fetchRowFoods(long rowId) throws SQLException {
        Cursor mCursor = mDb.query(true, DATABASE_NAME, new String[] {
                        DataBaseEntryFoods._ID, DataBaseEntryFoods.COLUMN_NAME, DataBaseEntryFoods.COLUMN_DAYS},
                DataBaseEntryFoods._ID + "=" + rowId, null, null, null, null, null);
        if(mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

   //TODO: Actualizar tablas_comidas


}
