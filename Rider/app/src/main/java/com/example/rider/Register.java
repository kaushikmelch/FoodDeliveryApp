package com.example.rider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText email, name, phone, password, community,radius;
    Button registerbutton;
    private FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText) findViewById(R.id.registeremail);
        name = (EditText) findViewById(R.id.registername);
        phone = (EditText) findViewById(R.id.registerphone);
        radius = (EditText) findViewById(R.id.registerradius);
        password = (EditText) findViewById(R.id.registerpassword);
        community = (EditText) findViewById(R.id.regsitercommunity);
        registerbutton = (Button) findViewById(R.id.registerbutton);
        firebaseauth = FirebaseAuth.getInstance();


        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
        if (firebaseauth.getCurrentUser() != null) {

        }
    }


    private void registerUser() {
        final String EMAIL = email.getText().toString();
        final String NAME = name.getText().toString();
        final String PHONE = phone.getText().toString();
        final String PASSWORD = password.getText().toString();
        final String COMMUNITY = community.getText().toString();
        final String RADIUS   =  radius.getText().toString();
        final int COUNTER   = 0;

        firebaseauth.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Driver user = new Driver(COMMUNITY, COUNTER , EMAIL, NAME, PASSWORD, PHONE , RADIUS);
                            FirebaseDatabase.getInstance().getReference("Drivers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register.this, "User Registration Successful", Toast.LENGTH_SHORT).show();
                                        Intent Homepage = new Intent(Register.this,Homepage.class);
                                        startActivity(Homepage);
                                    } else {
                                        Toast.makeText(Register.this, "User Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                }


                            });


                        } else {
                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}




