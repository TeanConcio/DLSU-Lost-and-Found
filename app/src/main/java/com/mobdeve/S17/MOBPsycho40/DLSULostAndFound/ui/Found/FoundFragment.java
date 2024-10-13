package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Found;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.R;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.FragmentFoundBinding;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.FoundItem;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.LostItem;

import java.time.LocalDateTime;

public class FoundFragment extends Fragment {

    private FragmentFoundBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFoundBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        FoundViewModel foundViewModel =
//                new ViewModelProvider(this).get(FoundViewModel.class);

//        final TextView textView = binding.textFound;
//        foundViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.foundFilter1.setOnTouchListener(this::onTouch);

        // RecyclerView
        binding.foundItemRecycler.setHasFixedSize(true);
        binding.foundItemRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // RecyclerView Data
        FoundItem[] foundItemList = new FoundItem[]{
                new FoundItem("Dominic \"THE GOAT\" Sia says that: According to all known laws of aviation, a bee isn't supposed to be able to fly", "Electronics", "It is the phone owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Br. Andrew Hall wants to know your location right here right now", R.drawable.sample_the_goat, LocalDateTime.now()),
                new FoundItem("iPhone 69 LIMITLESS", "Electronics", "It is the phone owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_iphone16_pro_max, LocalDateTime.now()),
                new FoundItem("Airpods Pro", "Electronics", "It is the airpods owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_airpods_pro, LocalDateTime.now()),
                new FoundItem("Macbook Pro 2021", "Electronics", "It is the macbook owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_macbook_pro_2021, LocalDateTime.now()),
                new FoundItem("iPad Pro 2021", "Electronics", "It is the ipad owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_ipad_pro_2021, LocalDateTime.now()),
                new FoundItem("Samsung Galaxy S21", "Electronics", "It is the phone owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_the_goat, LocalDateTime.now()),
                new FoundItem("Samsung Galaxy Buds", "Electronics", "It is the earbuds owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_the_goat, LocalDateTime.now()),
                new FoundItem("Samsung Galaxy Tab", "Electronics", "It is the tablet owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_the_goat, LocalDateTime.now()),
                new FoundItem("Samsung Galaxy Watch", "Electronics", "It is the watch owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_the_goat, LocalDateTime.now()),
                new FoundItem("Samsung Galaxy Book", "Electronics", "It is the laptop owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_the_goat, LocalDateTime.now()),
                new FoundItem("Samsung Galaxy Fold", "Electronics", "It is the phone owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_the_goat, LocalDateTime.now()),
        };

        // RecyclerView Adapter
        FoundItemAdapter foundItemAdapter = new FoundItemAdapter(foundItemList, getActivity());
        binding.foundItemRecycler.setAdapter(foundItemAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.filter_press);
            v.startAnimation(anim);
        }
        return false;
    }
}