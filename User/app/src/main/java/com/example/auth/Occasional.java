package com.example.auth;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.util.Objects;


public class Occasional extends Activity implements LocationListener {
    private static final String TAG = "location page";
    public static double[] coordinates_pick = new double[2];
    public static double[] coordinates_drop = new double[2];
    public static double[] coordinates_current = new double[2];
    public static double[] coordinates_default = new double[2];
    int flag_pick = 0;
    int flag_drop = 0;

    Button showlocation, placeorder, pickdefault, dropdefault;
    EditText editTextdrop,editTextpick;
    private String provider;
    private LocationManager locationManager;
    FirebaseAuth auth = Login.mAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occasional);
        userID = auth.getUid();
        Log.d(TAG, "holyyy mf1237657");

        showlocation = (Button) findViewById(R.id.occasionalmarkerbtn);
        placeorder = (Button) findViewById(R.id.placeorderbtn);
        pickdefault = findViewById(R.id.pick_default);
        dropdefault = findViewById(R.id.drop_default);
        editTextpick = (EditText) findViewById(R.id.pickuplocation);
        editTextdrop = (EditText) findViewById(R.id.droplocation);

        final DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Default");
        final DatabaseReference reffer = FirebaseDatabase.getInstance().getReference().child("Default");

        pickdefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(userID).exists())
                        {
                            editTextpick.setText("Default location selected");
                            defaultaddress defadd = dataSnapshot.child(userID).getValue(defaultaddress.class);
                            flag_pick = 1;
                            coordinates_default[0] = defadd.getDefaultlatitude();
                            coordinates_default[1] = defadd.getDefaultlongitiude();


                        }
                        else
                        {
                            Log.d(TAG, "holyyy mf1");
                            Intent i = new Intent(Occasional.this,Registerdefault.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        dropdefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                editTextdrop.setText("Default location selected");
                if (flag_pick == 0) {
                    reffer.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(userID).exists()) {
                                defaultaddress defadd = dataSnapshot.child(userID).getValue(defaultaddress.class);

                                coordinates_default[0] = defadd.getDefaultlatitude();
                                coordinates_default[1] = defadd.getDefaultlongitiude();
                                flag_drop = 1;


                            } else {

                                Intent i = new Intent(Occasional.this, Registerdefault.class);
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else
                    {
                        Toast.makeText(Occasional.this, "Pick location already selected as default",Toast.LENGTH_SHORT).show();
                }
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        if (ActivityCompat.checkSelfPermission(Occasional.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Occasional.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Occasional.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        android.location.Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            Log.d(TAG, "Location not available");
        }

        showlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.d(TAG, "Click Successfull!!!");

                String addresspick = editTextpick.getText().toString();
                String addressdrop = editTextdrop.getText().toString();
                String pickup = editTextpick.getText().toString();
                String drop = editTextdrop.getText().toString();

                com.example.auth.MainLocation locationAddress = new com.example.auth.MainLocation();
                if(TextUtils.isEmpty(pickup) || flag_pick == 1)
                {
                    if(flag_pick == 0)
                    coordinates_pick = coordinates_current;
                    else
                    coordinates_pick = coordinates_default;
                }
                else
                {
                    coordinates_pick = locationAddress.getAddressFromLocation(addresspick,
                            getApplicationContext());
                }
                if(TextUtils.isEmpty(drop) || flag_drop == 1)
                {
                    Log.d(TAG, "mother fucker " + flag_drop);
                    if (flag_drop == 0)
                        coordinates_drop = coordinates_current;
                    else
                        coordinates_drop = coordinates_default;
                }
                else
                {
                    coordinates_drop = locationAddress.getAddressFromLocation(addressdrop,
                            getApplicationContext());
                }

                Intent maps = new Intent(Occasional.this, maps.class);
                startActivity(maps);

            }
        });

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Cartpage = new Intent(Occasional.this, Cartpage.class);
                startActivity(Cartpage);
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        coordinates_current[0] =  (location.getLatitude());
        coordinates_current[1] = (location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

}
