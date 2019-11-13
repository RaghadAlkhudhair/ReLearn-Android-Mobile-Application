package com.example.my1stapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import android.widget.SearchView;
import android.widget.Button;



import android.text.TextUtils;




import com.google.firebase.storage.StorageReference;
import com.test.my1stapplication.MapsActivity;

import java.util.ArrayList;
import java.util.List;





public class BooksList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
        //implements NavigationView.OnNavigationItemSelectedListener {


    List<Post> postslist;
    DatabaseReference db;
    ListView listViewPosts;
    ImageView home;
    ImageButton logout;
    SearchView searchview;
    Button showB;
    accountInfo a = new accountInfo();
    private FirebaseAuth.AuthStateListener authStateListener;
    StorageReference courseReference, materialTypeReference;
    private Toolbar mActionBarToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.toolbar_logo);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        navigationView.setNavigationItemSelectedListener(this);
        db= FirebaseDatabase.getInstance().getReference("posts");

        listViewPosts = (ListView) findViewById(R.id.listViewPosts);
        //home = (ImageView) findViewById(R.id.home);
      //  logout=(ImageButton) findViewById(R.id.logout);
        searchview=(SearchView) findViewById(R.id.SearchBook);
        postslist= new ArrayList<>();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);


       bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(BooksList.this, "Home", Toast.LENGTH_SHORT).show();
                    //    bottomNavigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));

                        Intent Intenthome = new Intent(BooksList.this, BooksList.class);
                        startActivity(Intenthome);
                        break;
                    case R.id.cart:
                        Toast.makeText(BooksList.this, "Cart", Toast.LENGTH_SHORT).show();
                        Intent Intentcart = new Intent(BooksList.this, CartList.class);
                        startActivity(Intentcart);
                        break;
                    case R.id.map:
                        Toast.makeText(BooksList.this, "Map", Toast.LENGTH_SHORT).show();
                        Intent IntentMap = new Intent(BooksList.this, MapsActivity.class);
                        startActivity(IntentMap);
                        break;
                    case R.id.post:
                        Toast.makeText(BooksList.this, "post", Toast.LENGTH_SHORT).show();
                        Intent Intentpost = new Intent(BooksList.this, test.class);
                        startActivity(Intentpost);

                        break;
                    case R.id.fav:
                        Toast.makeText(BooksList.this, "favorite", Toast.LENGTH_SHORT).show();
                        Intent Intentfav = new Intent(BooksList.this, FaviroteActivity.class);
                        startActivity(Intentfav);
                        break;
                }
                return true;
            }
        });





       // logout.setOnClickListener(new View.OnClickListener() {
           // @Override
         //   public void onClick(View v) {
           //     FirebaseAuth.getInstance().signOut();
              //  Intent I = new Intent(BooksList.this, ActivityLogin.class);
              //  startActivity(I);
               // Log.e("TAG", "Message");
           // }
       // });


       // home.setOnClickListener(new View.OnClickListener() {
         //   @Override
           // public void onClick(View v) {
             //   moveToHome();
            //}
        //});
    }

    //public void moveToHome(){

       // Intent intent = new Intent(BooksList.this, UserActivity.class);
       // startActivity(intent);
  //  }
       @SuppressWarnings("StatementWithEmptyBody")
       @Override
       public boolean onNavigationItemSelected(MenuItem item) {
           int id = item.getItemId();
           if (id == R.id.click_out) {
               FirebaseAuth.getInstance().signOut();
               Intent inte = new Intent(this,ActivityLogin.class);
               startActivity(inte);
           }
           if (id == R.id.click_profile) {
               Intent inte = new Intent(this,accountInfo.class);
               startActivity(inte);
           }
           if (id == R.id.click_myPosts) {
               Intent inte = new Intent(this,myposts.class);
               startActivity(inte);
           }
           return true;
       }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.click_out) {
//            FirebaseAuth.getInstance().signOut();
//            Intent inte = new Intent(this,activitylogin.class);
//            startActivity(inte);
//        }
//        else if (id == R.id.click_profile) {
//            Intent inte = new Intent(this,accountinfo.class);
//            startActivity(inte);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    protected void onStart() {
        super.onStart();
      final  postsList adapter= new postsList(BooksList.this,postslist );
        listViewPosts.setAdapter(adapter);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postslist.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Post post = postSnapshot.getValue(Post.class);
                    postslist.add(post);
                }

                               // postsList adapter= new postsList(BooksList.this, postslist );
          //listViewPosts.setAdapter(adapter);



            }// raghad


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String newText) {
                if(TextUtils.isEmpty(newText)){
                    adapter.filter("");
                    adapter.notifyDataSetChanged();


                }
                else{
                    adapter.filter(newText);
                    listViewPosts.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(TextUtils.isEmpty(newText)){
                    adapter.filter("");
                    //listViewPosts.clearTextFilter();
                    adapter.notifyDataSetChanged();
                }
                else{
                    adapter.filter(newText);
                    //listViewPosts.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                return false;
            }

        });


        /*BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(BooksList.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent Intenthome = new Intent(BooksList.this, BooksList.class);
                        startActivity(Intenthome);
                        break;
                    case R.id.cart:
                        Toast.makeText(BooksList.this, "Cart", Toast.LENGTH_SHORT).show();
                        Intent Intentcart = new Intent(BooksList.this, CartList.class);
                        startActivity(Intentcart);

                        break;

                    case R.id.post:
                        Toast.makeText(BooksList.this, "post", Toast.LENGTH_SHORT).show();
                        Intent Intentpost = new Intent(BooksList.this, test.class);
                        startActivity(Intentpost);

                        break;
                    case R.id.profile:
                        Toast.makeText(BooksList.this, "Profile", Toast.LENGTH_SHORT).show();
                        Intent Intentprofile = new Intent(BooksList.this, profile.class);
                        startActivity(Intentprofile);
                        break;
                }
                return true;
            }
        });*/
        }
}
