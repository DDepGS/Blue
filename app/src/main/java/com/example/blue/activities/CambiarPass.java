package com.example.blue.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class CambiarPass extends AppCompatActivity {

    String id, tipo;
    EditText ContrasenaN, ContrasenaV, ContrasenaN2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_pass);

        SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        tipo = preferences.getString("tipo", "1");
        id = getIntent().getStringExtra("id");

        Button cambiar = findViewById(R.id.Cambiar);
        ContrasenaN = findViewById(R.id.ContrasenaN);
        ContrasenaV = findViewById(R.id.ContrasenaV);
        ContrasenaN2 = findViewById(R.id.ContrasenaVerify);

        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContrasenaN.getText().toString().trim().equals(ContrasenaN2.getText().toString().trim())){
                    Cambiar(Constants.IP_ADDRESS +"CambiarPass.php");
                }else{
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Cambiar(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Se ha modificado"))
                {
                    SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("contrasena", ContrasenaN.getText().toString().trim());
                    editor.commit();
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
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
                parametros.put("id", id);
                parametros.put("Cnueva", ContrasenaN.getText().toString().trim());
                parametros.put("Cvieja", ContrasenaV.getText().toString().trim());
                parametros.put("tipo", tipo);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}