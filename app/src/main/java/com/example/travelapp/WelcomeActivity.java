package com.example.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    private Button btnLogin, btnConnectFacebook, btnConnectGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnLogin = findViewById(R.id.btnLogin);
        btnConnectFacebook = findViewById(R.id.btnConnectFacebook);
        btnConnectGoogle = findViewById(R.id.btnConnectGoogle);

        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnConnectFacebook.setOnClickListener(view -> {
            // Implement Facebook login logic here
        });

        btnConnectGoogle.setOnClickListener(view -> {
            // Implement Google login logic here
        });
    }
}
