package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.ActivityLoginFormBinding;

public class LoginFormActivity extends AppCompatActivity {

    private ActivityLoginFormBinding binding;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        binding = ActivityLoginFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnLogin.setOnClickListener(v -> {
            binding.btnLogin.setEnabled(false);
            binding.btnLogin.setText("Signing in...");

            String email = binding.inputEmail.getText().toString();
            String password = binding.inputPassword.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginFormActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                resetLoginButton();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        resetLoginButton();

                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null) {
                                String userId = user.getUid();

                                reference = FirebaseDatabase.getInstance()
                                        .getReference("users")
                                        .child(userId);

                                reference.get().addOnCompleteListener(userTask -> {
                                    if (userTask.isSuccessful() && userTask.getResult().exists()) {
                                        String idNumber = userTask.getResult().child("idNumber").getValue(String.class);
                                        String firstName = userTask.getResult().child("firstName").getValue(String.class);
                                        String lastName = userTask.getResult().child("lastName").getValue(String.class);
                                        String email = userTask.getResult().child("email").getValue(String.class);

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("isLoggedIn", true);
                                        editor.putString("idNumber", idNumber);
                                        editor.putString("firstName", firstName);
                                        editor.putString("lastName", lastName);
                                        editor.putString("email", email);
                                        editor.apply();

                                        Toast.makeText(LoginFormActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(LoginFormActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(LoginFormActivity.this, "Failed to retrieve user data",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(LoginFormActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        });

        binding.linkRegisterForm.setOnClickListener(v -> {
            Intent registerFormIntent = new Intent(LoginFormActivity.this, RegisterFormActivity.class);
            startActivity(registerFormIntent);
            finish();
        });
    }

    protected void resetLoginButton() {
        binding.btnLogin.setEnabled(true);
        binding.btnLogin.setText("Login");
    }
}