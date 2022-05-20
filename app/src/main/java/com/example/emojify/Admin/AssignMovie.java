package com.example.emojify.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.emojify.R;
import com.example.emojify.model.Datum;
import com.example.emojify.model.getMessage;
import com.example.emojify.webservice.ApiInterface;
import com.example.emojify.webservice.Apiclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignMovie extends AppCompatActivity {
    String[] items;
    String[] itemsid;
    Spinner emotion;
    List<Datum> numberlist, num;
    Matrix matrix;
    String emotion_val;
    String[] movies;
    String[] movieid;
    String mval="";
    Spinner th, movie;
    Button btnadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_movie);
        emotion = findViewById(R.id.emotion);
        btnadd = findViewById(R.id.btnadd);
        movie = findViewById(R.id.mov);
        getspinner();
        getspinner1();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDoalog = new ProgressDialog(AssignMovie.this);
                progressDoalog.setMessage("please wait....");
                progressDoalog.setTitle("Registering");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


                progressDoalog.show();
                ApiInterface apiInterface =
                        Apiclient.getClient().create(ApiInterface.class);
                Call<getMessage> call = apiInterface.assign("assign",
                       mval,emotion_val);

                call.enqueue(new Callback<getMessage>() {
                    @Override
                    public void onResponse(Call<getMessage> call, Response<getMessage> response) {
                        progressDoalog.dismiss();
                        if (response.body().getMessage().equals("success")) {
                            Toast.makeText(AssignMovie.this, "Movie Assigned Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AssignMovie.this, AssignMovie.class));
                            finish();
                        } else {
                            Toast.makeText(AssignMovie.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<getMessage> call, Throwable t) {
                        progressDoalog.dismiss();
                    }
                });
            }
        });
        movie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mval = movieid[i].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        emotion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                emotion_val = itemsid[i].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void getspinner() {
        final ProgressDialog progressDoalog = new ProgressDialog(AssignMovie.this);
        progressDoalog.setMessage("please wait....");
        progressDoalog.setTitle("Processing");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCancelable(false);

        progressDoalog.show();

        final ApiInterface apiService =
                Apiclient.getClient().create(ApiInterface.class);
        Call<getMessage> call = apiService.spinner("getspinner");
        call.enqueue(new Callback<getMessage>() {
            @Override
            public void onResponse(Call<getMessage> call, Response<getMessage> response) {
                progressDoalog.dismiss();
                if (response.body().getMessage().equals("success")) {
                    numberlist = response.body().getData();
                    try {
                        items = new String[numberlist.size()];
                        itemsid = new String[numberlist.size()];
                        for (int i = 0; i < numberlist.size(); i++) {

                            items[i] = numberlist.get(i).getTname();
                            itemsid[i] = numberlist.get(i).getMid();
                        }

                        ArrayAdapter aa = new ArrayAdapter(AssignMovie.this, android.R.layout.simple_list_item_1, items);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        emotion.setAdapter(aa);
                    }catch(Exception e){
                        Toast.makeText(AssignMovie.this, "No theaters available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AssignMovie.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), AssignMovie.class);
                    startActivity(i);
                    finish();
                }


            }

            @Override
            public void onFailure(Call<getMessage> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(AssignMovie.this, t + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getspinner1() {
        final ProgressDialog progressDoalog = new ProgressDialog(AssignMovie.this);
        progressDoalog.setMessage("please wait....");
        progressDoalog.setTitle("Processing");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCancelable(false);

        progressDoalog.show();
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);

        final ApiInterface apiService =
                Apiclient.getClient().create(ApiInterface.class);
        Toast.makeText(this,  sp.getString("uid", "")+"", Toast.LENGTH_SHORT).show();
        Call<getMessage> call = apiService.spinner("viewmovie");
        call.enqueue(new Callback<getMessage>() {
            @Override
            public void onResponse(Call<getMessage> call, Response<getMessage> response) {
                progressDoalog.dismiss();
                if (response.body().getMessage().equals("success")) {
                    numberlist = response.body().getData();
                    try {
                        movies = new String[numberlist.size()];
                        movieid = new String[numberlist.size()];

                        for (int i = 0; i < numberlist.size(); i++) {

                            movies[i] = numberlist.get(i).getMname();
                            movieid[i] = numberlist.get(i).getEid();

                        }

                        ArrayAdapter aa = new ArrayAdapter(AssignMovie.this, android.R.layout.simple_list_item_1, movies);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        movie.setAdapter(aa);
                    } catch (Exception e) {
                        Toast.makeText(AssignMovie.this, e+"", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AssignMovie.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<getMessage> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(AssignMovie.this, t + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), AdminHome.class);
        startActivity(i);
        finish();
    }
}