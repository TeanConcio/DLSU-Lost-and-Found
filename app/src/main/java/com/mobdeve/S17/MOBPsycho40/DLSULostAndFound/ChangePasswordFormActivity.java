package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.ActivityChangePasswordFormBinding;

public class ChangePasswordFormActivity extends AppCompatActivity {

    private ActivityChangePasswordFormBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityChangePasswordFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnChangePassword.setOnClickListener(v -> {
            if(handleChangePassword()) {
                finish();
            }
        });
    }

    private boolean handleChangePassword() {
        String oldPassword = binding.inputOldPassword.getText().toString();
        String newPassword = binding.inputNewPassword.getText().toString();
        String confirmPassword = binding.inputConfirmPassword.getText().toString();

        // Add your password change logic here (validation, update password)
        return true;
    }
}