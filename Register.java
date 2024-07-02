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

public class Register extends AppCompatActivity {
    TextView rt1, rt2;
    EditText regname,regadde, regmail, regphone,reghei,regwei,regage,regpass;
    RadioGroup regen;

    Button rb1,regcal;
    String name, address,email, phonenumber,height,weight,age, password,gender,dob,status,message;

    //spinner cheyunna code
//    String spin;
//
//    Spinner spi;
//    String dis[]={"Select your district", "Alappuzha", "Ernakulam", "Kozhikode", "Palakkad", "Kollam", "Kannur", "Kasaragod", "Idukki","Kottayam", "Thrissur", "Pathanamthitta", "Malappuram", "Wayanad"," Thiruvananthapuram"};
//


    String url = config.baseurl+"register.php";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rt2 = findViewById(R.id.already);
        regname= findViewById(R.id.editTextName);
        regadde= findViewById(R.id.editTextAddress);
        regmail = findViewById(R.id.editTextEmail);
        regphone=findViewById(R.id.editTextPhone);
        reghei=findViewById(R.id.editTextheight);
        regwei=findViewById(R.id.editTextweight);
        regage=findViewById(R.id.editTextage);
        regen = findViewById(R.id.radioGroupGender);
        regpass = findViewById(R.id.editTextpassword);
        rb1 = findViewById(R.id.buttonRegister);


//spinner id binding
//        spi=findViewById(R.id.sp);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dis);
//        //spinner object.setAdapter(adapter);
//        spi.setAdapter(adapter);


        //Intent passing

//        rb1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(RegistrationActivity.this, LoginActivity.class);
//                startActivity(in);
//            }
//        });
        rt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
            }
        });



//        regcal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar cldr = Calendar.getInstance();
//                int day = cldr.get(Calendar.DAY_OF_MONTH);
//                int month = cldr.get(Calendar.MONTH);
//                int year = cldr.get(Calendar.YEAR);
//                DatePickerDialog datePicker = new DatePickerDialog(Register.this,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int day) {
//                                regcal.setText(day + "/" + (monthOfYear + 1) + "/" + year);
//                            }
//                        }, year, month, day);
//                datePicker.show();
//
//            }
//        });

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
        address = regadde.getText().toString();
        email = regmail.getText().toString();
        phonenumber = regphone.getText().toString();
        height=reghei.getText().toString();
        weight=regwei.getText().toString();
        age=regage.getText().toString();
        password = regpass.getText().toString();

        int id=regen.getCheckedRadioButtonId();
        RadioButton radioButton = regen.findViewById(id);
        gender = radioButton.getText().toString();

//        if(spin.equals("Select your district")){
//            Toast.makeText(Register.this, "Select your district", Toast.LENGTH_SHORT).show();
//        }


        if (TextUtils.isEmpty(name)) {
            regname.requestFocus();
            regname.setError("Enter name");
            return;
        }

        if (TextUtils.isEmpty(address)) {
            regadde.requestFocus();
            regadde.setError("Enter address");
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
        if (TextUtils.isEmpty(height)) {
            reghei.requestFocus();
            reghei.setError("Enter phone number");
            return;
        }
        if (TextUtils.isEmpty(weight)) {
            regwei.requestFocus();
            regwei.setError("Enter phone number");
            return;
        }
        if (TextUtils.isEmpty(age)) {
            regage.requestFocus();
            regage.setError("Enter phone number");
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
                        Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Register.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }

                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("address", address );
                params.put("email", email);
                params.put("phonenumber", phonenumber);
                params.put("height", height);
                params.put("weight", weight);
                params.put("age", age);
                params.put("gender", gender);
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
            Intent i =new Intent(Register.this,Login.class);
            startActivity(i);
            finish();
        }

    }
}
