package com.example.skind;

import static com.android.volley.Request.Method.GET;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LiveChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText message_text_text;
    ImageView send_btn;
    List<LiveMessage> messageList = new ArrayList<>();
    LiveMessageAdapter messageAdapter;

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    String senderId = "",receiverId = "", senderRole = "patient",doctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_livechat);

        senderId = getIntent().getStringExtra("sender_id");
        receiverId = getIntent().getStringExtra("receiver_id");
        senderRole = getIntent().getStringExtra("role");
        doctorName =  getIntent().getStringExtra("name");


        if(senderRole.equals("patient")){
            getSupportActionBar().setTitle("Chat with Dr. "+doctorName);
        }else{
            getSupportActionBar().setTitle("Chat with "+doctorName);
        }
        fetchMessages(senderId,receiverId);

        message_text_text = findViewById(R.id.message_text_text);
        send_btn = findViewById(R.id.send_btn);
        recyclerView =findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LiveChatActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        messageAdapter = new LiveMessageAdapter(messageList,doctorName);
        recyclerView.setAdapter(messageAdapter);

        if (!isConnected(LiveChatActivity.this)) {
            buildDialog(LiveChatActivity.this).show();
        }

//        loadMessages();

        message_text_text.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().length() == 0) {
                    send_btn.setEnabled(false);
                    // Toast.makeText(MainActivity.this, "Type your message", Toast.LENGTH_SHORT).show();
                } else {
                    send_btn.setEnabled(true);
                    send_btn.setOnClickListener(view -> {
                        String question = message_text_text.getText().toString().trim();
                        sendMessage(question);
//                        addToChat(question, Message.SEND_BY_ME);
                        message_text_text.setText("");

//                        callAPI(question);
                    });
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

    }

//    void addToChat(String message, String sendBy) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                messageList.add(new Message(message, sendBy));
//                messageAdapter.notifyDataSetChanged();
//                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
//                saveMessages();
//            }
//        });
//    } // addToChat End Here =====================

//    void addResponse(String response) {
//        messageList.remove(messageList.size() - 1);
//        Log.d("OPENAI", response);
//        addToChat(response, Message.SEND_BY_BOT);
//    } // addResponse End Here =======

    JSONArray formattedMessageList = new JSONArray();

//    void callAPI(String question) {
//        // okhttp
//        messageList.add(new Message("Typing...", Message.SEND_BY_BOT));
//
//        JSONObject jsonBody = new JSONObject();
//        try {
//
//            JSONObject systemMessage = new JSONObject();
//            systemMessage.put("role", "system");
//            systemMessage.put("content", "You are helpful assistant that only replied questions about human skin, such as skin disease, skin tone, skin analysis. Answers would be brief and in simple terms");
//
//            formattedMessageList.put(systemMessage);
//
//            for (Message message : messageList.subList(0, messageList.size() - 1)) {
//                JSONObject promptMessage = new JSONObject();
//                promptMessage.put("role", message.getSentBy().equalsIgnoreCase("bot") ? "assistant" : "user");
//                promptMessage.put("content", message.getMessage());
//                formattedMessageList.put(promptMessage);
//            }
//
//
//            jsonBody.put("model", "gpt-3.5-turbo");
//            jsonBody.put("max_tokens", 200);
//            jsonBody.put("temperature", 0.2);
//            jsonBody.put("messages",formattedMessageList);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//        RequestBody requestBody = RequestBody.create(jsonBody.toString(), JSON);
//        Request request = new Request.Builder()
//                .url(API.API_URL)
//                .header("Authorization", "Bearer " + API.API)
//                .post(requestBody)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                addResponse("Failed to load response due to" + e.getLocalizedMessage());
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    JSONObject jsonObject = null;
//                    try {
//                        jsonObject = new JSONObject(response.body().string());
//                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
//                        String result = jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
//                        addResponse(result.trim());
//                    } catch (JSONException e) {
//                        throw new RuntimeException(e);
//                    }
//                } else {
//                    addResponse("Failed to load response due to" + response.body().toString());
//                }
//
//            }
//        });
//
//    } // callAPI End Here =============




    public void sendMessage(String message) {
        String url = config.baseurl + "send_messages.php";

        StringRequest StringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                            fetchMessages(senderId,receiverId);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //run cheyikkumbo error indo ennu nokkan
                        Toast.makeText(LiveChatActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }

                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sender_id", senderId);
                params.put("receiver_id", receiverId );
                params.put("sender_role", senderRole);
                params.put("message", message);

                return params;
            }
        };

        Volley volley =  null;
        RequestQueue requestQueue = volley.newRequestQueue(this);
        requestQueue.add(StringRequest);
    }


    public void fetchMessages(String senderId, String receiverId) {
        String url = config.baseurl + "retrieve_messages.php?sender_id=" + senderId + "&receiver_id=" + receiverId;

        StringRequest StringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<LiveMessage>>() {
                        }.getType();
                        messageList.clear();
                        if(gson.fromJson(response, type)!=null){
                            messageList.addAll(gson.fromJson(response, type));
                            messageAdapter.notifyDataSetChanged();
                        }

                        System.out.println("MESSAGES:"+messageList.size());

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //run cheyikkumbo error indo ennu nokkan
                        Toast.makeText(LiveChatActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }

                }) {


        };

        //string reqt ne execute cheyan aanu requestqueue
        Volley volley =  null;
        RequestQueue requestQueue = volley.newRequestQueue(this);
        requestQueue.add(StringRequest);
    }

    public boolean isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {
            NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please check your internet connection.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
} // Public Class End Here =========================