package com.example.blue.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.blue.R;
import com.example.blue.adapters.AdapterR;
import com.example.blue.models.recordatorio;
import com.example.blue.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Recordatorios extends AppCompatActivity {

    ListView lista;
    AdapterR adaptador;
    TextView Error;

    public ArrayList<recordatorio> arrayrec = new ArrayList<>();

    String URL;
    recordatorio rec;
    String correou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorios);
        final Handler handler = new Handler();
        SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        String tipo = preferences.getString("tipo", "1");

        if (tipo.equals("1"))
        {
            correou = preferences.getString("correo", "");

        }
        else if (tipo.equals("2"))
        {
            correou= getIntent().getStringExtra("Correo");
        }

        Button nuevo = findViewById(R.id.NuevoRec);

        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NuevoRec.class);
                if(tipo.equals("2"))
                {
                    intent.putExtra("Correo", correou);
                }
                startActivity(intent);
            }
        });

        Error=findViewById(R.id.Error);
        Error.setVisibility(View.GONE);

        lista=findViewById(R.id.listaRec);
        adaptador=new AdapterR(this,arrayrec, tipo, correou);
        lista.setAdapter(adaptador);

        URL= Constants.IP_ADDRESS +"MostrarRec.php?correo="+correou;

        listar(URL);
    }

    private void listar(String URL)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id= jsonObject.getString("ID_recordatorio");
                        String nombre = jsonObject.getString("NombreRec");
                        String descripcion=jsonObject.getString("Descripcion");
                        String ID_paciente=jsonObject.getString("ID_paciente");
                        String tiempo= jsonObject.getString("TiempoEj");
                        String repeticion = jsonObject.getString("Repeticion");

                        rec = new recordatorio(id,nombre,descripcion,ID_paciente,tiempo, repeticion);
                        arrayrec.add(rec);
                        adaptador.notifyDataSetChanged();

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Error.setVisibility(View.VISIBLE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}