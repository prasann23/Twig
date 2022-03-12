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

public class LoginActivity extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLogin2;
    TextView mCreate;
    ProgressBar progressBar;

    private final BroadcastReceiver onLoginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean success = intent.getBooleanExtra("success", false);
            progressBar.setVisibility(View.GONE);

            if(success){
                Toast.makeText(context,"Logged in",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
            else
                Toast.makeText(context, "Error: " + intent.getStringExtra("error"), Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(User.isUserLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(TwigConstants.LOGIN_RECEIVER);
        registerReceiver(onLoginReceiver, filter);

        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        progressBar=findViewById(R.id.progressBar2);
        mLogin2=findViewById(R.id.login2);
        mCreate=findViewById(R.id.create);

        mLogin2.setOnClickListener(new View.OnClickListener() {
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
                    mPassword.setError("Must be long then 8 letter");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                User.loginUser(email, password, getApplicationContext());
            }
        });
        mCreate.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onLoginReceiver);
    }
}