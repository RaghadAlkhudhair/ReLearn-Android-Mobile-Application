package com.example.my1stapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.test.my1stapplication.MapsActivity;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText name,email,password,password2,phone;
    EditText IBAN, bank, address;
    Button mRegisterbtn;
    TextView mLoginPageBack;
    FirebaseAuth mAuth;
    DatabaseReference mdatabase;
    String Name,Email,Password,Phone ;
    ProgressDialog mDialog;
    Toolbar tb;


    private String Password2;


  User userOne;

    static String userID=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.phone);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        password2 = (EditText)findViewById(R.id.password2);
        mRegisterbtn = (Button)findViewById(R.id.signUp);
        mLoginPageBack = (TextView)findViewById(R.id.signIn);
        phone = (EditText) findViewById(R.id.phone);
        IBAN = (EditText) findViewById(R.id.IBAN);
        bank = (EditText) findViewById(R.id.bank);
        address = (EditText) findViewById(R.id.address);
        mAuth = FirebaseAuth.getInstance();
        mRegisterbtn.setOnClickListener(this);
        mLoginPageBack.setOnClickListener(this);
        mDialog = new ProgressDialog(this);
        mdatabase = FirebaseDatabase.getInstance().getReference("Users");
    }

    @Override
    public void onClick(View v) {
        if (v==mRegisterbtn){
            UserRegister();
        //    startActivity(new Intent(MainActivity.this, MapsActivity.class));
        }else if (v== mLoginPageBack){
            startActivity(new Intent(MainActivity.this,ActivityLogin.class));
            Log.e("TAG", "Message");
        }
    }

    private void UserRegister() {
        Name = name.getText().toString().trim();
        Email = email.getText().toString().trim();
        Password = password.getText().toString().trim();
        Password2 = password2.getText().toString().trim();
         Phone=phone.getText().toString().trim();
        if (TextUtils.isEmpty(Name)){
            Toast.makeText(MainActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(Email)){
            Toast.makeText(MainActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(Password)){
            Toast.makeText(MainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(Password2)){
            Toast.makeText(MainActivity.this, "Enter confirm Password", Toast.LENGTH_SHORT).show();
            return;
        }


        else if (Password.length()<6){
            Toast.makeText(MainActivity.this,"Password must be more then 6 digits",Toast.LENGTH_SHORT).show();
            return;
        }

        else  if (!(Password.equals(Password2))) {

            Toast.makeText(MainActivity.this,"Both password fields must be identical",Toast.LENGTH_SHORT).show();

        }
        else if (!(TextUtils.isEmpty(Phone))){
            if (Phone.length()<10){
                Toast.makeText(MainActivity.this,"phone number must not be less than 10 digits",Toast.LENGTH_SHORT).show();
            return;}
            else if  (Phone.length()>10){

             Toast.makeText(MainActivity.this,"phone number must not be more than 10 digits",Toast.LENGTH_SHORT).show();
                return;}
        }
        else if( !(TextUtils.isEmpty(IBAN.getText().toString())) && (IBAN.getText().toString().charAt(0)!='S'||IBAN.getText().toString().charAt(1)!='A' || IBAN.getText().toString().length()!=25)){
            Toast.makeText(MainActivity.this,"Check the IBAN", Toast.LENGTH_SHORT).show();
            IBAN.setError("IBAN should start with SA and digits equal 25");
            return;
        }



        mDialog.setMessage("Welcome to our community, we are currently creating your account..");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication failed,please check email and password are right." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        } else {
                            sendEmailVerification();
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            mDialog.dismiss();
                            finish();
                        }
                    }
                });

    }
    //Email verification code using FirebaseUser object and using isSucccessful()function.
    private void sendEmailVerification() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"Check your E-mail for verification",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        //Added by nada to catch the user ID and create a table that handles its ID
                        userID = user.getUid();
                        userOne = new User(name.getText().toString(),email.getText().toString(),IBAN.getText().toString(),phone.getText().toString(),user.getUid(),bank.getText().toString(),address.getText().toString());
                        Log.e("user create","DONE :)");
                        mdatabase.child(user.getUid()).setValue(userOne);

                        //End of lines added by Nada


                       // mdatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("longitude").setValue("7887.98");
                    }
                }
            });
        }
    }

    private void OnAuth(FirebaseUser user) {
        createAnewUser(user.getUid());
    }

    private void createAnewUser(String uid) {
        User user = BuildNewuser();
        mdatabase.child(uid).equals(user);
    }


    private User BuildNewuser(){
        return new User(
                getDisplayName(),
                getUserEmail(),
                getPassword(),
                new Date().getTime()
        );
    }

    public String getDisplayName() {
        return Name;
    }

    public String getUserEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }
}


