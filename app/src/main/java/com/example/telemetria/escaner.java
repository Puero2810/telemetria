package com.example.telemetria;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class escaner extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DatePickerDialog.OnDateSetListener setListener;
    private StorageReference sr;
    private String valor;
    private EditText e1;
    private EditText e2;
    private EditText e3;
    private Spinner s4;
    private Button b5;
    private int ano;
    private int mes;
    private int dia;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escaner);
        agregarDocument();

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

        s4 = (Spinner) findViewById(R.id.spinner_bloque);
        agregarSpinnerBloque();

        b5 = (Button) findViewById(R.id.agregar);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e1.getText().toString().equals("") || e2.getText().toString().equals("") || e3.getText().toString().equals("")){
                    Toast.makeText(escaner.this, "Ingrese bien los datos", Toast.LENGTH_SHORT);
                }
                else{
                    Map<String, Object> map = new HashMap<>();
                    map.put("ano_expiracion", ano);
                    map.put("mes_expiracion", mes);
                    map.put("dia_expiracion", dia);
                    map.put("capacidad", Integer.parseInt(e3.getText().toString()));
                    map.put("aula", s4.getSelectedItem());
                    map.put("marca", e1.getText().toString());
                    db.collection("extintor").document(valor).set(map);
                    Intent intent= new Intent(escaner.this, login.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void agregarDocument(){
        db.collection("extintor").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        int numero = Integer.parseInt(document.getId());
                        numero+=1;
                        valor = String.valueOf(numero);
                    }
                }
            }
        });
    }

    public void agregarSpinnerBloque(){
        db.collection("bloque").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<String> dynamicList = new ArrayList<String>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        for(String clave:document.getData().keySet()){
                            ArrayList<Object> valor = new ArrayList<>();
                            valor = (ArrayList<Object>) document.getData().get(clave);
                            for (Object obj: valor){
                                dynamicList.add(document.getId()+clave+"-"+(String) obj);
                            }
                        }
                    }
                    s4.setAdapter(new ArrayAdapter<String>(escaner.this, android.R.layout.simple_spinner_dropdown_item, dynamicList));
                }
            }
        });
    }
}