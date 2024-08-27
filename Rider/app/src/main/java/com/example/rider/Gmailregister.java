package com.example.rider;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.AutoScrollHelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rider.Driver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Gmailregister extends AppCompatActivity  {

    private static final String TAG =  "Testing id-";
    EditText email, name, phone, password, community,radius;
    Button registerbutton;
    FirebaseAuth auth = Loginpage.mAuth;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmailregister);
        email = (EditText) findViewById(R.id.gmailemail);
//        email.setText(firebaseauth.getCurrentUser().getEmail());
        name = (EditText) findViewById(R.id.gmailname);
        phone = (EditText) findViewById(R.id.gmailphone);
        password = (EditText) findViewById(R.id.gmailpassword);
        community = (EditText) findViewById(R.id.gmailcommunity);
        registerbutton = (Button) findViewById(R.id.gmailbutton);
        radius  =  findViewById(R.id.gmailradius);



        Log.d(TAG,Objects.requireNonNull(auth.getCurrentUser()).getUid());

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("Drivers");

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                final ProgressDialog progress  = new ProgressDialog(Gmailregister.this);
                progress.setMessage("Please wait....");
                progress.show();

                final String EMAIL = email.getText().toString();
                final String NAME = name.getText().toString();
                final String PHONE = phone.getText().toString();
                final String PASSWORD = password.getText().toString();
                final String COMMUNITY = community.getText().toString();
                final String RADIUS = radius.getText().toString();
                final int COUNTER = 0;

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //get user info
                        //Checking if the user not exists in database
                        if(dataSnapshot.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).exists()) {
                            progress.dismiss();
                            Toast.makeText(Gmailregister.this, "Phone Number already exists!!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            progress.dismiss();
                            Driver user = new Driver(COMMUNITY, COUNTER, EMAIL, NAME, PASSWORD, PHONE, RADIUS);
                            Log.d(TAG, user.getEMAIL());
                            table_user.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).setValue(user);
                            Toast.makeText(Gmailregister.this, "Sign up successful!!", Toast.LENGTH_SHORT).show();
                            Intent Homepage = new Intent(Gmailregister.this,Homepage.class);
                            startActivity(Homepage);
                            finish();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }
}




