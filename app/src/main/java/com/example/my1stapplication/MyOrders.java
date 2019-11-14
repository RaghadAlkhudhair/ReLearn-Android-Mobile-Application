package com.example.my1stapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyOrders extends AppCompatActivity {
    ListView listViewMyOrders;
    DatabaseReference ordersref;
    List<Order> myordersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);




        ordersref= FirebaseDatabase.getInstance().getReference("Orders");
        listViewMyOrders=(ListView)findViewById(R.id.listViewMyOrders);
        myordersList=new ArrayList<>();


    }



    public void onStart(){
        super.onStart();


        ordersref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myordersList.clear();
                for(DataSnapshot ordersnapshot:dataSnapshot.getChildren()){

                    Order o=ordersnapshot.getValue(Order.class);
                    if(o.getBuyerID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    myordersList.add(o);

                }
                MyOrdersList adapter= new MyOrdersList(MyOrders.this,myordersList);
                listViewMyOrders.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
