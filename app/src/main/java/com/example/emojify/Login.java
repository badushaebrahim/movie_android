package com.example.emojify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emojify.Admin.AdminHome;
import com.example.emojify.model.getMessage;
import com.example.emojify.theater.TheaterHome;
import com.example.emojify.webservice.ApiInterface;
import com.example.emojify.webservice.Apiclient;
import com.example.emojify.webservice.Validations;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText phone, otp;
    Button verify, otpverify;
    LinearLayout sec;
    TextView reg;
    long tid;
    String utype;
    SharedPreferences sp;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = findViewById(R.id.phone);
        otp = findViewById(R.id.votp);
        reg = findViewById(R.id.reg);
        verify = findViewById(R.id.verify);
        otpverify = findViewById(R.id.votpbtn);
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        ed = sp.edit();
        if (sp.getString("utype", "").equals("user")) {
            Intent i = new Intent(getApplicationContext(), WelcomePage.class);
            startActivity(i);
            finish();
        } else if (sp.getString("utype", "").equals("admin")) {
            Intent i = new Intent(getApplicationContext(), AdminHome.class);
            startActivity(i);
            finish();
        }else if (sp.getString("utype", "").equals("theater")){
            Intent i = new Intent(getApplicationContext(), TheaterHome.class);
            startActivity(i);
            finish();
        }
        sec = findViewById(R.id.otpverifysec);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
                finish();
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.getText().toString().isEmpty() || !phone.getText().toString().matches(Validations.mobile)) {
                    phone.setError("Enter Phone Number");
                } else {
                    callApi();
                }
            }
        });
        otpverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        if (utype.equals("user")) {
                            Intent i = new Intent(getApplicationContext(), WelcomePage.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(getApplicationContext(), AdminHome.class);
                            startActivity(i);
                            finish();
                        }

            }
        });

    }

    private void callApi() {
        final ProgressDialog progressDoalog = new ProgressDialog(Login.this);
        progressDoalog.setMessage("please wait....");
        progressDoalog.setTitle("Registering");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        progressDoalog.show();
        ApiInterface apiInterface =
                Apiclient.getClient().create(ApiInterface.class);
        Call<getMessage> call = apiInterface.login("login",
                phone.getText().toString());

        call.enqueue(new Callback<getMessage>() {
            @Override
            public void onResponse(Call<getMessage> call, Response<getMessage> response) {
                progressDoalog.dismiss();
                if (response.body().getMessage().equals("success")) {
                    ed.putString("utype", response.body().getObject().getUtype());
                    ed.putString("uid", response.body().getObject().getUid());
                    ed.putString("phone", response.body().getObject().getPhone());
                    ed.commit();
                    utype = response.body().getObject().getUtype();
                    if (utype.equals("user")) {
                        Intent i = new Intent(getApplicationContext(), WelcomePage.class);
                        startActivity(i);
                        finish();
                    } else if(utype.equals("admin")) {
                        Intent i = new Intent(getApplicationContext(), AdminHome.class);
                        startActivity(i);
                        finish();
                    }
                    else if(utype.equals("theater")) {
                        Intent i = new Intent(getApplicationContext(), TheaterHome.class);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<getMessage> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(Login.this, t+"", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), HomePage.class);
        startActivity(i);
        finish();
    }
}