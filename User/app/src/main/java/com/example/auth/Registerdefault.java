package com.example.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registerdefault extends Activity implements LocationListener
{
    private static final String TAG = "REGISTERDEFAULT" ;
    EditText defaultaddess;
    Button  defaultbtn;
    private String provider;
    private LocationManager locationManager;
    FirebaseAuth auth = Login.mAuth;
    String userId;
    public static double[] coordinates = new double[2];
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerdefault);
        defaultaddess = findViewById(R.id.default_location);
        defaultbtn = findViewById(R.id.default_address_btn);
        userId = auth.getUid();
        Log.d(TAG, "holyyy mf123");

//
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        provider = locationManager.getBestProvider(criteria, false);
//
//        if (ActivityCompat.checkSelfPermission(Registerdefault.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Registerdefault.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(Registerdefault.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//            return;
//        }

//        android.location.Location location = locationManager.getLastKnownLocation(provider);
//        if (location != null) {
//            System.out.println("Provider " + provider + " has been selected.");
//            onLocationChanged(location);
//        } else {
//            Log.d(TAG, "Location not available");
//        }
        defaultbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String defaultaddress = defaultaddess.getText().toString();
                com.example.auth.MainLocation locationAddress = new com.example.auth.MainLocation();

                coordinates = locationAddress.getAddressFromLocation(defaultaddress,
                        getApplicationContext());
                Log.d(TAG, "latitude" + coordinates[0]);
                Log.d(TAG, "Longitude" + coordinates[1]);


                final DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Default");;

                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        Log.d(TAG, "holyyy mf1234");
                        defaultaddress deffadd = new defaultaddress();
                        deffadd.setDefaultlatitude(coordinates[0]);
                        deffadd.setDefaultlongitiude(coordinates[1]);
                        reff.child(userId).setValue(deffadd);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Log.d(TAG, "holyyy mf1235");
                Intent i = new Intent(Registerdefault.this,Occasional.class);
                startActivity(i);
            }
        });
    }





    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
