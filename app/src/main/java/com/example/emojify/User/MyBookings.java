package com.example.emojify.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.emojify.Adapter.BookAdapter;
import com.example.emojify.Adapter.ShowAdapter;
import com.example.emojify.Adapter.TheaterAdapter;
import com.example.emojify.R;
import com.example.emojify.WelcomePage;
import com.example.emojify.model.Datum;
import com.example.emojify.model.getMessage;
import com.example.emojify.webservice.ApiInterface;
import com.example.emojify.webservice.Apiclient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookings extends AppCompatActivity {
    RecyclerView doctorrc;
    List<Datum> doctor;
    BookAdapter doctorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        doctorrc = findViewById(R.id.docrcc);
        doctor = new ArrayList<>();

        LinearLayoutManager ll = new LinearLayoutManager(this);
        doctorrc.setLayoutManager(ll);
        searchemployee();
    }

    private void searchemployee() {
        final ProgressDialog progressDoalog = new ProgressDialog(MyBookings.this);
        progressDoalog.setMessage("please wait....");
        progressDoalog.setTitle("Processing");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDoalog.show();
        SharedPreferences ss=getSharedPreferences("login", Context.MODE_PRIVATE);

        ApiInterface apiinterface = Apiclient.getClient().create(ApiInterface.class);
        Call<getMessage> call = apiinterface.getmymovie("getmybook",
                ss.getString("uid",""));

        call.enqueue(new Callback<getMessage>() {
            @Override
            public void onResponse(Call<getMessage> call, Response<getMessage> response) {
                progressDoalog.dismiss();
                if (response.body().getMessage().equals("success")) {
                    doctor = response.body().getData();
                    doctorAdapter = new BookAdapter(getApplicationContext(), doctor);
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
        startActivity(new Intent(getApplicationContext(), WelcomePage.class));
        finish();
    }
}