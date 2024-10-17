package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.ActivityEditProfileBinding;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnSave.setOnClickListener(v -> {
            if (handleSaveChanges()) {
                finish();
            }
        });

        Intent intent = getIntent();
        String idNumber = intent.getStringExtra("idNumber");
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String email = intent.getStringExtra("email");

        binding.inputIdNumber.setText(idNumber);
        binding.inputFirstName.setText(firstName);
        binding.inputLastName.setText(lastName);
        binding.inputEmail.setText(email);

        // Make ID number field disabled and visually indicate it
        binding.inputIdNumber.setEnabled(false);
        binding.inputIdNumber.setAlpha(0.6f);
    }

    private boolean handleSaveChanges() {
        String email = binding.inputEmail.getText().toString();
        String firstName = binding.inputFirstName.getText().toString();
        String lastName = binding.inputLastName.getText().toString();

        // Add your logic here to save changes (e.g., update user profile in the database)
        return true;
    }
}