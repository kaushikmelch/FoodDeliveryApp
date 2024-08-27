package com.example.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class Completedorders extends AppCompatActivity {

    ListView listView;
    ArrayList<String> completedorders = new ArrayList<>();
    String userID;
    FirebaseAuth auth = Login.mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completedorders);
        listView = findViewById(R.id.completedOrdersList);
        userID = auth.getUid();
        final DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Orders").child(userID).child("OrderList");
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            private static final String TAG = "fuck you";

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int iterator = 1;
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Location temp = ds.getValue(Location.class);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
//                    Date dateTime = new Date((ds.getValue(Location.class).getTimestamp()) * 1000);
//
//                    String status = ds.getValue(Location.class).getStatus();
                    long time = temp.getTimestamp() * 1000;
                    String date = simpleDateFormat.format(time);
                    String status = temp.getStatus();

                    Log.d(TAG, userID);
                    Log.d(TAG, "time" + date );
                    Log.d(TAG, "Status" + status);


                    if (status.matches("Completed")) {
                        String tmp = "Order Number : " + userID + Integer.toString(iterator) + "\n" +
                                "Small : " + ds.getValue(Location.class).getCart().get(0) + "\n" +
                                "Medium : " + ds.getValue(Location.class).getCart().get(1) + "\n" +
                                "Large : " + ds.getValue(Location.class).getCart().get(2) + "\n" +
                                "Date : " + date + "\n";
                        completedorders.add(tmp);
                        iterator++;
                    }
                }
                reff.removeEventListener(this);
                if (completedorders.isEmpty()) {
                    Toast.makeText(Completedorders.this, "There are no completed orders", Toast.LENGTH_LONG).show();
                } else {
                    ArrayAdapter arrayAdapter = new ArrayAdapter(Completedorders.this, android.R.layout.simple_list_item_1, completedorders);
                    listView.setAdapter(arrayAdapter);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
