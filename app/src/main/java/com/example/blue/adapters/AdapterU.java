package com.example.blue.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blue.activities.Chat;
import com.example.blue.activities.PacienteEspe;
import com.example.blue.R;
import com.example.blue.activities.Recordatorios;
import com.example.blue.models.Contacto;
import com.example.blue.utilities.Reportes;
import com.example.blue.models.u_paciente;
import com.example.blue.utilities.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterU extends ArrayAdapter<u_paciente> {

    Context context;
    List<u_paciente> arreglop;
    String correou;


    public AdapterU(@NonNull Context context, List<u_paciente>arreglop, String correoU)
    {
        super(context, R.layout.lista_pacientes,arreglop);
        this.context=context;
        this.arreglop=arreglop;
        this.correou=correoU;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_pacientes,null,true);

        TextView nombre = view.findViewById(R.id.Nombre);
        TextView usuario = view.findViewById(R.id.Usuario);
        TextView medicamento = view.findViewById(R.id.Medicamento);
        TextView diagnostico = view.findViewById(R.id.Diagnostico);
        Button reportes = view.findViewById(R.id.reportes);
        Button chat = view.findViewById(R.id.chat);
        Button eliminar = view.findViewById(R.id.eliminar);
        Button recordatorios = view.findViewById(R.id.rec);


        nombre.setText(arreglop.get(position).getNombre()+" "+arreglop.get(position).getAPaterno()+" "+arreglop.get(position).getAMaterno());
        usuario.setText(arreglop.get(position).getUsuario());
        medicamento.setText(arreglop.get(position).getMedicamento());
        diagnostico.setText(arreglop.get(position).getDiagnostico());



        reportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), Reportes.class);
                intent.putExtra("ID_usuario", arreglop.get(position).getID_paciente());
                intent.putExtra("Nombre", arreglop.get(position).getNombre());
                intent.putExtra("Apellidos", arreglop.get(position).getAPaterno()+" "+arreglop.get(position).getAMaterno());
                context.startActivity(intent);

            }
        });

        recordatorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), Recordatorios.class);
                intent.putExtra("Correo", arreglop.get(position).getCorreo());
                context.startActivity(intent);
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    AlertDialog advertencia = new AlertDialog.Builder(context)
                            .setTitle("Eliminando paciente")
                            .setMessage("¿Desea eliminar?")
                            .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String URL = Constants.IP_ADDRESS + "BorrarREP.php";
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(context.getApplicationContext(), "Se ha eliminado", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(parent.getContext(), PacienteEspe.class);
                                            ((Activity) context).finish();
                                            context.startActivity(intent);
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                                        @NonNull
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> parametros = new HashMap<String, String>();
                                            parametros.put("correo", correou);
                                            parametros.put("usuariop", arreglop.get(position).getUsuario());
                                            return parametros;
                                        }
                                    };
                                    RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                                    requestQueue.add(stringRequest);
                                }
                            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).create();
                    advertencia.show();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contacto user = new Contacto();
                user.name = arreglop.get(position).getNombre() + " " + arreglop.get(position).getAPaterno() + " " + arreglop.get(position).getAMaterno();
                user.email = arreglop.get(position).getCorreo();
                Intent intent = new Intent(context, Chat.class);
                intent.putExtra("Contacto", user);
                context.startActivity(intent);
            }
        });

        return view;
    }

}
