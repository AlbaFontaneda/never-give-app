package com.rigobertosl.nevergiveapp;

import android.provider.BaseColumns;

/**
 * Creamos esta clase para establecer el esquemas de las tablas
 */
public final class DataBaseContract {
    private DataBaseContract() {}

    /*Establecemos contenido de las tablas*/
    public static class DataBaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "tabla_ejercicios";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DAYS = "days";
        public static final String COLUMN_SERIES = "series";
        public static final String COLUMN_REPETICIONES = "repeticiones";
        public static final String COLUMN_DESCANSO = "descanso";
    }
}
