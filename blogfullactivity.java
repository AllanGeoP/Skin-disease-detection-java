package com.example.skind;;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

public class blogfullactivity  extends AppCompatActivity {
    ImageView iv1,iv2,wh2,v2;
    TextView t1,t2,t3,t4;
    String s1,s2,st1,st2,st3,st4,Uid;
    Button dbu;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogfullactivity);
        //  iv1=findViewById(R.id.iv1);
        t1=findViewById(R.id.bn1);
        t2=findViewById(R.id.bn2);



//        dbu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent in=new Intent(DesignviewActivity.this, upload_activity.class);
//                in.putExtra("id",Uid);
//                in.putExtra("name",st1);
//                in.putExtra("place",st2);
//                in.putExtra("phone",st3);
//                in.putExtra("email",st4);
//
//                startActivity(in);
//            }
//        });
//        v2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent in = new Intent(DesignviewActivity.this, viewmore_bimages.class);
//                startActivity(in);
//            }
//        });
//        wh2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                chatw();
//            }
//
//            private void chatw() {
//                Uri mUri = Uri.parse("smsto:" + st3);
//                Intent mIntent = new Intent(Intent.ACTION_SENDTO, mUri);
//                mIntent.setPackage("com.whatsapp");
//                mIntent.putExtra("sms_body", "The text goes here");
//                mIntent.putExtra("chat", true);
//                startActivity(Intent.createChooser(mIntent, ""));
//            }
//
//        });
//
        Intent intent=getIntent();
//        s1=intent.getStringExtra("image");
//        Picasso.get().load(configure.imgurl+s1).into(iv1);



        Uid=intent.getStringExtra("id");
        st1=intent.getStringExtra("name");
        t1.setText("name: "+st1);
        st2=intent.getStringExtra("details1");
        t2.setText("details : "+st2);
//        st3=intent.getStringExtra("date");
//        t3.setText("Uploading date: "+st3);
//        st4=intent.getStringExtra("addnotes");
//        t4.setText("Subject : "+st4);



    }
}