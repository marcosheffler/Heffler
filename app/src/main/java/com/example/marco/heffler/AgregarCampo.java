package com.example.marco.heffler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AgregarCampo extends AppCompatActivity implements View.OnClickListener{

    FloatingActionButton agregar;
    EditText campo, lote, fecha, hectareas, maquina, rinde, empleados;
    private Spinner cereal;
    SQLiteDatabase db;

    //variable para el cereal seleccionado.
    String cerealSeleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_campo);

        //crea el adaptador para llenar la lista desplegable
        ArrayAdapter<CharSequence> adaptador =
                ArrayAdapter.createFromResource(this,
                        R.array.valores_lista_cerales,
                        android.R.layout.simple_spinner_item);

        campo = (EditText) findViewById(R.id.campoAg);
        lote = (EditText) findViewById(R.id.loteAg);
        fecha = (EditText) findViewById(R.id.fechaAg);
        hectareas = (EditText) findViewById(R.id.hectareasAg);
        cereal = (Spinner) findViewById(R.id.cerealAg);
            // hace lo necesario  para obtener el itn seleccionado
            adaptador.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
            cereal.setAdapter(adaptador);
            cereal.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        cerealSeleccionado = parent.getItemAtPosition(position).toString();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        cerealSeleccionado= "";
                    }
                });
            cereal.setSelection(0);


        maquina = (EditText) findViewById(R.id.maquinaAg);
        rinde = (EditText) findViewById(R.id.rindeAg);
        empleados = (EditText) findViewById(R.id.empleadosAg);
        agregar = (FloatingActionButton) findViewById(R.id.agregarCamp);

        agregar.setOnClickListener(this);
        fecha.setOnClickListener(this);

        BDcampos campos = new BDcampos(this,"baseCampos", null,2);

        db = campos.getWritableDatabase();

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        fecha.setText(day + " / " + month + " / " + year);


    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.agregarCamp:
                db.execSQL("INSERT INTO campos (campo, lote, fecha, hectareas, cereal, maquina, rinde, empleados)" + "VALUES ( '"+campo.getText()+"' , '"+lote.getText()+"' ,'"+fecha.getText()+"' , '" + hectareas.getText() + "' , '" + cerealSeleccionado + "' , '" + maquina.getText() + "' , '"+rinde.getText()+"' ,'" + empleados.getText() + "')");
                Toast toast1 = Toast.makeText(getApplicationContext(), "Campo agregado", Toast.LENGTH_SHORT);
                toast1.show();
                Intent intent = new Intent(this, VerCampos.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.fechaAg:
                showDatePickerDialog();
        }

    }

    private void showDatePickerDialog() {

        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                fecha.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void onBackPressed (){
        Intent intent = new Intent(this, VerCampos.class);
        startActivity(intent);
        this.finish();
    }


    private  void nada(){

    }
}
