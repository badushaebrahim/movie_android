package com.example.emojify.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emojify.Admin.AllMovies;
import com.example.emojify.R;
import com.example.emojify.User.getTheaters;
import com.example.emojify.model.Datum;
import com.example.emojify.model.getMessage;
import com.example.emojify.webservice.ApiInterface;
import com.example.emojify.webservice.Apiclient;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> implements Filterable {

    Context context;
    List<Datum> doctor;
    List<Datum> exampleListFull;
    public MoviesAdapter(Context applicationContext, List<Datum> doctor) {
        this.context = applicationContext;
        this.doctor = doctor;
        exampleListFull = new ArrayList<>(doctor);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_emoji, parent, false);
        MoviesAdapter.MyViewHolder h = new MoviesAdapter.MyViewHolder(v);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
                SharedPreferences sp = context.getSharedPreferences("login", Context.MODE_PRIVATE);
                if (sp.getString("utype", "").equals("admin")||sp.getString("utype", "").equals("theater")) {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                    alertbox.setMessage("Do you want to Delete this Movie? ");
                    alertbox.setTitle("warning");
                    alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ApiInterface apiService =
                                    Apiclient.getClient().create(ApiInterface.class);

                            Call<getMessage> call = apiService.login("deletemovie",c.getEid());
                            call.enqueue(new Callback<getMessage>() {
                                @Override
                                public void onResponse(Call<getMessage> call, Response<getMessage> response) {
                                    if (response.body().getStatus().equals("success")) {
                                        Toast.makeText(context, "deleted successfull", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(context, AllMovies.class);
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
                    ed.putString("eid",c.getEid());
                    ed.putString("mname",c.getMname());
                    ed.putString("mpic",c.getMimg());
                    ed.commit();

                    Intent i = new Intent(context, getTheaters.class);
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
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Datum> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Datum item : exampleListFull) {

                    if (item.getMname().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }

                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            doctor.clear();
            doctor.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

