package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Lost;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ItemActivity;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.R;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.Category;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.LostItem;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class LostItemAdapter extends RecyclerView.Adapter<LostItemAdapter.ViewHolder> {

    private LostItem[] lostItemList;
    private LostItem[] filteredList;
    private Context context;

    // Stored search queries
    private Category category = null;
    private String query = "";

    public LostItemAdapter(LostItem[] lostItemList, FragmentActivity activity) {
        this.lostItemList = lostItemList;
        this.filteredList = lostItemList;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_lost_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final LostItem currentItem = filteredList[position];

        holder.lostItemImage.setImageResource(currentItem.getImage());
        holder.lostItemName.setText(currentItem.getName());
        holder.lostItemDate.setText(currentItem.getDateLost());
        holder.lostItemLocation.setText(currentItem.getLocation());
        holder.lostItemDescription.setText(currentItem.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Display " + currentItem.getName() + " information", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, ItemActivity.class);
                i.putExtra("image",currentItem.getImage());
                i.putExtra("name",currentItem.getName());
                i.putExtra("status",currentItem.getStatus().getString());
                i.putExtra("category",currentItem.getCategory().getString());
                i.putExtra("date",currentItem.getDateLost());
                i.putExtra("campus", currentItem.getCampus());
                i.putExtra("location",currentItem.getLocation());
                i.putExtra("description", currentItem.getDescription());
                context.startActivity(i);
            }
        });
    }

    public void filterByCategory(Category category) {
        this.category = category;
        applyFilters();
    }

    public void filterByQuery(String query) {
        this.query = query;
        applyFilters();
    }

    private void applyFilters() {
        filteredList = Arrays.stream(lostItemList)
                .filter(item -> (this.category == null || item.getCategory() == this.category) &&
                        (this.query == null || this.query.isEmpty() || item.getName().toLowerCase().contains(this.query.toLowerCase()) ||
                                item.getDescription().toLowerCase().contains(this.query.toLowerCase())))
                .toArray(LostItem[]::new);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filteredList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView lostItemImage;
        TextView lostItemName;
        TextView lostItemDate;
        TextView lostItemLocation;
        TextView lostItemDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lostItemImage = itemView.findViewById(R.id.lostItemImage);
            lostItemName = itemView.findViewById(R.id.lostItemName);
            lostItemDate = itemView.findViewById(R.id.lostItemDate);
            lostItemLocation = itemView.findViewById(R.id.lostItemLocation);
            lostItemDescription = itemView.findViewById(R.id.lostItemDescription);
        }
    }

}
