package com.example.my1stapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class myposts extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference db;
    List<Post> myarray = new ArrayList<>();
    ListView lv_pos;
    static mypostslist adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypostslist);
        Log.e("test finish","hello");
        db = FirebaseDatabase.getInstance().getReference("posts");
        adapter= new mypostslist(myposts.this,myarray );
        lv_pos = (ListView)findViewById(R.id.lv_pos);
        lv_pos.setAdapter(adapter);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myarray.clear();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    final Post post = postSnapshot.getValue(Post.class);
                    Log.e("test user ID",post.getPostID());
                    Log.e("test user name",post.getUsername());
                    Log.e("test user phone",post.getPhone());
                    Log.e("test user IBAN",post.getIBAN());
                    Log.e("test user address",post.getAddress());
                    if(post.getUrl()==null){

                    }else {
                        Log.e("test post photo url", post.getUrl());
                    }
                    if(post.getUserID()==null){

                    }else {
                        if (post.getUserID().equals(MainActivity.userID)) {
                            myarray.add(post);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
