package com.example.telemetria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;

public class escaner extends AppCompatActivity {
    private EditText e1;
    private EditText e2;
    private EditText e3;
    private Button e4;
    private Button b1;
    private int ano;
    private int mes;
    private int dia;
    private StorageReference sr;
    DatePickerDialog.OnDateSetListener setListener;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escaner);

        e1 = (EditText) findViewById(R.id.edit_text_marca);

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

        e3 = (EditText) findViewById(R.id.edit_text_capacidad);

        e4 = (Button) findViewById(R.id.button_codigo);
        e4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCamera();
            }
        });

        b1 = (Button) findViewById(R.id.agregar);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e1.getText().toString().equals("") || e2.getText().toString().equals("") || e3.getText().toString().equals("") || e4.getText().toString().equals("")){
                    Toast.makeText(escaner.this, "Ingrese bien los datos", Toast.LENGTH_SHORT);
                }
                else{
                    Map<String, Object> map = new HashMap<>();
                    map.put("ano_expiracion", ano);
                    map.put("capacidad", Integer.parseInt(e3.getText().toString()));
                    map.put("dia_expiracion", dia);
                    map.put("marca", e1.getText().toString());
                    map.put("mes_expiracion", mes);
                    map.put("qr", "");
                    map.put("ubicacion", "Laboratorio");
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("extintor").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Extintor agregado", Toast.LENGTH_LONG);
                            Intent intent= new Intent(escaner.this, login.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error al ingresar", Toast.LENGTH_LONG);
                        }
                    });
                }
            }
        });
    }

    private void onCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 2);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imagen = null;
            try {
                imagen = File.createTempFile("data", ".jpg", directorio);
            } catch (IOException e) {
                e.printStackTrace();
            }

            sr = FirebaseStorage.getInstance().getReference();
            StorageReference fp = sr.child("gs://telemetria-75492.appspot.com");
            System.out.println(imagen.toURI());
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    System.out.println("imagen");
//                }
//            });

        }
    }
}