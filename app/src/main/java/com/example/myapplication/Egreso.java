package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Egreso extends Activity {

    private Spinner spConcep,spCategoria;
    TextView tvFecha;
    EditText etMonto;
    Double montoNegativo;


    public static final int REQUEST_CODE=20;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egreso);

        tvFecha = findViewById(R.id.tvFecha);
        etMonto=findViewById(R.id.etMonto);

        //Declaro el ARRAY, con sus respectivos valores ***************************************

        spConcep = findViewById(R.id.spConcep);
        String[]concepOpciones={"Efectivo","Debito","Transferencia","Credito"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, concepOpciones);
        spConcep.setAdapter(adapter1);

        spCategoria= findViewById(R.id.spCategoria);
        String[]catOpciones={"Alquiler","Mercados","Transporte","Impuestos","Servicios","Esparcimiento","Otros"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, catOpciones);
        spCategoria.setAdapter(adapter2);
        //

    }


    //Metodo para abrir el nuevo activity de la seleccion de fecha **********
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

    public void aceptarEgreso(View view) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        montoNegativo=Double.parseDouble(etMonto.getText().toString());
        montoNegativo=montoNegativo*-1;
        String montoEnNegativo=String.valueOf(montoNegativo);



        String concepto = spConcep.getSelectedItem().toString();
        String categoria = spCategoria.getSelectedItem().toString();
        String fecha = tvFecha.getText().toString();

        if (tvFecha.getText().equals("dd/mm/aa")) {
            Toast.makeText(this, "ERROR - Verificar los datos ingresados", Toast.LENGTH_SHORT).show();
        }
        else {

                montoNegativo=montoNegativo*-1;
                ContentValues registro = new ContentValues();

                registro.put("monto", montoEnNegativo);
                registro.put("fecha", fecha);
                registro.put("concepto", concepto);
                registro.put("categoria", categoria);

                bd.insert("datos", null, registro);
                bd.close();
                Toast.makeText(this, "Se cargaron los datos correctamente", Toast.LENGTH_SHORT).show();
    }



            finish();
        }

//


// **********
public void cerrarVentana(View view){

        finish();
}

}