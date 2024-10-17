package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.R;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.FragmentItemsBinding;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.LostItem;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Lost.LostItemAdapter;

import java.time.LocalDateTime;

public class ItemsFragment extends Fragment {

    private FragmentItemsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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

        LostItem[] lostItemList = new LostItem[]{
                new LostItem("Dominic \"THE GOAT\" Sia says that: According to all known laws of aviation, a bee isn't supposed to be able to fly", "Electronics", "It is the phone owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Br. Andrew Hall wants to know your location right here right now", R.drawable.sample_the_goat, LocalDateTime.now()),
                new LostItem("iPhone 69 LIMITLESS", "Electronics", "It is the phone owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_iphone16_pro_max, LocalDateTime.now()),
                new LostItem("Airpods Pro", "Electronics", "It is the airpods owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_airpods_pro, LocalDateTime.now()),
        };

        // RecyclerView Adapter
        ItemsAdapter itemsAdapter = new ItemsAdapter(lostItemList, getActivity());
        binding.myLostItemRecycler.setAdapter(itemsAdapter);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}