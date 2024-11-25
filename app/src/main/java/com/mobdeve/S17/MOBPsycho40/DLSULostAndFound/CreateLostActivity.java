package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.ActivityCreateLostBinding;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.Category;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.LostItem;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class CreateLostActivity extends AppCompatActivity {

    DatabaseReference databaseLostItems;

    EditText input_title, input_location, input_description;
    Spinner spinner_campus, spinner_category;
    TextView input_date;

    private SharedPreferences sharedPreferences;

    private ActivityCreateLostBinding binding;

    private String encodedImage = "";

    private final ActivityResultCallback<Uri> getContent = uri -> {
        if (uri != null) {
            try {
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                binding.createLostItemImage.setBackground(null);
                binding.createLostItemImage.setImageBitmap(selectedImage);

                encodedImage = encodeImageToBase64(selectedImage, 3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private final ActivityResultLauncher<String> pickImage =
            registerForActivityResult(new ActivityResultContracts.GetContent(), getContent);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        databaseLostItems = FirebaseDatabase.getInstance().getReference("lostItems");

        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        binding = ActivityCreateLostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        binding.createLostItemImage.setOnClickListener(v -> {
            pickImage.launch("image/*");
        });
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
        LostItem lostItem = new LostItem(id, title, category, description, campus, location, null, dateStr, userID);

        if (!encodedImage.isEmpty()) {
            lostItem.setImage(encodedImage);
        }

        databaseLostItems.child(id).setValue(lostItem);

        input_title.setText("");
        input_location.setText("");
        input_description.setText("");
        input_date.setText("");
        encodedImage = "";

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
        Calendar calendar = Calendar.getInstance();

        // Initialize the TextView with today's date
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Format the current date and set it to the TextView
        String currentDate = (month + 1) + "/" + day + "/" + year;
        binding.inputDate.setText(currentDate);

        binding.inputDate.setOnClickListener(v -> {
            // Open the DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    R.style.CustomDatePickerDialog,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Format the selected date as MM/dd/yyyy and update the TextView
                        String selectedDate = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                        binding.inputDate.setText(selectedDate);
                    },
                    year, month, day // Use the current date as the default date
            );
            datePickerDialog.show();
        });
    }

    private String encodeImageToBase64(Bitmap image, int maxFileSizeKB) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int quality = 100; // Start with max quality
        int sizeX = image.getWidth();
        int sizeY = image.getHeight();
        image.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);

        // Compress until the file size is under the max limit
        while (byteArrayOutputStream.toByteArray().length / 1024 > maxFileSizeKB && quality > 10) {
            byteArrayOutputStream.reset(); // Clear the stream
            quality -= 10; // Reduce quality by 10%
            sizeX *= 0.9; // Reduce size by 10%
            sizeY *= 0.9; // Reduce size by 10%
            image = Bitmap.createScaledBitmap(image, sizeX, sizeY, true);
            image.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        }

        // Convert to Base64 string
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}