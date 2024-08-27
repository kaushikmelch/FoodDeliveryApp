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

public class Regularconfirmation extends AppCompatActivity {

    private static final String TAG = "Conformation";
    TextView ordernumber, driverphonetext, drivernametext, small, medium, large ,totalcost;
    Button navigation, backtohome;
    int counter = Orderpage.counter;
    final int count = counter + 1;
    FirebaseAuth auth = Login.mAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regularconfirmation);
        userID = auth.getUid();


        final DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Orders").child(userID).child("OrderList");

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                reff.child(Integer.toString(counter)).child("status").setValue("Completed");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ordernumber = findViewById(R.id.regular_ordernumbertext);
        driverphonetext = findViewById(R.id.regular_phonenumbertext);
        drivernametext = findViewById(R.id.regular_nametext);
        small = findViewById(R.id.regular_smalltext);
        medium = findViewById(R.id.regular_mediumtext);
        large = findViewById(R.id.regular_largetext);
        totalcost = findViewById(R.id.regular_totalcosttext);

        navigation = findViewById(R.id.regular_showlocation);
        backtohome = findViewById(R.id.regular_orderagain);


        ordernumber.setText("Order Number : "+ Orderpage.orderID);
        drivernametext.setText("Driver Phone Number : " + Regularotp.drivername);
        driverphonetext.setText("Driver Name : " +Regularotp.driverphone);
        small.setText(String.valueOf("Small Packets : " +  Orderpage.cart.get(0)));
        medium.setText(String.valueOf("Medium Packets : "  + Orderpage.cart.get(1)));
        large.setText(String.valueOf("Large Packets : " +  Orderpage.cart.get(2)));
        totalcost.setText("Total Cost : " + Orderpage.totalcost);






        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                final DatabaseReference regular_counter_Reff = FirebaseDatabase.getInstance().getReference().child("Users");

                regular_counter_Reff.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(userID).exists()) {
                            regular_counter_Reff.child(userID).child("counter").setValue(count);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent regularotp = new Intent(Regularconfirmation.this, Regulartracking.class);
                startActivity(regularotp);
            }
        });
        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                final DatabaseReference regular_counter_Reff = FirebaseDatabase.getInstance().getReference().child("Users");

                regular_counter_Reff.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(userID).exists()) {
                            regular_counter_Reff.child(userID).child("counter").setValue(count);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent homepage = new Intent(Regularconfirmation.this, Homepage.class);
                startActivity(homepage);
            }
        });
    }
}


