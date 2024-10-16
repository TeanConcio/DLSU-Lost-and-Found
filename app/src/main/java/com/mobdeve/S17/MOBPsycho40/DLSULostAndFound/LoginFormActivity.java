package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.ActivityLoginFormBinding;

public class LoginFormActivity extends AppCompatActivity {

    private ActivityLoginFormBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        binding = ActivityLoginFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnLogin.setOnClickListener(v -> {
            String idNumber = binding.inputIdNumber.getText().toString();
            String password = binding.inputPassword.getText().toString();

            // Replace this with actual authentication logic
            if (isValidCredentials(idNumber, password)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.putString("idNumber", idNumber);
                editor.putString("firstName", "Some");
                editor.putString("lastName", "User");
                editor.apply();

                Intent intent = new Intent(LoginFormActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginFormActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle register link
        binding.linkRegisterForm.setOnClickListener(v -> {
            Intent registerFormIntent = new Intent(LoginFormActivity.this, RegisterFormActivity.class);
            startActivity(registerFormIntent);
            finish();
        });
    }

    // Placeholder for actual authentication logic
    private boolean isValidCredentials(String idNumber, String password) {
        // Replace with actual authentication check
        return true;
    }
}