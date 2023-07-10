package com.example.blue.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.blue.databinding.ActivityMainChatBinding;
import com.example.blue.models.Contacto;
import com.example.blue.adapters.RecentConversationsAdapter;
import com.example.blue.listeners.ConversionListener;
import com.example.blue.models.ChatMessage;
import com.example.blue.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class MainChat extends AppCompatActivity implements ConversionListener {

    private ActivityMainChatBinding binding;
    private String correou, tipo;
    private List<ChatMessage> conversations;
    private RecentConversationsAdapter conversationsAdapter;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainChatBinding.inflate(getLayoutInflater());
        preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        correou = preferences.getString("correo", "");
        tipo = preferences.getString("tipo", "");
        setContentView(binding.getRoot());
        init();
        loadUserDetails();
        setListeners();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getConversions();
                handler.postDelayed(this,5000);
            }
        },100);
    }

    private void init(){
        conversations = new ArrayList<>();
        conversationsAdapter = new RecentConversationsAdapter(conversations, this);
        binding.conversationsRecyclerView.setAdapter(conversationsAdapter);
        binding.imageProfile.setOnClickListener(v -> onBackPressed());
    }

    private void setListeners(){
        binding.fabNewchat.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), UsersActivity.class)));
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
                    binding.textName.setText("Chats de " + nombre);
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
        editor.commit();
    }

    private void getConversions(){
        conversations.clear();
        String URL= Constants.IP_ADDRESS +"consultaConversacion.php?correo="+correou;;
        loading(true);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        ChatMessage chatMessage = new ChatMessage();
                        jsonObject = response.getJSONObject(i);
                        String senderMail = jsonObject.getString("mailSender");
                        String receiverMail = jsonObject.getString("mailReceiver");
                        chatMessage.senderEmail = senderMail;
                        chatMessage.receiverEmail = receiverMail;
                        if(correou.equals(senderMail)){
                            chatMessage.conversionName = jsonObject.getString("receiverName");
                            chatMessage.conversionId = jsonObject.getString("mailReceiver");
                        }else{
                            chatMessage.conversionName = jsonObject.getString("senderName");
                            chatMessage.conversionId = jsonObject.getString("mailSender");
                        }
                        byte[] mensajeDec = Base64.getDecoder().decode(jsonObject.getString("lastMessage"));
                        String decodificado = new String(mensajeDec);
                        chatMessage.message = decodificado;
                        //date
                        conversations.add(chatMessage);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if(conversations.size() > 0){
                    loading(false);
                    binding.textErrorMessage.setVisibility(View.GONE);
                    conversationsAdapter.notifyDataSetChanged();
                    binding.conversationsRecyclerView.smoothScrollToPosition(0);
                    binding.conversationsRecyclerView.setVisibility(View.VISIBLE);
                }else{
                    loading(false);
                    showErrorMessage();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading(false);
                showErrorMessage();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void showErrorMessage(){
        binding.textErrorMessage.setText(String.format("%s", "Sin conversaciones recientes"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConversionClicked(Contacto user) {
        Intent intent = new Intent(getApplicationContext(), Chat.class);
        intent.putExtra("Contacto", user);
        startActivity(intent);
    }
}