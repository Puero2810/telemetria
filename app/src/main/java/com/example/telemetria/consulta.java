package com.example.telemetria;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class consulta extends AppCompatActivity {

    private TextView datos, logo;
    private ListView listado_ext;
    private Button salir;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
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

        db.collection("extintor").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                extintores.add(document.getId().toString());
                                marca.add(document.getString("marca"));
                                ubicacion.add(document.getString("aula"));
                                porcentaje.add(document.getLong("porcentaje"));
                                Log.d(TAG, document.getId() + " => " + document.getData());
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
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}