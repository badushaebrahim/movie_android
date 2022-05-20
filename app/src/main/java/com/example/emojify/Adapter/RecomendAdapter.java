package com.example.emojify.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emojify.Login;
import com.example.emojify.R;
import com.example.emojify.model.Datum;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class RecomendAdapter extends RecyclerView.Adapter<RecomendAdapter.MyViewHolder> {

    Context context;
    List<Datum> doctor;

    public RecomendAdapter(Context applicationContext, List<Datum> doctor) {
        this.context = applicationContext;
        this.doctor = doctor;
    }

    @NonNull
    @Override
    public RecomendAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_emoji, parent, false);
        RecomendAdapter.MyViewHolder h = new RecomendAdapter.MyViewHolder(v);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull RecomendAdapter.MyViewHolder holder, int position) {
        final Datum c = doctor.get(position);
        holder.dname.setText("Movie\t:\t"+c.getMname());
        holder.dir.setText("Director\t:\t"+c.getDirector());
        holder.st.setText("Starring\t:\t"+c.getStar());
        holder.demail.setText("Language\t:\t"+c.getLanguage());
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] imageBytes = baos.toByteArray();
            imageBytes = Base64.decode(c.getMimg(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.doctorimg.setImageBitmap(decodedImage);
        } catch (Exception e) {

        }
        holder.cprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(i);
            }});

    }


    @Override
    public int getItemCount() {
        return doctor.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dname, dir, st, demail, dphone, ctime;
        CardView cprice;
        ImageView doctorimg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorimg = itemView.findViewById(R.id.dimg);
            dname = itemView.findViewById(R.id.dname);
            dir = itemView.findViewById(R.id.dir);
            st = itemView.findViewById(R.id.st);
            demail = itemView.findViewById(R.id.lang);
            cprice = itemView.findViewById(R.id.cardprice);
        }
    }
}

