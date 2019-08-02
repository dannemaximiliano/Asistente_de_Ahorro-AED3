package com.example.myapplication;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class Detalle extends Activity {

    TextView txt1, txt2, txt3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();

        Cursor c = bd.rawQuery("SELECT fecha,concepto,monto FROM datos", null);

//Nos aseguramos de que existe al menos un registro

        if (c.moveToFirst()) {

            //Recorremos el cursor hasta que no haya más registros

            do {
                String fecha = c.getString(0);
                String concepto = c.getString(1);
                //int monto1 = c.getInt(2);
                String monto = c.getString(2);
                String[] cadena = {fecha, concepto};

                //          txt1.setGravity(Gravity.CENTER_VERTICAL);
                txt1.setPadding(15, 15, 15, 15);
                txt1.setBackgroundResource(R.color.colorPrimary);
                txt1.setTextColor(Color.WHITE);
                //            txt2.setGravity(Gravity.CENTER_VERTICAL);
                txt2.setPadding(15, 15, 15, 15);
                txt2.setBackgroundResource(R.color.colorPrimary);
                txt2.setTextColor(Color.WHITE);
                //              txt3.setGravity(Gravity.CENTER_VERTICAL);
                txt3.setPadding(15, 15, 15, 15);
                txt3.setBackgroundResource(R.color.colorPrimary);
                txt3.setTextColor(Color.WHITE);


                txt1.append(fecha + "\n");
                txt2.append(concepto + "\n");

                //REVISAR ESTA PARTE -- NO ME TOMA LOS VALORES (negativo postivo)


                if (Integer.parseInt(monto) < 0) {
                    txt3.setTextColor(Color.GREEN);
                } else
                    txt3.setTextColor(Color.RED);


                txt3.append(monto + "\n");

            } while (c.moveToNext());
        }
        bd.close();


    }
}
