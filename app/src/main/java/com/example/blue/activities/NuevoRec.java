package com.example.blue.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blue.R;
import com.example.blue.utilities.Constants;

import java.util.HashMap;
import java.util.Map;

public class NuevoRec extends AppCompatActivity {

    String repeticion, correou, tipo;
    TextView Nombre, Descripcion;
    TimePicker Hora;
    Button Guardar, Cancelar;
    RadioButton Nunca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_rec);
        Nunca = findViewById(R.id.RNunca);
        Nunca.setChecked(true);
        repeticion = "0";
        SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
         tipo = preferences.getString("tipo", "1");

        if (tipo.equals("1"))
        {
            correou = preferences.getString("correo", "");

        }
        else if (tipo.equals("2"))
        {
            correou= getIntent().getStringExtra("Correo");
        }


        Nombre = findViewById(R.id.NombreRec);
        Descripcion = findViewById(R.id.DescRec);

        Hora = findViewById(R.id.hora);
        Hora.setIs24HourView(true);

        Guardar = findViewById(R.id.Guardar);
        Cancelar = findViewById(R.id.Cancelar);

        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Nombre.getText().toString().isEmpty()||Descripcion.getText().toString().isEmpty())
                {
                    Toast.makeText(NuevoRec.this, "Llene todos los datos", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    RegistroR(Constants.IP_ADDRESS +"NuevoRec.php");
                }
            }
        });

        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Recordatorios.class);
                if(tipo.equals("2"))
                {
                    intent.putExtra("Correo", correou);
                }
                startActivity(intent);
                finish();
            }
        });


    }

    public void Repeticion(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.RNunca:
                if (checked)
                    repeticion="0";
                break;
            case R.id.RDiario:
                if (checked)
                    repeticion="24";
                break;
            case R.id.RHora:
                if (checked)
                    repeticion="1";
                break;
        }
    }

    private void RegistroR(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Recordatorios.class);
                if(tipo.equals("2"))
                {
                    intent.putExtra("Correo", correou);
                }
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<String, String>();
                parametros.put("nombre", Nombre.getText().toString().trim());
                parametros.put("descripcion", Descripcion.getText().toString().trim());
                parametros.put("hora", String.valueOf(Hora.getHour()));
                parametros.put("minuto", String.valueOf(Hora.getMinute()));
                parametros.put("repeticion", repeticion);
                parametros.put("correo", correou);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}