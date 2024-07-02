package com.example.skind;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class hctfullactivity extends AppCompatActivity {
    EditText search;

    private String url=config.baseurl+"hct.php";
    private ArrayList<hctmodel> dataModelArrayList;
    private hctadapter rvAdapter;
    private RecyclerView recyclerView;
    private ProgressBar p;
    String sem,pn,phn,date,time;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hctfullactivity);
        recyclerView = findViewById(R.id.cycle1238note120);
        p = findViewById(R.id.bar1238note120);

//        Intent in=getIntent();
//        sem=in.getStringExtra("sem");
////
//        HashMap<String,String> map=new SessionManager(BooklistActivity.this).getUserDetails();
//        sem=map.get("sem");


//        search=findViewById(R.id.search123);
//        search.addTextChangedListener(new TextWatcher() {
//            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
//            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
//            @Override
//            public void afterTextChanged(Editable text) {
//                //new array list that will hold the filtered data
//                ArrayList<BooklistModel> filteredSongs = new ArrayList<>();
//
//                if (dataModelArrayList != null && !dataModelArrayList.isEmpty()) {
//                    //looping through existing elements
//                    for (BooklistModel  s: dataModelArrayList) {
//                        //if the existing elements contains the search input
//                        if (s.getSem().toLowerCase().contains(text.toString().toLowerCase())) {
//                            //adding the element to filtered list
//                            filteredSongs.add(s);
//                        }
//                    }
//                }
//
//                if (rvAdapter != null) {
//                    //calling a method of the adapter class and passing the filtered list
//                    rvAdapter.filterList(filteredSongs);
//                }
//            }
//        });

        fetchingJSON();
    }

    private void fetchingJSON() {

        p.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            p.setVisibility(View.GONE);

                            dataModelArrayList = new ArrayList<>();
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject dataobj = array.getJSONObject(i);

                                dataModelArrayList.add(new hctmodel(
                                        dataobj.getString("id"),
                                        dataobj.getString("name"),
                                        dataobj.getString("details1"),
                                        dataobj.getString("details2"),
                                        dataobj.getString("img")


                                ));
                            }
                            setupRecycler();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        p.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
//        {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("sem", sem);
//
//                return params;
//            }
//
//        };

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 20000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 20000;
            }

            @Override
            public void retry(VolleyError error) {
                p.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void setupRecycler(){
        rvAdapter = new hctadapter(this, dataModelArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }


}