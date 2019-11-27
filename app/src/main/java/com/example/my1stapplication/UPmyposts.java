package com.example.my1stapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UPmyposts extends AppCompatActivity {
    EditText materialname, coursename, description, IBAN, owner,bankname, price;
    Button buttonDone;
    Button Cancel;
    Spinner spinner;
    Spinner uniname;
    DatabaseReference db;
    ImageView home;
    ImageButton logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_my_posts);
        db = FirebaseDatabase.getInstance().getReference("posts");
        materialname = (EditText) findViewById(R.id.materialname);
        coursename = (EditText) findViewById(R.id.coursename);
        uniname = (Spinner) findViewById(R.id.uniname);
        price = (EditText) findViewById(R.id.price);
        description = (EditText) findViewById(R.id.description);
        home = (ImageView) findViewById(R.id.home);
        spinner = (Spinner) findViewById(R.id.spinner3);
        buttonDone = (Button) findViewById(R.id.button6);
        Cancel = (Button) findViewById(R.id.cancel_);
        final AlertDialog alertDialog = new AlertDialog.Builder(UPmyposts.this).create();
        alertDialog.setTitle("Caution");


        final String materialname1= getIntent().getStringExtra("materialname");
        final String coursename1= getIntent().getStringExtra("coursename");
        final String uniname1= getIntent().getStringExtra("uniname");
        final String materialtype1= getIntent().getStringExtra("materialtype");
        final String description1= getIntent().getStringExtra("description");
        final String price1= getIntent().getStringExtra("price");
        final String id = getIntent().getStringExtra("postID");
        final String bankName1 = getIntent().getStringExtra("bankname");
        final String phone = getIntent().getStringExtra("phone");
        final String address = getIntent().getStringExtra("address");
        final String IBAN = getIntent().getStringExtra("IBAN");
        final String username = getIntent().getStringExtra("username");
        final String URL = getIntent().getStringExtra("URL");
        materialname.setText(materialname1);
        coursename.setText(coursename1);
        for(int i= 0; i < uniname.getAdapter().getCount(); i++) {
            if(uniname.getAdapter().getItem(i).toString().equals(uniname1)) { uniname.setSelection(i); }
        }
        price.setText(price1);
        description.setText(description1);
        for(int i= 0; i < spinner.getAdapter().getCount(); i++) {
            if(spinner.getAdapter().getItem(i).toString().equals(materialtype1)) { spinner.setSelection(i); }
        }

        db = FirebaseDatabase.getInstance().getReference("posts");
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setMessage("Update is under processing");
                alertDialog.show();
                Post product=new Post(id,materialname.getText().toString(),coursename.getText().toString(),uniname.getSelectedItem().toString(),spinner.getSelectedItem().toString(),price.getText().toString(),description.getText().toString(),phone,address,IBAN,username,bankName1,MainActivity.userID);
                db.child(id).setValue(product);
                alertDialog.dismiss();
                finish();
            }

        });


    }

}
