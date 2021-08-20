package com.example.navegationdrawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

public class MainActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.escenario,new SessionFragment()).commit();
        String nombre=getIntent().getStringExtra("nombre");
        if(nombre=="true"){
            Intent miIntent=new Intent(MainActivityLogin.this,MainActivity.class);
            startActivity(miIntent);
        }
    }
}