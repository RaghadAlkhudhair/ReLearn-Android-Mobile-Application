package com.example.my1stapplication;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class cartAdapter extends ArrayAdapter<Post> {
    DatabaseReference mDatabaseReference ;
    public List<Post> postslist;
    private List<Post> total;
    private LayoutInflater inflater;
    private Context context;


    public cartAdapter(Activity context, List<Post> postslist){
        super(context, R.layout.cart_list, postslist );
        this.context=context;
        this.postslist=postslist;
        this.total=new ArrayList<Post>();
        this.inflater = LayoutInflater.from(this.context);
        //this.total.addAll(this.postslist);
    }


    public List<Post> getPostslist() {
        return postslist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View listviewitem, @NonNull ViewGroup parent) {
        if(listviewitem==null) {
            listviewitem = inflater.inflate(R.layout.cart_list, parent, false);
        }
        ImageButton delete = (ImageButton) listviewitem.findViewById(R.id.deleteCart);
        TextView materialname1 = (TextView) listviewitem.findViewById(R.id.materialname);
        TextView coursename1 = (TextView) listviewitem.findViewById(R.id.coursename);
        TextView uniname1 = (TextView) listviewitem.findViewById(R.id.uniname);
        TextView price1 = (TextView) listviewitem.findViewById(R.id.price);

        final Post post = postslist.get(position);
        ImageView photoImageView = (ImageView) listviewitem.findViewById(R.id.imageViewPost);
        if(post.getUrl() == null){

        }else{
            Log.e("test cart img", post.getUrl());
        }
        Glide.with(photoImageView.getContext()).load(post.getUrl()).into(photoImageView);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        materialname1.setText(post.getMaterialname());
        coursename1.setText(post.getCoursename());
        uniname1.setText(post.getUniname());
        price1.setText(post.getPrice());
        listviewitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), datailscart.class);
                intent.putExtra("itemName",post.getMaterialname());
                intent.putExtra("itemPrice",post.getPrice());
                intent.putExtra("uni",post.getUniname());
                intent.putExtra("ID",post.getPostID());
                intent.putExtra("desc",post.getDescription());
                intent.putExtra("type",post.getMaterialtype());
                intent.putExtra("coursename",post.getCoursename());
                intent.putExtra("phone",post.getPhone());
                intent.putExtra("address",post.getAddress());
                intent.putExtra("IBAN",post.getIBAN());
                intent.putExtra("username",post.getUsername());
                intent.putExtra("bankname",post.getBankname());
                intent.putExtra("userID",post.getUserID());
                intent.putExtra("URL",post.getUrl());
                intent.putExtra("Hide","true");
                Log.e("test","inside detail");
                getContext().startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseReference.child(post.getPostID()).removeValue();
                remove(post);
                Toast.makeText(getContext(), "Books has been removed", Toast.LENGTH_SHORT).show();
            }
        });

        if(total.isEmpty())
            total.addAll(postslist);

        return listviewitem;
    }


    public void savePosts(){
        if(total.isEmpty())
            total.addAll(postslist);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase();
        // total.addAll(postslist);
        //List<post> all =new ArrayList<post>();
        // all.addAll(postslist);

        postslist.clear();
        //postslist.addAll(all);


//post p=new post("122","s","s","s","s","s","s");
        if (charText.length() == 0) {
            postslist.addAll(total);
        } else {

            for (Post p : total) {
                if (p.getMaterialname().toLowerCase().contains(charText) || p.getCoursename().toLowerCase().contains(charText) || p.getUniname().toLowerCase().contains(charText))
                    postslist.add(p);
            }

            notifyDataSetChanged();

        }

    }}










