package com.example.blue.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.blue.R;
import com.example.blue.activities.CambiarPass;
import com.example.blue.activities.EditarUE;
import com.example.blue.activities.EditarUP;
import com.example.blue.activities.Inicio;
import com.example.blue.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Perfil extends AppCompatActivity {
    Button btnCerrar, btnEditar, btnCambiarContra;
    String correo, id, nombre, diagnostico,
            medicamento, apaterno, amaterno, contrasena,
            cedula, profesion, usuario, telefono;
    TextView NombrePerfil, Usuario, Info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        correo = preferences.getString("correo", "");
        String tipo = preferences.getString("tipo", "1");

        NombrePerfil = findViewById(R.id.NombrePerfil);
        Usuario = findViewById(R.id.UsuarioLabel);
        btnCerrar = findViewById(R.id.btnCerrarSesion);
        Info = findViewById(R.id.InfoPersonal);
        btnEditar = findViewById(R.id.btnEditarPerfil);
        btnCambiarContra = findViewById(R.id.CambiarContra);

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();

                Intent intent = new Intent(getApplicationContext(), Inicio.class);
                startActivity(intent);
                finish();
            }
        });

        if(tipo.equals("1"))
        {
            buscarUP(Constants.IP_ADDRESS +"BuscarPerfilP.php?correo="+correo);
        }
        else if( tipo.equals("2"))
        {
            buscarUE(Constants.IP_ADDRESS +"BuscarPerfilE.php?correo="+correo);
        }

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tipo.equals("1"))
                {
                    Intent intent = new Intent(getApplicationContext(), EditarUP.class);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("apaterno", apaterno);
                    intent.putExtra("amaterno", amaterno);
                    intent.putExtra("telefono", telefono);
                    intent.putExtra("diagnostico", diagnostico);
                    intent.putExtra("medicamento", medicamento);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("id", id);
                    startActivity(intent);

                }
                else if(tipo.equals("2"))
                {
                    Intent intent = new Intent(getApplicationContext(), EditarUE.class);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("apaterno", apaterno);
                    intent.putExtra("amaterno", amaterno);
                    intent.putExtra("telefono", telefono);
                    intent.putExtra("cedula", cedula);
                    intent.putExtra("profesion", profesion);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });

        btnCambiarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), CambiarPass.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

    }



    private void buscarUP(String URL)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        id= jsonObject.getString("ID_paciente");
                        nombre= jsonObject.getString("Nombre");
                        apaterno= jsonObject.getString("APaterno");
                        amaterno = jsonObject.getString("AMaterno");
                        usuario= jsonObject.getString("Usuario");
                        contrasena=jsonObject.getString("Contrasena");
                        diagnostico= jsonObject.getString("Diagnostico");
                        medicamento = jsonObject.getString("Medicamento");
                        telefono = jsonObject.getString("Telefono");
                        NombrePerfil.setText(jsonObject.getString("Nombre")+" "+jsonObject.getString("APaterno")+" "+jsonObject.getString("AMaterno"));
                        Usuario.setText(jsonObject.getString("Usuario"));
                        Info.setText("Medicamentos: "+jsonObject.getString("Medicamento")+"\n\nDiagnóstico: "+jsonObject.getString("Diagnostico"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void buscarUE(String URL)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        id= jsonObject.getString("ID_especialista");
                        nombre= jsonObject.getString("Nombre");
                        apaterno= jsonObject.getString("APaterno");
                        amaterno = jsonObject.getString("AMaterno");
                        usuario= jsonObject.getString("Usuario");
                        contrasena=jsonObject.getString("Contrasena");
                        cedula= jsonObject.getString("Cedula");
                        profesion = jsonObject.getString("Profesion");
                        telefono = jsonObject.getString("Telefono");
                        NombrePerfil.setText(jsonObject.getString("Nombre")+" "+jsonObject.getString("APaterno")+" "+jsonObject.getString("AMaterno"));
                        Usuario.setText(jsonObject.getString("Usuario"));
                        Info.setText("Profesión: "+jsonObject.getString("Profesion")+"\n\nCédula: "+jsonObject.getString("Cedula"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}