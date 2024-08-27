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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Otp extends AppCompatActivity {

    private static final String TAG = "otp";
    String userID, OTPtext;
    FirebaseAuth auth = Login.mAuth;
    EditText verifyotp;
    Button verify;
    public static String driverphone,drivername;
    public static double driver_latitude,driver_longitude;
    public static boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        verifyotp = findViewById(R.id.otpverify);
        verify = findViewById(R.id.buttonotp);



        final DatabaseReference reff = FirebaseDatabase.getInstance().getReference("OTP");
        Log.d(TAG, "fuck you");
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OTPtext = verifyotp.getText().toString();
                Log.d(TAG, OTPtext);
                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(OTPtext).exists())
                        {
                            Otpclass otp = dataSnapshot.child(OTPtext).getValue(Otpclass.class);
                            driver_latitude = otp.getDRIVER_LATITUDE();
                            driver_longitude = otp.getDRIVER_LONGITUDE();
                            drivername = otp.getNAME();
                            driverphone = otp.getPHONE();
                            reff.child(OTPtext).child("status").setValue(false);
                            Intent conformation = new Intent(Otp.this,Conformationpage.class);
                            startActivity(conformation);



                        }
                        else
                        {
                            Toast.makeText(Otp.this, "Wrong Password!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

//                    }
//                });
                    }
                });

            }

        });
    }
}