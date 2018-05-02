package com.rigobertosl.nevergiveapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.sql.Array;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
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

    private static final int DATABASE_VERSION = 1;


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
        public static final String COLUMN_TYPE = "tipo";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_DESCRIPTION= "description";


        private static final String SQL_CREATE_ENTRIES_LIST_TRAIN =
                "CREATE TABLE " + DataBaseEntryListTrain.TABLE_NAME + " (" +
                        DataBaseEntryListTrain._ID + " INTEGER PRIMARY KEY," +
                        DataBaseEntryListTrain.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryListTrain.COLUMN_SERIES + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryListTrain.COLUMN_REPETICIONES + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryListTrain.COLUMN_DESCANSO + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryListTrain.COLUMN_TYPE + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryListTrain.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryListTrain.COLUMN_IMAGE + " BLOB" + " )";

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

    public static class DataBaseEntryKcal implements BaseColumns {
        public static final String TABLE_NAME = "lista_kcal";
        public static final String COLUMN_FOOD_ID = "id_food";
        public static final String COLUMN_PASTA = "pasta";
        public static final String COLUMN_HUEVOS = "huevos";
        public static final String COLUMN_LECHE = "leche";
        public static final String COLUMN_CARNE = "carne";
        public static final String COLUMN_PESCADO = "pescado";
        public static final String COLUMN_VERDURA = "verdura";
        public static final String COLUMN_BOLLERIA = "bolleria";
        public static final String COLUMN_CEREALES = "cereales";
        public static final String COLUMN_LEGUMBRE = "legumbre";
        public static final String COLUMN_EMBUTIDO = "embutido";
        public static final String COLUMN_QUESO = "queso";
        public static final String COLUMN_YOGURT = "yogurt";


        private static final String SQL_CREATE_ENTRIES_KCAL =
                "CREATE TABLE " + DataBaseEntryKcal.TABLE_NAME + " (" +
                        DataBaseEntryKcal._ID + " INTEGER PRIMARY KEY," +
                        DataBaseEntryKcal.COLUMN_FOOD_ID + LONG_TYPE + COMMA_SEP +
                        DataBaseEntryKcal.COLUMN_PASTA + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryKcal.COLUMN_HUEVOS + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryKcal.COLUMN_LECHE + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryKcal.COLUMN_CARNE + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryKcal.COLUMN_PESCADO + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryKcal.COLUMN_VERDURA + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryKcal.COLUMN_BOLLERIA + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryKcal.COLUMN_CEREALES + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryKcal.COLUMN_LEGUMBRE + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryKcal.COLUMN_EMBUTIDO + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryKcal.COLUMN_QUESO + TEXT_TYPE + COMMA_SEP +
                        DataBaseEntryKcal.COLUMN_YOGURT + TEXT_TYPE + " )";

        private static final String SQL_DELETE_ENTRIES_KCAL =
                "DROP TABLE IF EXISTS " + DataBaseEntryKcal.TABLE_NAME;
    }

    /********************* COLUMNAS PARA TABLAS DE LOGROS *****************************/
    public static class DataBaseAchievementsTraining implements BaseColumns {
        public static final String TABLE_NAME = "tabla_logros_entrenamiento";
        public static final String COLUMN_ACHIEVEMENT_TRAINING_ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_POINTS = "points";
        public static final String COLUMN_COMPLETED = "completed";


        private static final String SQL_CREATE_ENTRIES_ACHIEVEMENTS_TRAINING =
                "CREATE TABLE " + DataBaseContract.DataBaseAchievementsTraining.TABLE_NAME + " (" +
                        DataBaseContract.DataBaseAchievementsTraining.COLUMN_ACHIEVEMENT_TRAINING_ID + " INTEGER PRIMARY KEY," +
                        DataBaseContract.DataBaseAchievementsTraining.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                        DataBaseContract.DataBaseAchievementsTraining.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                        DataBaseContract.DataBaseAchievementsTraining.COLUMN_POINTS + TEXT_TYPE + COMMA_SEP +
                        DataBaseAchievementsTraining.COLUMN_COMPLETED + TEXT_TYPE + " )";

        private static final String SQL_DELETE_ENTRIES_ACHIEVEMENTS_TRAINING =
                "DROP TABLE IF EXISTS " + DataBaseContract.DataBaseAchievementsTraining.TABLE_NAME;
    }

    /********************* COLUMNAS PARA TABLAS DE LOGROS *****************************/
    public static class DataBaseAchievementsFoods implements BaseColumns {
        public static final String TABLE_NAME = "tabla_logros_comidas";
        public static final String COLUMN_ACHIEVEMENT_FOODS_ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_POINTS = "points";
        public static final String COLUMN_COMPLETED = "completed";


        private static final String SQL_CREATE_ENTRIES_ACHIEVEMENTS_FOODS =
                "CREATE TABLE " + DataBaseContract.DataBaseAchievementsFoods.TABLE_NAME + " (" +
                        DataBaseContract.DataBaseAchievementsFoods.COLUMN_ACHIEVEMENT_FOODS_ID + " INTEGER PRIMARY KEY," +
                        DataBaseContract.DataBaseAchievementsFoods.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                        DataBaseContract.DataBaseAchievementsFoods.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                        DataBaseContract.DataBaseAchievementsFoods.COLUMN_POINTS + TEXT_TYPE + COMMA_SEP +
                        DataBaseAchievementsFoods.COLUMN_COMPLETED + TEXT_TYPE + " )";

        private static final String SQL_DELETE_ENTRIES_ACHIEVEMENTS_FOODS =
                "DROP TABLE IF EXISTS " + DataBaseContract.DataBaseAchievementsFoods.TABLE_NAME;
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
            db.execSQL(DataBaseEntryKcal.SQL_CREATE_ENTRIES_KCAL);
            db.execSQL(DataBaseAchievementsTraining.SQL_CREATE_ENTRIES_ACHIEVEMENTS_TRAINING);
            db.execSQL(DataBaseAchievementsFoods.SQL_CREATE_ENTRIES_ACHIEVEMENTS_FOODS);
        }

        public void onUpgrade(SQLiteDatabase db, int version1, int version2) {
            db.execSQL(DataBaseEntryNameTrain.SQL_DELETE_ENTRIES_NAME_TRAIN);
            db.execSQL(DataBaseEntryListTrain.SQL_DELETE_ENTRIES_LIST_TRAIN);
            db.execSQL(DataBaseEntryTrain.SQL_DELETE_ENTRIES_TRAIN);
            db.execSQL(DataBaseEntryFoods.SQL_DELETE_ENTRIES_FOODS);
            db.execSQL(DataBaseEntryKcal.SQL_DELETE_ENTRIES_KCAL);
            db.execSQL(DataBaseAchievementsTraining.SQL_DELETE_ENTRIES_ACHIEVEMENTS_TRAINING);
            db.execSQL(DataBaseAchievementsFoods.SQL_DELETE_ENTRIES_ACHIEVEMENTS_FOODS);
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

        String selectQuery = "SELECT tl.*, te.id_list FROM " + DataBaseEntryNameTrain.TABLE_NAME + " tn, " + DataBaseEntryListTrain.TABLE_NAME
                + " tl, " + DataBaseEntryTrain.TABLE_NAME + " te WHERE tn." + DataBaseEntryNameTrain.COLUMN_NAME + " = '" + mTrainingTable.getName() + "'" +
                " AND tn." + DataBaseEntryNameTrain._ID + " = te." + DataBaseEntryTrain.COLUMN_NAME_ID +
                " AND tl." + DataBaseEntryListTrain._ID + " = te." + DataBaseEntryTrain.COLUMN_LIST_ID;

        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Exercise newExercise = new Exercise(cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_NAME)),cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_SERIES)),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_REPETICIONES)), cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_DESCANSO)),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_TYPE)), cursor.getBlob(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_IMAGE)), cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_DESCRIPTION)));
                newExercise.setId(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryTrain.COLUMN_LIST_ID))));
                exercises.add(newExercise);
            } while (cursor.moveToNext());
        }
        return exercises;
    }

    /** Crear lista_ejercicos en la base de datos **/
    public long createTableListTraining(String name,  String series, String repeticiones, String descanso, String tipo, byte[] image, String description){
        ContentValues values = new ContentValues();
        values.put(DataBaseEntryListTrain.COLUMN_NAME, name);
        values.put(DataBaseEntryListTrain.COLUMN_SERIES, series);
        values.put(DataBaseEntryListTrain.COLUMN_REPETICIONES, repeticiones);
        values.put(DataBaseEntryListTrain.COLUMN_DESCANSO, descanso);
        values.put(DataBaseEntryListTrain.COLUMN_TYPE, tipo);
        values.put(DataBaseEntryListTrain.COLUMN_DESCRIPTION, description);
        values.put(DataBaseEntryListTrain.COLUMN_IMAGE, image);

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

    /** Comprueba si existe la tabla de ejercicios **/
    public boolean checkifTrainTableisEmpty() {
        boolean isEmpty = true;
        String count = "SELECT count(*) FROM " + DataBaseEntryNameTrain.TABLE_NAME;
        mDb = mDbHelper.getWritableDatabase();
        Cursor mcursor = mDb.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount > 0) isEmpty = false;
        return isEmpty;
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
        deleteKcal(foodTable);
    }

    /** Crear las kcal en la base de datos **/
    public long createTableKcal(long foodId,  boolean[] checks){
        ContentValues values = new ContentValues();
        values.put(DataBaseEntryKcal.COLUMN_FOOD_ID, foodId);
        values.put(DataBaseEntryKcal.COLUMN_PASTA, checks[0]);
        values.put(DataBaseEntryKcal.COLUMN_HUEVOS, checks[1]);
        values.put(DataBaseEntryKcal.COLUMN_LECHE, checks[2]);
        values.put(DataBaseEntryKcal.COLUMN_CARNE, checks[3]);
        values.put(DataBaseEntryKcal.COLUMN_PESCADO, checks[4]);
        values.put(DataBaseEntryKcal.COLUMN_VERDURA, checks[5]);
        values.put(DataBaseEntryKcal.COLUMN_BOLLERIA, checks[6]);
        values.put(DataBaseEntryKcal.COLUMN_CEREALES, checks[7]);
        values.put(DataBaseEntryKcal.COLUMN_LEGUMBRE, checks[8]);
        values.put(DataBaseEntryKcal.COLUMN_EMBUTIDO, checks[9]);
        values.put(DataBaseEntryKcal.COLUMN_QUESO, checks[10]);
        values.put(DataBaseEntryKcal.COLUMN_YOGURT, checks[11]);

        return mDb.insert(DataBaseEntryKcal.TABLE_NAME, null, values);
    }

    /** Borra un row de lista de kcal **/
    public void deleteKcal(FoodTable foodTable) {
        long tableId = foodTable.getId();
        mDb = mDbHelper.getWritableDatabase();
        mDb.delete(DataBaseEntryKcal.TABLE_NAME, DataBaseEntryKcal.COLUMN_FOOD_ID + " = ?",
                new String[] { String.valueOf(tableId) });
    }

    /** Actualizamos kcal de la tabla **/
    public void updateTableKcal(KcalTable kcalTable, boolean[] checks) {
        ContentValues values = new ContentValues();
        values.put(DataBaseEntryKcal.COLUMN_PASTA, checks[0]);
        values.put(DataBaseEntryKcal.COLUMN_HUEVOS, checks[1]);
        values.put(DataBaseEntryKcal.COLUMN_LECHE, checks[2]);
        values.put(DataBaseEntryKcal.COLUMN_CARNE, checks[3]);
        values.put(DataBaseEntryKcal.COLUMN_PESCADO, checks[4]);
        values.put(DataBaseEntryKcal.COLUMN_VERDURA, checks[5]);
        values.put(DataBaseEntryKcal.COLUMN_BOLLERIA, checks[6]);
        values.put(DataBaseEntryKcal.COLUMN_CEREALES, checks[7]);
        values.put(DataBaseEntryKcal.COLUMN_LEGUMBRE, checks[8]);
        values.put(DataBaseEntryKcal.COLUMN_EMBUTIDO, checks[9]);
        values.put(DataBaseEntryKcal.COLUMN_QUESO, checks[10]);
        values.put(DataBaseEntryKcal.COLUMN_YOGURT, checks[11]);
        mDb.update(DataBaseEntryKcal.TABLE_NAME, values, DataBaseEntryKcal._ID +"="+ kcalTable.getId(), null);
    }

    /** Cogemos la lista de calorias segun la comida **/
    public KcalTable getKcalTableByFood(long foodId) {
        KcalTable kcalTable = null;
        String selectQuery = "SELECT * FROM " + DataBaseEntryKcal.TABLE_NAME + " WHERE " + DataBaseEntryKcal.COLUMN_FOOD_ID + " = '" + foodId + "'";
        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        boolean pasta = false;
        boolean huevos = false;
        boolean leche = false;
        boolean carne = false;
        boolean pescado = false;
        boolean verdura = false;
        boolean bolleria = false;
        boolean cereales = false;
        boolean legumbre = false;
        boolean embutido = false;
        boolean queso = false;
        boolean yogurt = false;

        if(cursor.moveToFirst()) {
            do {
                if(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryKcal.COLUMN_PASTA))) == 1) {
                    pasta = true;
                }
                if(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryKcal.COLUMN_HUEVOS))) == 1) {
                    huevos = true;
                }
                if(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryKcal.COLUMN_LECHE))) == 1) {
                    leche = true;
                }
                if(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryKcal.COLUMN_CARNE))) == 1) {
                    carne = true;
                }
                if(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryKcal.COLUMN_PESCADO))) == 1) {
                    pescado = true;
                }
                if(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryKcal.COLUMN_VERDURA))) == 1) {
                    verdura = true;
                }
                if(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryKcal.COLUMN_BOLLERIA))) == 1) {
                    bolleria = true;
                }
                if(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryKcal.COLUMN_CEREALES))) == 1) {
                    cereales = true;
                }
                if(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryKcal.COLUMN_LEGUMBRE))) == 1) {
                    legumbre = true;
                }
                if(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryKcal.COLUMN_EMBUTIDO))) == 1) {
                    embutido = true;
                }
                if(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryKcal.COLUMN_QUESO))) == 1) {
                    queso = true;
                }
                if(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryKcal.COLUMN_YOGURT))) == 1) {
                    yogurt = true;
                }

                kcalTable = new KcalTable(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryKcal._ID))),
                        pasta, huevos, leche, carne, pescado, verdura, bolleria, cereales, legumbre, embutido, queso, yogurt);
            } while (cursor.moveToNext());
        }
        return kcalTable;
    }


    /********************* TABLAS DE ENTRENAMIENTO POR DEFECTO *****************************/

    /** Devuelve un arraylist con todas las tablas creadas por defecto **/
    public ArrayList<TrainingTable> getAllDefaultTables() {
        ArrayList<TrainingTable> table = new ArrayList<>();
        String selectQuery = "SELECT * FROM tabla_default";
        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                TrainingTable trainingTable = new TrainingTable(valueOf(cursor.getString(cursor.getColumnIndex("_id"))),
                        cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("days")));
                table.add(trainingTable);
            } while (cursor.moveToNext());
        }
        return table;
    }

    /** Devuelve la tabla por defecto por id **/
    public TrainingTable getDefaultTableByID(long ID){
        TrainingTable trainingTable = null;

        String selectQuery = "SELECT * FROM tabla_default WHERE _id = '" + ID + "'";

        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                trainingTable = new TrainingTable(valueOf(cursor.getString(cursor.getColumnIndex("_id"))),
                        cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("days")));

            } while (cursor.moveToNext());
        }

        return trainingTable;
    }

    /** Devuelve TODOS los ejercicios de la tabla cuyo nombre es "nombre" en un ArrayList<Exercise> **/
    public ArrayList<Exercise> getAllDefaultExercisesFromTable(TrainingTable mTrainingTable) {
        ArrayList<Exercise> exercises = new ArrayList<>();

        String selectQuery = "SELECT tl.*, te.id_list FROM tabla_default tn, ejercicios_default tl, link_default te WHERE tn.name = '" + mTrainingTable.getName() + "'" +
                " AND tn._id = te.id_name AND tl._id = te.id_list";

        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Exercise newExercise = new Exercise(cursor.getString(cursor.getColumnIndex("name")),cursor.getString(cursor.getColumnIndex("series")),
                        cursor.getString(cursor.getColumnIndex("repeticiones")), cursor.getString(cursor.getColumnIndex("descanso")), null,
                        cursor.getBlob(cursor.getColumnIndex("image")), cursor.getString(cursor.getColumnIndex("descripcion")));
                newExercise.setId(valueOf(cursor.getString(cursor.getColumnIndex("id_list"))));
                exercises.add(newExercise);
            } while (cursor.moveToNext());
        }
        return exercises;
    }

    /********************* IMAGENES Y DESCRIPCIONES DE LOS EJERCICIOS *****************************/

    /** Coge una imagen a partir de un nombre **/
    public byte[] getExerciseImage(String exerciseName) {
        byte[] b = new byte[]{};

        String selectQuery = "SELECT image FROM exercise_image WHERE exercise_image.name = '" + exerciseName + "'";

        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                b = cursor.getBlob(cursor.getColumnIndex("image"));

            } while (cursor.moveToNext());
        }
        return b;
    }

    /** Coge una imagen a partir de un nombre **/
    public String getExerciseDescription(String exerciseName) {
        String description = null;

        String selectQuery = "SELECT descripcion FROM exercise_image WHERE exercise_image.name = '" + exerciseName + "'";

        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                description = cursor.getString(cursor.getColumnIndex("descripcion"));

            } while (cursor.moveToNext());
        }
        return description;
    }


    /********************* PANTALLA DE LOGROS *****************************/

    /** Devuelve el total de puntos proveniente de los logros **/
    public int getTotalPoints(){
        int points = 0;

        String selectQuery = "SELECT * FROM " + DataBaseAchievementsTraining.TABLE_NAME;
        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(DataBaseAchievementsTraining.COLUMN_COMPLETED)).equals("true")) {
                    points += Integer.valueOf(cursor.getString(cursor.getColumnIndex(DataBaseAchievementsTraining.COLUMN_POINTS)));
                }
            } while (cursor.moveToNext());
        }
        selectQuery = "SELECT * FROM " + DataBaseAchievementsFoods.TABLE_NAME;
        cursor = mDb.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(DataBaseAchievementsFoods.COLUMN_COMPLETED)).equals("true")) {
                    points += Integer.valueOf(cursor.getString(cursor.getColumnIndex(DataBaseAchievementsFoods.COLUMN_POINTS)));
                }
            } while (cursor.moveToNext());
        }

        return points;
    }

    /** Devuelve todos los ejercicios de todas las tablas como un ArrayList<Exercise> **/
    public ArrayList<Exercise> getAllExercisesOfDataBase() {
        ArrayList<Exercise> exercises = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DataBaseEntryListTrain.TABLE_NAME;
        mDb = mDbHelper.getReadableDatabase();

        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Exercise newExercise = new Exercise(cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_NAME)),cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_SERIES)),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_REPETICIONES)), cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_DESCANSO)),
                        cursor.getString(cursor.getColumnIndex(DataBaseEntryListTrain.COLUMN_TYPE)), null, null);
                newExercise.setId(valueOf(cursor.getString(cursor.getColumnIndex(DataBaseEntryTrain._ID))));
                exercises.add(newExercise);
            } while (cursor.moveToNext());
        }
        return exercises;
    }

    public ArrayList<Achievement> getAllAchievementsCompleted(){
        ArrayList<Achievement> achievements = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DataBaseAchievementsTraining.TABLE_NAME;
        mDb = mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(DataBaseAchievementsTraining.COLUMN_COMPLETED)).equals("true")) {
                    Achievement achievement = new Achievement((cursor.getString(cursor.getColumnIndex(DataBaseAchievementsTraining._ID))),
                            cursor.getString(cursor.getColumnIndex(DataBaseAchievementsTraining.COLUMN_TITLE)), cursor.getString(cursor.getColumnIndex(DataBaseAchievementsTraining.COLUMN_DESCRIPTION)),
                            "training", cursor.getString(cursor.getColumnIndex(DataBaseAchievementsTraining.COLUMN_POINTS)),
                            Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DataBaseAchievementsTraining.COLUMN_COMPLETED))));
                    achievements.add(achievement);
                }
            } while (cursor.moveToNext());
        }

        selectQuery = "SELECT * FROM " + DataBaseAchievementsFoods.TABLE_NAME;
        cursor = mDb.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(DataBaseAchievementsFoods.COLUMN_COMPLETED)).equals("true")) {
                    Achievement achievement = new Achievement((cursor.getString(cursor.getColumnIndex(DataBaseAchievementsFoods._ID))),
                            cursor.getString(cursor.getColumnIndex(DataBaseAchievementsFoods.COLUMN_TITLE)), cursor.getString(cursor.getColumnIndex(DataBaseAchievementsFoods.COLUMN_DESCRIPTION)),
                            "foods", cursor.getString(cursor.getColumnIndex(DataBaseAchievementsFoods.COLUMN_POINTS)),
                            Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DataBaseAchievementsFoods.COLUMN_COMPLETED))));
                    achievements.add(achievement);
                }
            } while (cursor.moveToNext());
        }

        return achievements;
    }

    /** Crea un logro dentro de la base de datos, ya sea de entrenamiento o de comidas **/
    private void createAchievement(String type, String title, String description, String points, String completed) {
        ContentValues values = new ContentValues();
        if (type.equals("training")){
            values.put(DataBaseAchievementsTraining.COLUMN_TITLE, title);
            values.put(DataBaseAchievementsTraining.COLUMN_DESCRIPTION, description);
            values.put(DataBaseAchievementsTraining.COLUMN_POINTS, points);
            values.put(DataBaseAchievementsTraining.COLUMN_COMPLETED, completed);
            mDb.insert(DataBaseAchievementsTraining.TABLE_NAME, null, values);
        } else if (type.equals("foods")){
            values.put(DataBaseAchievementsFoods.COLUMN_TITLE, title);
            values.put(DataBaseAchievementsFoods.COLUMN_DESCRIPTION, description);
            values.put(DataBaseAchievementsFoods.COLUMN_POINTS, points);
            values.put(DataBaseAchievementsFoods.COLUMN_COMPLETED, completed);
            mDb.insert(DataBaseAchievementsFoods.TABLE_NAME, null, values);
        }
    }

    /** Carga todos los logros, tanto de entrenamiento como de comidas como ambos, en la base de datos **/
    public void newAchievements(String type){
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + DataBaseAchievementsTraining.TABLE_NAME, null);
        if (!cursor.moveToFirst()){
            String[] titles = {""};
            String[] descriptions = {""};
            String[] points = {""};

            if (type.equals("training") || type.equals("both")){
                titles = context.getResources().getStringArray(R.array.achievementsTrainingTitles);
                descriptions = context.getResources().getStringArray(R.array.achievementsTrainingDescriptions);
                points = context.getResources().getStringArray(R.array.achievementsTrainingPoints);
                for (int i = 0; i < titles.length; i++){
                    createAchievement("training", titles[i], descriptions[i], points[i], "false");
                }
            }
            if (type.equals("foods") || type.equals("both")){
                titles = context.getResources().getStringArray(R.array.achievementsFoodsTitles);
                descriptions = context.getResources().getStringArray(R.array.achievementsFoodsDescriptions);
                points = context.getResources().getStringArray(R.array.achievementsFoodsPoints);
                for (int i = 0; i < titles.length; i++){
                    createAchievement("foods", titles[i], descriptions[i], points[i], "false");
                }
            }
        }
    }

    /** Actualiza el valor de completed de todos los logros dentro de la base de datos, tanto de entrenamiento como de comidas como ambos **/
    public void reloadAchievements(String type){

        if (type.equals("training") || type.equals("both")){
            boolean[] achievements = getNumOfTables();
            boolean[] existTableOnDay = existDayTable();
            boolean[] typeExercises = getNumTypeExercises();
            boolean[] numExercises = getNumExercises();

            int tamaño = achievements.length;
            achievements = Arrays.copyOf(achievements, achievements.length + existTableOnDay.length);
            for ( int i = tamaño; i < achievements.length; i++){
                achievements[i] = existTableOnDay[i-tamaño];
            }
            tamaño = achievements.length;
            achievements = Arrays.copyOf(achievements, achievements.length + typeExercises.length);
            for ( int i = tamaño; i < achievements.length; i++){
                achievements[i] = typeExercises[i-tamaño];
            }
            tamaño = achievements.length;
            achievements = Arrays.copyOf(achievements, achievements.length + numExercises.length);
            for ( int i = tamaño; i < achievements.length; i++){
                achievements[i] = numExercises[i-tamaño];
            }

            for (int i = 0; i < achievements.length; i++){
                updateAchievement(i+1, "training", achievements[i]);
            }
        }
        if (type.equals("foods") || type.equals("both")){

            boolean[] achievements = getNumOfFoods();
            boolean[] numFoodsForDay = getNumFoodsForDay();
            String[] shortTypes = {"Desayuno", "Comida", "Cena"};
            boolean[] breakfastLaunchDinner = getBreakfastLaunchDinner(shortTypes);
            String[] Types = {"Desayuno", "Almuerzo", "Comida", "Merienda", "Cena"};
            boolean[] allFoodsPerDay = getBreakfastLaunchDinner(Types);

            int tamaño = achievements.length;
            achievements = Arrays.copyOf(achievements, achievements.length + numFoodsForDay.length);
            for ( int i = tamaño; i < achievements.length; i++){
                achievements[i] = numFoodsForDay[i-tamaño];
            }
            tamaño = achievements.length;
            achievements = Arrays.copyOf(achievements, achievements.length + breakfastLaunchDinner.length);
            for ( int i = tamaño; i < achievements.length; i++){
                achievements[i] = breakfastLaunchDinner[i-tamaño];
            }
            tamaño = achievements.length;
            achievements = Arrays.copyOf(achievements, achievements.length + allFoodsPerDay.length);
            for ( int i = tamaño; i < achievements.length; i++){
                achievements[i] = allFoodsPerDay[i-tamaño];
            }
            for (int i = 0; i < achievements.length; i++){
                updateAchievement(i+1, "foods", achievements[i]);
            }
        }
    }

    /** Actualiza el valor de completed de un logro cualquiera dentro de la base de datos **/
    public void updateAchievement(long id, String type, boolean completed){
        mDb = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (type.equals("training")){
            values.put(DataBaseAchievementsTraining.COLUMN_COMPLETED, String.valueOf(completed));
            mDb.update(DataBaseAchievementsTraining.TABLE_NAME, values, DataBaseAchievementsTraining._ID + "= ?",
                    new String[] { String.valueOf(id) });
        }else if (type.equals("foods")){
            values.put(DataBaseAchievementsFoods.COLUMN_COMPLETED, String.valueOf(completed));
            mDb.update(DataBaseAchievementsFoods.TABLE_NAME, values, DataBaseAchievementsFoods._ID + "= ?",
                    new String[] { String.valueOf(id) });
        }
    }

    /** Devuelve una lista de Logros del tipo que desees **/
    public ArrayList<Achievement> getAllAchievementsByType(String type){
        ArrayList<Achievement> achievements = new ArrayList<Achievement>();

        if (type.equals("training")){
            String selectQuery = "SELECT * FROM " + DataBaseAchievementsTraining.TABLE_NAME;
            mDb = mDbHelper.getReadableDatabase();
            Cursor cursor = mDb.rawQuery(selectQuery, null);
            if(cursor.moveToFirst()) {
                do {
                    Achievement achievement = new Achievement((cursor.getString(cursor.getColumnIndex(DataBaseAchievementsTraining._ID))),
                            cursor.getString(cursor.getColumnIndex(DataBaseAchievementsTraining.COLUMN_TITLE)), cursor.getString(cursor.getColumnIndex(DataBaseAchievementsTraining.COLUMN_DESCRIPTION)),
                            "training", cursor.getString(cursor.getColumnIndex(DataBaseAchievementsTraining.COLUMN_POINTS)),
                            Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DataBaseAchievementsTraining.COLUMN_COMPLETED))));
                    achievements.add(achievement);
                } while (cursor.moveToNext());
            }
        } else if (type.equals("foods")){
            String selectQuery = "SELECT * FROM " + DataBaseAchievementsFoods.TABLE_NAME;
            mDb = mDbHelper.getReadableDatabase();
            Cursor cursor = mDb.rawQuery(selectQuery, null);
            if(cursor.moveToFirst()) {
                do {
                    Achievement achievement = new Achievement((cursor.getString(cursor.getColumnIndex(DataBaseAchievementsFoods._ID))),
                            cursor.getString(cursor.getColumnIndex(DataBaseAchievementsFoods.COLUMN_TITLE)), cursor.getString(cursor.getColumnIndex(DataBaseAchievementsFoods.COLUMN_DESCRIPTION)),
                            "foods", cursor.getString(cursor.getColumnIndex(DataBaseAchievementsFoods.COLUMN_POINTS)),
                            Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DataBaseAchievementsFoods.COLUMN_COMPLETED))));
                    achievements.add(achievement);
                } while (cursor.moveToNext());
            }
        }
        return achievements;
    }

    /** Devuelve un array de booleans que representan si están completos o no los logros de entrenamiento de 0 a 4 **/
    private boolean[] getNumOfTables(){
        boolean[] createdTables = new boolean[5];

        int tablesSize = getAllTables().size();
        Arrays.fill(createdTables, true);

        if (tablesSize < 20){
            createdTables[4] = false;
        }
        if (tablesSize < 15){
            createdTables[3] = false;
        }
        if (tablesSize < 10){
            createdTables[2] = false;
        }
        if (tablesSize < 5){
            createdTables[1] = false;
        }
        if (tablesSize < 1){
            createdTables[0] = false;
        }

        return createdTables;
    }

    /** Devuelve un array de booleans que representan si están completos o no los logros de entrenamiento de 5 a 12 **/
    private boolean[] existDayTable(){
        boolean dayTable[] = new boolean[7];

        String[] weekDays = {"LU", "M", "X", "JU", "VI", "SA", "DO"};

        for (int i = 0; i < weekDays.length; i++){
            if(getAllTablesFilterByDay(weekDays[i]).size() >= 1) {
                dayTable[i] = true;
            }
        }
        dayTable = Arrays.copyOf(dayTable, dayTable.length + 1);
        if (Arrays.toString(dayTable).contains("t")){
            dayTable[dayTable.length - 1] = true;
        }
        return dayTable;
    }

    /** Devuelve un array de booleans que representan si están completos o no los logros de entrenamiento de 13 a 19 **/
    private boolean[] getNumExercises(){
        boolean[] numExercises = new boolean[4];

        Arrays.fill(numExercises, true);
        ArrayList<TrainingTable> allTables = getAllTables();
        int maxExercises = 0;

        for (int i = 0; i < allTables.size(); i++){
            int numExercisesFromTable = getAllExercisesFromTable(allTables.get(i)).size();
            if (numExercisesFromTable > maxExercises){
                maxExercises = numExercisesFromTable;
            }
        }
        if (maxExercises < 4){
            numExercises[0] = false;
        }
        if (maxExercises < 5){
            numExercises[1] = false;
        }
        if (maxExercises < 6){
            numExercises[2] = false;
        }
        if (maxExercises < 7){
            numExercises[3] = false;
        }
        return numExercises;
    }

    /** Devuelve un array de booleans que representan si están completos o no los logros de entrenamiento de 20 a 23 **/
    private boolean[] getNumTypeExercises() {
        boolean[] typeExercises = new boolean[7];

        String[] types = {"pecho", "espalda", "biceps", "triceps", "abdominales", "pierna"};
        Arrays.fill(typeExercises, false);
        ArrayList<TrainingTable> allTables = getAllTables();
        for (int i = 0; i < allTables.size(); i++){
            int[] contador = new int[types.length];
            ArrayList<Exercise> exercises = getAllExercisesFromTable(allTables.get(i));
            for (int j = 0; j < exercises.size(); j++){
                for (int z = 0; z < contador.length; z++){
                    if (exercises.get(j).getTipo().equals(types[z])){
                        contador[z]++;
                        if (contador[z] >= 3){
                            typeExercises[z] = true;
                            break;
                        }
                        break;
                    }
                }
            }
            if (contador[0] >= 1 && contador[1] >= 1 && contador[2] >= 1 && contador[3] >= 1 && contador[4] >= 1 && contador[5] >= 1){
                typeExercises[6] = true;
            }
        }
        return typeExercises;
    }

    /** Devuelve un array de booleans que representan si están completos o no los logros de comidas de 0 a 10 **/
    private boolean[] getNumOfFoods(){
        boolean[] createdFoods = new boolean[11];

        int foodsSize = getAllFoodTables().size();
        Arrays.fill(createdFoods, true);

        if (foodsSize < 35){
            createdFoods[10] = false;
        }
        if (foodsSize < 30){
            createdFoods[9] = false;
        }
        if (foodsSize < 25){
            createdFoods[8] = false;
        }
        if (foodsSize < 20){
            createdFoods[7] = false;
        }
        if (foodsSize < 15){
            createdFoods[6] = false;
        }
        if (foodsSize < 10){
            createdFoods[5] = false;
        }
        if (foodsSize < 5){
            createdFoods[4] = false;
        }
        if (foodsSize < 4){
            createdFoods[3] = false;
        }
        if (foodsSize < 3){
            createdFoods[2] = false;
        }
        if (foodsSize < 2){
            createdFoods[1] = false;
        }
        if (foodsSize < 1){
            createdFoods[0] = false;
        }
        return createdFoods;
    }

    /** Devuelve un array de booleans que representan si están completos o no los logros de comidas de 11 a 12 **/
    private boolean[] getNumFoodsForDay(){
        boolean[] numOfFoodsForDay = new boolean[2];

        String[] weekDays = {"LU", "M", "X", "JU", "VI", "SA", "DO"};

        for (int i = 0; i < weekDays.length; i++){
            if (getAllFoodsFilterByDay(weekDays[i]).size() >= 3){
                numOfFoodsForDay[0] = true;
            }
            if (getAllFoodsFilterByDay(weekDays[i]).size() >= 5){
                numOfFoodsForDay[1] = true;
                break;
            }
        }
        return numOfFoodsForDay;
    }

    /** Devuelve un array de booleans que representan si están completos o no los logros de comidas de 13 a 18 **/
    private boolean[] getBreakfastLaunchDinner(String[] types){
        boolean[] breakfastLaunchDinner = new boolean[3];

        String[] weekDays = {"LU", "M", "X", "JU", "VI", "SA", "DO"};

        int countDays = 0;
        for (int i = 0; i < weekDays.length; i++){
            int[] countFood = new int[types.length];
            ArrayList<FoodTable> foods = getAllFoodsFilterByDay(weekDays[i]);
            for (FoodTable food : foods){
                for (int j = 0; j < types.length; j++){
                    if (food.getType().equals(types[j])){
                        countFood[j]++;
                        break;
                    }
                }
                if (countFood[0] >= 1 && countFood[1] >= 1 && countFood[2] >= 1) {
                    countDays++;
                    break;
                }
            }
        }
        if (countDays >= 1) {
            breakfastLaunchDinner[0] = true;
        }
        if (countDays >= 3) {
            breakfastLaunchDinner[1] = true;
        }
        if (countDays >= 5) {
            breakfastLaunchDinner[2] = true;
        }
        return  breakfastLaunchDinner;
    }
}
