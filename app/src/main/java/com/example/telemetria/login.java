package com.example.telemetria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {
    private Button b1;
    private Button b2;
    private Button b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b1 = (Button) findViewById(R.id.escaner);
        b2 = (Button) findViewById(R.id.ubicacion);
        b3 = (Button) findViewById(R.id.consulta);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(login.this, escaner.class);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent= new Intent(login.this, consulta.class);
                //startActivity(intent);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(login.this, consulta.class);
                startActivity(intent);
            }
        });


        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        //db.collection("extintor").document("1").get().addOnSuccessListener(
        //          new OnSuccessListener<DocumentSnapshot>() {
        //            @Override
        //            public void onSuccess(DocumentSnapshot documentSnapshot) {
        //                if(documentSnapshot.exists()){
        //                    String usuario = documentSnapshot.getString("marca");
        //                    String contrasena = documentSnapshot.getString("ubicacion");
        //                    System.out.println(usuario);
        //                    System.out.println(contrasena);
        //                }
        //            }
        //        }
        //);
    }
}