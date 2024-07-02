package com.example.skind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class chooseuser extends AppCompatActivity {

    CardView tv1, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseuser);

        tv1 = findViewById(R.id.userlogin);
        tv2=findViewById(R.id.doctorlogin);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(chooseuser.this,Login.class);
                startActivity(in);
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(chooseuser.this,doclogin.class);
                startActivity(in);
            }
        });
    }

}
