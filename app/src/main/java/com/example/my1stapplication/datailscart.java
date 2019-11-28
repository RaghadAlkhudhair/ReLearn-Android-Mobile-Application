package com.example.my1stapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
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

public class datailscart extends AppCompatActivity {
    private FirebaseDatabase mItemsFirebaseDatabase;
    private DatabaseReference mItemsDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCustomerRefernce;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ModleCart cmObej;
    ImageView photoImageView;
    TextView main_txt,sub_main_txt,desc , matrialT,uniname , descr;
    Button fav,mainsitebtn;
    String itemName,itemPrice,uni,coursename,materialtype,Descr,ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
        // final Controller aController = (Controller) this.getApplicationContext();

        main_txt = findViewById(R.id.main_txt);
        sub_main_txt = findViewById(R.id.sub_main_txt);
        desc = findViewById(R.id.desc);
        matrialT = findViewById(R.id.matrialT);
        uniname = findViewById(R.id.uniname);
        descr = findViewById(R.id.descr);
        mainsitebtn = findViewById(R.id.mainsitebtn);
        fav = findViewById(R.id.addFav);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCustomerRefernce = mFirebaseDatabase.getReference();
        itemName = getIntent().getStringExtra("itemName");
        itemPrice = getIntent().getStringExtra("itemPrice");
        uni = getIntent().getStringExtra("uni");
        coursename = getIntent().getStringExtra("coursename");
        ID = getIntent().getStringExtra("ID");
        materialtype = getIntent().getStringExtra("type");
        Descr = getIntent().getStringExtra("desc");
        final String phone = getIntent().getStringExtra("phone");
        final String address = getIntent().getStringExtra("address");
        final String IBAN = getIntent().getStringExtra("IBAN");
        final String username = getIntent().getStringExtra("username");
        final String bnkName = getIntent().getStringExtra("bankname");
        final String usrID= getIntent().getStringExtra("userID");
        final String url = getIntent().getStringExtra("URL");
        photoImageView = (ImageView) findViewById(R.id.imageViewPost);
        Glide.with(photoImageView.getContext()).load(url).into(photoImageView);
        photoImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewPopUpHelper.enablePopUpOnClick(datailscart.this, photoImageView, photoImageView.getDrawable());
            }
        });
        if (getIntent().getStringExtra("Hide").equals("true")) {
            mainsitebtn.setVisibility(View.INVISIBLE);
            fav.setVisibility(View.INVISIBLE);
        }
        if (getIntent().getStringExtra("Hide").equals("true FAV")) {
            fav.setVisibility(View.INVISIBLE);
        }
        mCustomerRefernce.child("Fav").child(MainActivity.userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.e("Fav",postSnapshot.getKey());
                    if (postSnapshot.getKey().equals(ID)) {
                        Log.e("Fav", ID);
                        fav.setText("Item is already on favorite");
                        fav.setClickable(false);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mCustomerRefernce.child("Cart").child(MainActivity.userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.e("Cart",postSnapshot.getKey());
                    if (postSnapshot.getKey().equals(ID)) {
                        Log.e("Cart", ID);
                        mainsitebtn.setText("Item is already on cart");
                        mainsitebtn.setClickable(false);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mainsitebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post product=new Post(ID,itemName,coursename,uni,materialtype,itemPrice,Descr,phone,address,IBAN,username,bnkName,usrID,url);
                Log.e("Test Cart",url);
                mCustomerRefernce.child("Cart").child(MainActivity.userID).child(ID).setValue(product);
                Toast.makeText(datailscart.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post product=new Post(ID,itemName,coursename,uni,materialtype,itemPrice,Descr,phone,address,IBAN,username,bnkName,usrID,url);
                Log.e("Test Favorite",url);
                mCustomerRefernce.child("Fav").child(MainActivity.userID).child(ID).setValue(product);
                Toast.makeText(datailscart.this, "Added to Favorite", Toast.LENGTH_SHORT).show();
            }
        });
        FirebaseApp.initializeApp(this);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    descr.setText(itemPrice + " SR ");
                    sub_main_txt.setText(coursename);
                    main_txt.setText(itemName);
                    matrialT.setText("Material type: "+materialtype);
                    uniname.setText("University:"+uni);
                    desc.setText("Description: "+Descr);
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
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class UIutils {

        private Activity mActivity;

        public UIutils(Activity activity){
            mActivity = activity;
        }

        public void showPhoto(Uri photoUri){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(photoUri, "image/*");
            mActivity.startActivity(intent);
        }
    }
}
