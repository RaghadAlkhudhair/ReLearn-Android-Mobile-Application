package com.example.my1stapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import com.example.my1stapplication.config.Config;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import android.text.TextUtils;
import android.widget.Toast;

import org.json.JSONException;

import java.io.Serializable;

import java.util.HashMap;
import java.math.BigDecimal;

public class ConfirmOrder extends AppCompatActivity {

    EditText district, streetName, houseNo, phoneNo;
    Button checkout, cancel;

    DatabaseReference ordersref= FirebaseDatabase.getInstance().getReference("Orders");
    DatabaseReference postsref= FirebaseDatabase.getInstance().getReference("posts");
    DatabaseReference cartref= FirebaseDatabase.getInstance().getReference("Cart");
    final HashMap<String, Object> ordersMap=new HashMap<>();
    public static final int PAYPAL_REQUEST_CODE=7171;

    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK).clientId(Config.PAYPAL_CLIENT_ID);
    //Double totalPrice=getIntent().getDoubleExtra("totalPrice",0);


    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        Double totalPrice=getIntent().getDoubleExtra("totalPrice",0);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        district = (EditText) findViewById(R.id.district);
        streetName = (EditText) findViewById(R.id.streetName);
        houseNo = (EditText) findViewById(R.id.houseNo);
        phoneNo = (EditText) findViewById(R.id.phoneNo);
        checkout = (Button) findViewById(R.id.checkout);
        String buyerID = getIntent().getStringExtra("buyerID");
        // Double totalPrice=getIntent().getDoubleExtra("totalPrice",0);
        //HashMap cart=(HashMap) getIntent().getSerializableExtra("cart");

cancel=(Button)findViewById(R.id.cancel);


cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        moveToHome();
    }
});

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  String district1 = district.getText().toString();
                String streetName1 = streetName.getText().toString();
                String houseNo1 = houseNo.getText().toString();
                String phoneNo1 = phoneNo.getText().toString();
*/
                if (!TextUtils.isEmpty(district.getText().toString()) && !TextUtils.isEmpty(streetName.getText().toString())
                        && !TextUtils.isEmpty(houseNo.getText().toString()) && !TextUtils.isEmpty(phoneNo.getText().toString())) {


                    processPayment();


                } else {
                    Toast.makeText(getApplicationContext(), "please enter all fields", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    private void processPayment(){

        Double totalPrice=getIntent().getDoubleExtra("totalPrice",0);
        PayPalPayment payPalPayment =new PayPalPayment(new BigDecimal(totalPrice/3.75),"USD", "Pay for the material/s" , PayPalPayment.PAYMENT_INTENT_SALE);
        final String district1 = district.getText().toString();
        final   String streetName1 = streetName.getText().toString();
        final    String houseNo1 = houseNo.getText().toString();
        final String phoneNo1 = phoneNo.getText().toString();

        // Double totalPrice = getIntent().getDoubleExtra("totalPrice", 0);
        HashMap<String, Object> cart = (HashMap<String, Object>) getIntent().getSerializableExtra("cart");
        String Orderid = ordersref.push().getKey();
        ordersMap.put("orderID", Orderid);
        ordersMap.put("totalPrice", (totalPrice));
        ordersMap.put("buyerID", FirebaseAuth.getInstance().getCurrentUser().getUid());
        ordersMap.put("district", district1);
        ordersMap.put("streetName", streetName1);
        ordersMap.put("houseNo", houseNo1);
        ordersMap.put("phoneNo", phoneNo1);
        ordersMap.put("paied", false);
        ordersMap.put("shipped", false);
        ordersMap.put("takenFromOwner", false);
        //ordersMap.put("inCart", adapter);
        ordersref.child(Orderid).updateChildren(ordersMap);
        ordersref.child(Orderid).child("cart").updateChildren(cart);
        cartref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();

        for(int i=1;i<=cart.size();i++){
            Post pp= (Post) cart.get("post".concat(String.valueOf(i)));
            postsref.child(pp.postID).removeValue();

        }



        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Double totalPrice=getIntent().getDoubleExtra("totalPrice",0);


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {

                    try {

                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, PaymentDetails.class).putExtra("PaymentDetails", paymentDetails).putExtra("PaymentAmount", totalPrice));
                    } catch (JSONException e) {
                        e.printStackTrace();


                    }

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                }


            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(this, "Invalid Payment", Toast.LENGTH_SHORT).show();


            }

        }


    }


    public void moveToHome(){

        Intent intent = new Intent(ConfirmOrder.this, BooksList.class);
        startActivity(intent);
    }
}
