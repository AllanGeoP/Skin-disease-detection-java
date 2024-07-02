package com.example.skind;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class bloglistadapter extends RecyclerView.Adapter<bloglistadapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<bloglistmodel> dataModelArrayList;
    private Context c;

    public bloglistadapter(Context ctx, ArrayList<bloglistmodel> dataModelArrayList){
        c = ctx;
        inflater = LayoutInflater.from(c);
        this.dataModelArrayList = dataModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bloglist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.textView1.setText(dataModelArrayList.get(position).getName());
        if (!dataModelArrayList.get(position).getImg().equals("")) {
            Picasso.get().load(config.imgurl + dataModelArrayList.get(position).getImg()).into(holder.image);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, blogfullactivity.class);
                intent.putExtra("id", dataModelArrayList.get(position).getId());
                intent.putExtra("name", dataModelArrayList.get(position).getName());
                intent.putExtra("details1", dataModelArrayList.get(position).getDetails1());

                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView1;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            textView1 = itemView.findViewById(R.id.blogname);
            image = itemView.findViewById(R.id.blogimg);
        }
    }
}
