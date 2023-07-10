package com.example.blue.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.blue.R;
import com.example.blue.models.comentario;
import com.example.blue.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class AdapterC extends ArrayAdapter<comentario> {
    Context context;
    List<comentario>arregloc;
    TextView Usuario;

    public AdapterC(@NonNull Context context, List<comentario>arregloc)
    {
        super(context, R.layout.lista_comentarios, arregloc);
        this.context=context;
        this.arregloc=arregloc;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_comentarios,null,true);



        if(arregloc.get(position).getID_paciente().equals("null"))
        {
            MostrarAutorE(Constants.IP_ADDRESS +"MostrarUsuarioE.php?ID_especialista="+arregloc.get(position).getID_especialista());
        }
        else if(arregloc.get(position).getID_especialista().equals("null"))
        {
            MostrarAutorP(Constants.IP_ADDRESS +"MostrarUsuarioP.php?ID_paciente="+arregloc.get(position).getID_paciente());

        }

        Usuario = view.findViewById(R.id.Usuario);
        TextView Contenido = view.findViewById(R.id.Contenido);
        TextView Fecha = view.findViewById(R.id.Fecha);

        Contenido.setText(arregloc.get(position).getContenido());
        Fecha.setText(arregloc.get(position).getFecha());

        return view;
    }

    private void MostrarAutorE(String URL)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String usuario= jsonObject.getString("Usuario");
                        Usuario.setText(usuario);

                    } catch (JSONException e) {
                        Toast.makeText(context.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }

    private void MostrarAutorP(String URL)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String usuario= jsonObject.getString("Usuario");
                        Usuario.setText(usuario);

                    } catch (JSONException e) {
                        Toast.makeText(context.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }
}
