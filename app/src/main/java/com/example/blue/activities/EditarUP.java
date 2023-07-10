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

public class EditarUP extends AppCompatActivity {

    TextView Nombre, PrimerApellido, SegundoApellido, Usuario, Correo, Tel,
            Diagnostico, Medicamento;
    Button btnRegistro;
    String id, correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_up);

        SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        correo = preferences.getString("correo", "");

        id=getIntent().getStringExtra("id");

        Nombre=(TextView) findViewById(R.id.NombreInput);
        PrimerApellido=(TextView) findViewById(R.id.PApellidoIn);
        SegundoApellido=(TextView) findViewById(R.id.SApellidoIn);
        Usuario=(TextView) findViewById(R.id.UsuarioIn);
        Correo=(TextView) findViewById(R.id.CorreoIn);
        Tel=(TextView) findViewById(R.id.NumIn);
        Diagnostico=(TextView) findViewById(R.id.DiagnosticoInput);
        Medicamento=(TextView) findViewById(R.id.MedicamentoInput);

        Nombre.setText(getIntent().getStringExtra("nombre"));
        PrimerApellido.setText(getIntent().getStringExtra("apaterno"));
        SegundoApellido.setText(getIntent().getStringExtra("amaterno"));
        Usuario.setText(getIntent().getStringExtra("usuario"));
        Correo.setText(correo);
        Tel.setText(getIntent().getStringExtra("telefono"));
        Diagnostico.setText(getIntent().getStringExtra("diagnostico"));
        Medicamento.setText(getIntent().getStringExtra("medicamento"));


        btnRegistro=(Button) findViewById(R.id.Registrobtn);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 if(Nombre.getText().toString().isEmpty() || PrimerApellido.getText().toString().isEmpty()
                        ||Usuario.getText().toString().isEmpty() ||Correo.getText().toString().isEmpty()
                         ||Tel.getText().toString().isEmpty() || Diagnostico.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Llene todos los datos", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    RegistroP(Constants.IP_ADDRESS +"EditarUP.php");
                }
            }
        });
    }

    private void RegistroP(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("Se ha modificado"))
                {
                    SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("correo", Correo.getText().toString().trim());
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
                parametros.put("nombre", Nombre.getText().toString().trim());
                parametros.put("apaterno", PrimerApellido.getText().toString().trim());
                parametros.put("amaterno", SegundoApellido.getText().toString().trim());
                parametros.put("diagnostico", Diagnostico.getText().toString().trim());
                parametros.put("medicamento", Medicamento.getText().toString().trim());
                parametros.put("correo", Correo.getText().toString().trim());
                parametros.put("usuario", Usuario.getText().toString().trim());
                parametros.put("telefono", Tel.getText().toString().trim());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}