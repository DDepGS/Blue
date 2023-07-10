package com.example.blue.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class Login extends AppCompatActivity {
    TextView Contrasena, Correo;
    Button Login;
    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Contrasena= findViewById(R.id.Contrasena);
        Correo= findViewById(R.id.UsuarioCorreo);
        Login = findViewById(R.id.btnLogin);

        recuperarSesion();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Contrasena.getText().toString().isEmpty() && !Correo.getText().toString().isEmpty())
                {
                    ValidarPaciente(Constants.IP_ADDRESS +"LoginPaciente.php");
                }
                else if(!Contrasena.getText().toString().isEmpty() || !Correo.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Llene todos los datos", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Llene todos los datos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void ValidarPaciente(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty())
                {
                    Intent intent = new Intent(getApplicationContext(), Menu.class);
                    tipo="1";
                    guardarSesion();
                    startActivity(intent);
                    finish();
                }
                else
                {
                    ValidarEsp(Constants.IP_ADDRESS +"LoginEsp.php");
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
                parametros.put("contrasena", Contrasena.getText().toString().trim());
                parametros.put("correo", Correo.getText().toString().trim());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void ValidarEsp(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty())
                {

                    Intent intent = new Intent(getApplicationContext(), Menu.class);
                    tipo="2";
                    guardarSesion();
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Datos incorrectos", Toast.LENGTH_SHORT).show();
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
                parametros.put("contrasena", Contrasena.getText().toString().trim());
                parametros.put("correo", Correo.getText().toString().trim());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void guardarSesion()
    {
        SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("correo", Correo.getText().toString().trim());
        editor.putString("contrasena", Contrasena.getText().toString().trim());
        editor.putBoolean("sesion", true);
        editor.putString("tipo", tipo);
        editor.commit();
    }

    private void recuperarSesion()
    {
        SharedPreferences preferences=getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        Correo.setText(preferences.getString("correo", ""));
        Contrasena.setText(preferences.getString("contrasena", ""));
        tipo= preferences.getString("tipo", "1");
    }
}