package com.example.skind;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    RatingBar ratingBar;
    Button button;
    EditText feedback;
    TextView p,w;
    String rating;

    String feed,pa,wa,username,status,error,url= config.baseurl+"feedback.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ratingBar = findViewById(R.id.app_rating_bar);
        feedback = findViewById(R.id.feed1);
        p = findViewById(R.id.pf);
        w = findViewById(R.id.wno1);
        button = findViewById(R.id.btn_rate_app);


        username = new SessionManager(FeedbackActivity.this).getUserDetails().get("id");
        pa = new SessionManager(FeedbackActivity.this).getUserDetails().get("name");
        wa = new SessionManager(FeedbackActivity.this).getUserDetails().get("phonenumber");


        p.setText(pa);
        w.setText(wa);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logined();
            }
        });




    }

    private void logined(){
        feed=feedback.getText().toString();
        if(TextUtils.isEmpty(feed)){
            feedback.requestFocus();
            feedback.setError("Requred feild");
            return;
        }
        float ratingvalue=ratingBar.getRating();
        rating=Float.toString(ratingvalue);



        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                // progressBar.setVisibility(View.INVISIBLE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");
                    error = jsonObject.getString("error");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (status.equals("1")) {
                    Toast.makeText(FeedbackActivity.this, "Thank you for the feedback", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FeedbackActivity.this, Home.class));

                }else
                {
                    Toast.makeText(FeedbackActivity.this, "Failed!", Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(FeedbackActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){


            @Override
            protected Map<String, String> getParams(){

                Map map=new HashMap<String ,String >();

                map.put("rating",rating);
                map.put("id",username);
                map.put("name",pa);
                map.put("mobile_no",wa);
                map.put("feedback",feed);
                return map;

            }
        };

        RequestQueue queue= Volley.newRequestQueue(FeedbackActivity.this);
        queue.add(request);



    }
}