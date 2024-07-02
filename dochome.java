package com.example.skind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class dochome extends AppCompatActivity {

    CardView c1,p1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dochome);

        c1=findViewById(R.id.cardviewdocid1);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dochome.this,DocChatsActivity.class));
            }
        });
       // p1=findViewById(R.id.cardviewdocid2);


//        p1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent in= new Intent(dochome.this, drprofile.class);
//                startActivity(in);
//
//            }
//        });
   }
}