package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.ActivityUpdateFoundBinding;

import java.util.Calendar;

public class UpdateFoundActivity extends AppCompatActivity {

    private ActivityUpdateFoundBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityUpdateFoundBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i = getIntent();
        binding.inputTitle.setText(i.getStringExtra("name"));
        binding.inputLocation.setText(i.getStringExtra("location"));
        binding.inputDescription.setText(i.getStringExtra("description"));
        binding.inputDate.setText(i.getStringExtra("date"));
        //fix next time for status, category, and campus

        binding.btnUpdateFoundItem.setOnClickListener(v -> {
            finish();
        });

        setupDropdowns();
        setupDatePicker();
    }

    private void setupDropdowns() {
        // Sample data for dropdowns
        Spinner campusSpinner = binding.spinnerCampus;
        ArrayAdapter<CharSequence> adapterCampus = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.campus,
                android.R.layout.simple_spinner_item
        );
        adapterCampus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campusSpinner.setAdapter(adapterCampus);

        Spinner categorySpinner = binding.spinnerCategory;
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.category,
                android.R.layout.simple_spinner_item
        );
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapterCategory);

        Spinner statusSpinner = binding.spinnerStatus;
        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.status,
                android.R.layout.simple_spinner_item
        );
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapterStatus);
    }

    private void setupDatePicker() {
        TextView inputDate = binding.inputDate;
        inputDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getApplicationContext(),
                    R.style.CustomDatePickerDialog,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        inputDate.setText(date);
                    },
                    year, month, day);

            datePickerDialog.show();
        });
    }
}