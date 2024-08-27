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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Orderpage extends AppCompatActivity {

    Button regular,occasional;
    private static final String TAG = "Order Page";
    FirebaseAuth auth = Login.mAuth;
    String userID;
    public static List<Integer> cart = new ArrayList<>();
    public static String orderID ;
    String name,phone;
    long timestamp;
    String total ;
    public static int counter;
    public static double latitude_pick;
    public static  double longitude_pick;
    public static  double latitude_drop;
    public static  double longitude_drop;
    public static   String totalcost;
    int flag = 0 ;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderpage);
        regular = (Button)findViewById(R.id.regularbtn);
        occasional = (Button)findViewById(R.id.occasionalbtn);
        userID = auth.getUid();
        final com.example.auth.Location location = new com.example.auth.Location();
        final com.example.auth.transaction trans = new com.example.auth.transaction();
        timestamp = (System.currentTimeMillis() / 1000);
        location.setTimestamp(timestamp);


        final DatabaseReference regular_Reff = FirebaseDatabase.getInstance().getReference().child("Regular");
        final DatabaseReference regular_reff = FirebaseDatabase.getInstance().getReference().child("Orders").child(userID).child("OrderList");
        final DatabaseReference regular_counter_reference = FirebaseDatabase.getInstance().getReference().child("Users");
        final DatabaseReference regular_transaction_Reff = FirebaseDatabase.getInstance().getReference().child("Transactions");


        regular_counter_reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userID).exists()) {
                    USER user = dataSnapshot.child(userID).getValue(USER.class);
                    counter = user.getCOUNTER();
                    phone = user.getPHONE();
                    name = user.getNAME();

                } else {
                    Toast.makeText(Orderpage.this, "User Doesn't exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        occasional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Occasional = new Intent(Orderpage.this, Occasional.class);
                startActivity(Occasional);
            }
        });

        regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regular_Reff.addValueEventListener(new ValueEventListener() {


                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child((userID)).exists())
                        {
                            Regularclass regulardetails = dataSnapshot.child(userID).getValue(Regularclass.class);
                            latitude_pick = regulardetails.getLatitude_pick();
                            latitude_drop = regulardetails.getLatitude_drop();
                            longitude_pick = regulardetails.getLongitude_pick();
                            longitude_drop = regulardetails.getLongitude_drop();
                            cart = regulardetails.getCart();
                            totalcost = regulardetails.getTotalcost();
                            flag = 1;
                            Log.d(TAG, "fuck you123");


                        } else
                        {
                            Log.d(TAG, "fuck you12345");
                            Intent Regular = new Intent(Orderpage.this, Regular.class);
                            startActivity(Regular);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Log.d(TAG, "fuck you123456");

                if (flag == 1) {
                    regular_reff.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            location.setLatitude_pick(latitude_pick);
                            location.setLongitude_pick(longitude_pick);
                            location.setLatitude_drop(latitude_drop);
                            location.setLongitude_drop(longitude_drop);
                            location.setStatus("Not Assigned");
                            location.setCart(cart);
                            regular_reff.child(Integer.toString(counter)).setValue(location);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    regular_transaction_Reff.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            trans.setLatitude_pick(latitude_pick);
                            trans.setLongitude_pick(longitude_pick);
                            trans.setLatitude_drop(latitude_drop);
                            trans.setLongitude_drop(longitude_drop);
                            trans.setNAME(name);
                            orderID = userID + counter;
                            trans.setOrderId(orderID);
                            trans.setPHONE(phone);
                            trans.setCart(cart);
                            trans.setUserId(userID);
                            trans.setTotalcost(totalcost);
                            trans.setStatus("Not yet Assigned");
                            regular_transaction_Reff.child(Long.toString(timestamp)).setValue(trans);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    Intent Regularotp = new Intent(Orderpage.this, Regularotp.class);
                    startActivity(Regularotp);

                }


            }
        });
    }
}













