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
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.LostItem;

import java.time.format.DateTimeFormatter;

public class LostItemAdapter extends RecyclerView.Adapter<LostItemAdapter.ViewHolder> {

    LostItem[] lostItemList;
    Context context;

    public LostItemAdapter(LostItem[] lostItemList, FragmentActivity activity) {
        this.lostItemList = lostItemList;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_lost_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final LostItem currentItem = lostItemList[position];

        holder.lostItemImage.setImageResource(currentItem.getImage());
        holder.lostItemName.setText(currentItem.getName());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        holder.lostItemDate.setText(currentItem.getDateLost().format(dateTimeFormatter));
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
                i.putExtra("date",currentItem.getDateLost().format(dateTimeFormatter));
                i.putExtra("campus", currentItem.getCampus());
                i.putExtra("location",currentItem.getLocation());
                i.putExtra("description", currentItem.getDescription());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lostItemList.length;
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
