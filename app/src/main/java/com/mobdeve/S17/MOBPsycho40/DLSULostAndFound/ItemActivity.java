package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class ItemActivity extends AppCompatActivity {
    TextView itemName, itemCategory, itemLocation, itemDate, itemDescription, itemCampus, itemStatus;
    ImageView itemImage;

    Button btn_claim_item, btn_update_item;

    CardView itemStatusCard;

    private SharedPreferences sharedPreferences;

    private final ActivityResultLauncher<Intent> updateItemLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        itemName.setText(data.getStringExtra("name"));
                        itemCategory.setText(data.getStringExtra("category").replace("_", " "));
                        itemStatus.setText(data.getStringExtra("status"));
                        itemCampus.setText(data.getStringExtra("campus"));
                        itemLocation.setText(data.getStringExtra("location"));
                        itemDate.setText(data.getStringExtra("date"));
                        itemDescription.setText(data.getStringExtra("description"));
                        updateStatusCard(data.getStringExtra("status"));
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
        btn_claim_item = findViewById(R.id.btn_claim_item);
        btn_update_item = findViewById(R.id.btn_update_item);

        if (!sharedPreferences.getBoolean("isLoggedIn", false)){
            btn_update_item.setVisibility(Button.GONE);
            btn_claim_item.setVisibility(Button.GONE);
        }

        Intent i = getIntent();
        String itemID = i.getStringExtra("id");
        itemImage.setImageResource(i.getIntExtra("image",0));
        itemName.setText(i.getStringExtra("name"));
        itemCategory.setText(i.getStringExtra("category"));
        itemStatus.setText(i.getStringExtra("status"));
        itemCampus.setText(i.getStringExtra("campus"));
        itemLocation.setText(i.getStringExtra("location"));
        itemDate.setText(i.getStringExtra("date"));
        itemDescription.setText(i.getStringExtra("description"));

        if (itemStatus.getText().toString().equals("Lost")){
            itemStatusCard.setCardBackgroundColor(getResources().getColor(R.color.red_200));
            itemStatus.setTextColor(getResources().getColor(R.color.red_700));
            btn_claim_item.setVisibility(Button.GONE);
        }
        else if (itemStatus.getText().toString().equals("Found")){
            itemStatusCard.setCardBackgroundColor(getResources().getColor(R.color.green_200));
            itemStatus.setTextColor(getResources().getColor(R.color.green_700));

            if (!sharedPreferences.getBoolean("isAdmin", false)) {
                btn_update_item.setVisibility(Button.GONE);
            }else{
                btn_claim_item.setVisibility(Button.VISIBLE);
            }
            
        } else{
            itemStatusCard.setCardBackgroundColor(getResources().getColor(R.color.yellow_200));
            itemStatus.setTextColor(getResources().getColor(R.color.yellow_700));
            btn_claim_item.setVisibility(Button.GONE);
            //btn_update_item.setVisibility(Button.GONE);
        }

        btn_update_item.setOnClickListener(v -> {
            Intent intent = new Intent(ItemActivity.this, UpdateFoundActivity.class);
//            intent.putExtra("id", itemID);
//            intent.putExtra("image",i.getIntExtra("image",0));
//            intent.putExtra("name",i.getStringExtra("name"));
//            intent.putExtra("status",i.getStringExtra("status"));
//            intent.putExtra("category",i.getStringExtra("category"));
//            intent.putExtra("date",i.getStringExtra("date"));
//            intent.putExtra("campus", i.getStringExtra("campus"));
//            intent.putExtra("location",i.getStringExtra("location"));
//            intent.putExtra("description", i.getStringExtra("description"));

            String category = itemCategory.getText().toString();
            if (category != null) {
                category = category.replace(" ", "_");
            }

            intent.putExtra("id", itemID);
            intent.putExtra("image", i.getIntExtra("image", 0));
            intent.putExtra("name", itemName.getText().toString());
            intent.putExtra("status", itemStatus.getText().toString());
            intent.putExtra("category", category);
            intent.putExtra("date", itemDate.getText().toString());
            intent.putExtra("campus", itemCampus.getText().toString());
            intent.putExtra("location", itemLocation.getText().toString());
            intent.putExtra("description", itemDescription.getText().toString());
            updateItemLauncher.launch(intent);
        });
    }

    private void updateStatusCard(String status) {
        if ("Lost".equals(status)) {
            itemStatusCard.setCardBackgroundColor(getResources().getColor(R.color.red_200));
            itemStatus.setTextColor(getResources().getColor(R.color.red_700));
            btn_claim_item.setVisibility(Button.GONE);
        } else if ("Found".equals(status)) {
            itemStatusCard.setCardBackgroundColor(getResources().getColor(R.color.green_200));
            itemStatus.setTextColor(getResources().getColor(R.color.green_700));
            if (!sharedPreferences.getBoolean("isAdmin", false)) {
                btn_update_item.setVisibility(Button.GONE);
            } else {
                btn_claim_item.setVisibility(Button.VISIBLE);
            }
        } else {
            itemStatusCard.setCardBackgroundColor(getResources().getColor(R.color.yellow_200));
            itemStatus.setTextColor(getResources().getColor(R.color.yellow_700));
            btn_claim_item.setVisibility(Button.GONE);
        }
    }

}