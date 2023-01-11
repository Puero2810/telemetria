package com.example.telemetria;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class escaner extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DatePickerDialog.OnDateSetListener setListener;
    private String valor;
    private EditText e1;
    private EditText e2;
    private EditText e3;
    private Spinner s4;
    private Button b5;
    private Button b6;
    private int ano;
    private int mes;
    private int dia;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escaner);

        //Listas
        MainActivity main = new MainActivity();
        for(extintor i: main.getExtintores()){
            int numero = Integer.parseInt(i.getId());
            numero+=1;
            valor = String.valueOf(numero);
        }

        //Marca
        e1 = (EditText) findViewById(R.id.edit_text_marca);

        //Fecha
        e2 = (EditText) findViewById(R.id.edit_text_date);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(escaner.this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                ano = year;
                mes = month;
                dia = day;
                e2.setText(date);
            }
        };

        //Capacidad
        e3 = (EditText) findViewById(R.id.edit_text_capacidad);

        //Bloque
        s4 = (Spinner) findViewById(R.id.spinner_bloque);
        s4.setAdapter(new ArrayAdapter<String>(escaner.this, android.R.layout.simple_spinner_dropdown_item, main.getLaboratorios()));

        //Agregar
        b5 = (Button) findViewById(R.id.agregar);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e1.getText().toString().equals("") || e2.getText().toString().equals("") || e3.getText().toString().equals("")){
                    Toast.makeText(escaner.this, "Ingrese los datos correctamente", Toast.LENGTH_SHORT).show();
                }
                else{
                    Map<String, Object> map = new HashMap<>();
                    map.put("ano_expiracion", ano);
                    map.put("mes_expiracion", mes);
                    map.put("dia_expiracion", dia);
                    map.put("capacidad", Integer.parseInt(e3.getText().toString()));
                    map.put("porcentaje", 100);
                    map.put("aula", s4.getSelectedItem());
                    map.put("marca", e1.getText().toString());
                    db.collection("extintor").document(valor).set(map);
                    Intent intent= new Intent(escaner.this, login.class);
                    startActivity(intent);
                }
            }
        });

        //Atras
        b6 = (Button) findViewById(R.id.atras);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(escaner.this, login.class);
                startActivity(intent);
            }
        });
    }
}