package com.example.blue.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.example.blue.adapters.AdapterN;
import com.example.blue.models.Contacto;
import com.example.blue.models.notificacion;
import com.example.blue.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notificaciones extends AppCompatActivity {

    ListView lista;
    AdapterN adaptador;
    TextView Error;
    public ArrayList<notificacion> arraynotif = new ArrayList<>();
    String URL;
    notificacion notif;
    String correou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        String tipo = preferences.getString("tipo", "1");
        correou = preferences.getString("correo", "");



        Error=findViewById(R.id.Error);
        Error.setVisibility(View.GONE);

        lista=findViewById(R.id.listaNotif);
        adaptador = new AdapterN(this, arraynotif);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                if(arraynotif.get(position).getTipo().equals("Chat"))
                {
                    String[] usuario = arraynotif.get(position).getDescripcion().split(":");
                    Contacto user = new Contacto();
                    user.name = usuario[0];
                    user.email = usuario[1];
                    Intent intent = new Intent(getApplicationContext(), Chat.class);
                    intent.putExtra("Contacto", user);
                    startActivity(intent);
                }
                else if (arraynotif.get(position).getTipo().equals("Comentario"))
                {
                    Intent intent = new Intent(getApplicationContext(), MiForo.class);
                    startActivity(intent);
                }
                else if(arraynotif.get(position).getTipo().equals("Recordatorio"))
                {
                    Intent intent = new Intent(getApplicationContext(), Recordatorios.class);
                    startActivity(intent);
                }
                Estado(Constants.IP_ADDRESS +"CambiarEstadoNotif.php", position);

            }
        });

        if(tipo.equals("1"))
        {
            URL= Constants.IP_ADDRESS +"MostrarNotifP.php?correo="+correou;
        }
        else if(tipo.equals("2"))
        {
            URL = Constants.IP_ADDRESS +"MostrarNotifE.php?correo="+correou;
        }
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
                        String id= jsonObject.getString("ID_notificacion");
                        String nombre = jsonObject.getString("Nombre");
                        String descripcion=jsonObject.getString("Descripcion");
                        String ID_paciente=jsonObject.getString("ID_paciente");
                        String ID_especialista= jsonObject.getString("ID_especialista");
                        String tipo = jsonObject.getString("Tipo");
                        String estado = jsonObject.getString("Estado");

                        notif = new notificacion(id, nombre, descripcion, estado, ID_paciente,ID_especialista, tipo);
                        arraynotif.add(notif);
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

    private void Estado(String URL, int i)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                parametros.put("id_notif", arraynotif.get(i).getID_notificacion());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}