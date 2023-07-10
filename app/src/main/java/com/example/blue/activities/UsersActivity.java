package com.example.blue.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.blue.databinding.ActivityUsersBinding;
import com.example.blue.models.Contacto;
import com.example.blue.listeners.UserListener;
import com.example.blue.adapters.AdapterContacto;
import com.example.blue.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity implements UserListener {

    private ActivityUsersBinding binding;
    private String correou, tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
        correou = preferences.getString("correo", "");
        tipo = preferences.getString("tipo", "");
        setListeners();
        getUsers();
    }

    private void setListeners(){
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }

    private void getUsers(){
        String URL;
        loading(true);
        if(tipo.equals("2")){
            URL= Constants.IP_ADDRESS +"getUsersEsp.php?correo="+correou;
        }else{
            URL=Constants.IP_ADDRESS +"getUsersPac.php?correo="+correou;
        }

        List<Contacto> users = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        Contacto usuario = new Contacto();
                        jsonObject = response.getJSONObject(i);
                        usuario.name = jsonObject.getString("Nombre") + " " + jsonObject.getString("APaterno") + " " + jsonObject.getString("AMaterno");
                        usuario.username = jsonObject.getString("Usuario");
                        usuario.email = jsonObject.getString("Correo");
                        users.add(usuario);

                    } catch (JSONException e) {
                        //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if(users.size() > 0){
                    loading(false);
                    adapterSet(users);
                }else{
                    loading(false);
                    showErrorMessage();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.textErrorMessage.setVisibility(View.VISIBLE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void showErrorMessage(){
        binding.textErrorMessage.setText(String.format("%s", "Sin usuarios disponibles"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(Contacto user) {
        Intent intent = new Intent(getApplicationContext(), Chat.class);
        intent.putExtra("Contacto", user);
        startActivity(intent);
        finish();
    }

    public void adapterSet(List<Contacto> users){
        AdapterContacto usersAdapter = new AdapterContacto(users, this);
        binding.usersRecyclerView.setAdapter(usersAdapter);
        binding.usersRecyclerView.setVisibility(View.VISIBLE);
    }
}
