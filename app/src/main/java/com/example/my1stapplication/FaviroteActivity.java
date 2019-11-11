package com.example.my1stapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.my1stapplication.favList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FaviroteActivity extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase mydatabase;
    DatabaseReference myRef,myRef2;
    List<Post> myarray = new ArrayList<>();
    ListView lv_fav;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favirote);

        mydatabase = FirebaseDatabase.getInstance();

        myRef = mydatabase.getReference("Fav").child(MainActivity.userID);
        myRef2 = mydatabase.getReference("Fav").child(MainActivity.userID);
        final favList adapter= new favList(FaviroteActivity.this,myarray );
        lv_fav = (ListView)findViewById(R.id.lv_fav);
        b=(Button)  findViewById(R.id.cancel_);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToHome();
            }
        });
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myarray.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Post post = postSnapshot.getValue(Post.class);
                    myarray.add(post);
                    lv_fav.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








    }



    public void moveToHome(){

        Intent intent = new Intent(FaviroteActivity.this, BooksList.class);
        startActivity(intent);
    }
}
