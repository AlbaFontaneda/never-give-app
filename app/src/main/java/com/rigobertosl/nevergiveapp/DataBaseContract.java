package com.rigobertosl.nevergiveapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.lang.Long.valueOf;

/**
 * Creamos esta clase para establecer el esquemas de las tablas
 */
public class DataBaseContract {
    public DataBaseContract(Context context) {
        this.context = context;
    }

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "dbNeverGiveApp.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String LONG_TYPE = " LONG";
    private static final String COMMA_SEP = ",";

    /********************* COLUMNAS TABLAS DE ENTRENAMIENTO CUSTOMIZADAS *****************************/
    public static class DataBaseEntryNameTrain implements BaseColumns {
        public static final String TABLE_NAME = "nombre_ejercicios";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DAYS = "days";

        private static final String SQL_CREATE_ENTRIES_NAME_TRAIN =
                "CREATE TABLE " + DataBaseContract.DataBaseEntryNameTrain.TABLE_NAME + " (" +
                        DataBaseContract.DataBaseEntryNameTrain._ID + " INTEGER PRIMARY KEY," +
                        DataBaseContract.DataBaseEntryNameTrain.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                        DataBaseContract.DataBaseEntryNameTrain.COLUMN_DAYS + TEXT_TYPE + " )";

        private static final String SQL_DELETE_ENTRIES_NAME_TRAIN =
                "DROP TABLE IF EXISTS " + DataBaseContract.DataBaseEntryNameTrain.TABLE_NAME;
    }

    public static class DataBaseEntryListTrain implements BaseColumns {
        public static final String TABLE_NAME = "lista_ejercicios";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SERIES = "series";
        public static final String COLUMN_REPETICIONES = "repeticiones";
        public static final String COLUMN_DESCANSO = "descanso";


        private static final String SQL_CREATE_ENTRIES_LIST_TRAIN =
                "CREATE TABLE " + DataBaseEntryListTrain.TABLE_NAME + " (" +
                        DataBaseEntryListTrain._ID + " INTEGER PRIMARY KEY," +
                        DataBaseEntryListTrain.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryListTrain.COLUMN_SERIES + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryListTrain.COLUMN_REPETICIONES + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryListTrain.COLUMN_DESCANSO + TEXT_TYPE + " )";

        private static final String SQL_DELETE_ENTRIES_LIST_TRAIN =
                "DROP TABLE IF EXISTS " + DataBaseEntryListTrain.TABLE_NAME;
    }

    public static class DataBaseEntryTrain implements BaseColumns {
        public static final String TABLE_NAME = "tabla_ejercicios";
        public static final String COLUMN_NAME_ID = "id_name";
        public static final String COLUMN_LIST_ID = "id_list";


        private static final String SQL_CREATE_ENTRIES_TRAIN =
                "CREATE TABLE " + DataBaseEntryTrain.TABLE_NAME + " (" +
                        DataBaseEntryTrain._ID + " INTEGER PRIMARY KEY," +
                        DataBaseEntryTrain.COLUMN_NAME_ID + LONG_TYPE + COMMA_SEP +
                        DataBaseEntryTrain.COLUMN_LIST_ID + LONG_TYPE + " )";

        private static final String SQL_DELETE_ENTRIES_TRAIN =
                "DROP TABLE IF EXISTS " + DataBaseEntryTrain.TABLE_NAME;
    }

    /********************* COLUMNAS PARA TABLAS DE COMIDAS *****************************/
    public static class DataBaseEntryFoods implements BaseColumns {
        public static final String TABLE_NAME = "tabla_comidas";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DAYS = "days";
        public static final String COLUMN_TYPE_FOOD = "type";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_KCAL= "kcal";


        private static final String SQL_CREATE_ENTRIES_FOODS =
                "CREATE TABLE " + DataBaseContract.DataBaseEntryFoods.TABLE_NAME + " (" +
                        DataBaseContract.DataBaseEntryFoods._ID + " INTEGER PRIMARY KEY," +
                        DataBaseContract.DataBaseEntryFoods.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                        DataBaseContract.DataBaseEntryFoods.COLUMN_DAYS + TEXT_TYPE + COMMA_SEP +
                        DataBaseContract.DataBaseEntryFoods.COLUMN_TYPE_FOOD + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryFoods.COLUMN_KCAL + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryFoods.COLUMN_IMAGE + " BLOB" + " )";

        private static final String SQL_DELETE_ENTRIES_FOODS =
                "DROP TABLE IF EXISTS " + DataBaseContract.DataBaseEntryFoods.TABLE_NAME;
    }

    /********************* COLUMNAS PARA TABLAS DE ENTRENAMIENTO POR DEFECTO *****************************/
    public static class DataBaseDefaultTable implements BaseColumns {
        public static final String TABLE_NAME = "tabla_default";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DAYS = "days";

        private static final String SQL_CREATE_DEFAULT_TABLE =
                "CREATE TABLE " + DataBaseContract.DataBaseDefaultTable.TABLE_NAME + " (" +
                        DataBaseContract.DataBaseDefaultTable._ID + " INTEGER PRIMARY KEY," +
                        DataBaseContract.DataBaseDefaultTable.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                        DataBaseContract.DataBaseDefaultTable.COLUMN_DAYS + TEXT_TYPE + " )";

        private static final String SQL_DELETE_DEFAULT_TABLE =
                "DROP TABLE IF EXISTS " + DataBaseContract.DataBaseDefaultTable.TABLE_NAME;
    }

    public static class DataBaseDefaultExercises implements BaseColumns {
        public static final String TABLE_NAME = "ejercicios_default";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SERIES = "series";
        public static final String COLUMN_REPETICIONES = "repeticiones";
        public static final String COLUMN_DESCANSO = "descanso";


        private static final String SQL_CREATE_DEFAULT_EXERCISES =
                "CREATE TABLE " + DataBaseDefaultExercises.TABLE_NAME + " (" +
                        DataBaseDefaultExercises._ID + " INTEGER PRIMARY KEY," +
                        DataBaseDefaultExercises.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                        DataBaseDefaultExercises.COLUMN_SERIES + TEXT_TYPE + COMMA_SEP +
                        DataBaseDefaultExercises.COLUMN_REPETICIONES + TEXT_TYPE + COMMA_SEP +
                        DataBaseDefaultExercises.COLUMN_DESCANSO + TEXT_TYPE + " )";

        private static final String SQL_DELETE_DEFAULT_EXERCISES =
                "DROP TABLE IF EXISTS " + DataBaseDefaultExercises.TABLE_NAME;
    }

    public static class DataBaseDefaultLinkTable implements BaseColumns {
        public static final String TABLE_NAME = "link_default";
        public static final String COLUMN_NAME_ID = "id_name";
        public static final String COLUMN_LIST_ID = "id_list";


        private static final String SQL_CREATE_ENTRIES_DEFAULT_LINK =
                "CREATE TABLE " + DataBaseDefaultLinkTable.TABLE_NAME + " (" +
                        DataBaseDefaultLinkTable._ID + " INTEGER PRIMARY KEY," +
                        DataBaseDefaultLinkTable.COLUMN_NAME_ID + LONG_TYPE + COMMA_SEP +
                        DataBaseDefaultLinkTable.COLUMN_LIST_ID + LONG_TYPE + " )";

        private static final String SQL_DELETE_ENTRIES_DEFAULT_LINK =
                "DROP TABLE IF EXISTS " + DataBaseDefaultLinkTable.TABLE_NAME;
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
            db.execSQL(DataBaseEntryNameTrain.SQL_CREATE_ENTRIES_NAME_TRAIN);
            db.execSQL(DataBaseEntryListTrain.SQL_CREATE_ENTRIES_LIST_TRAIN);
            db.execSQL(DataBaseEntryTrain.SQL_CREATE_ENTRIES_TRAIN);
            db.execSQL(DataBaseEntryFoods.SQL_CREATE_ENTRIES_FOODS);
            db.execSQL(DataBaseDefaultTable.SQL_CREATE_DEFAULT_TABLE);
            db.execSQL(DataBaseDefaultExercises.SQL_CREATE_DEFAULT_EXERCISES);
            db.execSQL(DataBaseDefaultLinkTable.SQL_CREATE_ENTRIES_DEFAULT_LINK);
        }

        public void onUpgrade(SQLiteDatabase db, int version1, int version2) {
            db.execSQL(DataBaseEntryNameTrain.SQL_DELETE_ENTRIES_NAME_TRAIN);
            db.execSQL(DataBaseEntryListTrain.SQL_DELETE_ENTRIES_LIST_TRAIN);
            db.execSQL(DataBaseEntryTrain.SQL_DELETE_ENTRIES_TRAIN);
            db.execSQL(DataBaseEntryFoods.SQL_DELETE_ENTRIES_FOODS);
            db.execSQL(DataBaseDefaultTable.SQL_DELETE_DEFAULT_TABLE);
            db.execSQL(DataBaseDefaultExercises.SQL_DELETE_DEFAULT_EXERCISES);
            db.execSQL(DataBaseDefaultLinkTable.SQL_DELETE_ENTRIES_DEFAULT_LINK);
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

    /** Reseteo bases de datos **/
    public void resetDataBase() throws  SQLException{
        mDb.execSQL("DELETE FROM "+ DataBaseEntryNameTrain.TABLE_NAME);
        mDb.execSQL("DELETE FROM "+ DataBaseEntryListTrain.TABLE_NAME);
        mDb.execSQL("DELETE FROM "+ DataBaseEntryTrain.TABLE_NAME);
        mDb.execSQL("DELETE FROM "+ DataBaseEntryFoods.TABLE_NAME);
    }
    public void resetTrainingTables() throws  SQLException{
        mDb.execSQL("DELETE FROM "+ DataBaseEntryNameTrain.TABLE_NAME);
        mDb.execSQL("DELETE FROM "+ DataBaseEntryListTrain.TABLE_NAME);
        mDb.execSQL("DELETE FROM "+ DataBaseEntryTrain.TABLE_NAME);
    }
    public void resetFoods() throws  SQLException{
        mDb.execSQL("DELETE FROM "+ DataBaseEntryFoods.TABLE_NAME);
    }


    /********************* TABLAS DE ENTRENAMIENTO CUSTOMIZADAS *****************************/

    /** Crear nombre_ejercicos en la base de datos **/
    public TrainingTable createTableNameTraining(String name, String days){
        ContentValues values = new ContentValues();
        values.put(DataBaseEntryNameTrain.COLUMN_NAME, name);
        values.put(DataBaseEntryNameTrain.COLUMN_DAYS, days);
        mDb.insert(DataBaseEntryNameTrain.TABLE_NAME, null, values);

        String selectQuery = "SELECT * FROM " + DataBaseEntryNameTrain.TABLE_NAME;
        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        cursor.moveToLast();

        return new TrainingTable(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryNameTrain._ID))),name, days);
    }

    /** Devuelve un ArrayList con todas las tablas (nombre_ejercicios) que existen en la base de datos **/
    public ArrayList<TrainingTable> getAllTables() {
        ArrayList<TrainingTable> table = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DataBaseEntryNameTrain.TABLE_NAME;
        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                TrainingTable trainingTable = new TrainingTable(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryNameTrain._ID))),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryNameTrain.COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(DataBaseEntryNameTrain.COLUMN_DAYS)));
                table.add(trainingTable);
            } while (cursor.moveToNext());
        }
        return table;
    }

    /** Devuelve la tabla por id **/
    public TrainingTable getTrainingTableByID(long ID){
        TrainingTable trainingTable = null;

        String selectQuery = "SELECT * FROM " + DataBaseEntryNameTrain.TABLE_NAME + " WHERE " + DataBaseEntryNameTrain._ID + " = '" + ID + "'";

        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                trainingTable = new TrainingTable(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryNameTrain._ID))),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryNameTrain.COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(DataBaseEntryNameTrain.COLUMN_DAYS)));

            } while (cursor.moveToNext());
        }

        return trainingTable;
    }

    /** Devuelve TODOS los ejercicios de la tabla cuyo nombre es "nombre" en un ArrayList<Exercise> **/
    public ArrayList<Exercise> getAllExercisesFromTable(TrainingTable mTrainingTable) {
        ArrayList<Exercise> exercises = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DataBaseEntryNameTrain.TABLE_NAME + " tn, " + DataBaseEntryListTrain.TABLE_NAME
                + " tl, " + DataBaseEntryTrain.TABLE_NAME + " te WHERE tn." + DataBaseEntryNameTrain.COLUMN_NAME + " = '" + mTrainingTable.getName() + "'" +
                " AND tn." + DataBaseEntryNameTrain._ID + " = te." + DataBaseEntryTrain.COLUMN_NAME_ID +
                " AND tl." + DataBaseEntryListTrain._ID + " = te." + DataBaseEntryTrain.COLUMN_LIST_ID;

        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Exercise newExercise = new Exercise(cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_NAME)),cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_SERIES)),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_REPETICIONES)), cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_DESCANSO)));
                newExercise.setId(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryTrain.COLUMN_LIST_ID))));
                exercises.add(newExercise);
            } while (cursor.moveToNext());
        }
        return exercises;
    }

    /** Crear lista_ejercicos en la base de datos **/
    public long createTableListTraining(String name,  String series, String repeticiones, String descanso){
        ContentValues values = new ContentValues();
        values.put(DataBaseEntryListTrain.COLUMN_NAME, name);
        values.put(DataBaseEntryListTrain.COLUMN_SERIES, series);
        values.put(DataBaseEntryListTrain.COLUMN_REPETICIONES, repeticiones);
        values.put(DataBaseEntryListTrain.COLUMN_DESCANSO, descanso);

        return mDb.insert(DataBaseEntryListTrain.TABLE_NAME, null, values);
    }

    /** Crear tabla_ejercicios (asignación de varios elementos de la lista a un nombre) **/
    public long createTableTraining(long idName,  long idList){
        ContentValues values = new ContentValues();
        values.put(DataBaseEntryTrain.COLUMN_NAME_ID, idName);
        values.put(DataBaseEntryTrain.COLUMN_LIST_ID, idList);

        return mDb.insert(DataBaseEntryTrain.TABLE_NAME, null, values);
    }

    /** Delete row from nombre_ejercicios y por consiguiente todos los ejercicios asociados a la misma **/
    public void deleteTable(TrainingTable trainingTable, boolean deleteAllData) {
        mDb = mDbHelper.getWritableDatabase();
        if (deleteAllData) {

            List<Exercise> listEjercicios = getAllExercisesFromTable(trainingTable);

            for (Exercise exercise : listEjercicios) {
                deleteExercisesFromTable(exercise.getId());
            }
        }
        deleteTable(trainingTable.getId());
        deleteLinkTable(trainingTable.getId());
    }

    /** Delete ejercicios **/
    public void deleteExercisesFromTable(long ejercicioId) {
        mDb = mDbHelper.getWritableDatabase();
        mDb.delete(DataBaseEntryListTrain.TABLE_NAME, DataBaseEntryListTrain._ID + " = ?",
                new String[] { String.valueOf(ejercicioId) });
    }

    /** Delete table nombre_ejercicios **/
    public void deleteTable(long tableId) {
        mDb = mDbHelper.getWritableDatabase();
        mDb.delete(DataBaseEntryNameTrain.TABLE_NAME, DataBaseEntryNameTrain._ID + " = ?",
                new String[] { String.valueOf(tableId) });
    }

    /** Delete link table **/
    public void deleteLinkTable(long tableId) {
        mDb = mDbHelper.getWritableDatabase();
        mDb.delete(DataBaseEntryTrain.TABLE_NAME, DataBaseEntryTrain.COLUMN_NAME_ID + " = ?",
                new String[] { String.valueOf(tableId) });
    }

    /** Edit Table **/
    public int editTable(TrainingTable table, String newName, ArrayList<Exercise> newExercises){
        mDb = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseEntryNameTrain.COLUMN_NAME, newName);

        ArrayList<Exercise> oldExercises = getAllExercisesFromTable(table);
        editExercisesFromTable(table, oldExercises, newExercises);

        return mDb.update(DataBaseEntryNameTrain.TABLE_NAME, values,
                DataBaseContract.DataBaseEntryNameTrain._ID + " = ?",
                new String[] { String.valueOf(table.getId()) });
    }

    public void editExercisesFromTable(TrainingTable table, ArrayList<Exercise> oldExercises, ArrayList<Exercise> newExercises){
        mDb = mDbHelper.getWritableDatabase();

        for(int i=0; i<oldExercises.size(); i++){
            editExercise(oldExercises.get(i), newExercises.get(i));
        }
    }

    public int editExercise(Exercise oldExercise, Exercise newExercise){
        mDb = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseEntryListTrain.COLUMN_NAME, newExercise.getNombre());
        values.put(DataBaseEntryListTrain.COLUMN_SERIES, newExercise.getSeries());
        values.put(DataBaseEntryListTrain.COLUMN_REPETICIONES, newExercise.getRepeticiones());
        values.put(DataBaseEntryListTrain.COLUMN_DESCANSO, newExercise.getDescanso());

        return mDb.update(DataBaseEntryListTrain.TABLE_NAME, values, DataBaseEntryListTrain._ID + " = ?",
                new String[] { String.valueOf(oldExercise.getId()) });
    }

    /** Filtrado por dias en tablas de entrenamiento**/
    public ArrayList<TrainingTable> getAllTablesFilterByDay(String filterDay) {
        ArrayList<TrainingTable> tableByDay = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DataBaseEntryNameTrain.TABLE_NAME + " WHERE " + DataBaseEntryNameTrain.COLUMN_DAYS + " LIKE '%"+filterDay+"%'";

        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                TrainingTable trainingTable = new TrainingTable(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryNameTrain._ID))),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryNameTrain.COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(DataBaseEntryNameTrain.COLUMN_DAYS)));
                tableByDay.add(trainingTable);
            } while (cursor.moveToNext());
        }
        return tableByDay;
    }


    /************************************** TABLAS COMIDAS  *************************************/

    /** Crear tabla_comidas en la base de datos **/
    public FoodTable createTableFoods(String name, String days, String type, byte[] image, String kcal){
        ContentValues values = new ContentValues();
        values.put(DataBaseEntryFoods.COLUMN_NAME, name);
        values.put(DataBaseEntryFoods.COLUMN_DAYS, days);
        values.put(DataBaseEntryFoods.COLUMN_TYPE_FOOD, type);
        values.put(DataBaseEntryFoods.COLUMN_IMAGE, image);
        values.put(DataBaseEntryFoods.COLUMN_KCAL, kcal);
        mDb.insert(DataBaseEntryFoods.TABLE_NAME, null, values);

        String selectQuery = "SELECT * FROM " + DataBaseEntryFoods.TABLE_NAME;
        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        cursor.moveToLast();

        return new FoodTable(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryNameTrain._ID))),name, days, type, image, kcal);
    }
    /** Añadimos imagen **/
    public void addImageFood(FoodTable foodTable, byte[] image) {
        ContentValues values = new ContentValues();
        values.put(DataBaseEntryFoods.COLUMN_IMAGE, image);
        mDb.update(DataBaseEntryFoods.TABLE_NAME, values, DataBaseEntryFoods._ID +"="+ foodTable.getId(), null);
    }

    /** Actualizamos kcal **/
    public void updateKcal(FoodTable foodTable, String kcal) {
        ContentValues values = new ContentValues();
        values.put(DataBaseEntryFoods.COLUMN_KCAL, kcal);
        mDb.update(DataBaseEntryFoods.TABLE_NAME, values, DataBaseEntryFoods._ID +"="+ foodTable.getId(), null);
    }

    /** Devuelve un ArrayList con todas las tablas (tabla_comidas) que existen en la base de datos **/
    public ArrayList<FoodTable> getAllFoodTables() {
        ArrayList<FoodTable> table = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DataBaseEntryFoods.TABLE_NAME;
        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                FoodTable foodTable = new FoodTable(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods._ID))),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_DAYS)),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_TYPE_FOOD)), cursor.getBlob(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_KCAL)));
                table.add(foodTable);
            } while (cursor.moveToNext());
        }
        return table;
    }

    /** Filtrado por tipo de comidas**/
    public ArrayList<FoodTable> getAllFoodsFilterByType(String filterType) {
        ArrayList<FoodTable> foodByType = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DataBaseEntryFoods.TABLE_NAME + " WHERE " + DataBaseEntryFoods.COLUMN_TYPE_FOOD + " LIKE '%"+filterType+"%'";

        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                FoodTable foodTable = new FoodTable(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods._ID))),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_DAYS)),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_TYPE_FOOD)), cursor.getBlob(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_KCAL)));
                foodByType.add(foodTable);
            } while (cursor.moveToNext());
        }
        return foodByType;
    }

    /** Filtrado por dia de la semana en tablas de comidas**/
    public ArrayList<FoodTable> getAllFoodsFilterByDay(String filterDay) {
        ArrayList<FoodTable> foodByDay = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DataBaseEntryFoods.TABLE_NAME + " WHERE " + DataBaseEntryFoods.COLUMN_DAYS + " LIKE '%"+filterDay+"%'";

        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                FoodTable foodTable = new FoodTable(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods._ID))),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_DAYS)),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_TYPE_FOOD)), cursor.getBlob(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_KCAL)));
                foodByDay.add(foodTable);
            } while (cursor.moveToNext());
        }
        return foodByDay;
    }

    /** Devuelve una tabla de comidas por ID **/
    public FoodTable getFoodById(long foodId) {
        FoodTable foodTable = null;

        String selectQuery = "SELECT * FROM " + DataBaseEntryFoods.TABLE_NAME + " WHERE " + DataBaseEntryFoods._ID + " = '" + foodId + "'";

        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                foodTable = new FoodTable(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods._ID))),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_DAYS)),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_TYPE_FOOD)), cursor.getBlob(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryFoods.COLUMN_KCAL)));

            } while (cursor.moveToNext());
        }

        return foodTable;
    }

    /** Borra un row de tabla_comidas **/
    public void deleteFood(FoodTable foodTable) {
        long tableId = foodTable.getId();
        mDb = mDbHelper.getWritableDatabase();
        mDb.delete(DataBaseEntryFoods.TABLE_NAME, DataBaseEntryFoods._ID + " = ?",
                new String[] { String.valueOf(tableId) });
    }


    /********************* TABLAS DE ENTRENAMIENTO POR DEFECTO *****************************/

    /** Metemos los datos a las tablas por defecto **/
    public TrainingTable createDefaultTable(String name){
        ContentValues values = new ContentValues();
        values.put(DataBaseDefaultTable.COLUMN_NAME, name);
        //values.put(DataBaseEntryNameTrain.COLUMN_DAYS, days);
        mDb.insert(DataBaseDefaultTable.TABLE_NAME, null, values);

        String selectQuery = "SELECT * FROM " + DataBaseDefaultTable.TABLE_NAME;
        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        cursor.moveToLast();

        return new TrainingTable(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseDefaultTable._ID))),name, null);
    }

    /** Devuelve un arraylist con todas las tablas creadas por defecto **/
    public ArrayList<TrainingTable> getAllDefaultTables() {
        ArrayList<TrainingTable> table = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DataBaseDefaultTable.TABLE_NAME;
        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                TrainingTable trainingTable = new TrainingTable(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseDefaultTable._ID))),
                        cursor.getString(cursor.getColumnIndex(DataBaseDefaultTable.COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(DataBaseDefaultTable.COLUMN_DAYS)));
                table.add(trainingTable);
            } while (cursor.moveToNext());
        }
        return table;
    }

    /** Devuelve la tabla por defecto por id **/
    public TrainingTable getDefaultTableByID(long ID){
        TrainingTable trainingTable = null;

        String selectQuery = "SELECT * FROM " + DataBaseDefaultTable.TABLE_NAME + " WHERE " + DataBaseDefaultTable._ID + " = '" + ID + "'";

        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                trainingTable = new TrainingTable(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseDefaultTable._ID))),
                        cursor.getString(cursor.getColumnIndex(DataBaseDefaultTable.COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(DataBaseDefaultTable.COLUMN_DAYS)));

            } while (cursor.moveToNext());
        }

        return trainingTable;
    }

    /** Crear la lista de ejercicios por defecto en la base de datos **/
    public long createTableListDefaultTraining(String name,  String series, String repeticiones, String descanso){
        ContentValues values = new ContentValues();
        values.put(DataBaseDefaultExercises.COLUMN_NAME, name);
        values.put(DataBaseDefaultExercises.COLUMN_SERIES, series);
        values.put(DataBaseDefaultExercises.COLUMN_REPETICIONES, repeticiones);
        values.put(DataBaseDefaultExercises.COLUMN_DESCANSO, descanso);

        return mDb.insert(DataBaseDefaultExercises.TABLE_NAME, null, values);
    }

    /** Devuelve TODOS los ejercicios de la tabla cuyo nombre es "nombre" en un ArrayList<Exercise> **/
    public ArrayList<Exercise> getAllDefaultExercisesFromTable(TrainingTable mTrainingTable) {
        ArrayList<Exercise> exercises = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DataBaseDefaultTable.TABLE_NAME + " tn, " + DataBaseDefaultExercises.TABLE_NAME
                + " tl, " + DataBaseDefaultLinkTable.TABLE_NAME + " te WHERE tn." + DataBaseDefaultTable.COLUMN_NAME + " = '" + mTrainingTable.getName() + "'" +
                " AND tn." + DataBaseDefaultTable._ID + " = te." + DataBaseDefaultLinkTable.COLUMN_NAME_ID +
                " AND tl." + DataBaseDefaultExercises._ID + " = te." + DataBaseDefaultLinkTable.COLUMN_LIST_ID;

        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Exercise newExercise = new Exercise(cursor.getString(cursor.getColumnIndex(DataBaseDefaultExercises.COLUMN_NAME)),cursor.getString(cursor.getColumnIndex(DataBaseDefaultExercises.COLUMN_SERIES)),
                        cursor.getString(cursor.getColumnIndex(DataBaseDefaultExercises.COLUMN_REPETICIONES)), cursor.getString(cursor.getColumnIndex(DataBaseDefaultExercises.COLUMN_DESCANSO)));
                newExercise.setId(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseDefaultLinkTable.COLUMN_LIST_ID))));
                exercises.add(newExercise);
            } while (cursor.moveToNext());
        }
        return exercises;
    }

    /** Crear tabla_link (asignación de varios ejercicios por defecto a una tabla de ejercicios) **/
    public long createDefaultLinkTraining(long idName,  long idList){
        ContentValues values = new ContentValues();
        values.put(DataBaseDefaultLinkTable.COLUMN_NAME_ID, idName);
        values.put(DataBaseDefaultLinkTable.COLUMN_LIST_ID, idList);

        return mDb.insert(DataBaseDefaultLinkTable.TABLE_NAME, null, values);
    }

    /** Comprueba si existe la tabla de ejercicios por defecto **/
    public boolean checkifTableisEmpty() {
        boolean isEmpty = true;
        String count = "SELECT count(*) FROM " + DataBaseDefaultTable.TABLE_NAME;
        mDb = mDbHelper.getWritableDatabase();
        Cursor mcursor = mDb.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount > 0) isEmpty = false;
        return isEmpty;
    }


    /********************* PANTALLA DE EVENTOS *****************************/

    /** Devuelve todos los ejercicios de todas las tablas como un ArrayList<Exercise> **/
    public ArrayList<Exercise> getAllExercisesOfDataBase() {
        ArrayList<Exercise> exercises = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DataBaseEntryListTrain.TABLE_NAME;
        mDb = mDbHelper.getReadableDatabase();

        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Exercise newExercise = new Exercise(cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_NAME)),cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_SERIES)),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_REPETICIONES)), cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_DESCANSO)));
                newExercise.setId(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryTrain._ID))));
                exercises.add(newExercise);
            } while (cursor.moveToNext());
        }
        return exercises;
    }

    /** Develve el tiempo o la duración de todas las tablas como un string del formato mm:ss **/
    public String getTimeOfTables(){
        long minutes = 0;
        long seconds = getAllExercisesOfDataBase().size()*30; //Suponemos que la duración de la realización de cada ejercicio es de 30 segundos
        ArrayList<Exercise> allExercises = getAllExercisesOfDataBase();
        for(Exercise exercise : allExercises){
            String exercise_time = exercise.getDescanso();
            String[] min_sec = exercise_time.split(":");
            minutes += valueOf(min_sec[0])*60*valueOf(exercise.getSeries());
            seconds += valueOf(min_sec[1])*valueOf(exercise.getSeries());
        }
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }
}
