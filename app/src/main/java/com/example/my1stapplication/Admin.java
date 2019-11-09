package com.example.my1stapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my1stapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
    Button btnLogOut;
ListView listViewOrders;
DatabaseReference ordersref;
List<Order> ordersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_page);

ordersref= FirebaseDatabase.getInstance().getReference("Orders");
        listViewOrders=(ListView)findViewById(R.id.listViewOrders);
ordersList=new ArrayList<>();
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

public void onStart(){
        super.onStart();


ordersref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

ordersList.clear();
        for(DataSnapshot ordersnapshot:dataSnapshot.getChildren()){

Order o=ordersnapshot.getValue(Order.class);
ordersList.add(o);

        }
        OrdersList adapter= new OrdersList(Admin.this,ordersList);
        listViewOrders.setAdapter(adapter);

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

}
}
