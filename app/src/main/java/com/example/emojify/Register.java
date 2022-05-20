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
import android.widget.Toast;

import com.example.emojify.Admin.AdminHome;
import com.example.emojify.model.getMessage;
import com.example.emojify.webservice.ApiInterface;
import com.example.emojify.webservice.Apiclient;
import com.example.emojify.webservice.Validations;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
EditText name,phone,otp;
Button verify,otpverify,reg;
LinearLayout sec;
long tid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);


        reg=findViewById(R.id.reg);


     
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()){
                    name.setError("Enter Name");
                }
                else if(phone.getText().toString().isEmpty()){
                    phone.setError("Enter Phone Number");
                }
                else {
                    final ProgressDialog progressDoalog = new ProgressDialog(Register.this);
                    progressDoalog.setMessage("please wait....");
                    progressDoalog.setTitle("Registering");
                    progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


                    progressDoalog.show();
                    ApiInterface apiInterface =
                            Apiclient.getClient().create(ApiInterface.class);
                    Call<getMessage> call = apiInterface.Registe1r("register",
                            name.getText().toString(), phone.getText().toString());

                    call.enqueue(new Callback<getMessage>() {
                        @Override
                        public void onResponse(Call<getMessage> call, Response<getMessage> response) {
                            progressDoalog.dismiss();
                            if (response.body().getMessage().equals("success")) {
                                try {
                                    SmsManager sms = SmsManager.getDefault();
                                    sms.sendTextMessage(phone.getText().toString().trim(), null, "you are successfully Registered with movies app. you can login using this credentials.Thank you!", null, null);

                                } catch (Exception e) {

                                }
                                Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Register.this, Login.class));
                                finish();
                            } else {

                                Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Register.this, Register.class));
                                finish();
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

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
        finish();
    }
}