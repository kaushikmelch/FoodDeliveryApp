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


public class Regular extends Activity implements LocationListener {
    private static final String TAG = "location page";
    public static double[] coordinates_pick = new double[2];
    public static double[] coordinates_drop = new double[2];
    public static double[] coordinates_current = new double[2];
    Button showlocation,placeorder;
    EditText editTextpick,editTextdrop;
    private String provider;
    private LocationManager locationManager;
    //    FirebaseAuth auth = Login.mAuth;
//    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular);

        showlocation = (Button) findViewById(R.id.regular_markerbtn);
        placeorder   = (Button) findViewById(R.id.regular_placeorderbtn);
        editTextpick = (EditText) findViewById(R.id.regular_pickuplocation);
        editTextdrop = (EditText) findViewById(R.id.regular_droplocation);





        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        if (ActivityCompat.checkSelfPermission(Regular.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Regular.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Regular.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
                if(TextUtils.isEmpty(pickup))
                {
                    coordinates_pick = coordinates_current;
                }
                else
                {
                    coordinates_pick = locationAddress.getAddressFromLocation(addresspick,
                            getApplicationContext());
                }
                if(TextUtils.isEmpty(drop))
                {
                    coordinates_drop = coordinates_current;
                }
                else
                {
                    coordinates_drop = locationAddress.getAddressFromLocation(addressdrop,
                            getApplicationContext());
                }

                Intent Regularmaps = new Intent(Regular.this, Regularmaps.class);
                startActivity(Regularmaps);

            }
        });

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Regularcart = new Intent(Regular.this, Regularcart.class);
                startActivity(Regularcart);
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
