package com.example.twig;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
     EditText mName,mEmail,mPassword,mPhone;
     Button mRegister;
     TextView mLogin1;
     ProgressBar progressBar;

     private final BroadcastReceiver onRegisterReceiver = new BroadcastReceiver() {
         @Override
         public void onReceive(Context context, Intent intent) {
             boolean success = intent.getBooleanExtra("success", false);
             progressBar.setVisibility(View.GONE);

             if (success) {
                 Toast.makeText(context, "Successfully Registered", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(getApplicationContext(),MainActivity.class));
                 finish();
             } else
                 Toast.makeText(context,"Error: " + intent.getStringExtra("error"),Toast.LENGTH_SHORT).show();
         }
     };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        IntentFilter filter = new IntentFilter();
        filter.addAction(TwigConstants.REGISTER_RECEIVER);
        registerReceiver(onRegisterReceiver, filter);

        mName = findViewById(R.id.Name);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mPhone=findViewById(R.id.Phone);
        mRegister=findViewById(R.id.Register);
        mLogin1=findViewById(R.id.Login1);
        progressBar =findViewById(R.id.progressBar);

        mRegister.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name =mName.getText().toString().trim();
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

                User.registerUser(email, password, name, getApplicationContext());
            }
        });


        mLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onRegisterReceiver);
    }
}