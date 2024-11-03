package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.ActivityChangePasswordFormBinding;

public class ChangePasswordFormActivity extends AppCompatActivity {

    private ActivityChangePasswordFormBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityChangePasswordFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnChangePassword.setOnClickListener(v -> {
            binding.btnChangePassword.setEnabled(false);
            binding.btnChangePassword.setText("Changing Password...");

            String oldPassword = binding.inputOldPassword.getText().toString().trim();
            String newPassword = binding.inputNewPassword.getText().toString().trim();
            String confirmPassword = binding.inputConfirmPassword.getText().toString().trim();

            if (newPassword.isEmpty() || confirmPassword.isEmpty() || oldPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if(newPassword.length() < 8) {
                Toast.makeText(ChangePasswordFormActivity.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                resetChangePasswordButton();
                return;
            }

            if(!newPassword.matches(".*[a-z].*") || !newPassword.matches(".*[A-Z].*")) {
                Toast.makeText(ChangePasswordFormActivity.this, "Password must contain uppercase and lowercase letters", Toast.LENGTH_SHORT).show();
                resetChangePasswordButton();
                return;
            }

            if(!newPassword.matches(".*[0-9].*") || !newPassword.matches(".*[!@#$%^&*.,].*")) {
                Toast.makeText(ChangePasswordFormActivity.this, "Password must contain a number and a special character", Toast.LENGTH_SHORT).show();
                resetChangePasswordButton();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            if (currentUser != null && currentUser.getEmail() != null) {
                AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), oldPassword);

                // Re-authenticate the user
                currentUser.reauthenticate(credential).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Update the password
                        currentUser.updatePassword(newPassword).addOnCompleteListener(updateTask -> {
                            if (updateTask.isSuccessful()) {
                                Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(this, "Failed to change password. Try again later.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(this, "Old password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "User not authenticated. Please log in again.", Toast.LENGTH_SHORT).show();
            }

            resetChangePasswordButton();
        });
    }

    protected void resetChangePasswordButton() {
        binding.btnChangePassword.setEnabled(true);
        binding.btnChangePassword.setText("Change Password");
    }
}