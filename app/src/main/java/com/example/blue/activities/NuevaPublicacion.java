package com.example.blue.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class NuevaPublicacion extends AppCompatActivity {

    EditText Titulo, Contenido;
    Button Publicar;
    String anonimo = "0";
    String URL, correou, tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_publicacion);

        SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        tipo = preferences.getString("tipo", "1");
        correou = preferences.getString("correo", "");
        RadioButton publico = findViewById(R.id.esPublico);
        publico.setChecked(true);
        Titulo = findViewById(R.id.TituloPublicacion);
        Contenido = findViewById(R.id.ContenidoPublicacion);
        Publicar = findViewById(R.id.Publicar);

        Publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Titulo.getText().toString().isEmpty()||Contenido.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Falta llenar el contenido o el título", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Nueva(Constants.IP_ADDRESS +"NuevaPublicacion.php");
                }
            }
        });
    }

    public void esAnonimo(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.esAnonimo:
                if(checked){
                    anonimo = "1";
                }
                break;
            case R.id.esPublico:
                if (checked){
                    anonimo = "0";
                }
                break;
        }
    }

    private void Nueva(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.equals("Publicación ofensiva"))
                {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MiForo.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }

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
                parametros.put("correo", correou);
                parametros.put("titulo", Titulo.getText().toString().trim().trim());
                parametros.put("contenido", Contenido.getText().toString().trim());
                parametros.put("tipo",tipo);
                parametros.put("anonimo", anonimo);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}