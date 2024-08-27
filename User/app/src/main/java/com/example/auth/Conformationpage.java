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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Conformationpage extends AppCompatActivity
{

    private static final String TAG = "Conformation";
    TextView ordernumber,driverphonetext,drivernametext,small,medium,large,totalcost;
    Button navigation,backtohome;
    int counter = Cartpage.counter;
    final int count = counter + 1;
    FirebaseAuth auth = Login.mAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conformationpage);
        userID = auth.getUid();
        ordernumber = findViewById(R.id.ordernumbertext);
        driverphonetext = findViewById(R.id.phonenumbertext);
        drivernametext = findViewById(R.id.nametext);
        small = findViewById(R.id.smalltext);
        medium = findViewById(R.id.mediumtext);
        large = findViewById(R.id.largetext);
        navigation = findViewById(R.id.showlocation);
        backtohome = findViewById(R.id.orderagain);
        totalcost = findViewById(R.id.totalcosttext);
        ordernumber.setText(Cartpage.orderID);
        drivernametext.setText(Otp.drivername);
        driverphonetext.setText(Otp.driverphone);
        small.setText(String.valueOf(Cartpage.cart.get(0)));
        medium.setText(String.valueOf(Cartpage.cart.get(1)));
        large.setText(String.valueOf(Cartpage.cart.get(2)));
        ordernumber.setText("Order Number : "+ Cartpage.orderID);
        drivernametext.setText("Driver Phone Number : " + Otp.drivername);
        driverphonetext.setText("Driver Name : " +Otp.driverphone);
        small.setText(String.valueOf("Small Packets : " +  Cartpage.cart.get(0)));
        medium.setText(String.valueOf("Medium Packets : "  + Cartpage.cart.get(1)));
        large.setText(String.valueOf("Large Packets : " +  Cartpage.cart.get(2)));
        totalcost.setText("Total Cost : " + Cartpage.total);
        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent Otp = new Intent(Conformationpage.this, Tracking.class);
                startActivity(Otp);
            }
        });
        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatecounter();
            }
        });
    }

    public void updatecounter()
    {
        final DatabaseReference counter_Reff = FirebaseDatabase.getInstance().getReference().child("Users");

        counter_Reff.addListenerForSingleValueEvent(new ValueEventListener()
        {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userID).exists())
                {
                    counter_Reff.child(userID).child("counter").setValue(count);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        Intent Homepage = new Intent(Conformationpage.this, Homepage.class);
        startActivity(Homepage);
    }
}
