package com.example.skind;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.example.job.Home;
//import com.example.job.R;
//import com.example.job.SessionManager;
//import com.example.job.config;
import com.example.skind.Home;
import com.example.skind.R;
import com.example.skind.SessionManager;
import com.example.skind.config;
//import com.example.quitdrugs.Home;
//import com.example.quitdrugs.R;
//import com.example.quitdrugs.SessionManager;
//import com.example.quitdrugs.config;
//import com.example.quitdrugs.Home;
//import com.example.quitdrugs.R;
//import com.example.quitdrugs.SessionManager;
//import com.example.quitdrugs.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class drprofile extends AppCompatActivity {

    EditText regname, regmail, regphone,reghei,regpass;
    Button update;

    String pregname, pregmail, pregphone,preghei,pregpass,url= config.baseurl+"drprofileupdation.php";
    String ppid,ppregname,ppregmail, ppregphone,ppreghei,ppregpass,status,message;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drprofile);

        regname= findViewById(R.id.doceditTextName1);
        regmail = findViewById(R.id.doceditTextEmail1);
     //   regphone=findViewById(R.id.doceditTextPhone1);
      //  reghei=findViewById(R.id.doceditTextheight1);
        regpass = findViewById(R.id.doceditTextpassword1);
        update = findViewById(R.id.docbuttonRegister1);

        HashMap<String,String> data=new DrSessionManager(drprofile.this).getUserDetails();


        ppid=data.get("id");
        pregname=data.get("name");
        pregmail=data.get("email");
        pregphone=data.get("phone");
        preghei=data.get("specialization");
        pregpass=data.get("password");

        regname.setText(pregname);
        regmail.setText(pregmail);
        regphone.setText(pregphone);
        reghei.setText(preghei);
        regpass.setText(pregpass);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit();
            }
        });
        return ;
    }
    private void submit() {
        ppregname=regname.getText().toString();
        ppregmail=regmail.getText().toString();
        ppregphone=regphone.getText().toString();
        ppreghei=reghei.getText().toString();
        ppregpass=regpass.getText().toString();

        if (TextUtils.isEmpty(pregname)){
            regname.requestFocus();
            regname.setError("required field");
            return;
        }

        if (TextUtils.isEmpty(pregmail)){
            regmail.requestFocus();
            regmail.setError("required field");
            return;
        }
        if (TextUtils.isEmpty(pregphone)){
            regphone.requestFocus();
            regphone.setError("required field");
            return;
        }
        if (TextUtils.isEmpty(preghei)){
            reghei.requestFocus();
            reghei.setError("required field");
            return;
        }
        if (TextUtils.isEmpty(ppregpass)){
            regpass.requestFocus();
            regpass.setError("required field");
            return;
        }
        StringRequest str = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(drprofile.this, response, Toast.LENGTH_SHORT).show();


                try {
                    JSONObject json = new JSONObject(response);
                    status = json.getString("status");
                    message = json.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if ("0".equals(status)) {
                    Toast.makeText(drprofile.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(drprofile.this, "updation successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(drprofile.this, Home.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(drprofile.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id",ppid);
                params.put("name",ppregname);
                params.put("email",ppregmail);
                params.put("phone",ppregphone);
                params.put("specialization",ppreghei);
                params.put("password",ppregpass);

                return params;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(drprofile.this);
        rq.add(str);
    }



}
