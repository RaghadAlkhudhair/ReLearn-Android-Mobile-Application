package com.example.my1stapplication;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        ImageButton delete = (ImageButton) listviewitem.findViewById(R.id.delete);
        TextView materialname1 = (TextView) listviewitem.findViewById(R.id.materialname1);
        TextView coursename1 = (TextView) listviewitem.findViewById(R.id.coursename1);
        TextView uniname1 = (TextView) listviewitem.findViewById(R.id.uniname1);
        TextView price1 = (TextView) listviewitem.findViewById(R.id.price1);

        final Post post = postslist.get(position);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        materialname1.setText(post.getCoursename());
        coursename1.setText(post.getCoursename());
        uniname1.setText(post.getUniname());
        price1.setText(post.getPrice());

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










