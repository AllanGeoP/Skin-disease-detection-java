package com.example.skind;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.Map;

public class LiveMessageAdapter extends RecyclerView.Adapter<LiveMessageAdapter.MyViewHolder>{

    List<LiveMessage> messageList;
    String receiverName;
    public LiveMessageAdapter(List<LiveMessage> messageList,String receiverName) {
        this.messageList = messageList;
        this.receiverName = receiverName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, null);
        return new MyViewHolder(chatView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Map<String,String> user =  new SessionManager(holder.itemView.getContext()).getUserDetails();
        LiveMessage message = messageList.get(position);
        if (String.valueOf(user.get("id")).equals(message.sender_id)){
            holder.left_chat_view.setVisibility(View.GONE);
            holder.right_chat_view.setVisibility(View.VISIBLE);
            holder.right_chat_text_view.setText(message.getMessage());
        } else {
            holder.receiverNameTxt.setText(receiverName);
            holder.right_chat_view.setVisibility(View.GONE);
            holder.left_chat_view.setVisibility(View.VISIBLE);
            holder.left_chat_text_view.setText(message.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        MaterialCardView left_chat_view, right_chat_view;
        TextView left_chat_text_view, right_chat_text_view,receiverNameTxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            left_chat_view = itemView.findViewById(R.id.left_chat_view);
            right_chat_view = itemView.findViewById(R.id.right_chat_view);
            left_chat_text_view = itemView.findViewById(R.id.left_chat_text_view);
            right_chat_text_view = itemView.findViewById(R.id.right_chat_text_view);
            receiverNameTxt = itemView.findViewById(R.id.chatbOT);



        } // MyViewHolder itemView End Here ======

    } // MyViewHolder End Here ===================


} // MessageAdapter End Here =====================