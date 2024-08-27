package com.example.rider;

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

public class UpdatePage extends AppCompatActivity {

    EditText name, email, phone, password,radius;
    Button updatebtn;
    FirebaseDatabase mFirebaseDatabase;
    private static final String TAG = "Update Page";
    FirebaseAuth auth = Loginpage.mAuth;
    FirebaseAuth.AuthStateListener mAuthListerner;
    DatabaseReference reff;
    String userID;
    int counter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_page);
        name = (EditText) findViewById(R.id.updatename);
        email = (EditText) findViewById(R.id.updateemail);
        phone = (EditText) findViewById(R.id.updatephone);
        password = (EditText) findViewById(R.id.updatepassword);
        updatebtn = (Button) findViewById(R.id.updatebutton);
        radius  = findViewById(R.id.updateradius);



        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reff = database.getReference("Drivers");
        userID = auth.getUid();
        Log.d(TAG, userID);

        reff.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userID).exists())
                {
                    Driver user = dataSnapshot.child(userID).getValue(Driver.class);
                    Log.d(TAG, user.getNAME());
                    name.setText(user.getNAME());
                    email.setText(user.getEMAIL());
                    phone.setText(user.getPHONE());
                    password.setText(user.getPASSWORD());
                    counter = user.getCOUNTER();
                    radius.setText(user.getRADIUS());

                }
                else
                {
                    Toast.makeText(UpdatePage.this, "User Doesn't exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String EMAIL = email.getText().toString();
                final String NAME = name.getText().toString();
                final String PHONE = phone.getText().toString();
                final String PASSWORD = password.getText().toString();
                final String COMMUNITY = "USER";
                final String RADIUS  = radius.getText().toString();
                final int COUNTER = counter;
                reff.addValueEventListener(new ValueEventListener() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(userID).exists())
                        {
                            Driver user = new Driver(COMMUNITY, COUNTER, EMAIL, NAME, PASSWORD, PHONE , RADIUS);
                            reff.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).setValue(user);
                            Toast.makeText(UpdatePage.this, "Sign up successful!!", Toast.LENGTH_SHORT).show();
                            Intent Homepage = new Intent(UpdatePage.this,Homepage.class);
                            startActivity(Homepage);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(UpdatePage.this, "User Doesn't exists", Toast.LENGTH_SHORT).show();
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




