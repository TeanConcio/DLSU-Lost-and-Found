package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Items;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.R;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.FragmentItemsBinding;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.Category;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.ItemStatus;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.LostItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ItemsFragment extends Fragment {

    private FragmentItemsBinding binding;
    private SharedPreferences sharedPreferences;

    private ItemsAdapter itemsAdapter;

    private ArrayList<LostItem> lostItemList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        binding = FragmentItemsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        ItemsViewModel itemsViewModel =
//                new ViewModelProvider(this).get(ItemsViewModel.class);
//
//        final TextView textView = binding.textItems;
//        itemsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        // RecyclerView
        binding.myLostItemRecycler.setHasFixedSize(true);
        binding.myLostItemRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        lostItemList = new ArrayList<>();
        itemsAdapter = new ItemsAdapter(lostItemList, getActivity()); // Initialize class-level adapter
        binding.myLostItemRecycler.setAdapter(itemsAdapter);


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchMyLostItemsFromDatabase();
    }

    private void fetchMyLostItemsFromDatabase() {
        // Get user logged in from SharedPreferences
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            binding.textLoginPrompt.setVisibility(View.VISIBLE);
            return;
        } else {
            binding.textLoginPrompt.setVisibility(View.GONE);
        }

        // Get the userID from SharedPreferences
        String currentUserId = sharedPreferences.getString("userID", "");

        if (currentUserId.isEmpty()) {
            Toast.makeText(getContext(), "No user ID found in session", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference databaseLostItems = FirebaseDatabase.getInstance().getReference("lostItems");
        databaseLostItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<LostItem> filteredLostItemList = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    LostItem lostItem = postSnapshot.getValue(LostItem.class);
                    if (lostItem != null && lostItem.getUserID().equals(currentUserId)) {
                        filteredLostItemList.add(lostItem);
                    }
                }
                if (filteredLostItemList.isEmpty()) {
                 //do something
                }
                itemsAdapter.updateData(filteredLostItemList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error fetching lost items", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}