package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Items;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ItemActivity;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.R;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.ItemStatus;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.LostItem;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private ArrayList<LostItem> myLostItemList;
    Context context;

    public ItemsAdapter(ArrayList<LostItem> lostItemList, FragmentActivity activity) {
        this.myLostItemList = new ArrayList<>(lostItemList);
        this.context = activity;
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_my_item_lost_item,parent,false);
        ItemsAdapter.ViewHolder viewHolder = new ItemsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
        final LostItem currentItem = myLostItemList.get(position);

        holder.lostItemImage.setImageResource(0);
        holder.lostItemName.setText(currentItem.getName());
        holder.lostItemDate.setText(currentItem.getDateLost());
        holder.lostItemLocation.setText(currentItem.getLocation());
        holder.itemStatus.setText(currentItem.getStatus().getString());

        String imageString = currentItem.getImage();
        if (imageString != null && !imageString.isEmpty()) {
            try {
                // Decode the base64 string to a byte array
                byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);

                // Decode the byte array to a Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                // Set the Bitmap to the ImageView
                holder.lostItemImage.setImageBitmap(bitmap);
            } catch (IllegalArgumentException e) {
                // Handle error if base64 decoding fails
                e.printStackTrace();
                holder.lostItemImage.setImageResource(0);
            }
        } else {
            holder.lostItemImage.setImageResource(0);
        }

        if (currentItem.getStatus() == ItemStatus.Lost) {
            holder.itemStatusCard.setCardBackgroundColor(context.getResources().getColor(R.color.red_200));
            holder.itemStatus.setTextColor(context.getResources().getColor(R.color.red_700));
        } else {
            holder.itemStatusCard.setCardBackgroundColor(context.getResources().getColor(R.color.yellow_200));
            holder.itemStatus.setTextColor(context.getResources().getColor(R.color.yellow_700));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Display " + currentItem.getName() + " information", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, ItemActivity.class);
                i.putExtra("id",currentItem.getId());
                i.putExtra("image",currentItem.getImage());
                i.putExtra("name",currentItem.getName());
                i.putExtra("status",currentItem.getStatus().getString());
                i.putExtra("category",currentItem.getCategory().getString());
                i.putExtra("date",currentItem.getDateLost());
                i.putExtra("campus", currentItem.getCampus());
                i.putExtra("location",currentItem.getLocation());
                i.putExtra("description", currentItem.getDescription());
                i.putExtra("userID", currentItem.getUserID());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myLostItemList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView lostItemImage;
        TextView lostItemName;
        TextView lostItemDate;
        TextView lostItemLocation;
        TextView itemStatus;
        CardView itemStatusCard;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lostItemImage = itemView.findViewById(R.id.lostItemImage);
            lostItemName = itemView.findViewById(R.id.lostItemName);
            lostItemDate = itemView.findViewById(R.id.lostItemDate);
            lostItemLocation = itemView.findViewById(R.id.lostItemLocation);
            itemStatus = itemView.findViewById(R.id.itemStatus);
            itemStatusCard = itemView.findViewById(R.id.itemStatusCard);

        }
    }

    public void updateData(ArrayList<LostItem> newLostItemList) {
        this.myLostItemList.clear();
        this.myLostItemList.addAll(newLostItemList);
        notifyDataSetChanged(); // Notify RecyclerView about the data change
    }



}
