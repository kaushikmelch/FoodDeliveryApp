package com.example.auth;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cartpage extends AppCompatActivity {
    private static final String TAG = "CartActivity";
    Button placeorder,showprice;
    TextView smalltotal, mediumtotal, largetotal, grandtotal, deliverytotal;
    public static List<Integer> cart = new ArrayList<>();
    int total_small,total_medium,total_large;
    int small_count,medium_count,large_count;
    int total_grand;
    String smallprice,mediumprice,largeprice;
    int total_delivery = maps.delivery_charge;
    FirebaseAuth auth = Login.mAuth;
    EditText small,medium,large;
    public static String orderID ;
    String name,phone;
    String userID;
    long timestamp;
    public  static  String total = "0";
    public static int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartpage);
        placeorder = findViewById(R.id.btn_place_order);
        smalltotal = findViewById(R.id.smallpackettotalprice);
        mediumtotal = findViewById(R.id.mediumpackettotalprice);
        largetotal = findViewById(R.id.largepackettotalprice);
        grandtotal = findViewById(R.id.total_price_text_view);
        showprice = findViewById(R.id.showprice);
        small = findViewById(R.id.smallpacketquantity);
        medium = findViewById(R.id.mediumpacketquantity);
        large = findViewById(R.id.largepacketquantity);
        small = findViewById(R.id.smallpacketquantity);
        deliverytotal = findViewById(R.id.deliverycharges);
        deliverytotal.setText("Delivery charges = " + total_delivery);
        userID = auth.getUid();

        final com.example.auth.Location location = new com.example.auth.Location();
        final com.example.auth.transaction trans = new com.example.auth.transaction();
        timestamp = (System.currentTimeMillis() / 1000);
        location.setTimestamp(timestamp);

        final DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Orders").child(userID).child("OrderList");
        final DatabaseReference counter_reference = FirebaseDatabase.getInstance().getReference().child("Users");

        final DatabaseReference transaction_Reff = FirebaseDatabase.getInstance().getReference().child("Transactions");


        counter_reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userID).exists()) {
                    USER user = dataSnapshot.child(userID).getValue(USER.class);
                    counter = user.getCOUNTER();
                    phone = user.getPHONE();
                    name = user.getNAME();

                } else {
                    Toast.makeText(Cartpage.this, "User Doesn't exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        showprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                small_count = Integer.parseInt(small.getText().toString());
                smallprice = String.valueOf(small_count * 100);
                medium_count = Integer.parseInt(medium.getText().toString());
                mediumprice = String.valueOf(medium_count * 200);
                large_count = Integer.parseInt(large.getText().toString());
                largeprice = String.valueOf(large_count * 300);
                smalltotal.setText("Total Price = " + smallprice);
                mediumtotal.setText("Total Price = " + mediumprice);
                largetotal.setText("Total Price = " + largeprice);
                total = String.valueOf((small_count * 100) + (medium_count * 200) + (large_count * 300) + total_delivery);
                grandtotal.setText("Total = " + total);
            }
        });


        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                cart.add(small_count);
                cart.add(medium_count);
                cart.add(large_count);

                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        location.setLatitude_pick(Occasional.coordinates_pick[0]);
                        location.setLongitude_pick(Occasional.coordinates_pick[1]);
                        location.setLatitude_drop(Occasional.coordinates_drop[0]);
                        location.setLongitude_drop(Occasional.coordinates_drop[1]);
                        location.setStatus("Not Assigned");
                        location.setCart(cart);
                        reff.child(Integer.toString(counter)).setValue(location);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



//                final int count = counter + 1;
//                counter_Reff.addListenerForSingleValueEvent(new ValueEventListener() {
//
//                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.child(userID).exists()) {
//
//
//                            counter_Reff.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("counter").setValue(count);
//                            Toast.makeText(Cartpage.this, "Sign up successful!!", Toast.LENGTH_SHORT).show();
//                            finish();
//                        } else {
//                            Toast.makeText(Cartpage.this, "User Doesn't exists", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });




                transaction_Reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        trans.setLatitude_pick(Occasional.coordinates_pick[0]);
                        trans.setLongitude_pick(Occasional.coordinates_pick[1]);
                        trans.setLatitude_drop(Occasional.coordinates_drop[0]);
                        trans.setLongitude_drop(Occasional.coordinates_drop[1]);
                        trans.setNAME(name);
                        orderID = userID+counter;
                        trans.setOrderId(orderID);
                        trans.setPHONE(phone);
                        trans.setCart(cart);
                        trans.setUserId(userID);
                        trans.setCounter(counter);
                        trans.setTotalcost(total);
                        trans.setStatus("Not yet Assigned");
                        transaction_Reff.child(Long.toString(timestamp)).setValue(trans);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent Otp = new Intent(Cartpage.this, Otp.class);
                startActivity(Otp);

//                Intent Register = new Intent(Cartpage.this, Register.class);
//                startActivity(Register);

            }
        });





    }


    }



