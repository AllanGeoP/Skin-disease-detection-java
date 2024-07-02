package com.example.skind;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocChatsActivity extends AppCompatActivity {


    Map<String,String> user;

    List<Patient> patientsList;
    PatientsAdapter patientsAdapter;
    RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_chats);

        getSupportActionBar().setTitle("Inbox");

        user = new SessionManager(this).getUserDetails();
        list = findViewById(R.id.patientsList);


        patientsList = new ArrayList<>();

        patientsAdapter = new PatientsAdapter(DocChatsActivity.this,patientsList);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(patientsAdapter);

        fetChats();

    }

    public void fetChats() {
        String url = config.baseurl + "fetch_patients.php?doctor_id="+user.get("id");

        StringRequest StringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Patient>>() {
                        }.getType();
                        patientsList.clear();
                        if(gson.fromJson(response, type)!=null){
                            patientsList.addAll(gson.fromJson(response, type));
                            patientsAdapter.notifyDataSetChanged();
                        }

                        System.out.println("MESSAGES:"+patientsList.size());

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //run cheyikkumbo error indo ennu nokkan
                        Toast.makeText(DocChatsActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }

                }) {


        };

        //string reqt ne execute cheyan aanu requestqueue
        Volley volley =  null;
        RequestQueue requestQueue = volley.newRequestQueue(this);
        requestQueue.add(StringRequest);
    }
}