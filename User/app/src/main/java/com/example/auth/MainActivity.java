package com.example.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static final int RC_SIGN_IN = 1;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent Login = new Intent(MainActivity.this, Login.class);
        startActivity(Login);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }
}
//
//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
//            {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if(user!=null)
//                {
//                    //User is signed in
//                    Toast.makeText(MainActivity.this, "You're now signed in. Welcome to FriendlyChat.", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    //User is signed out
//                    startActivityForResult(
//                            AuthUI.getInstance()
//                                    .createSignInIntentBuilder()
//                                    .setAvailableProviders(Arrays.asList(
//                                            new AuthUI.IdpConfig.EmailBuilder().build(),
//                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
//                                    .build(),
//                            RC_SIGN_IN);
//                }
//            }
//        };
//    }
//
//    protected void onPause()
//    {
//        super.onPause();
//        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
//    }
//
//    protected void onResume()
//    {
//        super.onResume();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//    }
