package com.example.emojify.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.emojify.HomePage;
import com.example.emojify.R;
import com.example.emojify.WelcomePage;

public class Changelanguage extends AppCompatActivity {
    Button mal,eng,ta,hi;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changelanguage);
        mal=findViewById(R.id.mal);
        eng=findViewById(R.id.eng);
        ta=findViewById(R.id.ta);
        hi=findViewById(R.id.hi);
        sp=getSharedPreferences("language", Context.MODE_PRIVATE);
        mal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed=sp.edit();
                ed.putString("lan","Malayalam");
                ed.commit();
                Intent i = new Intent(getApplicationContext(), WelcomePage.class);
                startActivity(i);
                finish();
            }
        });
        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed=sp.edit();
                ed.putString("lan","English");
                ed.commit();
                Intent i = new Intent(getApplicationContext(), WelcomePage.class);
                startActivity(i);
                finish();
            }
        });
        ta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed=sp.edit();
                ed.putString("lan","Tamil");
                ed.commit();
                Intent i = new Intent(getApplicationContext(), WelcomePage.class);
                startActivity(i);
                finish();
            }
        });
        hi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed=sp.edit();
                ed.putString("lan","Hindi");
                ed.commit();
                Intent i = new Intent(getApplicationContext(), WelcomePage.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), WelcomePage.class);
        startActivity(i);
        finish();
    }

}