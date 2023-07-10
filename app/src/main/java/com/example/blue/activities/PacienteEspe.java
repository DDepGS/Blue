package com.example.blue.activities;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
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
import com.example.blue.adapters.AdapterU;
import com.example.blue.models.u_paciente;
import com.example.blue.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PacienteEspe extends AppCompatActivity {

    ListView lista;
    AdapterU adaptador;
    TextView Error;

    public ArrayList<u_paciente> usuarios_pacientes= new ArrayList<>();

    u_paciente p_usuarios;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_espe);
        lista=findViewById(R.id.listaPacientes);
        lista=findViewById(R.id.listaPacientes);
        SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        String correou = preferences.getString("correo", "");
        adaptador=new AdapterU(this,usuarios_pacientes, correou);
        lista.setAdapter(adaptador);

        Error = findViewById(R.id.Error);
        Error.setVisibility(View.GONE);

        if(checkPermission()) {

        } else {
            requestPermissions();
        }

        String URL = Constants.IP_ADDRESS +"MostrarREP.php?correo="+correou;
        buscarP(URL);
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
                        String id= jsonObject.getString("ID_paciente");
                        String nombre = jsonObject.getString("Nombre");
                        String apellido1=jsonObject.getString("APaterno");
                        String apellido2=jsonObject.getString("AMaterno");
                        String diagnostico= jsonObject.getString("Diagnostico");
                        String contrasena=jsonObject.getString("Contrasena");
                        String medicamento=jsonObject.getString("Medicamento");
                        String correo=jsonObject.getString("Correo");
                        String telefono=jsonObject.getString("Telefono");
                        String usuario =jsonObject.getString("Usuario");

                        p_usuarios = new u_paciente( id, nombre, apellido1, apellido2, diagnostico,contrasena,medicamento,correo,usuario, telefono);
                        usuarios_pacientes.add(p_usuarios);
                        adaptador.notifyDataSetChanged();
                        Error.setVisibility(View.GONE);
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

    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 200) {
            if(grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if(writeStorage && readStorage) {
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }
}