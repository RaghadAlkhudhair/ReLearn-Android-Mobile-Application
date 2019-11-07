package com.example.my1stapplication;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import static com.example.my1stapplication.MainActivity.userID;

public class accountInfo extends AppCompatActivity{
        DatabaseReference db;
        TextView up;
        View ex;
        User userRef;

        FirebaseUser mAuth;
        ArrayList<User> uac = new ArrayList<User>();
         TextView email, name, IBAN,phone,bank,Welcome,emailID,owner,address;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.profile_);
            name = (TextView) findViewById(R.id.name);
            IBAN = (TextView) findViewById(R.id.IBAN);
            phone = (TextView) findViewById(R.id.phone);
            bank = (TextView) findViewById(R.id.bank);
            Welcome = (TextView) findViewById(R.id.Welcome);
            emailID = (TextView) findViewById(R.id.emailID);
            ex = (View) findViewById(R.id.Exit);
            owner = (TextView) findViewById(R.id.accc);
            address = (TextView) findViewById(R.id.address);
            db = FirebaseDatabase.getInstance().getReference("Users").child(userID);
            up= (TextView) findViewById(R.id.Update);

            db.addListenerForSingleValueEvent(new ValueEventListener() {
                // @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Log.e("ID3", (dataSnapshot.getKey()));
                            Log.e("ID03", userID);
                            if (dataSnapshot.getKey().equalsIgnoreCase(userID)) {
                                User one = dataSnapshot.getValue(User.class);
                                name.setText(one.getName());
                                bank.setText(one.getBank());
                                Welcome.setText(one.getName());
                                emailID.setText(one.getEmail());
                                IBAN.setText(one.getIban());
                                phone.setText(one.getPhone());
                                owner.setText(one.getOwner());
                                address.setText(one.getAddr());

                                break;
                            }
                            Log.e("ID5", dataSnapshot.getKey());
                        }
                    }catch (Exception E){   E.printStackTrace();}
                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exit();
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
            }
        });
        }

        public void Exit(){
            finish();
        }

        public void Update(){
            userRef = new User();
            // Get auth credentials from the user for re-authentication
            // Prompt the user to re-provide their sign-in credentials

                     if(IBAN.getText().toString().charAt(0)!='S'||IBAN.getText().toString().charAt(1)!='A'){
                        Toast.makeText(accountInfo.this,"Check the IBAN", Toast.LENGTH_SHORT).show();
                        IBAN.setError("IBAN should start with SA");
                    } else
                        if(phone.getText().toString().charAt(0)!='0'||phone.getText().toString().charAt(1)!='5') {
                         Toast.makeText(accountInfo.this, "Check the phone", Toast.LENGTH_SHORT).show();
                         phone.setError("Phone number format is wrong");
                     } else
                         if(IBAN.getText().toString().length()==25 && phone.length()==10) {
                             userRef.setBank(bank.getText().toString());
                             userRef.setIban(IBAN.getText().toString());
                             userRef.setPhone(phone.getText().toString());
                             userRef.setName(name.getText().toString());
                             userRef.setAddr(address.getText().toString());
                             userRef.setOwner(owner.getText().toString());
                             userRef.setEmail(emailID.getText().toString());
                             db.setValue(userRef);
                             Toast.makeText(accountInfo.this, "Your account's information has been updated", Toast.LENGTH_SHORT).show();
            } else {
                             if (!(IBAN.getText().toString().length() == 25)) {
                                 Toast.makeText(accountInfo.this, "Check the IBAN", Toast.LENGTH_SHORT).show();
                                 IBAN.setError("IBAN should be 25 characters and starting with SA");
                             } else if (phone.length() != 10) {
                                 Toast.makeText(accountInfo.this, "Check the phone", Toast.LENGTH_SHORT).show();
                                 phone.setError("Phone should be 10 characters");
                             }
                         }

        }
        }


