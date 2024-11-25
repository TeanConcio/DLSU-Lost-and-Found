package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItemActivity extends AppCompatActivity {
    TextView itemName, itemCategory, itemLocation, itemDate, itemDescription, itemCampus, itemStatus;
    ImageView itemImage;

    Button btn_delete_item, btn_update_item;

    CardView itemStatusCard;

    private SharedPreferences sharedPreferences;

    private final ActivityResultLauncher<Intent> updateItemLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        itemName.setText(data.getStringExtra("name"));
                        itemCategory.setText(data.getStringExtra("category"));
                        itemStatus.setText(data.getStringExtra("status"));
                        itemCampus.setText(data.getStringExtra("campus"));
                        itemLocation.setText(data.getStringExtra("location"));
                        itemDate.setText(data.getStringExtra("date"));
                        itemDescription.setText(data.getStringExtra("description"));
                        updateStatusCard(data.getStringExtra("status"));

                        String imageString = data.getStringExtra("image");
                        if (imageString != null && !imageString.isEmpty()) {
                            try {
                                // Decode the base64 string to a byte array
                                byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);

                                // Decode the byte array to a Bitmap
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                                // Set the Bitmap to the ImageView
                                itemImage.setImageBitmap(bitmap);
                            } catch (IllegalArgumentException e) {
                                // Handle error if base64 decoding fails
                                e.printStackTrace();
                                itemImage.setImageResource(0);
                            }
                        } else {
                            itemImage.setImageResource(0);
                        }
                    }
                }
            });

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

        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        itemImage = findViewById(R.id.itemImage);
        itemName = findViewById(R.id.itemName);
        itemCategory = findViewById(R.id.itemCategory);
        itemStatus = findViewById(R.id.itemStatus);
        itemLocation = findViewById(R.id.itemLocation);
        itemDate = findViewById(R.id.itemDate);
        itemDescription = findViewById(R.id.itemDescription);
        itemCampus = findViewById(R.id.itemCampus);
        itemStatusCard = findViewById(R.id.itemStatusCard);
        btn_delete_item = findViewById(R.id.btn_delete_item);
        btn_update_item = findViewById(R.id.btn_update_item);

        if (!sharedPreferences.getBoolean("isLoggedIn", false)){
            btn_update_item.setVisibility(Button.GONE);
            btn_delete_item.setVisibility(Button.GONE);
        }

        Intent i = getIntent();
        String itemID = i.getStringExtra("id");
        itemName.setText(i.getStringExtra("name"));
        itemCategory.setText(i.getStringExtra("category"));
        itemStatus.setText(i.getStringExtra("status"));
        itemCampus.setText(i.getStringExtra("campus"));
        itemLocation.setText(i.getStringExtra("location"));
        itemDate.setText(i.getStringExtra("date"));
        itemDescription.setText(i.getStringExtra("description"));

        String imageString = i.getStringExtra("image");
        if (imageString != null && !imageString.isEmpty()) {
            try {
                // Decode the base64 string to a byte array
                byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);

                // Decode the byte array to a Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                // Set the Bitmap to the ImageView
                itemImage.setImageBitmap(bitmap);
            } catch (IllegalArgumentException e) {
                // Handle error if base64 decoding fails
                e.printStackTrace();
                itemImage.setImageResource(0);
            }
        } else {
            itemImage.setImageResource(0);
        }


        //LOST ITEMS
        if (itemStatus.getText().toString().equals("Lost")){
            itemStatusCard.setCardBackgroundColor(getResources().getColor(R.color.red_200));
            itemStatus.setTextColor(getResources().getColor(R.color.red_700));

         // if userid in item is not equal to user id in shared preferences, hide update and delete buttons
            if (sharedPreferences.getString("userID", "").equals(i.getStringExtra("userID"))) {
                btn_update_item.setVisibility(Button.VISIBLE);
                btn_delete_item.setVisibility(Button.VISIBLE);
            } else {
                btn_update_item.setVisibility(Button.GONE);
                btn_delete_item.setVisibility(Button.GONE);
            }

            btn_update_item.setOnClickListener(v -> {
                Intent intent = new Intent(ItemActivity.this, UpdateLostActivity.class);
                intent.putExtra("id", itemID);
                intent.putExtra("image", imageString);
                intent.putExtra("name", itemName.getText().toString());
                intent.putExtra("status", itemStatus.getText().toString());
                intent.putExtra("category", itemCategory.getText().toString());
                intent.putExtra("date", itemDate.getText().toString());
                intent.putExtra("campus", itemCampus.getText().toString());
                intent.putExtra("location", itemLocation.getText().toString());
                intent.putExtra("description", itemDescription.getText().toString());
                intent.putExtra ("userID", i.getStringExtra("userID"));
                updateItemLauncher.launch(intent);
            });

            btn_delete_item.setOnClickListener(v -> {
                if (itemID != null) {
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("lostItems").child(itemID);
                    dR.removeValue();
                    Toast.makeText(getApplicationContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
        //FOUND ITEMS
        else if (itemStatus.getText().toString().equals("Found")){
            itemStatusCard.setCardBackgroundColor(getResources().getColor(R.color.green_200));
            itemStatus.setTextColor(getResources().getColor(R.color.green_700));

            if (!sharedPreferences.getBoolean("isAdmin", false)) {
                btn_update_item.setVisibility(Button.GONE);
                btn_delete_item.setVisibility(Button.GONE);
            }

            btn_update_item.setOnClickListener(v -> {
                Intent intent = new Intent(ItemActivity.this, UpdateFoundActivity.class);
                intent.putExtra("id", itemID);
                intent.putExtra("image", i.getIntExtra("image", 0));
                intent.putExtra("name", itemName.getText().toString());
                intent.putExtra("status", itemStatus.getText().toString());
                intent.putExtra("category", itemCategory.getText().toString());
                intent.putExtra("date", itemDate.getText().toString());
                intent.putExtra("campus", itemCampus.getText().toString());
                intent.putExtra("location", itemLocation.getText().toString());
                intent.putExtra("description", itemDescription.getText().toString());
                updateItemLauncher.launch(intent);
            });

            btn_delete_item.setOnClickListener(v -> {
                if (itemID != null) {
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("foundItems").child(itemID);
                    dR.removeValue();
                    Toast.makeText(getApplicationContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            
        } else{
            itemStatusCard.setCardBackgroundColor(getResources().getColor(R.color.yellow_200));
            itemStatus.setTextColor(getResources().getColor(R.color.yellow_700));
            btn_delete_item.setVisibility(Button.GONE);
            //btn_update_item.setVisibility(Button.GONE);
        }


    }

    private void updateStatusCard(String status) {
        if ("Lost".equals(status)) {
            itemStatusCard.setCardBackgroundColor(getResources().getColor(R.color.red_200));
            itemStatus.setTextColor(getResources().getColor(R.color.red_700));
            btn_delete_item.setVisibility(Button.GONE);
        } else if ("Found".equals(status)) {
            itemStatusCard.setCardBackgroundColor(getResources().getColor(R.color.green_200));
            itemStatus.setTextColor(getResources().getColor(R.color.green_700));
            if (!sharedPreferences.getBoolean("isAdmin", false)) {
                btn_update_item.setVisibility(Button.GONE);
            } else {
                btn_delete_item.setVisibility(Button.VISIBLE);
            }
        } else {
            itemStatusCard.setCardBackgroundColor(getResources().getColor(R.color.yellow_200));
            itemStatus.setTextColor(getResources().getColor(R.color.yellow_700));
            btn_delete_item.setVisibility(Button.GONE);
        }
    }

}