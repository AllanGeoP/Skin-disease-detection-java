package com.example.skind;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.PatientViewHolder> {

    private Context context;
    private List<Patient> patientsList;

    public PatientsAdapter(Context context, List<Patient> patientsList) {
        this.context = context;
        this.patientsList = patientsList;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_patient, parent, false);
        return new PatientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Map<String,String> user = new SessionManager(context).getUserDetails();

        Patient patient = patientsList.get(position);
        holder.nameTextView.setText(patient.getName());
        holder.phoneTextView.setText(patient.getPhoneNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LiveChatActivity.class);
                intent.putExtra("sender_id",String.valueOf(user.get("id")));
                intent.putExtra("receiver_id",String.valueOf(patient.getId()));
                intent.putExtra("role", "doctor");
                intent.putExtra("name", String.valueOf(patient.getName()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientsList.size();
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView phoneTextView;

        public PatientViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.patient_name);
            phoneTextView = itemView.findViewById(R.id.patient_phone);
        }
    }
}
