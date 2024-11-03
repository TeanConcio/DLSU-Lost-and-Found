package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.ActivityEditProfileBinding;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            reference = FirebaseDatabase.getInstance().getReference("users").child(userId);
        }

        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnBack.setOnClickListener(v -> finish());

        // Set initial values from intent
        Intent intent = getIntent();
        String idNumber = intent.getStringExtra("idNumber");
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String email = intent.getStringExtra("email");

        binding.inputIdNumber.setText(idNumber);
        binding.inputFirstName.setText(firstName);
        binding.inputLastName.setText(lastName);
        binding.inputEmail.setText(email);

        // Make ID number and email field disabled and visually indicate it
        binding.inputIdNumber.setEnabled(false);
        binding.inputIdNumber.setAlpha(0.6f);
        binding.inputEmail.setEnabled(false);
        binding.inputEmail.setAlpha(0.6f);

        binding.btnSave.setOnClickListener(v -> saveProfileChanges());
    }

    private void saveProfileChanges() {
        binding.btnSave.setEnabled(false);
        binding.btnSave.setText("Saving Changes...");

        String newFirstName = binding.inputFirstName.getText().toString().trim();
        String newLastName = binding.inputLastName.getText().toString().trim();

        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();

            // Update user details in Realtime Database
            reference.child("firstName").setValue(newFirstName);
            reference.child("lastName").setValue(newLastName)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Update SharedPreferences with the new values
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("firstName", newFirstName);
                        editor.putString("lastName", newLastName);
                        editor.apply();

                        Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                });
        }

        resetSaveButton();
    }

    private void resetSaveButton() {
        binding.btnSave.setEnabled(true);
        binding.btnSave.setText("Save Changes");
    }
}