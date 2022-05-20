package com.example.emojify.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.emojify.HomePage;
import com.example.emojify.R;

public class AdminHome extends AppCompatActivity {
ImageView uemogy,umusic,vemogy,vmusic,assig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        uemogy=findViewById(R.id.uemogy);
        umusic=findViewById(R.id.umusic);
        assig=findViewById(R.id.assign);
        vemogy=findViewById(R.id.vmusic);
        vmusic=findViewById(R.id.vtheater);
        uemogy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddMovies.class);
                startActivity(i);
                finish();
            }
        });
        assig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AssignMovie.class);
                startActivity(i);
                finish();
            }
        });
        umusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddTheater.class);
                startActivity(i);
                finish();
            }
        });
        vmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AllTheaters.class);
                startActivity(i);
                finish();
            }
        });
        vemogy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AllMovies.class);
                startActivity(i);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), HomePage.class);
        startActivity(i);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item11:
               SharedPreferences sp=getSharedPreferences("login", Context.MODE_PRIVATE);
               SharedPreferences.Editor ed=sp.edit();
               ed.putString("utype","");
               ed.commit();
                Intent i = new Intent(getApplicationContext(), HomePage.class);
                startActivity(i);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}