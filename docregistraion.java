package com.example.skind;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class docregistraion extends AppCompatActivity {
    TextView rt1, rt2;
    EditText regname,regadde, regmail, regphone,regspecialization,regwei,regage,regpass;
    RadioGroup regen;

    Button rb1,regcal;
    String name, address,email, phonenumber,specialization,weight,age, password,gender,dob,status,message;

    //spinner cheyunna code
//    String spin;
//
//    Spinner spi;
//    String dis[]={"Select your district", "Alappuzha", "Ernakulam", "Kozhikode", "Palakkad", "Kollam", "Kannur", "Kasaragod", "Idukki","Kottayam", "Thrissur", "Pathanamthitta", "Malappuram", "Wayanad"," Thiruvananthapuram"};
//


    String url = config.baseurl+"docregister.php";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docregistration);

        rt2 = findViewById(R.id.docalready);
        regname= findViewById(R.id.docName);
        regmail = findViewById(R.id.docEmail);
        regphone=findViewById(R.id.docPhone);
        regspecialization=findViewById(R.id.docSpecialization);
        //   regen = findViewById(R.id.docradioGroupGender);
        regpass = findViewById(R.id.docpassword);
        rb1 = findViewById(R.id.docbuttonRegister);


        rt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(docregistraion.this, doclogin.class);
                startActivity(i);
            }
        });



        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration1();
            }
        });


    }

    private void registration1() {
        // spin=spi.getSelectedItem().toString();
        name = regname.getText().toString();
        email = regmail.getText().toString();
        phonenumber = regphone.getText().toString();
        specialization=regspecialization.getText().toString();
        password = regpass.getText().toString();

//        int id=regen.getCheckedRadioButtonId();
//        RadioButton radioButton = regen.findViewById(id);
//        gender = radioButton.getText().toString();

//        if(spin.equals("Select your district")){
//            Toast.makeText(Register.this, "Select your district", Toast.LENGTH_SHORT).show();
//        }


        if (TextUtils.isEmpty(name)) {
            regname.requestFocus();
            regname.setError("Enter name");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            regmail.requestFocus();
            regmail.setError("Enter email");
            return;
        }
        if (TextUtils.isEmpty(phonenumber)) {
            regphone.requestFocus();
            regphone.setError("Enter phone number");
            return;
        }
        if (TextUtils.isEmpty(specialization)) {
            regspecialization.requestFocus();
            regspecialization.setError("Enter Specialization");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            regpass.requestFocus();
            regpass.setError("Enter password");
            return;
        }
        if (password.length() < 6) {
            regpass.setError( "Password Must be 6 Characters");
            return;
        }
        if (phonenumber.length() < 10) {
            regphone.setError( "Enter Valid Phone Number");
            return;
        }
        if (phonenumber.length() > 10) {
            regphone.setError( "Enter Valid Phone Number");
            return;
        }
        StringRequest StringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(docregistraion.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject c = new JSONObject(response);
                            status = c.getString("status");
                            message = c.getString("message");
                            checklogin();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //run cheyikkumbo error indo ennu nokkan
                        Toast.makeText(docregistraion.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }

                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("phone", phonenumber);
                params.put("specialization", specialization);
                params.put("password", password);
                return params;
            }


        };

        //string reqt ne execute cheyan aanu requestqueue
        Volley volley =  null;
        RequestQueue requestQueue = volley.newRequestQueue(this);
        requestQueue.add(StringRequest);
    }


    private void checklogin() {
        if (status.equals("0")){
            Toast.makeText(this, "Invalied", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show();
            Intent i =new Intent(docregistraion.this,Login.class);
            startActivity(i);
            finish();
        }

    }
}
