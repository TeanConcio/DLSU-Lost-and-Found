package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.ActivityUpdateLostBinding;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.Category;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.ItemStatus;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.LostItem;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class UpdateLostActivity extends AppCompatActivity {

    private ActivityUpdateLostBinding binding;

    private String encodedImage = "";

    private final ActivityResultCallback<Uri> getContent = uri -> {
        if (uri != null) {
            try {
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                binding.updateLostItemImage.setBackground(null);
                binding.updateLostItemImage.setImageBitmap(selectedImage);

                encodedImage = encodeImageToBase64(selectedImage);
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

        binding = ActivityUpdateLostBinding.inflate(getLayoutInflater());
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

        String status = i.getStringExtra("status");
        String category = i.getStringExtra("category");
        String campus = i.getStringExtra("campus");

        setupDropdowns(status, category, campus);
        setupDatePicker();

        encodedImage = i.getStringExtra("image");
        if (encodedImage != null && !encodedImage.isEmpty()) {
            try {
                // Decode the base64 string to a byte array
                byte[] imageBytes = Base64.decode(encodedImage, Base64.DEFAULT);

                // Decode the byte array to a Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                // Set the Bitmap to the ImageView
                binding.updateLostItemImage.setImageBitmap(bitmap);
            } catch (IllegalArgumentException e) {
                // Handle error if base64 decoding fails
                e.printStackTrace();
                binding.updateLostItemImage.setImageResource(0);
            }
        } else {
            binding.updateLostItemImage.setImageResource(0);
        }

        binding.updateLostItemImage.setOnClickListener(v -> {
            pickImage.launch("image/*");
        });

        binding.btnUpdateLostItem.setOnClickListener(v -> {
            updateLostItem();
        });
    }

    private void updateLostItem() {
        String id = getIntent().getStringExtra("id");
        String title = binding.inputTitle.getText().toString();
        String location = binding.inputLocation.getText().toString();
        String description = binding.inputDescription.getText().toString();
        String date = binding.inputDate.getText().toString();
        String statusStr =  getIntent().getStringExtra("status");
        String categoryStr = binding.spinnerCategory.getSelectedItem().toString();
        String campus = binding.spinnerCampus.getSelectedItem().toString();
        String userID = getIntent().getStringExtra("userID");

        Category category;
        try {
            category = Category.valueOf(categoryStr);
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, "Invalid category selected.", Toast.LENGTH_SHORT).show();
            return;
        }

        ItemStatus status;
        try {
            status = ItemStatus.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, "Invalid status selected.", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("lostItems").child(id);

        LostItem lostItem = new LostItem(id, title, category, description, campus, location, null, date, userID);

        if (!encodedImage.isEmpty()) {
            lostItem.setImage(encodedImage);
        }

        dR.setValue(lostItem);
        Toast.makeText(this, "Lost item updated", Toast.LENGTH_LONG).show();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("name", title);
        resultIntent.putExtra("location", location);
        resultIntent.putExtra("description", description);
        resultIntent.putExtra("date", date);
        resultIntent.putExtra("status", statusStr);
        resultIntent.putExtra("category", categoryStr);
        resultIntent.putExtra("campus", campus);
        resultIntent.putExtra("image", encodedImage);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void setupDropdowns(String status, String category, String campus) {
        // Setup campus spinner
        Spinner campusSpinner = binding.spinnerCampus;
        ArrayAdapter<CharSequence> adapterCampus = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.campus,
                android.R.layout.simple_spinner_item
        );
        adapterCampus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campusSpinner.setAdapter(adapterCampus);
        // Set the selection based on the campus value
        int campusPosition = adapterCampus.getPosition(campus);
        if (campusPosition >= 0) {
            campusSpinner.setSelection(campusPosition);
        }

        // Setup category spinner
        Spinner categorySpinner = binding.spinnerCategory;
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.category,
                android.R.layout.simple_spinner_item
        );
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapterCategory);
        // Set the selection based on the category value
        int categoryPosition = adapterCategory.getPosition(category);
        if (categoryPosition >= 0) {
            categorySpinner.setSelection(categoryPosition);
        }

        // Setup status spinner
//        Spinner statusSpinner = binding.spinnerStatus;
//        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(
//                getApplicationContext(),
//                R.array.statusLost, // Updated to reference a different array for lost items
//                android.R.layout.simple_spinner_item
//        );
//        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        statusSpinner.setAdapter(adapterStatus);
//        // Set the selection based on the status value
//        int statusPosition = adapterStatus.getPosition(status);
//        if (statusPosition >= 0) {
//            statusSpinner.setSelection(statusPosition);
//        }
    }

    private void setupDatePicker() {
        TextView inputDate = binding.inputDate;
        inputDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            // Check if the date string is not empty
            String dateStr = inputDate.getText().toString();
            if (!dateStr.isEmpty()) {
                // Parse the date string (assumed format: MM/dd/yyyy)
                String[] dateParts = dateStr.split("/");
                int month = Integer.parseInt(dateParts[0]) - 1; // Month is 0-indexed
                int day = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);

                // Set the calendar to the parsed date
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
            }

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

    private String encodeImageToBase64(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
