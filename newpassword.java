package com.example.skind;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skind.Login;
import com.example.skind.R;
import com.example.skind.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class newpassword extends AppCompatActivity {
    EditText newpassword,confirm;
    Button confirmpass;
    String password,cpassword,name,status,message,url = config.baseurl+"newpass.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        newpassword = findViewById(R.id.newpassword);
        confirm = findViewById(R.id.confirm);
        confirmpass = findViewById(R.id.confirmpass);

        Intent intent = getIntent();
        name = intent.getStringExtra("phonenumber");

        confirmpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword();
            }
        });

    }

    private void newPassword() {
        password = newpassword.getText().toString();
        cpassword = confirm.getText().toString();

        if (TextUtils.isEmpty(password)){
            newpassword.setError("Required");
            newpassword.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(cpassword))
        {
            confirm.setError("Required");
            confirm.requestFocus();
            return;
        }
        else if (!password.equals(cpassword))
        {
            Toast.makeText(this, "password mismatch", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");
                    message = jsonObject.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if ("1".equals(status)) {
                    Toast.makeText(newpassword.this, message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                } else {
                    Toast.makeText(newpassword.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(newpassword.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("password",password);
                map.put("phonenumber",name);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}