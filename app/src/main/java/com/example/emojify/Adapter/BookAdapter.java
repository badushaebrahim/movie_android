package com.example.emojify.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emojify.Admin.AllTheaters;
import com.example.emojify.R;
import com.example.emojify.User.MyBookings;
import com.example.emojify.User.getShows;
import com.example.emojify.model.Datum;
import com.example.emojify.model.getMessage;
import com.example.emojify.webservice.ApiInterface;
import com.example.emojify.webservice.Apiclient;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    Context context;
    List<Datum> doctor;

    public BookAdapter(Context applicationContext, List<Datum> doctor) {
        this.context = applicationContext;
        this.doctor = doctor;
    }

    @NonNull
    @Override
    public BookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_book, parent, false);
        BookAdapter.MyViewHolder h = new BookAdapter.MyViewHolder(v);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.MyViewHolder holder, int position) {
        final Datum c = doctor.get(position);
        holder.mname.setText("\tMovie Name: " + c.getMname());
        holder.tname.setText("\tTheater Name: " + c.getTname());
        holder.time.setText("\tShow time : " + c.getTime());
        holder.bdate.setText("\tBooked date : " + c.getBdate());
        holder.num.setText("\tNumber of tickets : " + c.getNumticket());
        holder.amount.setText("\tPaid Amount : " + c.getAmount());
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] imageBytes = baos.toByteArray();
            imageBytes = Base64.decode(c.getMpic(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.mpic.setImageBitmap(decodedImage);

        } catch (Exception e) {

        }
        try {
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        byte[] imageBytes1 = baos1.toByteArray();
        imageBytes1 = Base64.decode(c.getQrcode(), Base64.DEFAULT);
        Bitmap decodedImage1 = BitmapFactory.decodeByteArray(imageBytes1, 0, imageBytes1.length);
        holder.qr.setImageBitmap(decodedImage1);
        } catch (Exception e) {

        }
        holder.cprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = context.getSharedPreferences("login", Context.MODE_PRIVATE);
                if (sp.getString("utype", "").equals("admin")||sp.getString("utype", "").equals("theater")) {


                }else{
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                    alertbox.setMessage("Do you want to cancel this ticket? ");
                    alertbox.setTitle("warning");
                    alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ApiInterface apiService =
                                    Apiclient.getClient().create(ApiInterface.class);

                            Call<getMessage> call = apiService.login("cancelticket", c.getBid());
                            call.enqueue(new Callback<getMessage>() {
                                @Override
                                public void onResponse(Call<getMessage> call, Response<getMessage> response) {
                                    if (response.body().getStatus().equals("success")) {
                                        Toast.makeText(context, "deleted successfull", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(context, MyBookings.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        context.startActivity(i);
                                    } else {
                                        Toast.makeText(context, "delete failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<getMessage> call, Throwable t) {

                                    Toast.makeText(context, t + "", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    });
                    alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    alertbox.show();
                }

            }
        });



    }


    @Override
    public int getItemCount() {
        return doctor.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mname, tname, time, bdate, num, amount;
        CardView cprice;
        ImageView mpic,qr;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cprice = itemView.findViewById(R.id.cardprice);
            mname = itemView.findViewById(R.id.mname);
            tname = itemView.findViewById(R.id.tname);
            time = itemView.findViewById(R.id.time);
            bdate = itemView.findViewById(R.id.bdate);
            num = itemView.findViewById(R.id.num);
            amount = itemView.findViewById(R.id.amount);
            qr = itemView.findViewById(R.id.qr);


        }
    }
}


