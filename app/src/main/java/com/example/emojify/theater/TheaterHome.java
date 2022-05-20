package com.example.emojify.theater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.emojify.Admin.AddMovies;
import com.example.emojify.Admin.VAddShowtime;
import com.example.emojify.HomePage;
import com.example.emojify.R;

public class TheaterHome extends AppCompatActivity {
ImageView omovies,obook,atime,afare,vstime,vfare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater_home);
        omovies=findViewById(R.id.omovies);
        obook=findViewById(R.id.obook);
        atime=findViewById(R.id.atime);
        vstime=findViewById(R.id.vstime);
        vfare=findViewById(R.id.vfare);
        afare=findViewById(R.id.afare);
        omovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), OurMovies.class);
                startActivity(i);
                finish();
            }
        });
        vstime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ViewShowTime.class);
                startActivity(i);
                finish();
            }
        });
        vfare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ViewFare.class);
                startActivity(i);
                finish();
            }
        });
        afare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddFare.class);
                startActivity(i);
                finish();
            }
        });
        obook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), OurBookings.class);
                startActivity(i);
                finish();
            }
        });
        atime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), VAddShowtime.class);
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