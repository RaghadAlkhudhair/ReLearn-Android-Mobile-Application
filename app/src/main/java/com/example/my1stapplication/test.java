package com.example.my1stapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class test extends AppCompatActivity {
    EditText materialname, coursename, description, IBAN, owner,bankname, price;
    Button buttonDone;
    Button Cancel;
    Spinner spinner;
    Spinner uniname;
    DatabaseReference db;
    FirebaseStorage mFirebaseStorage;
    StorageReference mPhotosStorageReference;
    private static final int RC_PHOTO_PICKER =  2;
    ImageView home;
    ImageButton upload;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Post.photoIsUploaded=false;
        db = FirebaseDatabase.getInstance().getReference("posts");
     //  IBAN = (EditText) findViewById(R.id.IBAN);
        materialname = (EditText) findViewById(R.id.materialname);
        coursename = (EditText) findViewById(R.id.coursename);
        uniname = (Spinner) findViewById(R.id.uniname);
       // owner = (EditText) findViewById(R.id.owner);
        //bankname = (EditText) findViewById(R.id.bankname);
        price = (EditText) findViewById(R.id.price);
        description = (EditText) findViewById(R.id.description);
        home = (ImageView) findViewById(R.id.home);
        spinner = (Spinner) findViewById(R.id.spinner3);
        buttonDone = (Button) findViewById(R.id.button6);
        Cancel = (Button) findViewById(R.id.cancel_);
//logout=(ImageButton) findViewById(R.id.logout);
        home.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        moveToHome();
    }
});
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToHome();
            }
        });
        buttonDone.setOnClickListener(new View.OnClickListener(){

    @Override
    public void onClick(View v) {
        addPost();

    }
} );
        mFirebaseStorage = FirebaseStorage.getInstance();
        mPhotosStorageReference = mFirebaseStorage.getReference().child("Posts");
        upload=(ImageButton) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }

        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            StorageReference photoRef = mPhotosStorageReference.child(selectedImageUri.getLastPathSegment());
            final ProgressDialog pr = ProgressDialog.show(test.this, "Please wait", "processing", true);
            photoRef.putFile(selectedImageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png' in uri
                            Post.wphotoUrl=uri.toString();
                            Log.e("photo link is",Post.wphotoUrl);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });

                    pr.dismiss();
                    Post.photoIsUploaded=true;
                    upload.setImageDrawable(getResources().getDrawable(R.drawable.done));
                    upload.setClickable(false);
                    Toast.makeText(test.this,"Image is uploaded succefully",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void addPost(){

        String materialname1= materialname.getText().toString().trim();
        String coursename1= coursename.getText().toString().trim();
        String uniname1= uniname.getSelectedItem().toString().trim();
        String description1= description.getText().toString().trim();
        String price1= price.getText().toString().trim();
        String photoURL = Post.wphotoUrl;
        String spinner1= spinner.getSelectedItem().toString().trim();
        String id = MainActivity.userID;
    if(!TextUtils.isEmpty(materialname1) &&
            !TextUtils.isEmpty(coursename1) && !TextUtils.isEmpty(uniname1) &&
            !TextUtils.isEmpty(price1) && !TextUtils.isEmpty(spinner1) && !TextUtils.isEmpty(description1) && Post.photoIsUploaded ) {
       // String id=   db.push().getKey();
        //Post post = new Post(id,materialname1,coursename1,uniname1,spinner1,price1 ,description1);
        //db.child(id).setValue(post);
        //Toast.makeText(this,"Done", Toast.LENGTH_LONG).show();

        Intent n= new Intent (test.this, ConfrimAddMaterial.class);
        n.putExtra("materialname",materialname1 );
        n.putExtra("coursename",coursename1 );
        n.putExtra("uniname",uniname1 );
        n.putExtra("materialtype",spinner1 );
        n.putExtra("price",price1 );
        n.putExtra("description",description1 );
        n.putExtra("userID",id );
        n.putExtra("URL",photoURL);
        startActivity(n);
    }else{
        Toast.makeText(this,"please make sure all information are entered",Toast.LENGTH_LONG).show();    }
}






    public void moveToHome(){

        Intent intent = new Intent(test.this, BooksList.class);
        startActivity(intent);
    }


}
