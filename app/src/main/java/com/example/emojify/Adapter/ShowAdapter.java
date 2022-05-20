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
import com.example.emojify.User.Bookingdate;
import com.example.emojify.User.getShows;
import com.example.emojify.model.Datum;
import com.example.emojify.model.getMessage;
import com.example.emojify.webservice.ApiInterface;
import com.example.emojify.webservice.Apiclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.MyViewHolder> {

    Context context;
    List<Datum> doctor;

    public ShowAdapter(Context applicationContext, List<Datum> doctor) {
        this.context = applicationContext;
        this.doctor = doctor;
    }

    @NonNull
    @Override
    public ShowAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_show, parent, false);
        ShowAdapter.MyViewHolder h = new ShowAdapter.MyViewHolder(v);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAdapter.MyViewHolder holder, int position) {
        final Datum c = doctor.get(position);
        holder.tname.setText(c.getTime());



        holder.cprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences ss=context.getSharedPreferences("ticket",Context.MODE_PRIVATE);
                SharedPreferences.Editor ed=ss.edit();
                ed.putString("time",c.getTime());
                ed.commit();

                Intent i = new Intent(context, Bookingdate.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(i);

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




        }
    }
}

