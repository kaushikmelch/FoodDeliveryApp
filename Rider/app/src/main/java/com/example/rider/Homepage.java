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

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Homepage extends AppCompatActivity {

    private static final String TAG = "Homepage";
    Button order,update,ongoing,previous,logout;
    GoogleSignInClient mGoogleSignInClient = Loginpage.mGoogleSignInClient;
    FirebaseAuth auth = Loginpage.mAuth;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Log.d(TAG, Objects.requireNonNull(auth.getCurrentUser()).getUid());
        order = (Button)findViewById(R.id.takeorderbtn);
        update = (Button)findViewById(R.id.updatebtn);
        logout = (Button)findViewById(R.id.logoutbtn);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Orderpage= new Intent(Homepage.this, Orderpage.class);
                startActivity(Orderpage);

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Updatepage = new Intent(Homepage.this, UpdatePage.class);
                startActivity(Updatepage);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                signOut();
            }
        });


    }



    private void signOut(){

        auth.signOut();
        Log.d(TAG, String.valueOf(mGoogleSignInClient.getInstanceId()));
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent Login = new Intent(Homepage.this, Loginpage.class);
                        startActivity(Login);
                    }
                });
    }
}
