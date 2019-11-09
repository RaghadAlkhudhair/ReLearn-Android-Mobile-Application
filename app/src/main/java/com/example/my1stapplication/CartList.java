package com.example.my1stapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartList extends Activity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = database.getReference();
    private ArrayList<ModelProducts> cartcollect;
    private float totalcost=0;
    private int totalproducts=0;
    private HashMap<String,String> user;
    private String email;
    private ListView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    DatabaseReference db;
    List<Post> postslist;
    ListView listViewPosts;
    Double total=0.0;
    TextView TotalView;
    EditText address;
    DatabaseReference ordersref=FirebaseDatabase.getInstance().getReference("Orders");
    final HashMap<String, Object> ordersMap=new HashMap<>();
    final HashMap<String, Object> cartMap=new HashMap<>();
    Button confirmButton;
    Button Back;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);
        email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        TextView showCartContent = (TextView) findViewById(R.id.showCart);
        //final Button thirdBtn = (Button) findViewById(R.id.cartBtn);
        listViewPosts = (ListView) findViewById(R.id.listCartPosts);
        TotalView=(TextView)findViewById(R.id.total);
        confirmButton=(Button)findViewById(R.id.confrimButton);
        Back=(Button)findViewById(R.id.Back);

        //final String uni = getIntent().getStringExtra("uni");
        //final String coursename = getIntent().getStringExtra("coursename");
        postslist= new ArrayList<>();
        final cartAdapter adapter = new cartAdapter(CartList.this,postslist);
        listViewPosts.setAdapter(adapter);

        // Get Cart Size
      //  final int cartSize = Controller.getCart().getCartSize();

        String showString = "";
        final String itemName = getIntent().getStringExtra("itemName");
        final String itemPrice = getIntent().getStringExtra("itemPrice");
        final String uni = getIntent().getStringExtra("uni");
        final String coursename = getIntent().getStringExtra("coursename");
/******** Show Cart Products on screen - Start ********/
        db= FirebaseDatabase.getInstance().getReference("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Log.e("test for cart","Going to the listener");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("test for cart","I'm here I hope I can find a value");
                int counter=0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.e("test for cart",postSnapshot.getKey());
                    Post post = postSnapshot.getValue(Post.class);
                    Log.e("test for cart", post.getPostID());
                    counter++;
                    adapter.add(post);
                    adapter.notifyDataSetChanged();
                    cartMap.put("post".concat(String.valueOf(counter)), post);
                    Double thisPrice=Double.parseDouble(post.getPrice());
                    total=total+thisPrice;
                }
                TotalView.setText("Sum Price: "+total+ " SR" +"\n" + "Shipment: 20 SR\n\n"+"Total: "+(total+20)+" SR");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listViewPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(CartList.this, datailscart.class);
                intent.putExtra("itemName", postslist.get(position).getMaterialname());
                intent.putExtra("itemPrice", postslist.get(position).getPrice());
                intent.putExtra("uni", postslist.get(position).getUniname());
                intent.putExtra("coursename", postslist.get(position).getCoursename());
                intent.putExtra("Hide","true");
                startActivity(intent);
            }
        });

/******** Show Cart Products on screen - End ********/





        confirmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(adapter.isEmpty()) {
                    ordersMap.clear();
                    TotalView.setText("Your cart is empty");
                }
                else {
                    Intent intent2 = new Intent(CartList.this, ConfirmOrder.class);
                    intent2.putExtra("totalPrice", (total+20));
                    intent2.putExtra("buyerID",FirebaseAuth.getInstance().getCurrentUser().getUid());
                    intent2.putExtra("cart",cartMap);
                    startActivity(intent2);

                }}
        });

        Back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToHome();
            }
        });



    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    @Override
    protected void onStart() {
                    super.onStart();
    }



//
//    private void getValues() {
//
//        //create new session object by passing application context
//        session = new UserSession(getApplicationContext());
//
//        //validating session
//        session.isLoggedIn();
//
//        //get User details if logged in
//        user = session.getUserDetails();
//
//        email = user.get(UserSession.KEY_EMAIL);
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

    /*public void viewProfile(View view) {
        startActivity(new Intent(CartList.this, ContactsContract.Profile.class));
        finish();
    }*/

    @Override
    protected void onResume() {
        super.onResume();

    }


    public void moveToHome(){

        Intent intent = new Intent(CartList.this, BooksList.class);
        startActivity(intent);
    }

   /* public void Notifications(View view) {

        startActivity(new Intent(Cart.this,NotificationActivity.class));
        finish();
    }*/
}
