package com.example.my1stapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Array;
import java.util.List;
import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.my1stapplication.Post;
import com.example.my1stapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class favList extends ArrayAdapter<Post> {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Activity context;
    public List<Post> postslist;
    private List<Post> total;

    DatabaseReference mDatabaseReference,myref ;


    public favList(Activity context, List<Post> postslist){
        super(context, R.layout.favlist, postslist );
        this.context=context;
        this.postslist=postslist;
        this.total=new ArrayList<Post>();

        //this.total.addAll(this.postslist);
    }


    public List<Post> getPostslist() {
        return postslist;
    }

    @NonNull
    @Override
    public Activity getContext() {
        return context;
    }




    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listviewitem= inflater.inflate(R.layout.favlist,null, true);
        TextView materialname1 = (TextView) listviewitem.findViewById(R.id.materialname);
        TextView coursename1 = (TextView) listviewitem.findViewById(R.id.coursename);
        TextView uniname1 = (TextView) listviewitem.findViewById(R.id.uniname);
        TextView price1 = (TextView) listviewitem.findViewById(R.id.price);
        ImageButton aCa = (ImageButton) listviewitem.findViewById(R.id.addCart);
        ImageButton aDa = (ImageButton) listviewitem.findViewById(R.id.removeFav);
        ImageView photoImageView  = (ImageView) listviewitem.findViewById(R.id.imageViewPost);
        final Post post = postslist.get(position);

        Glide.with(photoImageView.getContext()).load(post.getUrl()).into(photoImageView);
        myref = FirebaseDatabase.getInstance().getReference("Fav").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
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
                intent.putExtra("IBAN",post.getIBAN());
                intent.putExtra("phone",post.getPhone());
                intent.putExtra("address",post.getAddress());
                intent.putExtra("username",post.getUsername());
                intent.putExtra("bankname",post.getBankname());
                intent.putExtra("userID",post.getUserID());
                intent.putExtra("URL",post.getUrl());
                Log.e("test fav img", post.getUrl());
                intent.putExtra("Hide","true FAV");
                Log.e("test","inside detail");
                getContext().startActivity(intent);
            }
        });
        aCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseReference.child(post.getPostID()).setValue(post);
                myref.child(post.getPostID()).removeValue();
                remove(post);
                Toast.makeText(getContext(), "Material/s added to cart successfully", Toast.LENGTH_SHORT).show();
            }
        });

        aDa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myref.child(post.getPostID()).removeValue();
                remove(post);
                Toast.makeText(getContext(), "Material/s removed successfully", Toast.LENGTH_SHORT).show();
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
        //List<Post> all =new ArrayList<Post>();
        // all.addAll(postslist);

        postslist.clear();
        //postslist.addAll(all);


//Post p=new Post("122","s","s","s","s","s","s");
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










