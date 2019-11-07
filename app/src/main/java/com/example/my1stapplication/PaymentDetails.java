package com.example.my1stapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONObject;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
public class PaymentDetails extends AppCompatActivity {
    DatabaseReference mDatabaseReference ;

    TextView txtId,txtAmount,txtStatus;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);


        txtId = (TextView)findViewById(R.id.txtId);
        home=(Button)findViewById(R.id.home);


        Intent intent=getIntent();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToHome();
            }
        });

        try{

            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));



        }
        catch (JSONException e){
            e.printStackTrace();


        }
    }

    public void moveToHome(){

        Intent intent = new Intent(PaymentDetails.this, BooksList.class);
        startActivity(intent);
    }

    private void showDetails(JSONObject response , String paymentAmount) throws JSONException {

        try {
            txtId.setText(response.getString("id"));

        }

        catch(JSONException e){
            e.printStackTrace();
        }
    }

}
