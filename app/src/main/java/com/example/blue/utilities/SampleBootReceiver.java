package com.example.blue.utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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

public class SampleBootReceiver extends BroadcastReceiver {

    int id, repetir;
    String nombre, descripcion;

    @Override
    public void onReceive(Context context, Intent intent) {

        id= intent.getIntExtra("ID_recordatorio",0);
        nombre= intent.getStringExtra("Nombre");
        descripcion = intent.getStringExtra("Descripcion");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Recordatorios";
            String description = "Soy un canal";
            int importance = NotificationManager.IMPORTANCE_MAX;
            NotificationChannel channel = new NotificationChannel("Canal_rec", name, NotificationManager.IMPORTANCE_HIGH);
            channel.setVibrationPattern(new long[] {0,400,300,400});
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icono_notif_barra)
                .setContentTitle(nombre)
                .setContentText(descripcion)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setChannelId("Canal_rec")
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        notificationManager.notify(id, builder.build());

        String URL= Constants.IP_ADDRESS +"NuevaNotifRec.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                parametros.put("id_recordatorio", String.valueOf(id));
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);

    }

}
