package com.example.blue.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

public class RegistroU extends AppCompatActivity {

    LinearLayout Especialista, Paciente;
    TextView Nombre, PrimerApellido, SegundoApellido, Usuario, Correo, Contrasena, Tel,
            Diagnostico, Medicamento, Profesion, Cedula;
    Button btnRegistro;
    Integer opcion=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_u);
        Especialista= (LinearLayout) findViewById(R.id.Especialista);
        Especialista.setVisibility(View.GONE);

        Paciente= (LinearLayout) findViewById(R.id.Paciente);
        Paciente.setVisibility(View.GONE);

        Nombre=(TextView) findViewById(R.id.NombreInput);
        PrimerApellido=(TextView) findViewById(R.id.PApellidoIn);
        SegundoApellido=(TextView) findViewById(R.id.SApellidoIn);
        Usuario=(TextView) findViewById(R.id.UsuarioIn);
        Correo=(TextView) findViewById(R.id.CorreoIn);
        Contrasena=(TextView) findViewById(R.id.ContrasenaIn);
        Tel=(TextView) findViewById(R.id.NumIn);
        Diagnostico=(TextView) findViewById(R.id.DiagnosticoInput);
        Medicamento=(TextView) findViewById(R.id.MedicamentoInput);
        Profesion=(TextView) findViewById(R.id.ProfesionInput);
        Cedula=(TextView) findViewById(R.id.CedulaInput);

        btnRegistro=(Button) findViewById(R.id.Registrobtn);

        btnRegistro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {

                switch (opcion)
                {
                    case 1:
                        if(Nombre.getText().toString().isEmpty() && PrimerApellido.getText().toString().isEmpty()
                                && Usuario.getText().toString().isEmpty() &&Correo.getText().toString().isEmpty()
                                && Contrasena.getText().toString().isEmpty() &&Tel.getText().toString().isEmpty()
                                && Diagnostico.getText().toString().isEmpty())
                        {
                            Toast.makeText(getApplicationContext(),"Llene todos los datos", Toast.LENGTH_SHORT).show();
                        }
                        else if(Nombre.getText().toString().isEmpty() || PrimerApellido.getText().toString().isEmpty()
                                ||Usuario.getText().toString().isEmpty() ||Correo.getText().toString().isEmpty()
                                || Contrasena.getText().toString().isEmpty() ||Tel.getText().toString().isEmpty()
                                || Diagnostico.getText().toString().isEmpty())
                        {
                            Toast.makeText(getApplicationContext(),"Llene todos los datos", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            RegistroP(Constants.IP_ADDRESS +"RegistroPaciente.php");
                        }
                        break;
                    case 2:
                        if(Nombre.getText().toString().isEmpty() && PrimerApellido.getText().toString().isEmpty()
                                &&SegundoApellido.getText().toString().isEmpty() && Usuario.getText().toString().isEmpty()
                                &&Correo.getText().toString().isEmpty() && Contrasena.getText().toString().isEmpty()
                                &&Tel.getText().toString().isEmpty() && Profesion.getText().toString().isEmpty()
                                &&Cedula.getText().toString().isEmpty())
                        {
                            Toast.makeText(getApplicationContext(),"Llene todos los datos", Toast.LENGTH_SHORT).show();
                        }
                        else if(Nombre.getText().toString().isEmpty() || PrimerApellido.getText().toString().isEmpty()
                                ||Usuario.getText().toString().isEmpty() ||Correo.getText().toString().isEmpty()
                                || Contrasena.getText().toString().isEmpty() ||Tel.getText().toString().isEmpty()
                                ||Profesion.getText().toString().isEmpty() ||Cedula.getText().toString().isEmpty())
                        {
                            Toast.makeText(getApplicationContext(),"Llene todos los datos", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            RegistroE(Constants.IP_ADDRESS +"RegistroEsp.php");
                        }
                        break;
                    default:
                        Toast.makeText(RegistroU.this, "Llene todo el registro", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void TipoRegistro(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.PacienteIn:
                if (checked)
                    Paciente.setVisibility(View.VISIBLE);
                Especialista.setVisibility(View.GONE);
                opcion=1;
                break;
            case R.id.EspecialistaIn:
                if (checked)
                    Paciente.setVisibility(View.GONE);
                Especialista.setVisibility(View.VISIBLE);
                opcion=2;
                break;
        }
    }


    private void RegistroE(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Se ha registrado"))
                {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
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
                parametros.put("nombre", Nombre.getText().toString());
                parametros.put("apaterno", PrimerApellido.getText().toString());
                parametros.put("amaterno", SegundoApellido.getText().toString());
                parametros.put("profesion", Profesion.getText().toString());
                parametros.put("contrasena", Contrasena.getText().toString());
                parametros.put("cedula", Cedula.getText().toString().trim());
                parametros.put("correo", Correo.getText().toString());
                parametros.put("usuario", Usuario.getText().toString());
                parametros.put("telefono", Tel.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void RegistroP(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Se ha registrado"))
                {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
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
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> parametros=new HashMap<String, String>();
                parametros.put("nombre", Nombre.getText().toString().trim());
                parametros.put("apaterno", PrimerApellido.getText().toString().trim());
                parametros.put("amaterno", SegundoApellido.getText().toString().trim());
                parametros.put("diagnostico", Diagnostico.getText().toString().trim());
                parametros.put("contrasena", Contrasena.getText().toString().trim());
                parametros.put("medicamento", Medicamento.getText().toString().trim());
                parametros.put("correo", Correo.getText().toString().trim());
                parametros.put("usuario", Usuario.getText().toString().trim());
                parametros.put("telefono", Tel.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}