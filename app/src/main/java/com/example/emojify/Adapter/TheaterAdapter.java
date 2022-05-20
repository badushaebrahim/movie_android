package com.example.emojify.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emojify.Admin.AllTheaters;
import com.example.emojify.R;
import com.example.emojify.User.getShows;
import com.example.emojify.User.getTheaters;
import com.example.emojify.model.Datum;
import com.example.emojify.model.getMessage;
import com.example.emojify.webservice.ApiInterface;
import com.example.emojify.webservice.Apiclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.MyViewHolder> {

    Context context;
    List<Datum> doctor;

    public TheaterAdapter(Context applicationContext, List<Datum> doctor) {
        this.context = applicationContext;
        this.doctor = doctor;
    }

    @NonNull
    @Override
    public TheaterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_music, parent, false);
        TheaterAdapter.MyViewHolder h = new TheaterAdapter.MyViewHolder(v);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterAdapter.MyViewHolder holder, int position) {
        final Datum c = doctor.get(position);
        holder.tname.setText("\tTheater Name: " + c.getTname());
        holder.scap.setText("\tSeat Capacity : " + c.getScap());
        holder.address.setText("\tTheater Address : " + c.getTaddress());
        holder.phone.setText("\tTheater Phone : " + c.getTphone());


        holder.cprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = context.getSharedPreferences("login", Context.MODE_PRIVATE);
                if (sp.getString("utype", "").equals("admin")||sp.getString("utype", "").equals("theater")) {

                    AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                    alertbox.setMessage("Do you want to Delete this Theater? ");
                    alertbox.setTitle("warning");
                    alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ApiInterface apiService =
                                    Apiclient.getClient().create(ApiInterface.class);

                            Call<getMessage> call = apiService.login("deletetheater", c.getMid());
                            call.enqueue(new Callback<getMessage>() {
                                @Override
                                public void onResponse(Call<getMessage> call, Response<getMessage> response) {
                                    if (response.body().getStatus().equals("success")) {
                                        Toast.makeText(context, "deleted successfull", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(context, AllTheaters.class);
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
                }else{
                    SharedPreferences ss=context.getSharedPreferences("ticket",Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed=ss.edit();
                    ed.putString("tid",c.getMid());
                    ed.putString("tname",c.getTname());
                    ed.commit();

                    Intent i = new Intent(context, getShows.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(i);
                }

            }
        });



    }


    @Override
    public int getItemCount() {
        return doctor.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tname, scap, address, phone, dphone, review, ucount, pcount,language;
        CardView cprice;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cprice = itemView.findViewById(R.id.cardprice);
            tname = itemView.findViewById(R.id.dname);
            scap = itemView.findViewById(R.id.dedu);
            address = itemView.findViewById(R.id.dsp);
            phone = itemView.findViewById(R.id.email);



        }
    }
}

