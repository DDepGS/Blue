package com.example.blue.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blue.R;

public class Inicio extends AppCompatActivity {
    Button botonLogin, botonRegistro;
    TextView recuperarp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        botonLogin=(Button) findViewById(R.id.BotonLogin);
        botonRegistro=(Button) findViewById(R.id.BotonRegistro);
        recuperarp = findViewById(R.id.RecuperarPass);

        recuperarp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), RecuperarPass.class);
                startActivity(intent);
            }
        });

        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view.getContext(), RegistroU.class);
                startActivityForResult(intent,0);
            }
        });

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), Login.class);
                startActivityForResult(intent,0);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("prefsesion", Context.MODE_PRIVATE);
                boolean sesion = preferences.getBoolean("sesion", false);
                if(sesion)
                {
                    Intent intent = new Intent (getApplicationContext(), Menu.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Inicie sesión", Toast.LENGTH_SHORT);
                }
            }
        }, 200);
    }
    public void onBackPressed() { moveTaskToBack(false); }

}