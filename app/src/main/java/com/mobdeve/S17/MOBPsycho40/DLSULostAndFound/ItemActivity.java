package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ItemActivity extends AppCompatActivity {
    TextView itemName, itemCategory, itemLocation, itemDate, itemDescription, itemCampus;
    ImageView itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        itemImage = findViewById(R.id.itemImage);
        itemName = findViewById(R.id.itemName);
        itemCategory = findViewById(R.id.itemCategory);
        itemLocation = findViewById(R.id.itemLocation);
        itemDate = findViewById(R.id.itemDate);
        itemDescription = findViewById(R.id.itemDescription);
        itemCampus = findViewById(R.id.itemCampus);

        Intent i = getIntent();
        itemImage.setImageResource(i.getIntExtra("image",0));
        itemName.setText(i.getStringExtra("name"));
        itemCategory.setText(i.getStringExtra("category"));
        itemCampus.setText(i.getStringExtra("campus"));
        itemLocation.setText(i.getStringExtra("location"));
        itemDate.setText(i.getStringExtra("date"));
        itemDescription.setText(i.getStringExtra("description"));



    }
}