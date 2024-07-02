package com.example.skind.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

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


public class ProfileFragment extends Fragment {

    EditText regname,regadde, regmail,regen, regphone,reghei,regwei,regage,regpass;
    Button update;

    String pregname,pregadde, pregmail,pregen, pregphone,preghei,pregwei,pregage,pregpass,url= config.baseurl+"profileupdation.php";
    String ppid,ppregname,ppregadde, ppregmail,ppregen, ppregphone,ppreghei,ppregwei,ppregage,ppregpass,status,message;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile, container, false);

        regname= v.findViewById(R.id.editTextName1);
        regadde= v.findViewById(R.id.editTextAddress1);
        regmail = v.findViewById(R.id.editTextEmail1);
        regphone=v.findViewById(R.id.editTextPhone1);
        reghei=v.findViewById(R.id.editTextheight1);
        regwei=v.findViewById(R.id.editTextweight1);
        regage=v.findViewById(R.id.editTextage1);
        regen = v.findViewById(R.id.editTextgender1);
        regpass = v.findViewById(R.id.editTextpassword1);
        update = v.findViewById(R.id.buttonRegister1);

        HashMap<String,String> data=new SessionManager(getActivity()).getUserDetails();



        ppid=data.get("id");
        pregname=data.get("name");
        pregadde=data.get("address");
        pregmail=data.get("email");
        pregphone=data.get("phonenumber");
        preghei=data.get("height");
        pregwei=data.get("weight");
        pregage=data.get("age");
        pregen=data.get("gender");
        pregpass=data.get("password");

        regname.setText(pregname);
        regadde.setText(pregadde);
        regmail.setText(pregmail);
        regphone.setText(pregphone);
        reghei.setText(preghei);
        regwei.setText(pregwei);
        regage.setText(pregage);
        regen.setText(pregen);
        regpass.setText(pregpass);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit();
            }
        });
        return v;
    }
    private void submit() {
        ppregname=regname.getText().toString();
        ppregadde=regadde.getText().toString();
        ppregmail=regmail.getText().toString();
        ppregphone=regphone.getText().toString();
        ppreghei=reghei.getText().toString();
        ppregwei=regwei.getText().toString();
        ppregage=regage.getText().toString();
        ppregen=regen.getText().toString();
        ppregpass=regpass.getText().toString();

        if (TextUtils.isEmpty(pregname)){
            regname.requestFocus();
            regname.setError("required field");
            return;
        }

        if (TextUtils.isEmpty(pregadde)){
            regadde.requestFocus();
            regadde.setError("required field");
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
        if (TextUtils.isEmpty(pregwei)){
            regwei.requestFocus();
            regwei.setError("required field");
            return;
        }
        if (TextUtils.isEmpty(pregage)){
            regage.requestFocus();
            regage.setError("required field");
            return;
        }
        if (TextUtils.isEmpty(pregen)){
            regen.requestFocus();
            regen.setError("required field");
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

                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();


                try {
                    JSONObject json = new JSONObject(response);
                    status = json.getString("status");
                    message = json.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if ("0".equals(status)) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "updation successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), Home.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id",ppid);
                params.put("name",ppregname);
                params.put("address",ppregadde);
                params.put("email",ppregmail);
                params.put("phonenumber",ppregphone);
                params.put("height",ppreghei);
                params.put("weight",ppregwei);
                params.put("age",ppregage);
                params.put("gender",ppregen);
                params.put("password",ppregpass);

                return params;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(str);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Object binding = null;
    }

}
