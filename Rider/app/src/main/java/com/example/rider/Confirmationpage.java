package com.example.rider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Confirmationpage extends AppCompatActivity
{
    TextView ordernumber,customerphone,customername,small,medium,large,totalcost;
    Button navigation,complete;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmationpage);
        final DatabaseReference otp_reff = FirebaseDatabase.getInstance().getReference().child("OTP");
        otp_reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                otp_reff.child(Orderpage.smsMessage).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ordernumber = findViewById(R.id.ordernumbertext);
        customername = findViewById(R.id.nametext);
        customerphone = findViewById(R.id.phonenumbertext);
        small = findViewById(R.id.smalltext);
        medium =  findViewById(R.id.mediumtext);
        large = findViewById(R.id.largetext);
        totalcost = findViewById(R.id.totalcosttext);

        complete = findViewById(R.id.completeorder);
        navigation = findViewById(R.id.showlocation);

        ordernumber.setText("Order Number = " + Orderpage.OrderId);
        customername.setText(("Customer Name = ") + Orderpage.NAME);
        customerphone.setText("Customer Phone Number = "+ Orderpage.PHONE );
        small.setText("Small = " + Orderpage.cart.get(0));
        medium.setText("Medium = " + Orderpage.cart.get(1));
        large.setText("Large = " + Orderpage.cart.get(2));
        totalcost.setText("Total Cost = " + Orderpage.totalcost);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                final DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Orders").child(Orderpage.iduser).child("OrderList");

                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        reff.child(Integer.toString(Orderpage.counter)).child("status").setValue("Completed");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent i = new Intent(Confirmationpage.this,Homepage.class);
                startActivity(i);


            }
        });

        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Confirmationpage.this,Tracking.class);
                startActivity(i);
            }
        });








    }
}
