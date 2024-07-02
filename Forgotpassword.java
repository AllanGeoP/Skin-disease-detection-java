package com.example.skind;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Forgotpassword extends AppCompatActivity {
    Button sub;
    EditText contact;
    String username,status,message,url = config.baseurl+"forgetpass.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        sub = findViewById(R.id.sub);
        contact = findViewById(R.id.contact);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), newpassword.class));
                finish();
                checkUserName();
            }
        });
    }

    private void checkUserName() {
        username = contact.getText().toString();
        if (TextUtils.isEmpty(username))
        {
            contact.setError("Required");
            contact.requestFocus();
            return;
        }

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reponse>>",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");
                    message = jsonObject.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if ("1".equals(status)) {
//                    Toast.makeText(ForgotActivity.this, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),newpassword.class);
                    intent.putExtra("phonenumber",username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Forgotpassword.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Forgotpassword.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("phonenumber",username);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}