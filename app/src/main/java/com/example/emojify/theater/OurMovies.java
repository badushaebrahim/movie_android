package com.example.emojify.theater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.emojify.Adapter.MoviesAdapter;
import com.example.emojify.Admin.AdminHome;
import com.example.emojify.Admin.AllMovies;
import com.example.emojify.R;
import com.example.emojify.model.Datum;
import com.example.emojify.model.getMessage;
import com.example.emojify.webservice.ApiInterface;
import com.example.emojify.webservice.Apiclient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OurMovies extends AppCompatActivity {
    RecyclerView doctorrc;
    List<Datum> doctor;
    MoviesAdapter doctorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_movies);
        doctorrc = findViewById(R.id.docrc);
        doctor = new ArrayList<>();

        GridLayoutManager ll = new GridLayoutManager(this,2);
        doctorrc.setLayoutManager(ll);
        searchemployee();
    }

    private void searchemployee() {
        final ProgressDialog progressDoalog = new ProgressDialog(OurMovies.this);
        progressDoalog.setMessage("please wait....");
        progressDoalog.setTitle("Processing");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDoalog.show();
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        ApiInterface apiinterface = Apiclient.getClient().create(ApiInterface.class);
        Call<getMessage> call = apiinterface.getmymovie("viewourmovie",sp.getString("uid",""));

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
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), TheaterHome.class));
        finish();
    }
}