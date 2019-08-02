package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.time.Month;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitulo, tvSaldo, tvIngresos, tvEgreso, tvTarjCred;
    private int varMes,varDia;
    private String mesNombre;
        double sumatoria, sumatoriaIngresos, sumatoriaEgresos, sumatoriaTarjCred;
    String prueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitulo=findViewById(R.id.tvTitulo);
        tvSaldo=findViewById(R.id.tvSaldo);
        tvIngresos=findViewById(R.id.tvIngresos);
        tvEgreso=findViewById(R.id.tvEgreso);
        tvTarjCred=findViewById(R.id.tvTarjCred);
        Calendar C= Calendar.getInstance();

        varMes = C.get(Calendar.MONTH);
        switch (varMes) {
            case 0:
                mesNombre = "Enero";
                break;
            case 1:
                mesNombre = "Febrero";
                break;
            case 2:
                mesNombre = "Marzo";
                break;
            case 3:
                mesNombre = "Abril";
                break;
            case 4:
                mesNombre = "Mayo";
                break;
            case 5:
                mesNombre = "Junio";
                break;
            case 6:
                mesNombre = "Julio";
                break;
            case 7:
                mesNombre = "Agosto";
                break;
            case 8:
                mesNombre = "Septiembre";
                break;
            case 9:
                mesNombre = "Octubre";
                break;
            case 10:
                mesNombre = "Noviembre";
                break;
            case 11:
                mesNombre = "Diciembre";
                break;
        }

        varDia=C.get(Calendar.DAY_OF_MONTH);

        tvTitulo.setText("Saldo al: " + varDia + " de " + mesNombre);

        actualizar();
    }

    public void ejecutarIngreso(View view){
        Intent i = new Intent(this, ingreso.class);
        startActivity(i);
    }
    public void ejecutarEgreso(View view){
        Intent i = new Intent(this, Egreso.class);
        startActivity(i);
    }
    public void ejecutarDetalle(View view){
        Intent i = new Intent(this, Detalle.class);
        startActivity(i);
    }

    public void actualizar(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Cursor var1=bd.rawQuery("select sum(monto) from datos",null);



//TABLA - CONCEPTO
        Cursor var4=bd.rawQuery("select sum(monto) from datos where concepto='Credito'",null);

        if (var4.moveToFirst()){
            sumatoriaTarjCred=var4.getInt(0);
        }
        sumatoriaTarjCred=sumatoriaTarjCred*-1;


        if (sumatoriaTarjCred == -0.0){
            sumatoriaTarjCred = 0.0;
        }

        tvTarjCred.setText("$ " + String.valueOf(sumatoriaTarjCred));

//////
        if (var1.moveToFirst()){
            sumatoria=var1.getInt(0);
        }

        if (sumatoria>15000) {
            tvSaldo.setTextColor(Color.rgb(0, 255, 0));
        }else if (sumatoria<7000) {
            tvSaldo.setTextColor(Color.rgb(255, 0, 0));
        } else {
            tvSaldo.setTextColor(Color.rgb(255, 255, 0));
        }

       sumatoria = sumatoria + sumatoriaTarjCred;
      tvSaldo.setText(String.valueOf(sumatoria));


//TABLA - INGRESOS
        Cursor var2=bd.rawQuery("select sum(monto) from datos where monto>0",null);

        if (var2.moveToFirst()){
            sumatoriaIngresos=var2.getInt(0);
        }
        tvIngresos.setText("$ " + String.valueOf(sumatoriaIngresos));
//TABLA - EGRESOS
        Cursor var3=bd.rawQuery("select sum(monto) from datos where monto<0",null);

        if (var3.moveToFirst()){
            sumatoriaEgresos=var3.getInt(0);
            sumatoriaEgresos=sumatoriaEgresos+sumatoriaTarjCred;
            sumatoriaEgresos=sumatoriaEgresos*-1;
            if (sumatoriaEgresos == -0.0){
                sumatoriaEgresos = 0.00;
            }
        }
        tvEgreso.setText("$ " + String.valueOf(sumatoriaEgresos));


        bd.close();
    }

    protected void onResume(){
        super.onResume();
        actualizar();

    }


}
