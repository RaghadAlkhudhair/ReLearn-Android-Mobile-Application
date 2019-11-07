package com.example.my1stapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import android.app.Activity;
import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class postsList extends ArrayAdapter<Post> {
    FirebaseDatabase mydatabase;
    DatabaseReference myRef;

    private Activity context;
   public List<Post> postslist;
    private List<Post> total;




    public postsList(Activity context, List<Post> postslist){
        super(context, R.layout.bookslistlayout, postslist );
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listviewitem= inflater.inflate(R.layout.bookslistlayout,null, true);
        TextView materialname1 = (TextView) listviewitem.findViewById(R.id.materialname);
        TextView coursename1 = (TextView) listviewitem.findViewById(R.id.coursename);
        TextView uniname1 = (TextView) listviewitem.findViewById(R.id.uniname);
        TextView price1 = (TextView) listviewitem.findViewById(R.id.price);
        final ImageButton ib_fav = (ImageButton)listviewitem.findViewById(R.id.ib_fav);
        final Post post = postslist.get(position);

        materialname1.setText(post.getMaterialname());
        coursename1.setText(post.getCoursename());
        uniname1.setText(post.getUniname());
        price1.setText(post.getPrice());
        ib_fav.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View v) {
                ib_fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                mydatabase = FirebaseDatabase.getInstance();
                myRef =mydatabase.getReference();
                myRef.child("Fav").child(MainActivity.userID).child(post.getPostID()).setValue(post);
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










