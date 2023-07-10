package com.example.blue.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blue.models.Contacto;
import com.example.blue.adapters.ChatAdapter;
import com.example.blue.databinding.ActivityChatBinding;
import com.example.blue.models.ChatMessage;
import com.example.blue.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Chat extends AppCompatActivity {

    private ActivityChatBinding binding;
    private Contacto receiverUser;
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private String correou, name;
    private String conversionId = null;
    private SharedPreferences preferences;
    private Base64.Decoder decodificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        correou = preferences.getString("correo", "");
        name = preferences.getString("nombre","");
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadingReceiverDetails();
        init();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarMensajes();
                handler.postDelayed(this,5000);
            }
        },100);
    }

    private void init(){
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(
                chatMessages,
                correou);
        binding.chatRecyclerView.setAdapter(chatAdapter);
    }

    private void sendMessage(){
        if(binding.inputMessage.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Mensaje vacío", Toast.LENGTH_SHORT).show();
        }else {
            String URL = Constants.IP_ADDRESS + "sendMessage.php";
            generarNotificacion(name, correou, receiverUser.email);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    if (conversionId != null) {
                        updateConversion(binding.inputMessage.getText().toString());
                    } else {
                        HashMap<String, String> conversion = new HashMap<>();
                        conversion.put("lastMessage", binding.inputMessage.getText().toString());
                        conversion.put("mailSender", correou);
                        conversion.put("nameSender", name);
                        conversion.put("mailReceiver", receiverUser.email);
                        conversion.put("nameReceiver", receiverUser.name);
                        addConversion(conversion);
                    }
                    binding.inputMessage.setText(null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> message = new HashMap<>();
                    message.put("mailSender", correou);
                    message.put("mailReceiver", receiverUser.email);
                    message.put("message", binding.inputMessage.getText().toString());
                    return message;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private void mostrarMensajes(){
        chatMessages.clear();
        String URL= Constants.IP_ADDRESS + "consultaMensajes.php?correo="+correou+"&correo2="+receiverUser.email;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        ChatMessage chatMessage = new ChatMessage();
                        jsonObject = response.getJSONObject(i);
                        chatMessage.senderEmail = jsonObject.getString("mailSender");
                        chatMessage.receiverEmail = jsonObject.getString("mailReceiver");
                        byte[] mensajeDec = Base64.getDecoder().decode(jsonObject.getString("mensaje"));
                        String decodificado = new String(mensajeDec);
                        chatMessage.message = decodificado;
                        chatMessage.dateTime = jsonObject.getString("fecha");
                        chatMessages.add(chatMessage);
                    } catch (JSONException e) {
                        //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                //Collections.sort(chatMessages, (obj1, obj2) -> obj1.dateObject.compareTo(obj2.dateObject));
                int count = chatMessages.size();
                if(count == 0){
                    binding.textErrorMessage.setVisibility(View.VISIBLE);
                    chatAdapter.notifyDataSetChanged();
                }else{
                    chatAdapter.notifyItemRangeChanged(chatMessages.size(), chatMessages.size());
                    binding.chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
                    binding.textErrorMessage.setVisibility(View.GONE);
                }
                binding.chatRecyclerView.setVisibility(View.VISIBLE);
                binding.ProgressBar.setVisibility(View.GONE);
                if (conversionId == null){
                    checkForConversion();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.textErrorMessage.setVisibility(View.VISIBLE);
                binding.ProgressBar.setVisibility(View.GONE);
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


    private void loadingReceiverDetails(){
        receiverUser = (Contacto) getIntent().getSerializableExtra("Contacto");
        binding.textName.setText(receiverUser.name);
    }

    private void setListeners(){

        binding.imageBack.setOnClickListener(v -> onBackPressed());
        binding.layoutSend.setOnClickListener(v -> sendMessage());
    }

    private String getReadableDateTime(Date date){
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }

    private void addConversion(HashMap<String, String> conversion){
        String URL = Constants.IP_ADDRESS +"addConversion.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                conversionId = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return conversion;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void updateConversion(String lastmessage){
        String URL = Constants.IP_ADDRESS +"updateConversion.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> actualizacion = new HashMap<>();
                actualizacion.put("id", conversionId);
                actualizacion.put("lastMessage", lastmessage);
                actualizacion.put("mailSender", correou);
                actualizacion.put("nameSender", name);
                actualizacion.put("mailReceiver", receiverUser.email);
                actualizacion.put("nameReceiver", receiverUser.name);
                actualizacion.put("fecha", new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault())
                        .format(new Date()));
                return actualizacion;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void checkForConversion(){
        if(chatMessages.size() != 0){
            checkForConversionRemotely(
                    correou,
                    receiverUser.email
            );
            checkForConversionRemotely(
                    receiverUser.email,
                    correou
            );
        }
    }

    private void checkForConversionRemotely(String senderId, String receiverId){
        String URL = Constants.IP_ADDRESS +"buscarConversacion.php?correo="+senderId+"&correo2="+receiverId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = response.getJSONObject(0);
                    String converId = jsonObject.getString("id");
                    conversionId = converId;
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Sin Conexion", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void generarNotificacion(String nombreUsuario, String mailSend, String mailReceive){
        String URL = Constants.IP_ADDRESS +"NuevaNotifChat.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> actualizacion = new HashMap<>();
                actualizacion.put("Nombre", "Nuevo Mensaje de: ");
                actualizacion.put("Descripcion", nombreUsuario+":"+mailSend);
                actualizacion.put("id", mailReceive);
                return actualizacion;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}