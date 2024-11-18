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

import java.util.Arrays;
import java.util.Date;

public class LostItemAdapter extends RecyclerView.Adapter<LostItemAdapter.ViewHolder> {

    private LostItem[] lostItemList;
    private LostItem[] filteredList;
    private Context context;

    // Stored search queries
    private Category category = null;
    private String query = "";
    private String campus = "";
    private String location = "";
    private Date startDate = null;
    private Date endDate = null;
    private int sortBy = 0; // Default sort by newest post

    public LostItemAdapter(LostItem[] lostItemList, FragmentActivity activity) {
        this.lostItemList = lostItemList;
        this.filteredList = lostItemList;
        this.context = activity;

        // Apply default sort
        this.applySort();
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

    public void filterByCategory(Category category) { this.filterByCategory(category, true); }
    public void filterByCategory(Category category, boolean applyFilters) {
        this.category = category;
        if (applyFilters) applyFilters();
    }

    public void filterByQuery(String query) { this.filterByQuery(query, true); }
    public void filterByQuery(String query, boolean applyFilters) {
        this.query = query;
        if (applyFilters) applyFilters();
    }

    public void filterByCampus(String campus) { this.filterByCampus(campus, true); }
    public void filterByCampus(String campus, boolean applyFilters) {
        this.campus = campus;
        if (applyFilters) applyFilters();
    }

    public void filterByLocation(String location) { this.filterByLocation(location, true); }
    public void filterByLocation(String location, boolean applyFilters) {
        this.location = location;
        if (applyFilters) applyFilters();
    }

    public void filterByDateRange(Date startDate, Date endDate) { this.filterByDateRange(startDate, endDate, true); }
    public void filterByDateRange(Date startDate, Date endDate, boolean applyFilters) {
        this.startDate = startDate;
        this.endDate = endDate;
        if (applyFilters) applyFilters();
    }

    private void applyFilters() {
        filteredList = Arrays.stream(lostItemList)
                .filter(item -> (this.category == null || item.getCategory() == this.category) &&
                        (this.query == null || this.query.isEmpty() || item.getName().toLowerCase().contains(this.query.toLowerCase()) ||
                                item.getDescription().toLowerCase().contains(this.query.toLowerCase())) &&
                        (this.campus == null || this.campus.equalsIgnoreCase("All") || this.campus.isEmpty() || item.getCampus().equalsIgnoreCase(this.campus)) &&
                        (this.location == null || this.location.isEmpty() || item.getLocation().toLowerCase().contains(this.location.toLowerCase())) &&
                        (this.startDate == null || this.endDate == null ||
                                (item.parseDateLostAsDate().after(this.startDate) && item.parseDateLostAsDate().before(this.endDate))))
                .toArray(LostItem[]::new);
        applySort();
    }

    public void sortBy(int sortBy) { this.sortBy(sortBy, true); }
    public void sortBy(int sortBy, boolean applySort) {
        this.sortBy = sortBy;
        if (applySort) applySort();
    }

    private void applySort() {
        filteredList = Arrays.stream(filteredList)
                .sorted((item1, item2) -> {
                    switch (this.sortBy) {
                        case 1: // Oldest Post
                            return item1.parseDateLostAsDate().compareTo(item2.parseDateLostAsDate());
                        case 2: // A-Z
                            return item1.getName().compareToIgnoreCase(item2.getName());
                        case 3: // Z-A
                            return item2.getName().compareToIgnoreCase(item1.getName());
                        default: // Newest Post
                            return item2.parseDateLostAsDate().compareTo(item1.parseDateLostAsDate());
                    }
                })
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
