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

public class Currentorders extends AppCompatActivity {

    ListView listView;
    ArrayList<String> currentorders = new ArrayList<>();
    String userID;
    FirebaseAuth auth = Login.mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentorders);
        listView = findViewById(R.id.currentOrdersList);
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


                    if (status.matches("Not Assigned")) {
                        String tmp = "Order Number : " + userID + Integer.toString(iterator) + "\n" +
                                "Small : " + ds.getValue(Location.class).getCart().get(0) + "\n" +
                                "Medium : " + ds.getValue(Location.class).getCart().get(1) + "\n" +
                                "Large : " + ds.getValue(Location.class).getCart().get(2) + "\n" +
                                "Date : " + date + "\n";
                        currentorders.add(tmp);
                        iterator++;
                    }
                }
                reff.removeEventListener(this);
                if (currentorders.isEmpty()) {
                    Toast.makeText(Currentorders.this, "There are no ongoing orders", Toast.LENGTH_LONG).show();
                } else {
                    ArrayAdapter arrayAdapter = new ArrayAdapter(Currentorders.this, android.R.layout.simple_list_item_1, currentorders);
                    listView.setAdapter(arrayAdapter);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
