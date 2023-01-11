package com.example.telemetria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final ArrayList<extintor> extintores = new ArrayList<>();
    private final ArrayList<extintor> ex = new ArrayList<>();
    private final ArrayList<Integer> listaA = new ArrayList<>();

    private static final ArrayList<String> laboratorios = new ArrayList<>();
    private final ArrayList<String> lab = new ArrayList<>();
    private final ArrayList<Integer> listaB = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(3000, 1000){
            @Override
            public void onTick(long l) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("extintor").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            int a = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                extintor agregar = new extintor(document.getId(), document.getString("aula"), document.getString("marca"), Integer.parseInt(String.valueOf(document.getLong("dia_expiracion"))), Integer.parseInt(String.valueOf(document.getLong("mes_expiracion"))), Integer.parseInt(String.valueOf(document.getLong("ano_expiracion"))), Integer.parseInt(String.valueOf(document.getLong("capacidad"))), Integer.parseInt(String.valueOf(document.getLong("porcentaje"))));
                                ex.add(agregar);
                                listaA.add(a);
                                a++;
                            }
                        }
                    }
                });

                db.collection("bloque").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            int b = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                for(String clave:document.getData().keySet()){
                                    ArrayList<Object> valor = new ArrayList<>();
                                    valor = (ArrayList<Object>) document.getData().get(clave);
                                    for (Object obj: valor){
                                        lab.add(document.getId()+clave+"-"+(String) obj);
                                        listaB.add(b);
                                        b++;
                                    }
                                }
                            }
                        }
                    }
                });
            }

            @Override
            public void onFinish() {
                Set conjuntoA = new HashSet<>(listaA);
                listaA.clear();
                listaA.addAll(conjuntoA);
                for(int i = 0; i < listaA.toArray().length; i++){
                    extintores.add(ex.get(i));
                }

                Set conjuntoB = new HashSet<>(listaB);
                listaB.clear();
                listaB.addAll(conjuntoB);
                for(int i = 0; i < listaB.toArray().length; i++){
                    laboratorios.add(lab.get(i));
                }

                System.out.println("Main");
                System.out.println(extintores);
                System.out.println(laboratorios);
                Intent intent= new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }
        }.start();
    }

    static ArrayList<String> getLaboratorios(){
        return laboratorios;
    }

    static ArrayList<extintor> getExtintores(){
        return extintores;
    }
}