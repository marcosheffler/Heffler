package com.example.marco.heffler;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import java.util.ArrayList;

public class VerCampos extends AppCompatActivity implements View.OnClickListener {

    SQLiteDatabase db;
    FloatingActionButton borrarFloat, agregarFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_campos);

        BDcampos campos = new BDcampos(this,"baseCampos", null,2);

        db = campos.getWritableDatabase();

        String q = "SELECT * FROM campos";
        Cursor registros = db.rawQuery(q, null);


        Tabla tabla = new Tabla(this, (TableLayout) findViewById(R.id.tabla));
        tabla.agregarCabecera(R.array.cabecera_general);

        if(registros.moveToFirst()){
            do{
                ArrayList<String> elementos = new ArrayList<String>();
                elementos.add(registros.getString(1));
                elementos.add(registros.getString(2));
                elementos.add(registros.getString(3));
                elementos.add(registros.getString(4));
                elementos.add(registros.getString(5));
                elementos.add(registros.getString(6));
                elementos.add(registros.getString(7));
                elementos.add(registros.getString(8));
                tabla.agregarFilaTabla(elementos);
            }while (registros.moveToNext());
        }

        borrarFloat = (FloatingActionButton) findViewById(R.id.borraFlot);
        borrarFloat.setOnClickListener(this);
        agregarFloat = (FloatingActionButton) findViewById(R.id.agregaFlot);
        agregarFloat.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.borraFlot:
                    Intent intent = new Intent(this, Borrar.class);
                    startActivity(intent);
                    this.finish();
                    break;
                case R.id.agregaFlot:
                    Intent intent2 = new Intent(this, AgregarCampo.class);
                    startActivity(intent2);
                    this.finish();
                    break;
            }

        }

    public void onBackPressed (){
        setResult(RESULT_OK);
        finish();
    }

}
