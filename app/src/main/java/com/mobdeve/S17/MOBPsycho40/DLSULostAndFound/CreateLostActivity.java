package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.ActivityCreateLostBinding;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.Category;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.LostItem;

import java.util.Calendar;

public class CreateLostActivity extends AppCompatActivity {

    DatabaseReference databaseLostItems;

    EditText input_title, input_location, input_description;
    Spinner spinner_campus, spinner_category;
    TextView input_date;

    private SharedPreferences sharedPreferences;


    private ActivityCreateLostBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        databaseLostItems = FirebaseDatabase.getInstance().getReference("lostItems");

        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        binding = ActivityCreateLostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // getting views
        input_title = binding.inputTitle;
        input_location = binding.inputLocation;
        input_description = binding.inputDescription;
        spinner_campus = binding.spinnerCampus;
        spinner_category = binding.spinnerCategory;
        input_date = binding.inputDate;

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnAddLostItem.setOnClickListener(v -> {
            addLostItem();
        });

        setupDropdowns();
        setupDatePicker();
    }

    protected void addLostItem(){
        String title = input_title.getText().toString().trim();
        String location = input_location.getText().toString().trim();
        String description = input_description.getText().toString().trim();
        String campus = spinner_campus.getSelectedItem().toString();
        String categoryStr = spinner_category.getSelectedItem().toString();
        String dateStr = input_date.getText().toString().trim();
        String userID = sharedPreferences.getString("userID", "");

        Category category;
        try {
            category = Category.valueOf(categoryStr);
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, "Invalid category selected.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate input fields

        if (title.isEmpty()) {
            input_title.setError("Title is required");
            input_title.requestFocus();
            return;
        }
        if (location.isEmpty()) {
            input_location.setError("Location is required");
            input_location.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            input_description.setError("Description is required");
            input_description.requestFocus();
            return;
        }

        if (dateStr.isEmpty()) {
            input_date.setError("Date is required");
            input_date.requestFocus();
            return;
        }

        //Add the found item
        String id = databaseLostItems.push().getKey();
        LostItem lostItem = new LostItem(id, title, category, description, campus, location, 0, dateStr, userID);

        databaseLostItems.child(id).setValue(lostItem);

        input_title.setText("");
        input_location.setText("");
        input_description.setText("");
        input_date.setText("");
        Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();
        finish();

    };

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
    }

    private void setupDatePicker() {
        TextView inputDate = binding.inputDate;
        inputDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    R.style.CustomDatePickerDialog,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Format the date as MM/dd/yyyy
                        String date = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                        inputDate.setText(date);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });
    }

}