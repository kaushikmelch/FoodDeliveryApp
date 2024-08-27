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

public class Regularcart extends AppCompatActivity
{



    private static final String TAG = "CartActivity";
    Button placeorder,showprice;
    TextView smalltotal, mediumtotal, largetotal, grandtotal, deliverytotal;
    public static List<Integer> cart = new ArrayList<>();
    int small_count,medium_count,large_count;
    String smallprice,mediumprice,largeprice;
    int total_delivery = Regularmaps.delivery_charge;
    FirebaseAuth auth = Login.mAuth;
    EditText small,medium,large;
    String userID;
    String total = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regularcart);
        Log.d(TAG, "fuck 3");
        placeorder = findViewById(R.id.regular_cart_placeorder);
        smalltotal = findViewById(R.id.regular_cart_smallpackettotalprice);
        mediumtotal = findViewById(R.id.regular_cart_mediumpackettotalprice);
        largetotal = findViewById(R.id.regular_cart_largepackettotalprice);
        grandtotal = findViewById(R.id.regular_cart_total_price_text_view);
        showprice = findViewById(R.id.regular_cart_showprice);
        small = findViewById(R.id.regular_cart_smallpacketquantity);
        medium = findViewById(R.id.regular_cart_mediumpacketquantity);
        large = findViewById(R.id.regular_cart_largepacketquantity);
        small = findViewById(R.id.regular_cart_smallpacketquantity);
        deliverytotal = findViewById(R.id.regular_cart_deliverycharges);
        deliverytotal.setText("Delivery charges = " + total_delivery);
        userID = auth.getUid();

        final DatabaseReference regular_Register_reff = FirebaseDatabase.getInstance().getReference().child("Regular");

        Log.d(TAG, "fuck 4");
        showprice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                small_count = Integer.parseInt(small.getText().toString());
                smallprice = String.valueOf(small_count * 80);
                medium_count = Integer.parseInt(medium.getText().toString());
                mediumprice = String.valueOf(medium_count * 160);
                large_count = Integer.parseInt(large.getText().toString());
                largeprice = String.valueOf(large_count * 240);
                smalltotal.setText("Total Price = " + smallprice);
                mediumtotal.setText("Total Price = " + mediumprice);
                largetotal.setText("Total Price = " + largeprice);
                total = String.valueOf((small_count * 80) + (medium_count * 160) + (large_count * 240) + total_delivery);
                grandtotal.setText("Total Price"+ total);
                Log.d(TAG, "total cost" + total);
            }
        });


        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                cart.add(small_count);
                cart.add(medium_count);
                cart.add(large_count);

                regular_Register_reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Regularclass reg = new Regularclass();
                        reg.setLatitude_pick(Regular.coordinates_pick[0]);
                        reg.setLongitude_pick(Regular.coordinates_pick[1]);
                        reg.setLongitude_drop(Regular.coordinates_drop[1]);
                        reg.setLatitude_drop(Regular.coordinates_drop[0]);
                        reg.setCart(cart);
                        Log.d(TAG, "total cost" + total);
                        reg.setTotalcost(total);
                        regular_Register_reff.child(userID).setValue(reg);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Log.d(TAG, "fuck 5");
                Intent Orderpage = new Intent(Regularcart.this, com.example.auth.Orderpage.class);
                startActivity(Orderpage);
            }
            });




            }

    }




