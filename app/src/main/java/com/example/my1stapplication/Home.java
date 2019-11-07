package com.example.my1stapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity {


    TextView main_txt,sub_main_txt,desc , matrialT,uniname , descr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookslistlayout);

        main_txt = findViewById(R.id.materialname);
        sub_main_txt = findViewById(R.id.coursename);
        desc=findViewById(R.id.price);
        //matrialT =findViewById(R.id.matrialT);
        uniname =findViewById(R.id.uniname);
        //descr=findViewById(R.id.descr);
        //Button mainsitebtn = findViewById(R.id.mainsitebtn)      ;

        //mainsitebtn.setOnClickListener(new View.OnClickListener() {

            /*@Override
            public void onClick(View v) {
                Toast.makeText(datailscart.this, "the book has been added", Toast.LENGTH_SHORT).show();
            }*/
        //});

        FirebaseApp.initializeApp(this);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    try {
                        List<String> posts = new ArrayList<>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            posts.add(postSnapshot.getValue().toString());
                            String coursename = dataSnapshot.child("-LqXwjARONySi3JvUwAG").child("coursename").getValue().toString();
                            String price = dataSnapshot.child("-LqXwjARONySi3JvUwAG").child("price").getValue().toString();
                            String materialname =dataSnapshot.child("-LqXwjARONySi3JvUwAG").child("materialname").getValue().toString();
                            String materialtype = dataSnapshot.child("-LqXwjARONySi3JvUwAG").child("materialtype").getValue().toString();
                            String uni = dataSnapshot.child("-LqXwjARONySi3JvUwAG").child("uniname").getValue().toString();
                            String Descr = dataSnapshot.child("-LqXwjARONySi3JvUwAG").child("description").getValue().toString();
                            desc.setText(price + " SR ");
                            sub_main_txt.setText(coursename);
                            main_txt.setText(materialname);
                            //matrialT.setText( "Material Type: "+materialtype);
                            uniname.setText(uni);
                            //descr.setText("Description: "+Descr);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                /*    HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;

                          //  Toast.makeText(MainActivity.this, "" + userData.get("coursename"), Toast.LENGTH_SHORT).show();

                        } catch (ClassCastException cce) {

                            try {

                                String mString = String.valueOf(dataMap.get(key));

                            } catch (ClassCastException cce2) {

                            }
                        }

                    }
*/


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
