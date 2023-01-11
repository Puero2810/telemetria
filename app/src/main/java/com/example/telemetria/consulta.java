package com.example.telemetria;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class consulta extends AppCompatActivity {
    private TextView datos, logo;
    private ListView listado_ext;
    private Button salir;

    ArrayList <String> extintores;
    ArrayList<String> marca;
    ArrayList<String> ubicacion;
    ArrayList<Long> porcentaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        logo = (TextView)findViewById(R.id.text1);
        datos = (TextView)findViewById(R.id.tv1);
        listado_ext = (ListView)findViewById(R.id.lv1);
        MainActivity main = new MainActivity();

        extintores = new ArrayList<>();
        marca = new ArrayList<>();
        ubicacion = new ArrayList<>();
        porcentaje = new ArrayList<>();

        salir = (Button) findViewById(R.id.exit);

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(consulta.this, login.class);
                startActivity(intent);
            }
        });

        for(extintor i: main.getExtintores()){
            extintores.add(i.getId());
            marca.add(i.getMarca());
            ubicacion.add(i.getAula());
            porcentaje.add((long) i.getPorcentaje());
        }

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(consulta.this, R.layout.list_item2,extintores);
        listado_ext.setAdapter(adapter);
        listado_ext.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                datos.setText("El extintor " + listado_ext.getItemAtPosition(i) + " tiene los siguientes atributos:\n" + "marca: "
                        + marca.get(i) + "\n" + "ubicacion: " + ubicacion.get(i)+"\n" +
                        "porcentaje: " + porcentaje.get(i) + "\n");
            }
        });
    }
}