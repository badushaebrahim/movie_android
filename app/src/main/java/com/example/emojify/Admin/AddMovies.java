package com.example.emojify.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.emojify.R;
import com.example.emojify.model.Datum;
import com.example.emojify.model.getMessage;
import com.example.emojify.webservice.ApiInterface;
import com.example.emojify.webservice.Apiclient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMovies extends AppCompatActivity {
    ImageView emogy;
    EditText ename, director, star,lang;
    Button btnupload;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Bitmap bitmapProfile = null;
    String encodedImage = "";
    private static int RESULT_LOAD_IMAGE = 1;
    private String userChosenTask;
    RadioButton m, f;
    int newWidth = 500;
    String gen;
    int newHeight = 500;
    String[] items;
    String[] itemsid;

    List<Datum> numberlist, num;
    Matrix matrix;
    String emotion_val;
    Bitmap resizedBitmap;
    private int mYear, mMonth, mDay, mHour, mMinute;
    float scaleWidth;

    float scaleHeight;
    DatePickerDialog datePickerDialog;
    ByteArrayOutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emogy);
        emogy = findViewById(R.id.imageView);
        ename = findViewById(R.id.mname);
        director = findViewById(R.id.director);
        star = findViewById(R.id.star);

        lang = findViewById(R.id.lang);
        btnupload = findViewById(R.id.button);

        emogy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ename.getText().toString().isEmpty()) {
                    ename.setError("Enter Emotion");
                } else if (director.getText().toString().isEmpty()) {
                    director.setError("Enter Director name");
                }
                else if (star.getText().toString().isEmpty()) {
                    star.setError("Enter Starring details");
                }
                else if (lang.getText().toString().isEmpty()) {
                    lang.setError("Enter movie language");
                }else if (encodedImage.equals("")) {
                    Toast.makeText(AddMovies.this, "please coose an movie image", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog progressDoalog = new ProgressDialog(AddMovies.this);
                    progressDoalog.setMessage("please wait....");
                    progressDoalog.setTitle("Registering");
                    progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


                    progressDoalog.show();
                    ApiInterface apiInterface =
                            Apiclient.getClient().create(ApiInterface.class);
                    Call<getMessage> call = apiInterface.Register("addmovie",
                            ename.getText().toString(), director.getText().toString(),
                            star.getText().toString(), encodedImage,"emotion_val",lang.getText().toString());

                    call.enqueue(new Callback<getMessage>() {
                        @Override
                        public void onResponse(Call<getMessage> call, Response<getMessage> response) {
                            progressDoalog.dismiss();
                            if (response.body().getMessage().equals("success")) {
                                Toast.makeText(AddMovies.this, "Movie Added Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddMovies.this, AddMovies.class));
                                finish();
                            } else {
                                Toast.makeText(AddMovies.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

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
        Intent i = new Intent(getApplicationContext(), AdminHome.class);
        startActivity(i);
        finish();
    }

    private void selectImage() {
        //final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddMovies.this);
        builder.setTitle("Upload your documents");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                // boolean result = Utility.checkPermission(Register.this);
                if (items[item].equals("Take Photo")) {
                    userChosenTask = "Take Photo";
                    // if (result)
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChosenTask = "Choose from Library";
                    // if (result)
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                    Log.d("dialog dismiss ", "true");
                }
            }
        });
        builder.show();
    }


    private void galleryIntent() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                int nh = (int) (bm.getHeight() * (512.0 / bm.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bm, 102, nh, true);
                reZize(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    void reZize(Bitmap bp) {
        int width = bp.getWidth();
        int height = bp.getHeight();
        Matrix matrix = new Matrix();
        scaleWidth = ((float) newWidth) / width;
        scaleHeight = ((float) newHeight) / height;
        matrix.postScale(scaleWidth, scaleHeight);
        resizedBitmap = Bitmap.createBitmap(bp, 0, 0, width, height, matrix, true);
        outputStream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        if (resizedBitmap != null) {
            getStringImage(resizedBitmap);
        }
    }

    public void getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        emogy.setImageBitmap(bmp);
        // Toast.makeText(getContext(), encodedImage+"", Toast.LENGTH_SHORT).show();
        //return encodedImage;
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        //Toast.makeText(getContext(), "" + destination, Toast.LENGTH_SHORT).show();
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        bitmapProfile = thumbnail;
        if (bitmapProfile != null) {
            getStringImage(bitmapProfile);
        }


    }
}