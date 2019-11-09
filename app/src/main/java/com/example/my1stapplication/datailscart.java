package com.example.my1stapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class datailscart extends AppCompatActivity implements java.io.Serializable {
    private FirebaseDatabase mItemsFirebaseDatabase;
    private DatabaseReference mItemsDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCustomerRefernce;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ModleCart cmObej;
    TextView main_txt,sub_main_txt,desc , matrialT,uniname , descr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
       // final Controller aController = (Controller) this.getApplicationContext();

        main_txt = findViewById(R.id.main_txt);
        sub_main_txt = findViewById(R.id.sub_main_txt);
        desc=findViewById(R.id.desc);
        matrialT =findViewById(R.id.matrialT);
        uniname =findViewById(R.id.uniname);
        descr=findViewById(R.id.descr);
        Button mainsitebtn = findViewById(R.id.mainsitebtn)      ;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCustomerRefernce = mFirebaseDatabase.getReference();
        final String itemName = getIntent().getStringExtra("itemName");
        final String itemPrice = getIntent().getStringExtra("itemPrice");
        final String uni = getIntent().getStringExtra("uni");
        final String coursename = getIntent().getStringExtra("coursename");
        final String ID = getIntent().getStringExtra("ID");
        if(getIntent().getStringExtra("Hide").equals("true")){
            mainsitebtn.setVisibility(View.INVISIBLE);
        }
        mainsitebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               DatabaseReference pp = FirebaseDatabase.getInstance().getReference("posts").child(ID);
               // Post product=new Post(ID,matrialT.getText().toString(),coursename,uni,matrialT.getText().toString(),itemPrice,"Test");
               // Log.e("Test",coursename);
                mCustomerRefernce.child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ID).setValue(pp);
                Toast.makeText(datailscart.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });


        FirebaseApp.initializeApp(this);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    desc.setVisibility(View.INVISIBLE);
                    matrialT.setVisibility(View.INVISIBLE);

                    descr.setText(itemPrice + " SR ");
                    sub_main_txt.setText(coursename);
                    main_txt.setText(itemName);
                    //matrialT.setText( "Material Type: "+materialtype);
                    uniname.setText("University:"+uni);
                    //desc.setText("Description: "+Descr);
                    /*try {
                        List<String> posts = new ArrayList<>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            posts.add(postSnapshot.getValue().toString());
                            String coursename = dataSnapshot.child("-LqXwjARONySi3JvUwAG").child("coursename").getValue().toString();
                            String price = dataSnapshot.child("-LqXwjARONySi3JvUwAG").child("price").getValue().toString();
                            String materialname =dataSnapshot.child("-LqXwjARONySi3JvUwAG").child("materialname").getValue().toString();
                            String materialtype = dataSnapshot.child("-LqXwjARONySi3JvUwAG").child("materialtype").getValue().toString();
                            String uni = dataSnapshot.child("-LqXwjARONySi3JvUwAG").child("uniname").getValue().toString();
                            String Descr = dataSnapshot.child("-LqXwjARONySi3JvUwAG").child("description").getValue().toString();
                            descr.setText(price + " SR ");
                            sub_main_txt.setText(coursename);
                            main_txt.setText(materialname);
                            matrialT.setText( "Material Type: "+materialtype);
                            uniname.setText(uni);
                            desc.setText("Description: "+Descr);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/

                    /*HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;

                            Toast.makeText(datailscart.this, "" + userData.get("coursename"), Toast.LENGTH_SHORT).show();

                        } catch (ClassCastException cce) {

                            try {

                                String mString = String.valueOf(dataMap.get(key));

                            } catch (ClassCastException cce2) {

                            }
                        }

                    }*/



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
