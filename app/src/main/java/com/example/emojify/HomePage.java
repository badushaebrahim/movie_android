package com.example.emojify;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.emojify.Adapter.RecomendAdapter;
import com.example.emojify.model.Datum;
import com.example.emojify.model.getMessage;
import com.example.emojify.webservice.ApiInterface;
import com.example.emojify.webservice.Apiclient;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage extends AppCompatActivity {
    RecyclerView doctorrc;
    List<Datum> doctor;
    RecomendAdapter doctorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        requestPermission();
        doctorrc = findViewById(R.id.recrec);
        doctor = new ArrayList<>();

        GridLayoutManager ll = new GridLayoutManager(this,2);
        doctorrc.setLayoutManager(ll);
        searchemployee();
    }

    private void searchemployee() {
        SharedPreferences sp=getSharedPreferences("language", Context.MODE_PRIVATE);
        final ProgressDialog progressDoalog = new ProgressDialog(HomePage.this);
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
                try {
                    if (response.body().getMessage().equals("success")) {
                        doctor = response.body().getData();
                        doctorAdapter = new RecomendAdapter(getApplicationContext(), doctor);
                        doctorrc.setAdapter(doctorAdapter);
                        doctorAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){}
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
        getMenuInflater().inflate(R.menu.log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), LanguageChooser.class);
        startActivity(i);
        finish();

    }
    public void requestPermission() {

        //Multiple permission request
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


    /**
     * Showing Alert Dialog with Settings option in case of deny any permission
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}