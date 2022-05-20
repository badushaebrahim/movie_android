package com.example.emojify.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.emojify.R;
import com.example.emojify.model.Datum;
import com.example.emojify.webservice.ApiInterface;
import com.example.emojify.webservice.Apiclient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AddTheater extends AppCompatActivity{
    EditText mname, mlink, movie, uname, pass,sadd;
    Spinner emotion, language;
    Button btnadd;
    String lang_val, emotion_val;
    String[] items;
    String[] itemsid;
    List<Datum> numberlist, num;
    String Lang[] = {"Malayalam", "Tamil", "English", "Hindi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music_list);
        mname = findViewById(R.id.tname);
        mlink = findViewById(R.id.scap);
        sadd = findViewById(R.id.saddress);
        movie = findViewById(R.id.tphone);
        uname = findViewById(R.id.uname);
        pass = findViewById(R.id.pass);


        btnadd = findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mname.getText().toString().isEmpty()) {
                    mname.setError("Enter Music Name");
                } else if (mlink.getText().toString().isEmpty()) {
                    mlink.setError("enter seating capacity");
                }
                else if (sadd.getText().toString().isEmpty()) {
                    sadd.setError("Enter Theater address");
                } else if (movie.getText().toString().isEmpty()) {
                    movie.setError("Enter theater phone");
                }else if (uname.getText().toString().isEmpty()) {
                    uname.setError("Enter theater username");
                }
                else if (pass.getText().toString().isEmpty()) {
                    pass.setError("Enter theater password");
                }
                else{
                    uploadPDF();
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), AdminHome.class);
        startActivity(i);
        finish();
    }


    private void uploadPDF() {
        final ProgressDialog progressDoalog = new ProgressDialog(AddTheater.this);
        progressDoalog.setMessage("please wait....this may take one or more minute depending on the file size");
        progressDoalog.setTitle("Uploading");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        String pdfname = String.valueOf(Calendar.getInstance().getTimeInMillis());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Apiclient.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();


        ApiInterface getResponse = retrofit.create(ApiInterface.class);
        Call<String> call = getResponse.Addmusic( "addtheaters",
                mname.getText().toString(), mlink.getText().toString(),
                sadd.getText().toString(), movie.getText().toString(),
                uname.getText().toString(),pass.getText().toString());
        Log.d("assss", "asss");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDoalog.dismiss();
                Log.d("mullllll", response.body().toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (jsonObject.getString("message").equals("success")) {
                        try {
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(movie.getText().toString().trim(), null, "you are successfully Registered with movies app. you can login using this credentials.Thank you! \nusername="+uname.getText().toString()+"\npassword="+pass.getText().toString(), null, null);

                        } catch (Exception e) {

                        }
                        Toast.makeText(AddTheater.this, "Theater added successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), AddTheater.class);
                        startActivity(i);
                        finish();

                    } else {
                        mname.setText(jsonObject.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDoalog.dismiss();
                Log.d("gttt", call.toString());
            }
        });

    }

}