package com.example.blue.adapters;

import static android.content.Context.ALARM_SERVICE;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.example.blue.activities.EditarRec;
import com.example.blue.R;
import com.example.blue.activities.Recordatorios;
import com.example.blue.utilities.SampleBootReceiver;
import com.example.blue.models.recordatorio;
import com.example.blue.utilities.Constants;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterR extends ArrayAdapter<recordatorio> {

    Context context;
    List<recordatorio>arreglorec;
    String tipo, correo;
    public AdapterR(@NonNull Context context, List<recordatorio>arreglorec, String tipo, String correo)
    {
        super(context, R.layout.lista_recordatorios, arreglorec);
        this.context=context;
        this.arreglorec=arreglorec;
        this.tipo =tipo;
        this.correo=correo;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_recordatorios, null, true);

        TextView Nombre= view.findViewById(R.id.Nombre);
        TextView Desc = view.findViewById(R.id.Descripcion);

        Button activar = view.findViewById(R.id.activar);
        Button eliminar = view.findViewById(R.id.eliminar);
        ImageButton editar = view.findViewById(R.id.editar);

        if(tipo.equals("2"))
        {
            activar.setVisibility(View.GONE);
        }

        Nombre.setText(arreglorec.get(position).getNombreRec());
        Desc.setText(arreglorec.get(position).getDescripcion());

        activar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SampleBootReceiver.class);

                intent.putExtra("ID_recordatorio", Integer.parseInt(arreglorec.get(position).getID_recordatorio()));
                intent.putExtra("Nombre", arreglorec.get(position).getNombreRec());
                intent.putExtra("Descripcion", arreglorec.get(position).getDescripcion());

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context.getApplicationContext(), Integer.parseInt(arreglorec.get(position).getID_recordatorio()), intent, PendingIntent.FLAG_MUTABLE);

                String valor = arreglorec.get(position).getTiempoEj();
                String[] lista = valor.split(":");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(lista[0]));
                calendar.set(Calendar.MINUTE, Integer.parseInt(lista[1]));
                calendar.set(Calendar.SECOND, 0);

                if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                }

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                if(arreglorec.get(position).getRepeticion().equals("0"))
                {
                    alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                }
                else if(arreglorec.get(position).getRepeticion().equals("24"))
                {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY, pendingIntent);
                }
                else if(arreglorec.get(position).getRepeticion().equals("1"))
                {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_HOUR,pendingIntent);
                }

                Toast.makeText(context, "Alarma a las "+arreglorec.get(position).getTiempoEj(),Toast.LENGTH_LONG).show();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, SampleBootReceiver.class);

                intent.putExtra("ID_recordatorio", Integer.parseInt(arreglorec.get(position).getID_recordatorio()));
                intent.putExtra("Nombre", arreglorec.get(position).getNombreRec());
                intent.putExtra("Descripcion", arreglorec.get(position).getDescripcion());

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context.getApplicationContext(), Integer.parseInt(arreglorec.get(position).getID_recordatorio()), intent, PendingIntent.FLAG_MUTABLE);

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

                if (alarmManager!=null)
                {
                    alarmManager.cancel(pendingIntent);
                }

                String URL= Constants.IP_ADDRESS +"BorrarRec.php";

                StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context.getApplicationContext(),"Se ha eliminado", Toast.LENGTH_LONG).show();
                        Intent intent2 = new Intent(context, Recordatorios.class);
                        intent2.putExtra("Correo", correo);
                        ((Activity)context).finish();
                        context.startActivity(intent2);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context.getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros=new HashMap<String, String>();
                        parametros.put("id",arreglorec.get(position).getID_recordatorio());
                        return parametros;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(context.getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditarRec.class);

                intent.putExtra("ID_recordatorio", arreglorec.get(position).getID_recordatorio());
                intent.putExtra("Nombre", arreglorec.get(position).getNombreRec());
                intent.putExtra("Descripcion", arreglorec.get(position).getDescripcion());
                intent.putExtra("Tiempo", arreglorec.get(position).getTiempoEj());
                intent.putExtra("Repeticion", arreglorec.get(position).getRepeticion());
                intent.putExtra("Correo", correo);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
