package com.example.twig;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.neatroots.samplesocial.MainActivity;

public class Register extends AppCompatActivity {
 EditText mName,mEmail,mPassword,mPhone;
 Button mRegister;
 TextView mLogin1;
 FirebaseAuth fAuth;
 ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName = findViewById(R.id.Name);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mPhone=findViewById(R.id.Phone);
        mRegister=findViewById(R.id.Register);
        mLogin1=findViewById(R.id.Login1);
        fAuth = FirebaseAuth.getInstance();
        progressBar =findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() !=null)
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("password is required");
                    return;
                }
                if(password.length()<8){
                    mPassword.setError("must be long then 8 letter");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Register.this,"Successfully Registered",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                    else
                        Toast.makeText(Register.this,"error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                });
            }
        });


        mLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}