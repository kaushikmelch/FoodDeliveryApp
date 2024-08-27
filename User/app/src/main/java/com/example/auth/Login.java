package com.example.auth;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;






import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import java.util.Objects;

public class Login extends AppCompatActivity{
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    EditText email, password;
    Button signin, changepassword, signup;
    LoginButton loginButton;
    SignInButton signInButton;
    public  static  GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;
    public static FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reff;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = (EditText) findViewById(R.id.loginemail);
        password = (EditText) findViewById(R.id.loginpassword);
        signin = (Button) findViewById(R.id.loginbtn);
        signup = (Button) findViewById(R.id.registerbtn);
        signInButton= (SignInButton) findViewById(R.id.googleloginbtn);
        database = FirebaseDatabase.getInstance();
        loginButton = findViewById(R.id.facebook_login_btn);
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        FacebookSdk.sdkInitialize(getApplicationContext());


        reff = database.getReference("Users");

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuser();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Register = new Intent(Login.this, Register.class);
                startActivity(Register);
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                signIn();
            }
        });

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel()
            {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error)
            {
                Log.d(TAG, "facebook:onError", error);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        final ProgressDialog progress = new ProgressDialog(Login.this);
        progress.setMessage("Please wait....");
        progress.show();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            progress.dismiss();
                            reff.addValueEventListener(new ValueEventListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    Log.d(TAG,Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                                    if (dataSnapshot.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).exists()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithCredential:success");
                                        Intent Homepage = new Intent(Login.this,Homepage.class);
                                        startActivity(Homepage);
                                    } else {
                                        Log.d(TAG, "signInWithCredential: notsuccess");
                                        Intent Gmailregister  = new Intent(Login.this, Gmailregister.class);
                                        startActivity(Gmailregister);
                                    }
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Intent Login = new Intent(Login.this, Login.class);
                            startActivity(Login);
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
//
                });
    }


        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null) {
                Intent Homepage = new Intent(Login.this, Homepage.class);
                startActivity(Homepage);
            }

        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if(resultCode != RESULT_CANCELED) {
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e);
                    // [START_EXCLUDE]
                    Intent Login = new Intent(Login.this, Login.class);
                    startActivity(Login);
                    // [END_EXCLUDE]
                }
            }
            else
            {
                mCallbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }
    }



    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        final ProgressDialog progress = new ProgressDialog(Login.this);
        progress.setMessage("Please wait....");
        progress.show();
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            progress.dismiss();
                            reff.addValueEventListener(new ValueEventListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    Log.d(TAG,Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                                    if (dataSnapshot.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).exists()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithCredential: success");

                                        Intent Homepage = new Intent(Login.this,Homepage.class);
                                        startActivity(Homepage);
                                    } else {
                                        Log.d(TAG, "signInWithCredential: notsuccess");
                                        Intent Gmailregister  = new Intent(Login.this, Gmailregister.class);
                                        startActivity(Gmailregister);
                                    }
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            }
                         else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Intent Login = new Intent(Login.this, Login.class);
                            startActivity(Login);
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void loginuser()
    {
        final ProgressDialog progress = new ProgressDialog(Login.this);
        progress.setMessage("Please wait....");
        progress.show();
        final String EMAIL = email.getText().toString();
        final String PASSWORD = password.getText().toString();

        mAuth.signInWithEmailAndPassword(EMAIL,PASSWORD)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            progress.dismiss();
                            Intent Homepage = new Intent(Login.this,Homepage.class);
                            startActivity(Homepage);
                            Toast.makeText(Login.this, "User Login Successful", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Intent Register = new Intent(Login.this, Register.class);
                            startActivity(Register);
                        }
                    }
                });

    }

}

