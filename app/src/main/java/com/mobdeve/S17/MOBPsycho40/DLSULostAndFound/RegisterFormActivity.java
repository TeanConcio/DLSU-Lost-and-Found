package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.ActivityLoginFormBinding;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.ActivityRegisterFormBinding;

public class RegisterFormActivity extends AppCompatActivity {

    private ActivityRegisterFormBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_form);

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
            handleRegistration();
        });

        binding.linkLoginForm.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginFormActivity.class));
            finish();
        });
    }

    private void handleRegistration() {
        // Add your registration logic here (validation, form submission)
    }
}