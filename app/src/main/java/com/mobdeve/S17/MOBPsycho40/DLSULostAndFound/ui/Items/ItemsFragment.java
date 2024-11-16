package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Items;

import android.annotation.SuppressLint;
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
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.Category;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.FoundItem;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.ItemStatus;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.LostItem;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Lost.LostItemAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;


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

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        LostItem[] lostItemList = new LostItem[]{
                new LostItem("Black Leather Wallet", Category.ESSENTIALS, "A small black wallet with a few cards and cash. Owner's ID says 'John Dela Cruz'.", "Manila", "Found on the table near the cafeteria entrance", R.drawable.sample_black_wallet, formatter.format(new Date())),
                new LostItem("Green Hoodie", Category.CLOTHES, "A green hoodie with the DLSU logo. It looks slightly worn but in good condition.", "BGC", "Found draped over a chair in the library", R.drawable.sample_green_hoodie, formatter.format(new Date())),
                new LostItem("Running Shoes", Category.SPORTS_EQUIPMENT, "A pair of red Adidas running shoes, size 8. Slightly worn out.", "Manila", "Found in the gym locker room", R.drawable.sample_running_shoes, formatter.format(new Date()))
        };
        lostItemList[2].setStatus(ItemStatus.CLAIMED);

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