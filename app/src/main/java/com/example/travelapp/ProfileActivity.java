package com.example.travelapp;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private static final int REQUEST_CAMERA_PERMISSION = 3;

    private ImageView ivBackIcon, ivProfileImage;
    private EditText etUsername, etEmail, etPhone, etGender, etDob;
    private Button btnUploadImage, btnSaveProfile, btnLogout;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private String username;
    private String profileImageUrl;
    private Uri currentImageUri;
    private ActivityResultLauncher<Uri> takePictureLauncher;
    private ExecutorService executorService;

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

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        executorService = Executors.newSingleThreadExecutor();

        ivBackIcon.setOnClickListener(view -> onBackPressed());

        btnSaveProfile.setOnClickListener(view -> saveUserProfile());

        btnLogout.setOnClickListener(view -> {
            // Implement logout logic here
            logoutUser();
        });

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        if (username != null) {
            fetchUserProfile(username);
        }

        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
            if (result) {
                ivProfileImage.setImageURI(currentImageUri);
                uploadImageToFirebase(currentImageUri);
            } else {
                Log.e(TAG, "Failed to capture image");
            }
        });

        btnUploadImage.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                captureImage();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            } else {
                Toast.makeText(this, "Camera permission is required to take pictures", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetchUserProfile(String username) {
        db.collection("users").document(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    etUsername.setText(document.getString("username"));
                    etEmail.setText(document.getString("email"));
                    etPhone.setText(document.getString("phone"));
                    etGender.setText(document.getString("gender"));
                    etDob.setText(document.getString("dob"));
                    profileImageUrl = document.getString("profileImage");
                    if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                        loadProfileImage(profileImageUrl);
                    } else {
                        ivProfileImage.setImageResource(R.drawable.user);
                    }
                }
            } else {
                Log.e(TAG, "Failed to fetch user profile", task.getException());
            }
        });
    }

    private void loadProfileImage(String url) {
        executorService.execute(() -> {
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                runOnUiThread(() -> ivProfileImage.setImageBitmap(bitmap));
            } catch (Exception e) {
                Log.e(TAG, "Failed to load image", e);
            }
        });
    }

    private Uri createImageUri() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    private void captureImage() {
        Uri imageUri = createImageUri();
        if (imageUri != null) {
            currentImageUri = imageUri;
            takePictureLauncher.launch(imageUri);
        } else {
            Log.e(TAG, "Failed to create image URI");
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        if (imageUri == null) {
            Log.e(TAG, "Image URI is null");
            return;
        }

        StorageReference storageRef = storage.getReference();
        StorageReference profileImagesRef = storageRef.child("profile_images/" + username + ".jpg");

        profileImagesRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> profileImagesRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    profileImageUrl = uri.toString();
                    Log.d(TAG, "Profile image URL: " + profileImageUrl);
                    saveProfileImageUrlToFirestore(profileImageUrl);
                }))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to upload image to Firebase", e));
    }

    private void saveProfileImageUrlToFirestore(String downloadUrl) {
        db.collection("users").document(username)
                .update("profileImage", downloadUrl)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Profile image URL saved to Firestore"))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to save profile image URL to Firestore", e));
    }

    private void saveUserProfile() {
        Map<String, Object> user = new HashMap<>();
        user.put("username", etUsername.getText().toString());
        user.put("email", etEmail.getText().toString());
        user.put("phone", etPhone.getText().toString());
        user.put("gender", etGender.getText().toString());
        user.put("dob", etDob.getText().toString());
        if (profileImageUrl != null) {
            user.put("profileImage", profileImageUrl);
        }

        db.collection("users").document(username)
                .set(user)
                .addOnSuccessListener(aVoid -> Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error updating profile", e);
                    Toast.makeText(ProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                });
    }

    private void logoutUser() {
        Intent intent = new Intent(ProfileActivity.this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
