package com.example.skind;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class upload extends AppCompatActivity {
    private static ProgressDialog mProgressDialog;
    EditText time2;
    EditText dh, dd, patientname;
    TextView doctorname, phone;
    Button c,morein,caer;
    Calendar xtime;
    Button fee;
    String url = config.baseurl + "upload.php";
    String doctor, h, phn, patient, ca, ti, status, message, id, id1, disease, probability;
    TextView diseaseTxt, probTxt;
    private RequestQueue rQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        disease = getIntent().getStringExtra("class");
        probability = String.valueOf(getIntent().getFloatExtra("prob", 0));

        xtime = Calendar.getInstance();
        doctorname = findViewById(R.id.td);
        morein=findViewById(R.id.moreinfo);
        caer=findViewById(R.id.care);
        fee=findViewById(R.id.feedbackk);

        diseaseTxt = findViewById(R.id.diseaseTxt);
        probTxt = findViewById(R.id.probTxt);
//        patientname = findViewById(R.id.td3);
        phone = findViewById(R.id.td2);
      //  b = findViewById(R.id.td6);


        diseaseTxt.setText(disease);
        probTxt.setText("Probability: " + probability);



        morein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(upload.this,doclist.class);
                startActivity(in);
            }
        });


        fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(upload.this,FeedbackActivity.class);
                startActivity(in);
            }
        });


        caer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(upload.this, hctfullactivity.class);
                startActivity(in);
            }
        });
//
//        Intent intent = getIntent();
//        doctor = intent.getStringExtra("name");
//        doctorname.setText(doctor);

        HashMap<String, String> map = new SessionManager(upload.this).getUserDetails();
        id = map.get("id");
        doctor = map.get("name");
        doctorname.setText(doctor);
        phn = map.get("phonenumber");
        phone.setText(phn);


//
//        c.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar cldr = Calendar.getInstance();
//                int day = cldr.get(Calendar.DAY_OF_MONTH);
//                int month = cldr.get(Calendar.MONTH);
//                int year = cldr.get(Calendar.YEAR);
//                DatePickerDialog datePicker = new DatePickerDialog(upload.this,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int day) {
//                                c.setText(day + "/" + (monthOfYear + 1) + "/" + year);
//                            }
//                        }, year, month, day);
//                datePicker.show();
//
//            }
//        });
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                upload1();
//            }
//        });


    }

    private void upload1() {
        doctor = doctorname.getText().toString();

//        patient = patientname.getText().toString();

        phn = phone.getText().toString();

//        ca = c.getText().toString();
//
//        ti = time2.getText().toString();


        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;
            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("nam  ", displayName);

                        uploadPDF(displayName, uri);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ", displayName);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadPDF(final String pdfname, Uri pdffile) {
        InputStream iStream = null;
        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            showSimpleProgressDialog(upload.this, null, "Uploading image", false);
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            removeSimpleProgressDialog();
                            Log.d("res", new String(response.data));
                            rQueue.getCache().clear();
                            try {

                                JSONObject jsonObject = new JSONObject(new String(response.data));

                                jsonObject.toString().replace("\\\\", "");

                                status = jsonObject.getString("status");
                                message = jsonObject.getString("message");

                                if (status.equals("1")) {
                                    //    Toast.makeText(this, " successfully", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(upload.this, "successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(upload.this, Home.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(upload.this, message, Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            removeSimpleProgressDialog();
                            Toast.makeText(upload.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", doctor);

                    params.put("phonenumber", phn);
                    params.put("img", ca);


                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    params.put("filename", new DataPart(pdfname, inputData));
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            rQueue = Volley.newRequestQueue(this);
            rQueue.add(volleyMultipartRequest);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    public void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            Log.e("Log", "inside catch IllegalArgumentException");
            ie.printStackTrace();
        } catch (RuntimeException re) {
            Log.e("Log", "inside catch RuntimeException");
            re.printStackTrace();
        } catch (Exception e) {
            Log.e("Log", "Inside catch Exception");
            e.printStackTrace();
        }

    }

    public void showSimpleProgressDialog(Context context, String title,
                                         String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
