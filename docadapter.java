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
import java.util.Map;

public class docadapter extends RecyclerView.Adapter<docadapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<docmodel> dataModelArrayList;
    private Context c;

    public docadapter(Context ctx, ArrayList<docmodel> dataModelArrayList){
        c = ctx;
        inflater = LayoutInflater.from(c);
        this.dataModelArrayList = dataModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drlists, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.textView1.setText(dataModelArrayList.get(position).getName());
        Map<String,String> user =  new SessionManager(holder.itemView.getContext()).getUserDetails();

        holder.textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, LiveChatActivity.class);
                intent.putExtra("sender_id",String.valueOf(user.get("id")));
                intent.putExtra("receiver_id",dataModelArrayList.get(position).getId());
                intent.putExtra("role", "patient");
                intent.putExtra("name", dataModelArrayList.get(position).getName());

                c.startActivity(intent);
            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, docfull.class);
                intent.putExtra("id", dataModelArrayList.get(position).getId());
                intent.putExtra("name", dataModelArrayList.get(position).getName());
                intent.putExtra("email", dataModelArrayList.get(position).getPhone());
                intent.putExtra("phone", dataModelArrayList.get(position).getEmail());
                intent.putExtra("specialization", dataModelArrayList.get(position).getSpecialization());

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
            cardView = itemView.findViewById(R.id.c);
            textView1 = itemView.findViewById(R.id.don);
            image = itemView.findViewById(R.id.blogimg);
        }
    }
}
