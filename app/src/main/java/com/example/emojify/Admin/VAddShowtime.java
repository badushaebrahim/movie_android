package com.example.emojify.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.emojify.R;
import com.example.emojify.model.Datum;
import com.example.emojify.model.getMessage;
import com.example.emojify.theater.TheaterHome;
import com.example.emojify.webservice.ApiInterface;
import com.example.emojify.webservice.Apiclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VAddShowtime extends AppCompatActivity {
    Spinner th, movie;
    EditText time;
    Button btnadd;
    String mval="";
    String[] itemsid;
    String[] items;
    String[] movies;
    String[] movieid;
    Spinner emotion;
    List<Datum> numberlist, num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vadd_showtime);

        movie = findViewById(R.id.mov);
        time = findViewById(R.id.tname);
        btnadd = findViewById(R.id.btnadd);

        getspinner1();

        movie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mval = movieid[i].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mval.equals("")) {
                    Toast.makeText(VAddShowtime.this, "please select a movie", Toast.LENGTH_SHORT).show();
                } else if (time.getText().toString().isEmpty()) {
                    time.setError("Enter show time");
                } else {
                    SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                    final ProgressDialog progressDoalog = new ProgressDialog(VAddShowtime.this);
                    progressDoalog.setMessage("please wait....");
                    progressDoalog.setTitle("Registering");
                    progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


                    progressDoalog.show();
                    ApiInterface apiInterface =
                            Apiclient.getClient().create(ApiInterface.class);
                    Call<getMessage> call = apiInterface.addshow("addshowtime",
                            mval,time.getText().toString(),sp.getString("uid",""));

                    call.enqueue(new Callback<getMessage>() {
                        @Override
                        public void onResponse(Call<getMessage> call, Response<getMessage> response) {
                            progressDoalog.dismiss();
                            if (response.body().getMessage().equals("success")) {
                                Toast.makeText(VAddShowtime.this, "Time Added Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(VAddShowtime.this, VAddShowtime.class));
                                finish();
                            } else {
                                Toast.makeText(VAddShowtime.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<getMessage> call, Throwable t) {
                            progressDoalog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void getspinner1() {
        final ProgressDialog progressDoalog = new ProgressDialog(VAddShowtime.this);
        progressDoalog.setMessage("please wait....");
        progressDoalog.setTitle("Processing");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCancelable(false);

        progressDoalog.show();
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);

        final ApiInterface apiService =
                Apiclient.getClient().create(ApiInterface.class);
        Toast.makeText(this,  sp.getString("uid", "")+"", Toast.LENGTH_SHORT).show();
        Call<getMessage> call = apiService.getmymovie("viewourmovie", sp.getString("uid", ""));
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

                        ArrayAdapter aa = new ArrayAdapter(VAddShowtime.this, android.R.layout.simple_list_item_1, movies);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        movie.setAdapter(aa);
                    } catch (Exception e) {
                        Toast.makeText(VAddShowtime.this, e+"", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(VAddShowtime.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<getMessage> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(VAddShowtime.this, t + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), TheaterHome.class));
        finish();
    }
}