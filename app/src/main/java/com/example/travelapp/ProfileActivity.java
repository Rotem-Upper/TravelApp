package com.example.travelapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private ImageView ivBackIcon, ivProfileImage;
    private EditText etUsername, etEmail, etPhone, etGender, etDob;
    private Button btnUploadImage, btnSaveProfile, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivBackIcon = findViewById(R.id.ivBackIcon);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etGender = findViewById(R.id.etGender);
        etDob = findViewById(R.id.etDob);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnLogout = findViewById(R.id.btnLogout);

        ivBackIcon.setOnClickListener(view -> onBackPressed());
        btnSaveProfile.setOnClickListener(view -> {
            // Implement save profile logic here
        });
        btnLogout.setOnClickListener(view -> {
            // Implement logout logic here
        });
    }
}
