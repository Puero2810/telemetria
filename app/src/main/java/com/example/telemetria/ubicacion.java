package com.example.telemetria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    private final HashMap<String,List<String>> listHashMap = new HashMap<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);
        listView=findViewById(R.id.explv);
        boton = (Button)findViewById(R.id.exit);

        new CountDownTimer(4000, 1000){
            @Override
            public void onTick(long l) {
                initData();
            }

            @Override
            public void onFinish() {
                listAdapter=new ExpandableListAdapter(ubicacion.this,listdataheader,listHashMap);
                listView.setAdapter(listAdapter);

            }
        }.start();
    }

    private void initData(){
        agregarBloque();
        agregarAula();
    }

    public void agregarBloque(){
        db.collection("bloque").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        listdataheader.add(document.getId());
                    }
                    Set miConjunto = new HashSet<>(listdataheader);
                    listdataheader.clear();
                    listdataheader.addAll(miConjunto);

                }
            }
        });
    }

    public void agregarAula(){
        db.collection("bloque").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    int i = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        List<String> ed= new ArrayList<>();
                        for(String clave:document.getData().keySet()){
                            ArrayList<Object> valor = (ArrayList<Object>) document.getData().get(clave);
                            for (Object obj: valor){
                                ed.add(clave+obj);
                            }
                        }
                        listHashMap.put(listdataheader.get(i), ed);
                        i++;
                    }
                }
            }
        });
    }

    public void irMenuPrincipal(View v){
        Intent intent= new Intent(ubicacion.this, login.class);
        startActivity(intent);
    }
}