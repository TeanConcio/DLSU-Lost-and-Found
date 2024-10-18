package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Found;

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
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.FoundItem;

import java.time.format.DateTimeFormatter;

public class FoundItemAdapter extends RecyclerView.Adapter<FoundItemAdapter.ViewHolder> {

    FoundItem[] foundItemList;
    Context context;

    public FoundItemAdapter(FoundItem[] foundItemList, FragmentActivity activity) {
        this.foundItemList = foundItemList;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_found_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final FoundItem currentItem = foundItemList[position];

        holder.foundItemImage.setImageResource(currentItem.getImage());
        holder.foundItemName.setText(currentItem.getName());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        holder.foundItemDate.setText(currentItem.getDateFound().format(dateTimeFormatter));
        holder.foundItemLocation.setText(currentItem.getLocation());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Display " + currentItem.getName() + " information", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, ItemActivity.class);
                i.putExtra("image",currentItem.getImage());
                i.putExtra("name",currentItem.getName());
                i.putExtra("category",currentItem.getCategory().getString());
                i.putExtra("date",currentItem.getDateFound().format(dateTimeFormatter));
                i.putExtra("campus", currentItem.getCampus());
                i.putExtra("location",currentItem.getLocation());
                i.putExtra("description", currentItem.getDescription());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foundItemList.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView foundItemImage;
        TextView foundItemName;
        TextView foundItemDate;
        TextView foundItemLocation;
        TextView foundItemDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foundItemImage = itemView.findViewById(R.id.foundItemImage);
            foundItemName = itemView.findViewById(R.id.foundItemName);
            foundItemDate = itemView.findViewById(R.id.foundItemDate);
            foundItemLocation = itemView.findViewById(R.id.foundItemLocation);
        }
    }

}
