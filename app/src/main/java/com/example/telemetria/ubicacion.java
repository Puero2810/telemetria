package com.example.telemetria;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ubicacion extends AppCompatActivity {
    private Button boton;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private final List<String> listdataheader = new ArrayList<>();
    private final HashMap<String, List<String>> listHashMap = new HashMap<>();
    MainActivity main = new MainActivity();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        //Lista
        listView = findViewById(R.id.explv);
        initData();
        listAdapter = new ExpandableListAdapter(ubicacion.this, listdataheader, listHashMap);
        listView.setAdapter(listAdapter);

        //Boton
        boton = (Button) findViewById(R.id.exit);
    }

    private void initData() {
        agregarBloque();
        agregarAula();
    }

    public void agregarBloque() {
        for (extintor i : main.getExtintores()) {
            String aula = i.getAula();
            String[] separar = aula.split("-");
            String parte0 = separar[0];
            listdataheader.add(parte0);
        }
        Set conjuntoA = new HashSet<>(listdataheader);
        listdataheader.clear();
        listdataheader.addAll(conjuntoA);
    }

    public void agregarAula() {
        for (String a : listdataheader) {
            List<String> ed = new ArrayList<>();
            for (extintor b : main.getExtintores()) {
                String aula = b.getAula();
                String[] separar = aula.split("-");
                String parte0 = separar[0];
                String parte1 = separar[1];
                if (parte0.equals(a)) {
                    ed.add(parte1);
                }
            }
            Set conjuntoA = new HashSet<>(ed);
            ed.clear();
            ed.addAll(conjuntoA);
            listHashMap.put(a, ed);
        }
    }

    public void irMenuPrincipal(View v) {
        Intent intent = new Intent(ubicacion.this, login.class);
        startActivity(intent);
    }
}