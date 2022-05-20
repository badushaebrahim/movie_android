package com.example.emojify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emojify.Adapter.MoviesAdapter;
import com.example.emojify.User.Changelanguage;
import com.example.emojify.User.MyBookings;
import com.example.emojify.model.Datum;
import com.example.emojify.model.getMessage;
import com.example.emojify.webservice.ApiInterface;
import com.example.emojify.webservice.Apiclient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomePage extends AppCompatActivity {
    Button change,mybook;
    RecyclerView doctorrc;
    List<Datum> doctor;
    MoviesAdapter doctorAdapter;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        change = findViewById(R.id.button3);
        mybook = findViewById(R.id.ticket);
        mybook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MyBookings.class);
                startActivity(i);
                finish();
            }
        });
        search=findViewById(R.id.search);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Changelanguage.class);
                startActivity(i);
                finish();
            }
        });
        SharedPreferences sp = getSharedPreferences("language", Context.MODE_PRIVATE);
        doctorrc = findViewById(R.id.recrec);
        doctor = new ArrayList<>();

        GridLayoutManager ll = new GridLayoutManager(this,2);
        doctorrc.setLayoutManager(ll);
        searchemployee();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input

                    doctorAdapter.getFilter().filter(editable.toString());


            }
        });
    }

    private void searchemployee() {
        SharedPreferences sp=getSharedPreferences("language", Context.MODE_PRIVATE);
        final ProgressDialog progressDoalog = new ProgressDialog(WelcomePage.this);
        progressDoalog.setMessage("please wait....");
        progressDoalog.setTitle("Processing");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDoalog.show();
        ApiInterface apiinterface = Apiclient.getClient().create(ApiInterface.class);
        Call<getMessage> call = apiinterface.recomend("viewmovie1",sp.getString("lan",""));

        call.enqueue(new Callback<getMessage>() {
            @Override
            public void onResponse(Call<getMessage> call, Response<getMessage> response) {
                progressDoalog.dismiss();
                if (response.body().getMessage().equals("success")) {
                    doctor = response.body().getData();
                    doctorAdapter = new MoviesAdapter(getApplicationContext(), doctor);
                    doctorrc.setAdapter(doctorAdapter);
                    doctorAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<getMessage> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), t + "", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logs, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), HomePage.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item11:
                SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("utype", "");
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