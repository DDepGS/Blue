package com.example.blue.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blue.R;
import com.example.blue.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BuscarPaciente extends AppCompatActivity {
    ImageButton btnBuscar;
    ImageView fondo;
    EditText inputUsuario;
    TextView NoUsuario, NombreU, UsuarioU;
    LinearLayout InfoU;
    String correou;
    Button Agregar, Eliminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_paciente);

        btnBuscar = findViewById(R.id.btnBuscar);
        fondo = findViewById(R.id.fondo_buscar);
        inputUsuario = findViewById(R.id.InputBuscar);
        NoUsuario = findViewById(R.id.UsuarioNoExisteLabel);
        NoUsuario.setVisibility(View.GONE);
        InfoU = findViewById(R.id.UsuarioInfo);
        InfoU.setVisibility(View.GONE);
        NombreU = findViewById(R.id.NombreU);
        UsuarioU = findViewById(R.id.UsuarioU);
        Agregar = findViewById(R.id.Agregar);
        Eliminar = findViewById(R.id.Eliminar);

        SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        correou = preferences.getString("correo", "");

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buscarP(Constants.IP_ADDRESS +"BuscarPaciente.php?usuario="+inputUsuario.getText().toString().trim());
            }
        });

        Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                REP(Constants.IP_ADDRESS +"RegistroREP.php");
            }
        });

        Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                REP(Constants.IP_ADDRESS +"BorrarREP.php");
            }
        });
    }

    private void buscarP(String URL)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        //Toast.makeText(getApplicationContext(), jsonObject.getString("Usuario"), Toast.LENGTH_SHORT).show();
                        fondo.setVisibility(View.GONE);
                        InfoU.setVisibility(View.VISIBLE);
                        NombreU.setText(jsonObject.getString("Nombre")+" "+jsonObject.getString("APaterno")+" "+jsonObject.getString("AMaterno"));
                        UsuarioU.setText(jsonObject.getString("Usuario"));
                        NoUsuario.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NoUsuario.setVisibility(View.VISIBLE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void REP(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
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
                parametros.put("usuariop", UsuarioU.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}