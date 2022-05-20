package com.example.emojify.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.emojify.Adapter.MoviesAdapter;
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

public class AllMovies extends AppCompatActivity {
    RecyclerView doctorrc;
    List<Datum> doctor;
    MoviesAdapter doctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_emogies);

        doctorrc = findViewById(R.id.docrc);
        doctor = new ArrayList<>();

        GridLayoutManager ll = new GridLayoutManager(this,2);
        doctorrc.setLayoutManager(ll);
        searchemployee();
    }

    private void searchemployee() {
        final ProgressDialog progressDoalog = new ProgressDialog(AllMovies.this);
        progressDoalog.setMessage("please wait....");
        progressDoalog.setTitle("Processing");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDoalog.show();
        ApiInterface apiinterface = Apiclient.getClient().create(ApiInterface.class);
        Call<getMessage> call = apiinterface.spinner("viewmovie");

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
        startActivity(new Intent(getApplicationContext(), AdminHome.class));
        finish();
    }
}