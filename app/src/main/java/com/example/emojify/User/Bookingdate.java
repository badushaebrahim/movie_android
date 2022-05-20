package com.example.emojify.User;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emojify.Adapter.ShowAdapter;
import com.example.emojify.Admin.AddMovies;
import com.example.emojify.R;
import com.example.emojify.WelcomePage;
import com.example.emojify.model.getMessage;
import com.example.emojify.webservice.ApiInterface;
import com.example.emojify.webservice.Apiclient;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Bookingdate extends AppCompatActivity {
    EditText cno, cvv, exp, amt, cdate,price,numticket;
    Button pay;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public final static int QRcodeWidth = 500;
    private static final String IMAGE_DIRECTORY = "/QRcodeDemonuts";
    Bitmap bitmap;
    SharedPreferences ss,sp;
    String qrstring = "";

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Bitmap bitmapProfile = null;
    private String userChosenTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingdate);
         ss=getSharedPreferences("ticket", Context.MODE_PRIVATE);
         sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        cno = findViewById(R.id.cno);
        cvv = findViewById(R.id.cvv);
        exp = findViewById(R.id.exp);
        amt = findViewById(R.id.Amt);
        cdate = findViewById(R.id.cdate);
        pay = findViewById(R.id.pay);
        price = findViewById(R.id.price);
        numticket = findViewById(R.id.nticket);
        numticket.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
              Double total=Double.parseDouble(price.getText().toString())*Double.parseDouble(numticket.getText().toString());
                amt.setText(total+"");


            }
        });
        searchemployee();
        cdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cno.getText().toString().isEmpty()) {
                    cno.setError("Enter card number");
                } else if (cvv.getText().toString().isEmpty()) {
                    cvv.setError("Enter card cvv");
                } else if (exp.getText().toString().isEmpty()) {
                    exp.setError("Enter card exp");
                } else if (amt.getText().toString().isEmpty()) {
                    amt.setError("Enter amount");
                } else {
                    Qualification();
                    bookticket();

                }
            }
        });
    }
    private void Qualification() {
        try {
            bitmap = TextToImageEncode("Movie name\t:\t"+ss.getString("mname","")+"\nTheater Name\t:\t"+ss.getString("tname","")
                    +"\nShow Time\t:\t"+ss.getString("time","")+"\nBooking Date\t:\t"+cdate.getText().toString()+"\nNo.of.Ticket\t:\t"+
                    numticket.getText().toString()+"\nAmount paid\t:\t"+amt.getText().toString());
        } catch (WriterException e) {
            e.printStackTrace();
        }

        getStringQRcode(bitmap);
        String path = saveImage(bitmap);

    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getApplicationContext(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    public void getStringQRcode(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        qrstring = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.d("@@", qrstring);
        //return encodedImage;
    }
    private void bookticket() {

        final ProgressDialog progressDoalog = new ProgressDialog(Bookingdate.this);
        progressDoalog.setMessage("please wait....");
        progressDoalog.setTitle("Registering");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        progressDoalog.show();
        ApiInterface apiInterface =
                Apiclient.getClient().create(ApiInterface.class);
        Call<getMessage> call = apiInterface.generateticket("bookticket",
               ss.getString("mname",""),ss.getString("tname",""),
                ss.getString("time",""),cdate.getText().toString(),
                numticket.getText().toString(),amt.getText().toString(),
                sp.getString("uid",""),ss.getString("tid",""),
                ss.getString("mpic",""),qrstring);

        call.enqueue(new Callback<getMessage>() {
            @Override
            public void onResponse(Call<getMessage> call, Response<getMessage> response) {
                progressDoalog.dismiss();
                if (response.body().getMessage().equals("success")) {
                    Toast.makeText(Bookingdate.this, "Movie Booked Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Bookingdate.this, WelcomePage.class));
                    finish();
                } else {
                    Toast.makeText(Bookingdate.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<getMessage> call, Throwable t) {
                progressDoalog.dismiss();
            }
        });
    }

    private void searchemployee() {
        final ProgressDialog progressDoalog = new ProgressDialog(Bookingdate.this);
        progressDoalog.setMessage("please wait....");
        progressDoalog.setTitle("Processing");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDoalog.show();
        SharedPreferences ss=getSharedPreferences("ticket", Context.MODE_PRIVATE);

        ApiInterface apiinterface = Apiclient.getClient().create(ApiInterface.class);
        Call<getMessage> call = apiinterface.getshow("getprice",
                ss.getString("tid",""),ss.getString("eid",""));

        call.enqueue(new Callback<getMessage>() {
            @Override
            public void onResponse(Call<getMessage> call, Response<getMessage> response) {
                progressDoalog.dismiss();
                if (response.body().getMessage().equals("success")) {
                    price.setText(response.body().getData().get(0).getPrice().toString());

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
        startActivity(new Intent(getApplicationContext(), getShows.class));
        finish();
    }

    private void datePicker() {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Bookingdate.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (monthOfYear < 10) {

                            String date_time = dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year;
                            cdate.setText(date_time + "");
                        } else {
                            String date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            cdate.setText(date_time + "");
                        }
                        //*************Call Time Picker Here ********************

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
}