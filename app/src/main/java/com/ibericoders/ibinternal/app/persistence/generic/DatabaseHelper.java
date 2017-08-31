package com.ibericoders.ibinternal.app.persistence.generic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context ctx, String databaseName){

        //Recibimos los datos y los envía a SQLiteOpenHelper para empezar el proceso de creación de la BD

        super(ctx, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Instruccion SQL de creacion de la tabla de expenses.

        String expensesTableCreation = "create table expensesTable (_id integer primary key autoincrement,";
        expensesTableCreation += "name text,description text,amount double,date text,category integer)";

        //Ejecutar la instrucción 1.
        db.execSQL(expensesTableCreation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Instrucciones sobre qué hacer cuando se actualice la version de la base de datos.

    }
}
