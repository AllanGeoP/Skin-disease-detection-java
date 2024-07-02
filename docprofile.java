package com.example.skind;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skind.DrSessionManager;
import com.example.skind.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class docprofile extends AppCompatActivity {
    EditText name;
    EditText Email;
    EditText Phone;
    EditText specialization;
    EditText password;

    Button update;

    String pname,pemail,pphn,pspec,ppass,url= config.baseurl+"drprofileupdation.php";
    String ppid,ppname,ppemail,ppphn,ppspec,pppass,status,message;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docprofile);

        name = findViewById(R.id.editTextName223);
        Email=findViewById(R.id.editTextdistrict223);
        Phone= findViewById(R.id.editTextArea223);
        specialization = findViewById(R.id.editTextward223);
        password = findViewById(R.id.editTextpassword223);

        update = findViewById(R.id.buttonRegister223);


        HashMap<String,String> data=new DrSessionManager(docprofile.this).getUserDetails();
        ppid=data.get("id");
        pname=data.get("name");
        pemail=data.get("email");
        pphn=data.get("phonenumber");
        ppspec=data.get("specialization");
        ppass=data.get("password");



        name.setText(pname);
        Email.setText(pemail);
        Phone.setText(pphn);
        specialization.setText(pspec);
        password.setText(ppass);




        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit();
            }
        });
        return ;
    }
    private void submit() {
        ppname=name.getText().toString();
        ppemail=Email.getText().toString();
        ppphn=Phone.getText().toString();
        ppspec=specialization.getText().toString();
        pppass=password.getText().toString();



        if (TextUtils.isEmpty(pname)){
            name.requestFocus();
            name.setError("required field");
            return;
        }
        if (TextUtils.isEmpty(pemail)){
            Email.requestFocus();
            Email.setError("required field");
            return;
        }
        if (TextUtils.isEmpty(pphn)){
            Phone.requestFocus();
            Phone.setError("required field");
            return;
        }
        if (TextUtils.isEmpty(pspec)){
            specialization.requestFocus();
            specialization.setError("required field");
            return;
        }
        if (TextUtils.isEmpty(ppass)){
            password.requestFocus();
            password.setError("required field");
            return;
        }
//        if (TextUtils.isEmpty(parea)){
//            area.requestFocus();
//            area.setError("required field");
//            return;
//        }
//        if (TextUtils.isEmpty(pward)){
//            wardnumber.requestFocus();
//            wardnumber.setError("required field");
//            return;
//        }
        StringRequest str = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(docprofile.this, response, Toast.LENGTH_SHORT).show();


                try {
                    JSONObject json = new JSONObject(response);
                    status = json.getString("status");
                    message = json.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if ("0".equals(status)) {
                    Toast.makeText(docprofile.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(docprofile.this, "updation successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(docprofile.this, dochome.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(docprofile.this ,error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();



                params.put("id",ppid);
                params.put("name",ppname);
                params.put("email",pppass);
                params.put("phone",ppphn);
                params.put("specialization",ppphn);
                params.put("password",pppass);


                return params;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(docprofile.this);
        rq.add(str);
    }


}