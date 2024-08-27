package com.example.rider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
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

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class Orderpage extends AppCompatActivity implements LocationListener {

    private static final String TAG = "Order page-----------";
    TextView orderno,customername,customerphone;
    Button OTPbtn;
    public static double[] coordinates_current = new double[2];
    private String provider;
    private LocationManager locationManager;
    FirebaseAuth auth = Loginpage.mAuth;
    String userID;
    float radius;
    private static final int REQUEST_SMS_PERMISSION = 1;
    public static double latitude_pick;
    public static  double longitude_pick;
    public static  double latitude_drop;
    public static  double longitude_drop;
    public static  String NAME,PHONE;
    public static  List<Integer> cart;
    public static   String status;
    public static   String totalcost;
    public static   String OrderId;
    public static  String iduser;
    public static int counter;

    Boolean result = true;
    private String driverphone,drivername;
    String timestamp = "NULL";
   public static String smsMessage= new DecimalFormat("000000").format(new Random().nextInt(999999));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderpage);
        orderno = findViewById(R.id.ordernumbertext);
        customername = findViewById(R.id.nametext);
        customerphone = findViewById(R.id.phonenumbertext);
        OTPbtn = findViewById(R.id.buttonotp);
        userID = auth.getUid();


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        if (ActivityCompat.checkSelfPermission(Orderpage.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Orderpage.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Orderpage.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        android.location.Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            Log.d(TAG, "Location not available");
        }



        final DatabaseReference radius_reff = FirebaseDatabase.getInstance().getReference().child("Drivers");
        radius_reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(userID).exists()) {
                    Driver user = dataSnapshot.child(userID).getValue(Driver.class);
                    radius = Float.valueOf(user.getRADIUS());
                    drivername =  user.getNAME();
                    driverphone = user.getPHONE();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        final DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Transactions");
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                showdata(dataSnapshot);
                if(timestamp!="NULL")
                    reff.child(timestamp).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        OTPbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPhoneStatePermission();
            }
        });

    }

    private void showdata(DataSnapshot dataSnapshot) {
        Double latitude, longitude;
        int flag = 0;
        float results[] = new float[10];
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            transaction trans = ds.getValue(transaction.class);
            latitude = trans.getLatitude_pick();
            longitude = trans.getLongitude_pick();
            Location.distanceBetween(latitude, longitude, coordinates_current[0], coordinates_current[1], results);
            Log.d(TAG,  Float.toString(results[0]/1000));
            if ((results[0]/1000) <= radius)
            {
                timestamp = ds.getKey();
                latitude_pick = trans.getLatitude_pick();
                latitude_drop = trans.getLatitude_drop();
                longitude_pick = trans.getLongitude_pick();
                longitude_drop = trans.getLongitude_drop();
                NAME = trans.getNAME();
                PHONE = trans.getPHONE();
                counter = trans.getCounter();
                iduser = trans.getUserId();
                status = trans.getStatus();
                totalcost = trans.getTotalcost();
                OrderId = trans.getOrderId();
                cart = trans.getCart();
                flag = 1;
                break;
            }
        }
        if (flag == 0)
            Toast.makeText(Orderpage.this, "No Orders in your radius", Toast.LENGTH_SHORT).show();
        else
        {
            orderno.setText("Order Number = " + OrderId);
            customerphone.setText("Customer Phone Number = " + PHONE);
            customername.setText("Customer Name = " + NAME);


        }

    }

    private void showPhoneStatePermission()
    {

        int permissionCheck = ContextCompat.checkSelfPermission(Orderpage.this, Manifest.permission.READ_PHONE_STATE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(Orderpage.this, Manifest.permission.READ_PHONE_STATE)) {
                Toast.makeText(Orderpage.this, "Permission not granted", Toast.LENGTH_SHORT).show();

            }
            else {

                requestPermission(Manifest.permission.READ_PHONE_STATE, REQUEST_SMS_PERMISSION);
            }
        }
        else {
            Toast.makeText(Orderpage.this, "Permission already exists", Toast.LENGTH_SHORT).show();
            sendSMS();
        }
    }

    private void requestPermission(String permissionName, int permissionCode)
    {

        ActivityCompat.requestPermissions(Orderpage.this, new String[]{permissionName}, permissionCode);

    }

    private  void sendSMS()
    {

        Toast.makeText(Orderpage.this, "Inside SMS send method", Toast.LENGTH_SHORT).show();
        final SmsManager smsManager = android.telephony.SmsManager.getDefault();
        PendingIntent sentPI;
        String SENT = "SMS_SENT";
        try {

            smsManager.sendTextMessage(PHONE, null, smsMessage, null, null);

            final DatabaseReference otp_reff = FirebaseDatabase.getInstance().getReference().child("OTP");
            otp_reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    Otpclass otp = new Otpclass();
                    otp.setDRIVER_LATITUDE(coordinates_current[0]);
                    otp.setDRIVER_LONGITUDE(coordinates_current[1]);
                    otp.setNAME(drivername);
                    otp.setPHONE(driverphone);
                    otp.setStatus(true);
                    otp_reff.child(smsMessage).setValue(otp);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            Log.d(TAG, "1234");
            Toast.makeText(Orderpage.this, "SMS sent successfully", Toast.LENGTH_SHORT).show();

                final DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("OTP");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        Log.d(TAG, "onDataChange: fuck the world");
                        if(dataSnapshot.child(smsMessage).exists())
                        {
                            Otpclass otp = dataSnapshot.child(smsMessage).getValue(Otpclass.class);
                            if(!otp.isStatus())
                            {
                                String message = "Your booking for " + OrderId + " is confirmed" + " and will be delivered by " + drivername + "( " + driverphone  + " )" +  ". Please pay Rs " + totalcost + ".";
                                smsManager.sendTextMessage(PHONE, null, message, null, null);

                                Intent Confirmation = new Intent(Orderpage.this, Confirmationpage.class);
                                startActivity(Confirmation);
                            }
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        } catch (Exception e) {
            e.printStackTrace();
            ;
            Log.d(TAG, e.toString());
            Toast.makeText(Orderpage.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_SMS_PERMISSION : if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Orderpage.this, "SMS Permission Obtained!", Toast.LENGTH_SHORT).show();
                sendSMS();
            }
            else {
                Toast.makeText(Orderpage.this, "Permission Denied To Send Confirmation SMS", Toast.LENGTH_SHORT).show();
            }
                break;
        }
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
    public void onLocationChanged(Location location)
    {
        coordinates_current[0] =  (location.getLatitude());
        coordinates_current[1] = (location.getLongitude());
        Log.d(TAG, "latitudeeeee " + coordinates_current[0]);
        Log.d(TAG, "longitudeee"  + coordinates_current[1]);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider)
    {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
}
