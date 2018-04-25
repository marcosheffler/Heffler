package com.example.marco.heffler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marco on 16/11/2017.
 */

public class BDcampos extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE campos (id INTEGER PRIMARY KEY AUTOINCREMENT, campo TEXT, lote TEXT, fecha TEXT, hectareas TEXT, cereal TEXT, maquina TEXT, rinde TEXT, empleados TEXT)";

    public BDcampos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqlCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST campos");
        db.execSQL(sqlCreate);

    }
}
