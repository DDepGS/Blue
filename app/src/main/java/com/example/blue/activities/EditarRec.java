package com.example.blue.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blue.R;
import com.example.blue.databinding.ActivityEditarRecBinding;
import com.example.blue.utilities.Constants;

import java.util.HashMap;
import java.util.Map;

public class EditarRec extends AppCompatActivity {


    String repeticion, correou, tipo, id;
    TextView Nombre, Descripcion;
    TimePicker Hora;
    Button Guardar, Cancelar;
    RadioButton selectR;
    private ActivityEditarRecBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_rec);

        SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        tipo = preferences.getString("tipo", "1");
        correou = preferences.getString("correo", "");

        Nombre = findViewById(R.id.NombreRec);
        Descripcion = findViewById(R.id.DescRec);

        Nombre.setText(getIntent().getStringExtra("Nombre"));
        Descripcion.setText(getIntent().getStringExtra("Descripcion"));

        String valor = getIntent().getStringExtra("Tiempo");
        String[] lista = valor.split(":");

        Hora = findViewById(R.id.hora);
        Hora.setIs24HourView(true);
        Hora.setHour(Integer.parseInt(lista[0]));
        Hora.setMinute(Integer.parseInt(lista[1]));

        repeticion= getIntent().getStringExtra("Repeticion");
        if(repeticion.equals("0"))
        {
            selectR = findViewById(R.id.RNunca);
            selectR.setChecked(true);
        }
        else if (repeticion.equals("1"))
        {
            selectR = findViewById(R.id.RHora);
            selectR.setChecked(true);
        }
        else if (repeticion.equals("24"))
        {
            selectR = findViewById(R.id.RDiario);
            selectR.setChecked(true);
        }
        id=getIntent().getStringExtra("ID_recordatorio");

        Guardar = findViewById(R.id.Guardar);
        Cancelar = findViewById(R.id.Cancelar);

        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Nombre.getText().toString().isEmpty()||Descripcion.getText().toString().isEmpty())
                {
                    Toast.makeText(EditarRec.this, "Llene todos los datos", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Actualizar(Constants.IP_ADDRESS +"EditarRec.php");
                }
            }
        });

        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void Repeticion(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.RNunca:
                if (checked)
                    repeticion="0";
                break;
            case R.id.RDiario:
                if (checked)
                    repeticion="24";
                break;
            case R.id.RHora:
                if (checked)
                    repeticion="1";
                break;
        }
    }

    private void Actualizar(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(EditarRec.this, "Se ha actualizado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Recordatorios.class);
                if (tipo.equals("2"))
                {
                    intent.putExtra("Correo",getIntent().getStringExtra("Correo"));
                }
                startActivity(intent);
                finish();

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
                parametros.put("nombre", Nombre.getText().toString().trim());
                parametros.put("descripcion", Descripcion.getText().toString().trim());
                parametros.put("hora", String.valueOf(Hora.getHour()));
                parametros.put("minuto", String.valueOf(Hora.getMinute()));
                parametros.put("repeticion", repeticion);
                parametros.put("id",id);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}