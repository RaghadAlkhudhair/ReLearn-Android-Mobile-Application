package com.example.my1stapplication;

import android.widget.ArrayAdapter;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.List;
import android.app.Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrdersList extends ArrayAdapter<Order> {


private Activity context;
private List<Order> ordersList;
CheckBox rePaid, shipped;
DatabaseReference ordersref;

public OrdersList(Activity context, List<Order> ordersList){
    super(context, R.layout.orders,ordersList);
    this.context=context;
    this.ordersList=ordersList;
    ordersref=FirebaseDatabase.getInstance().getReference("Orders");



}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.orders, null,true );
        TextView t1=(TextView) listViewItem.findViewById(R.id.textView1);
        TextView t2=(TextView) listViewItem.findViewById(R.id.textView2);
        TextView t3=(TextView) listViewItem.findViewById(R.id.textView3);
        TextView t4=(TextView) listViewItem.findViewById(R.id.textView4);
        TextView t5=(TextView) listViewItem.findViewById(R.id.textView5);
        TextView t6=(TextView) listViewItem.findViewById(R.id.textView6);
        TextView t7=(TextView) listViewItem.findViewById(R.id.textView7);
        final CheckBox rePaid=(CheckBox) listViewItem.findViewById(R.id.checkBox);
       final CheckBox shipped=(CheckBox) listViewItem.findViewById(R.id.shipped);


       final Order order=ordersList.get(position);
        if(order.getShipped().equals("false") || order.getRePaid().equals("false")) {


            t1.setText(order.getOrderID());
            t2.setText(order.getBuyerID());
            t3.setText(order.getDistrict());
            t4.setText(order.getStreetNumber());
            t5.setText(order.getHouseNo());
            t6.setText(order.getPhone());
            t7.setText(order.getTotalPrice());
        }
        else{
            ordersref.child(order.getOrderID()).removeValue();
        }

        rePaid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rePaid.isChecked())
                    ordersref.child(order.getOrderID()).child("rePaid").setValue("True");

            }
        });



        shipped.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (shipped.isChecked())
                    ordersref.child(order.getOrderID()).child("shipped").setValue("True");

            }
        });




return listViewItem;
    }
}
