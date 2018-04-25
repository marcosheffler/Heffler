package com.example.marco.heffler;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Borrar extends AppCompatActivity implements View.OnClickListener{

    SQLiteDatabase db;

    EditText idBorrar;
    FloatingActionButton borrar;
    int idBuscado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar);

        BDcampos campos = new BDcampos(this,"baseCampos", null,2);

        db = campos.getWritableDatabase();

        String q = "SELECT id, campo, lote, fecha, hectareas, cereal FROM campos";
        Cursor registros = db.rawQuery(q, null);


        Tabla tabla = new Tabla(this, (TableLayout) findViewById(R.id.tabla));
        tabla.agregarCabecera(R.array.cabecera_borrar);

        if(registros.moveToFirst()){
            do{
                ArrayList<String> elementos = new ArrayList<String>();
                elementos.add(registros.getString(0));
                elementos.add(registros.getString(1));
                elementos.add(registros.getString(2));
                elementos.add(registros.getString(3));
                elementos.add(registros.getString(4));
                elementos.add(registros.getString(5));
                tabla.agregarFilaTabla(elementos);
            }while (registros.moveToNext());
        }

        idBorrar = (EditText) findViewById(R.id.idBor);
       // idBorrar.setText("0");
        borrar = (FloatingActionButton) findViewById(R.id.borrarId);

        borrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.borrarId:
                if(idBorrar.getText().toString().length() == 0 ){
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Debe escribir un identificador", Toast.LENGTH_SHORT);
                    toast1.show();
                }else {
                    idBuscado = Integer.parseInt(String.valueOf(idBorrar.getText()));

                    String q = "SELECT * FROM campos WHERE id= " + idBuscado + " ";
                    Cursor registros = db.rawQuery(q, null);

                    if (registros.moveToFirst()) {
                        db.execSQL("DELETE FROM campos WHERE id= " + idBuscado + " ");
                        Toast toast1 = Toast.makeText(getApplicationContext(), "Campo borrado", Toast.LENGTH_SHORT);
                        toast1.show();
                        Intent intent = new Intent(this, this.getClass());
                        startActivity(intent);
                        this.finish();
                        break;
                    }else {
                        Toast toast1 = Toast.makeText(getApplicationContext(), "No existe el identficador", Toast.LENGTH_SHORT);
                        toast1.show();
                    }
                }
        }
    }
    public void onBackPressed (){
        Intent intent = new Intent(this, VerCampos.class);
        startActivity(intent);
        this.finish();
    }

    }




