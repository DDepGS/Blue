package com.example.blue.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.blue.databinding.ActivityMenuBinding;
import com.example.blue.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Menu extends AppCompatActivity {
    Button btnChat, btnForo, btnEspacio, btnRecord,
    btnPaciente, btnNuevoPaciente;
    ImageView PFP;
    ImageButton btn1, btn2, btn3, btn4, btn5, btnNotif;
    private ActivityMenuBinding binding;
    String correou, Animo, URL, tipo;

    TextView Saludo, nombre;
    SharedPreferences preferences;

    LinearLayout PacienteEsp, Escala, ExcPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_menu);
        btnChat = findViewById(R.id.btnChat);
        btnEspacio = findViewById(R.id.btnPersonal);
        btnForo = findViewById(R.id.btnForo);
        btnPaciente = findViewById(R.id.btnPaEsp);
        btnRecord = findViewById(R.id.btnRecordatorios);
        PFP = findViewById(R.id.Pfp);
        PacienteEsp = findViewById(R.id.PacientesEsp);
        Escala =findViewById(R.id.EscalaAnimo);
        ExcPaciente = findViewById(R.id.ExcPaciente);
        btnNuevoPaciente = findViewById(R.id.btnNuevoPE);
        Saludo = findViewById(R.id.SaludoMenu);
        btn1 = findViewById(R.id.btnPesimo);
        btn2 = findViewById(R.id.btnMal);
        btn3 = findViewById(R.id.btnRegular);
        btn4 = findViewById(R.id.btnBien);
        btn5 = findViewById(R.id.btnMuyBien);
        btnNotif = findViewById(R.id.btnNotif);
        nombre = findViewById(R.id.nombreUsuario);

        preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        tipo = preferences.getString("tipo", "1");
        correou = preferences.getString("correo", "");

        loadUserDetails();
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if(tipo.equals("1"))
        {
            PacienteEsp.setVisibility(View.GONE);
            URL= Constants.IP_ADDRESS +"NotifPendientesP.php?correo="+correou;

        }
        else if(tipo.equals("2"))
        {
            Escala.setVisibility(View.GONE);
            ExcPaciente.setVisibility(View.GONE);
            URL= Constants.IP_ADDRESS +"NotifPendientesE.php?correo="+correou;
        }

        btnNuevoPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BuscarPaciente.class);
                startActivity(intent);
            }
        });

        PFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), Perfil.class);
                startActivity(intent);
            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Recordatorios.class);
                startActivity(intent);
            }
        });

        btnEspacio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainNota.class);
                startActivity(intent);
            }
        });

        btnPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PacienteEspe.class);
                startActivity(intent);
            }
        });

        btnForo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Foro.class);
                startActivity(intent);
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainChat.class);
                startActivity(intent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animo="1";
                RegistroA(Constants.IP_ADDRESS +"RegistroAnimo.php");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animo="2";
                RegistroA(Constants.IP_ADDRESS +"RegistroAnimo.php");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animo="3";
                RegistroA(Constants.IP_ADDRESS +"RegistroAnimo.php");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animo="4";
                RegistroA(Constants.IP_ADDRESS +"RegistroAnimo.php");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animo="5";
                RegistroA(Constants.IP_ADDRESS + "RegistroAnimo.php");
            }
        });

        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Notificaciones.class);
                startActivity(intent);
            }
        });

        Notif(URL);
    }

    @Override
    public void onBackPressed() { moveTaskToBack(false); }

    private void RegistroA(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
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
                parametros.put("animo", Animo);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void Notif(String URL)
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
                        btnNotif.setImageResource(R.drawable.icono_notif_si);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void loadUserDetails(){
        String URL;
        if(tipo.equals("2")){
            URL= Constants.IP_ADDRESS +"chatMainUser.php?correo="+correou;
        }else{
            URL= Constants.IP_ADDRESS +"chatMainUserPac.php?correo="+correou;
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                try {
                    jsonObject = response.getJSONObject(0);
                    String nombre = jsonObject.getString("Nombre") + " " + jsonObject.getString("APaterno") + " " + jsonObject.getString("AMaterno");
                    guardarNombre(nombre);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Sin Conexion", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void guardarNombre(String name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nombre", name);
        nombre.setText(name);
        editor.commit();
    }
}