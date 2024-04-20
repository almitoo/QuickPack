package com.example.quickpack;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickpack.Data.UserItemData;
import com.example.quickpack.Utils.FirebaseUtils;
import com.example.quickpack.Models.UserCalendar;
import com.example.quickpack.Models.UserItems;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private TextInputEditText registerEmail, registerPass;
    private Button btnRegister;
    private TextView clickLogin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmail = findViewById(R.id.register_email);
        registerPass = findViewById(R.id.register_password);
        btnRegister = findViewById(R.id.btn_register);
        clickLogin=findViewById(R.id.login_now);

        getSupportActionBar().hide();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emil = String.valueOf(registerEmail.getText());
                String pass = String.valueOf(registerPass.getText());
                if (emil.isEmpty()) {
                    registerEmail.setError("Email cannot be empty");
                }
                if (pass.length() < FirebaseUtils.MIN_PASSWORD_LENGTH) {
                    registerPass.setError("Password length must be at least " + FirebaseUtils.MIN_PASSWORD_LENGTH);
                } else {
                    FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(emil, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                              UserItems userItems = UserItemData.getDefaultUserItems(FirebaseAuth.getInstance().getUid());

                              UserCalendar userCalendar = new UserCalendar(FirebaseAuth.getInstance().getUid(),
                                        null);
                                Map<String, Object> map = new HashMap<>();
                                map.put(FirebaseUtils.USER_ITEMS + "/" + FirebaseAuth.getInstance().getUid(),
                                        userItems);
                                map.put(FirebaseUtils.USER_CALENDAR + "/" + FirebaseAuth.getInstance().getUid(),
                                        userCalendar);

                                FirebaseDatabase.getInstance().getReference()
                                        .updateChildren(map, new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                if(error == null) {
                                                    Toast.makeText(Register.this, "Register Successful",
                                                            Toast.LENGTH_SHORT).show();

                                                } else {
                                                    FirebaseAuth.getInstance().getCurrentUser().delete();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(Register.this, "Register Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        clickLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });
    }




}