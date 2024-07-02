package com.example.skind;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class doclogin extends AppCompatActivity {
    TextView lt1,lt2,forgot;
    EditText lname,lpassword;
    Button button;

    String regadde, regmail, regphone,regspecialization,regwei,regage,regen;

    String name,password;
    String status,message,id;
    String url = config.baseurl+"doclogin.php";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doclogin);

        lt1=findViewById(R.id.docregistertextview);
        //  lt2=findViewById(R.id.forgetpassword);
        lname=findViewById(R.id.docusername);
        lpassword=findViewById(R.id.docpassword);
        button=findViewById(R.id.docbuttonsigin);

//        forgot=findViewById(R.id.forgetpassword);
//        forgot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Login.this,Forgotpassword.class);
//                startActivity(intent);
//            }
//        });


//        forgot=findViewById(R.id.forgot);
//        forgot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Loginactivity.this,Forgotpassword.class);
//                startActivity(intent);
//            }
//        });

        //   btnResetPassword = findViewById(R.id.btnResetPassword);



        lt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(doclogin.this, docregistraion.class);
                startActivity(i);

            }
        });

//        lb1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(login.this,Home.class);
//                startActivity(i);
//
//            }
//        });


//login method creation code=call the string variable
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        name = lname.getText().toString();
        password = lpassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            lname.requestFocus();
            lname.setError("Enter name");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            lpassword.requestFocus();
            lpassword.setError("Enter password");
            return;
        }
        StringRequest StringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject c = new JSONObject(response);
                            status = c.getString("status");
                            message = c.getString("message");


                            id = c.getString("id");
                            name = c.getString("name");
                            regmail = c.getString("email");
                            regphone = c.getString("phone");
                            regspecialization = c.getString("specialization");
                            password = c.getString("password");
                            checklogin();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(doclogin.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }

                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("password", password);
                return params;
            }


        };
        Volley volley =  null;
        RequestQueue requestQueue = volley.newRequestQueue(this);
        requestQueue.add(StringRequest);
    }







    private void checklogin() {
        if (status.equals("0")){
            Toast.makeText(this, "Invalied", Toast.LENGTH_SHORT).show();
        }else {
            //     Toast.makeText(this, "correct", Toast.LENGTH_SHORT).show();
              new DrSessionManager(doclogin.this).createLoginSession(id, name,regadde, regmail, regphone,regspecialization,regwei,regage,regen,password );
            Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(doclogin.this, dochome.class);
            startActivity(i);
            finish();

        }
    }
}
