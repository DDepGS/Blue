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
import android.widget.ImageButton;
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
import com.example.blue.adapters.AdapterC;
import com.example.blue.models.comentario;
import com.example.blue.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MostrarPublicacion extends AppCompatActivity {

    TextView Titulo, Contenido, Autor, Fecha, Error;
    String idpubli, correou, URL, tipo;
    ImageButton Eliminar;
    Button Comentar;
    EditText Comentario;

    ListView lista;
    AdapterC adaptador;
    public ArrayList<comentario> arraycoment = new ArrayList<>();
    comentario coment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_publicacion);

        Titulo=findViewById(R.id.TituloPublicacion);
        Autor =findViewById(R.id.UsuarioAutor);
        Contenido = findViewById(R.id.ContenidoPublicacion);
        Fecha = findViewById(R.id.Fecha);
        Eliminar = findViewById(R.id.Eliminar);
        Eliminar.setVisibility(View.GONE);
        Error = findViewById(R.id.Error);
        Error.setVisibility(View.GONE);
        Comentario = findViewById(R.id.NuevoComentario);
        Comentar =findViewById(R.id.Comentar);

        lista=findViewById(R.id.listaComent);
        adaptador=new AdapterC(this,arraycoment);
        lista.setAdapter(adaptador);

        SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        tipo = preferences.getString("tipo", "1");
        correou = preferences.getString("correo", "");

        Intent intent= getIntent();
        idpubli= intent.getExtras().getString("ID_publicacion");

        Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Borrar(Constants.IP_ADDRESS +"BorrarPublicacion.php");
            }
        });

        Comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Comentario.getText().toString().isEmpty())
                {
                    Toast.makeText(MostrarPublicacion.this, "No hay comentario", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    NuevoComentario(Constants.IP_ADDRESS +"NuevoComentario.php");
                    NuevaNotif(Constants.IP_ADDRESS +"NuevaNotifComent.php");
                }
            }
        });

        URL=Constants.IP_ADDRESS +"MostrarPublicacion.php?ID_publicacion="+idpubli;

        MostrarP(URL);

        Comentarios(Constants.IP_ADDRESS +"MostrarComentarios.php?id_publicacion="+idpubli);


    }

    private void MostrarP(String URL)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id= jsonObject.getString("ID_publicacion");
                        String titulo = jsonObject.getString("Titulo");
                        String contenido=jsonObject.getString("Contenido");
                        String ID_paciente=jsonObject.getString("ID_paciente");
                        String ID_especialista= jsonObject.getString("ID_especialista");
                        String fecha = jsonObject.getString("Fecha");
                        String anonimo = jsonObject.getString("anonimo");

                        Titulo.setText(titulo);
                        Contenido.setText(contenido);
                        Fecha.setText(fecha);
                        if (ID_paciente.equals("null")) {
                            MostrarAutorE(Constants.IP_ADDRESS + "MostrarUsuarioE.php?ID_especialista=" + ID_especialista, anonimo);
                        } else if (ID_especialista.equals("null")) {
                            MostrarAutorP(Constants.IP_ADDRESS + "MostrarUsuarioP.php?ID_paciente=" + ID_paciente, anonimo);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void MostrarAutorP(String URL, String anonimo)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String usuario= jsonObject.getString("Usuario");
                        Autor.setText("De: "+usuario);
                        if(jsonObject.getString("Correo").equals(correou))
                        {
                            Eliminar.setVisibility(View.VISIBLE);
                        }
                        if(anonimo.equals("0")){
                            Autor.setText("De: "+usuario);
                        }else{
                            Autor.setText("De: Anónimo");
                        }


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void MostrarAutorE(String URL, String anonimo)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String usuario= jsonObject.getString("Usuario");
                        if(jsonObject.getString("Correo").equals(correou))
                        {
                            Eliminar.setVisibility(View.VISIBLE);
                        }
                        if(anonimo.equals("0")){
                            Autor.setText("De: "+usuario);
                        }else{
                            Autor.setText("De: Anónimo");
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void Borrar(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Se ha eliminado la publicación", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MiForo.class);
                finish();
                startActivity(intent);
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
                parametros.put("id", idpubli);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void NuevoComentario(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Comentario enviado", Toast.LENGTH_SHORT).show();
                Comentario.setText("");
                Intent intent = new Intent(getApplicationContext(), MostrarPublicacion.class);
                intent.putExtra("ID_publicacion", idpubli);
                finish();
                startActivity(intent);
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
                parametros.put("id_publicacion", idpubli);
                parametros.put("contenido", Comentario.getText().toString().trim());
                parametros.put("tipo",tipo);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void Comentarios(String URL)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id_coment= jsonObject.getString("ID_comentario");
                        String contenido=jsonObject.getString("Contenido");
                        String ID_paciente=jsonObject.getString("ID_paciente");
                        String ID_especialista= jsonObject.getString("ID_especialista");
                        String fecha= jsonObject.getString("Fecha");
                        String id_publi= jsonObject.getString("ID_publicacion");

                        coment = new comentario(id_coment,contenido,ID_paciente,ID_especialista,fecha,id_publi);
                        arraycoment.add(coment);
                        adaptador.notifyDataSetChanged();

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Error.setVisibility(View.VISIBLE);

                lista.setVisibility(View.GONE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void NuevaNotif(String URL)
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
                parametros.put("id_publicacion", idpubli);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}