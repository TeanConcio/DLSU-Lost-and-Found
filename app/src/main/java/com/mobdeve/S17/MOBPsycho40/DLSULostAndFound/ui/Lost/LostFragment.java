package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Lost;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.material.datepicker.MaterialDatePicker;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.R;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.FragmentLostBinding;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.Category;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.LostItem;

import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class LostFragment extends Fragment {

    private FragmentLostBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


//        LostViewModel lostViewModel =
//                new ViewModelProvider(this).get(LostViewModel.class);

//        final TextView textView = binding.textLost;
//        lostViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.lostFilter1.setOnTouchListener(this::onTouch);

        // RecyclerView
        binding.lostItemRecycler.setHasFixedSize(true);
        binding.lostItemRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // RecyclerView Data
        LostItem[] lostItemList = new LostItem[]{
                new LostItem("Dominic \"THE GOAT\" Sia says that: According to all known laws of aviation, a bee isn't supposed to be able to fly", Category.ELECTRONICS, "It is the phone owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Br. Andrew Hall wants to know your location right here right now", R.drawable.sample_the_goat, LocalDate.now()),
                new LostItem("iPhone 69 LIMITLESS", Category.ELECTRONICS, "It is the phone owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_iphone16_pro_max, LocalDate.now()),
                new LostItem("Airpods Pro", Category.ELECTRONICS, "It is the airpods owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_airpods_pro, LocalDate.now()),
                new LostItem("Macbook Pro 2021", Category.ELECTRONICS, "It is the macbook owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_macbook_pro_2021, LocalDate.now()),
                new LostItem("iPad Pro 2021", Category.ELECTRONICS, "It is the ipad owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_ipad_pro_2021, LocalDate.now()),
                new LostItem("Samsung Galaxy S21", Category.ELECTRONICS, "It is the phone owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_the_goat, LocalDate.now()),
                new LostItem("Samsung Galaxy Buds", Category.ELECTRONICS, "It is the earbuds owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_the_goat, LocalDate.now()),
                new LostItem("Samsung Galaxy Tab", Category.ELECTRONICS, "It is the tablet owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_the_goat, LocalDate.now()),
                new LostItem("Samsung Galaxy Watch", Category.ELECTRONICS, "It is the watch owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_the_goat, LocalDate.now()),
                new LostItem("Samsung Galaxy Book", Category.ELECTRONICS, "It is the laptop owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_the_goat, LocalDate.now()),
                new LostItem("Samsung Galaxy Fold", Category.ELECTRONICS, "It is the phone owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_the_goat, LocalDate.now()),
        };

        // RecyclerView Adapter
        LostItemAdapter lostItemAdapter = new LostItemAdapter(lostItemList, getActivity());
        binding.lostItemRecycler.setAdapter(lostItemAdapter);

        // Filter Button
        binding.lostFilterButton.setOnClickListener(v -> {
            showSearchDialog();
        });

        //Add spinner





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


    private void showSearchDialog() {
        // Create a dialog instance
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_search_filter);

        Button btnSearchConfirm = dialog.findViewById(R.id.btn_filteredSearch);

        //Spinner for Campus
        Spinner campusSpinner = dialog.findViewById(R.id.spinner_campus);
        ArrayAdapter<CharSequence> adapterCampus = ArrayAdapter.createFromResource(
                this.requireContext(),
                R.array.campus,
                R.layout.spinner_item
        );
        adapterCampus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campusSpinner.setAdapter(adapterCampus);

        // Spinner for Sort By
        Spinner sortBySpinner = dialog.findViewById(R.id.spinner_sort_by);
        ArrayAdapter<CharSequence> adapterSortBy = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.sort_by,
                R.layout.spinner_item
        );
        adapterSortBy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBySpinner.setAdapter(adapterSortBy);

        //Date Range Picker
        TextView dateRangeTextView = dialog.findViewById(R.id.input_date_range);
        dateRangeTextView.setOnClickListener(v -> showDateRangePicker(dateRangeTextView));


        //Dialog Position and Dim ammount
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.gravity = Gravity.TOP;
            //dialog margin
            params.x = -60;
            params.y = 100;
            params.dimAmount = 0;
            dialog.getWindow().setAttributes(params);
        }
        dialog.show();
    }

    private void showDateRangePicker(final TextView date) {
        MaterialDatePicker<Pair<Long, Long>> dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select Date Range")
                .build();

        // Show the date range picker
        dateRangePicker.show(getParentFragmentManager(), "DATE_RANGE_PICKER");

        // Handle the positive button click (when the date range is selected)
        dateRangePicker.addOnPositiveButtonClickListener(selection -> {
            if (selection != null) {
                Long startDate = selection.first;
                Long endDate = selection.second;

                // Convert the start and end dates from milliseconds to a human-readable format
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                String formattedStartDate = simpleDateFormat.format(new Date(startDate));
                String formattedEndDate = simpleDateFormat.format(new Date(endDate));

                // Update the TextView with the selected start and end dates
                date.setText(formattedStartDate + " - " + formattedEndDate);
               // date.setText("Start: " + formattedStartDate + "\nEnd: " + formattedEndDate);
            }
        });
    }





}