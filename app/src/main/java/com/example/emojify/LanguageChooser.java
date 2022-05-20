package com.example.emojify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.emojify.Admin.AdminHome;

public class LanguageChooser extends AppCompatActivity {
Button mal,eng,ta,hi;
SharedPreferences sp;
SharedPreferences.Editor ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_chooser);
        mal=findViewById(R.id.mal);
        eng=findViewById(R.id.eng);
        ta=findViewById(R.id.ta);
        hi=findViewById(R.id.hi);
sp=getSharedPreferences("language", Context.MODE_PRIVATE);
        mal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed=sp.edit();
                ed.putString("lan","malayalam");
                ed.commit();
                Intent i = new Intent(getApplicationContext(), HomePage.class);
                startActivity(i);
                finish();
            }
        });
        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed=sp.edit();
                ed.putString("lan","english");
                ed.commit();
                Intent i = new Intent(getApplicationContext(), HomePage.class);
                startActivity(i);
                finish();
            }
        });
        ta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed=sp.edit();
                ed.putString("lan","tamil");
                ed.commit();
                Intent i = new Intent(getApplicationContext(), HomePage.class);
                startActivity(i);
                finish();
            }
        });
        hi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed=sp.edit();
                ed.putString("lan","hindi");
                ed.commit();
                Intent i = new Intent(getApplicationContext(), HomePage.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }
}