package com.example.blue.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.blue.adapters.AdapterF;
import com.example.blue.models.publicacion;
import com.example.blue.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MiForo extends AppCompatActivity {

    ListView lista;
    AdapterF adaptador;
    Button Todo, nueva;
    TextView Error;
    public ArrayList<publicacion> arraypubli = new ArrayList<>();
    String URL;
    publicacion publi;
    String correou;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_foro);
        SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        String tipo = preferences.getString("tipo", "1");
        correou = preferences.getString("correo", "");

        Todo=findViewById(R.id.Todo);
        Error=findViewById(R.id.Error);
        Error.setVisibility(View.GONE);
        nueva = findViewById(R.id.Nueva);

        lista=findViewById(R.id.listaPubli);
        adaptador=new AdapterF(this,arraypubli);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MostrarPublicacion.class);
                intent.putExtra("ID_publicacion", arraypubli.get(position).getID_publicacion());
                startActivity(intent);
            }
        });

        if(tipo.equals("1"))
        {
            URL= Constants.IP_ADDRESS +"MostrarForoP.php?correo="+correou;
        }
        else if(tipo.equals("2"))
        {
            URL= Constants.IP_ADDRESS +"MostrarForoE.php?correo="+correou;
        }

        Todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Foro.class);
                finish();
                startActivity(intent);
            }
        });

        nueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NuevaPublicacion.class);
                startActivity(intent);
            }
        });

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
                        String id= jsonObject.getString("ID_publicacion");
                        String Titulo = jsonObject.getString("Titulo");
                        String Contenido=jsonObject.getString("Contenido");
                        String ID_paciente=jsonObject.getString("ID_paciente");
                        String ID_especialista= jsonObject.getString("ID_especialista");

                        publi = new publicacion(id,Titulo,Contenido,ID_paciente,ID_especialista);
                        arraypubli.add(publi);
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