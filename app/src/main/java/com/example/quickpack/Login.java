package com.example.quickpack;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    TextInputEditText loginEmail, loginPass;
    Button btnLogin;
    private Button btnGoogleLogin;
    TextView clickRegister;
    TextView loginText;

    private FirebaseAuth auth;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private static final int REQ_ONE_TAP = 100;

    // Check if user is signed in (non-null) and update UI accordingly.
     @Override
    public void onStart() {
       super.onStart();

        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
           startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.login_email);
        loginPass = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.btn_login);
        clickRegister=findViewById(R.id.register_text);


        getSupportActionBar().hide();


        clickRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emil = String.valueOf(loginEmail.getText());
                String pass = String.valueOf(loginPass.getText());
                if (emil.isEmpty()) {
                    loginEmail.setError("Email cannot be empty");
                }
                if (pass.isEmpty()) {
                    loginPass.setError("Password cannot be empty");
                } else {
                    auth.signInWithEmailAndPassword(emil, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Login Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }



}