package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ingreso extends AppCompatActivity {
    Spinner spConcep;
    TextView tvFecha;
    EditText etMonto;
    String selecSp;

    public static final int REQUEST_CODE=10;

//INGRESO-MAIN
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);

        tvFecha = findViewById(R.id.tvFecha);
        etMonto=findViewById(R.id.etMonto);

        //Declaro el ARRAY, con sus respectivos valores ***************************************

        spConcep = findViewById(R.id.spConcep);
        String[] concepOpciones = {"Sueldo", "Prestamo", "Otros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, concepOpciones);
        spConcep.setAdapter(adapter);

    }

//Metodo para abrir el nuevo activity de la seleccion de fecha ***************************************
    public void seleccionDeFecha(View view){
        Intent i = new Intent(this, Selecfecha.class);
        i.putExtra("Palabra", tvFecha.getText().toString());
        startActivityForResult(i,REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode==RESULT_OK && requestCode==REQUEST_CODE){
            tvFecha.setText(data.getExtras().getString("retorno"));
        }
    }

//
//Guardar cambios en la db ***************************************

    public void aceptarIngreso(View view) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String monto = etMonto.getText().toString();
        String concepto = spConcep.getSelectedItem().toString();
        String fecha = tvFecha.getText().toString();

        if (monto.isEmpty() || tvFecha.getText().equals("dd/mm/aa")) {
            Toast.makeText(this, "ERROR - Verificar los datos ingresados", Toast.LENGTH_SHORT).show();
        }
        else {
            ContentValues registro = new ContentValues();

            registro.put("monto", monto);
            registro.put("fecha", fecha);
            registro.put("concepto", concepto);

            bd.insert("datos", null, registro);
            bd.close();

            Toast.makeText(this, "Se cargaron los datos correctamente", Toast.LENGTH_SHORT).show();
            finish();
        }
        }


//
//Cerrar ventana sin guardar cambios ***************************************

    public void cerrarVentana(View view){
        finish();
    }
//
}