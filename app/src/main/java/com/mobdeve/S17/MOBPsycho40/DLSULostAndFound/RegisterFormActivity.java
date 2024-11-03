package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.ActivityLoginFormBinding;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.ActivityRegisterFormBinding;

public class RegisterFormActivity extends AppCompatActivity {

    private ActivityRegisterFormBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_form);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        binding = ActivityRegisterFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Apply window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnRegister.setOnClickListener(v -> {
            reference = database.getReference("users");

            binding.btnRegister.setEnabled(false);
            binding.btnRegister.setText("Registering User...");

            String email = String.valueOf(binding.inputEmail.getText());
            String password = String.valueOf(binding.inputPassword.getText());
            String idNumber = String.valueOf(binding.inputIdNumber.getText());
            String firstName = String.valueOf(binding.inputFirstName.getText());
            String lastName = String.valueOf(binding.inputLastName.getText());

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(idNumber) || TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
                Toast.makeText(RegisterFormActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                resetRegisterButton();
                return;
            }

            if(password.length() < 8) {
                Toast.makeText(RegisterFormActivity.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                resetRegisterButton();
                return;
            }

            if(!password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*")) {
                Toast.makeText(RegisterFormActivity.this, "Password must contain uppercase and lowercase letters", Toast.LENGTH_SHORT).show();
                resetRegisterButton();
                return;
            }

            if(!password.matches(".*[0-9].*") || !password.matches(".*[!@#$%^&*.,].*")) {
                Toast.makeText(RegisterFormActivity.this, "Password must contain a number and a special character", Toast.LENGTH_SHORT).show();
                resetRegisterButton();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        resetRegisterButton();

                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null) {
                                String userId = user.getUid();
                                User userData = new User(idNumber, firstName, lastName, email);

                                reference.child(userId).setValue(userData)
                                    .addOnCompleteListener(saveTask -> {
                                        if (saveTask.isSuccessful()) {
                                            Toast.makeText(RegisterFormActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                            moveToLogin();
                                        } else {
                                            Toast.makeText(RegisterFormActivity.this, "Failed to save additional data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            }
                        } else {
                            Toast.makeText(RegisterFormActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        });

        binding.linkLoginForm.setOnClickListener(v -> {
            moveToLogin();
        });
    }

    protected void moveToLogin() {
        startActivity(new Intent(this, LoginFormActivity.class));
        finish();
    }

    protected void resetRegisterButton() {
        binding.btnRegister.setEnabled(true);
        binding.btnRegister.setText("Register");
    }

    public static class User {
        public String idNumber;
        public String firstName;
        public String lastName;
        public String email;

        public User() {}

        public User(String idNumber, String firstName, String lastName, String email) {
            this.idNumber = idNumber;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }
    }
}