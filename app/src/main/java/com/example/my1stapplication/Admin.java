package com.example.my1stapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my1stapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import androidx.appcompat.app.AppCompatActivity;import androidx.appcompat.app.AppCompatActivity;

public class Admin extends AppCompatActivity {
    Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_page);
        btnLogOut = (Button) findViewById(R.id.logout);
    btnLogOut.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        FirebaseAuth.getInstance().signOut();
        Intent I = new Intent(Admin.this, ActivityLogin.class);
        startActivity(I);
        Log.e("TAG", "Message");

    }
    });
}
}
