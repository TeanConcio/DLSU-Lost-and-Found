package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Lost;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.material.datepicker.MaterialDatePicker;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.CreateLostActivity;
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

    private Drawable filterSelectedColor;
    private Drawable filterUnselectedColor;

    private LinearLayout categoryFilterView;
    private Category selectedCategory = null;
    private LinearLayout selectedCategoryView = null;

    private LostItem[] lostItemList;
    private SharedPreferences sharedPreferences;
    private LostItemAdapter lostItemAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        binding = FragmentLostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        LostViewModel lostViewModel =
//                new ViewModelProvider(this).get(LostViewModel.class);
//        final TextView textView = binding.textLost;
//        lostViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        filterSelectedColor = ContextCompat.getDrawable(requireContext(), R.drawable.bg_ripple_default_white);
        filterUnselectedColor = ContextCompat.getDrawable(requireContext(), R.color.white);

        // Make filters for each category in the scroll view
        categoryFilterView = binding.lostFilterScroll;
        this.makeCategoryFilters();


        // Data
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        lostItemList = new LostItem[]{
                new LostItem("iPad Pro 2021", Category.ELECTRONICS, "It is the ipad owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_ipad_pro_2021, formatter.format(new Date())),
                new LostItem("Black Leather Wallet", Category.ESSENTIALS, "A small black wallet with a few cards and cash. Owner's ID says 'John Dela Cruz'.", "Manila", "Found on the table near the cafeteria entrance", R.drawable.sample_black_wallet, formatter.format(new Date())),
                new LostItem("Green Hoodie", Category.CLOTHES, "A green hoodie with the DLSU logo. It looks slightly worn but in good condition.", "BGC", "Found draped over a chair in the library", R.drawable.sample_green_hoodie, formatter.format(new Date())),
                new LostItem("Mathematics Notebook", Category.STATIONERIES, "A spiral-bound notebook with 'Math 101' written on the cover. Contains detailed notes.", "Laguna", "Found under a desk in the lecture hall", R.drawable.sample_math_notebook, formatter.format(new Date())),
                new LostItem("Blue Water Bottle", Category.ESSENTIALS, "A stainless steel blue water bottle. No visible brand. Slight dent on the side.", "Off-Campus", "Found near the basketball court", R.drawable.sample_blue_bottle, formatter.format(new Date())),
                new LostItem("iPhone 16 Pro Max", Category.ELECTRONICS, "It is the phone owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_iphone16_pro_max, formatter.format(new Date())),
                new LostItem("Running Shoes", Category.SPORTS_EQUIPMENT, "A pair of red Adidas running shoes, size 8. Slightly worn out.", "Manila", "Found in the gym locker room", R.drawable.sample_running_shoes, formatter.format(new Date())),
                new LostItem("Physics Textbook", Category.BOOKS, "A hardcover Physics textbook, 'Fundamentals of Physics'. Owner's name is inside: 'Sarah'.", "Laguna", "Found on a study table in the student center", R.drawable.sample_physics_textbook, formatter.format(new Date())),
                new LostItem("Umbrella", Category.OTHER_ITEMS, "A small black foldable umbrella with a red handle.", "BGC", "Left in the restroom", R.drawable.sample_umbrella, formatter.format(new Date())),
                new LostItem("Glasses Case", Category.ACCESSORIES, "A black hard-shell glasses case. Contains prescription glasses.", "Off-Campus", "Found at the reception counter of a coffee shop", R.drawable.sample_glasses_case, formatter.format(new Date())),
                new LostItem("Brown Leather Belt", Category.ACCESSORIES, "A brown leather belt with a silver buckle. Slightly worn but in good condition.", "Manila", "Found hanging on a chair in the cafeteria", R.drawable.sample_brown_leather_belt, formatter.format(new Date())),
                new LostItem("Canvas Tote Bag", Category.CONTAINERS, "A white canvas tote bag with a floral design. Contains a few books and snacks.", "Laguna", "Left in Room 201 of the science building", R.drawable.sample_canvas_totebag, formatter.format(new Date()))
        };

        if (!sharedPreferences.getBoolean("isLoggedIn", false)) {
            binding.addLostItem.setVisibility(View.GONE);
        }

        // RecyclerView and Adapter
        binding.lostItemRecycler.setHasFixedSize(true);
        binding.lostItemRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        lostItemAdapter = new LostItemAdapter(lostItemList, getActivity());
        binding.lostItemRecycler.setAdapter(lostItemAdapter);

        // Filter Button
        binding.lostFilterButton.setOnClickListener(v -> {
            showSearchDialog();
        });

        binding.addLostItem.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateLostActivity.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void makeCategoryFilters() {
        // Create a filter button for the "All" category
        LinearLayout allFilter = binding.allLostFilter;
        allFilter.setOnClickListener(v -> onSelectFilter(v, null));
        allFilter.setOnTouchListener(this::onTouch);

        // Create a filter button for each category
        for (Category category : Category.values()) {

            // Create a LinearLayout
            LinearLayout filter = new LinearLayout(getContext());
            LinearLayout.LayoutParams filterParams = new LinearLayout.LayoutParams(
                    convertPxToDp(75),
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            filter.setLayoutParams(filterParams);
            filter.setBackground(filterUnselectedColor);
            filter.setClickable(true);
            filter.setGravity(Gravity.CENTER);
            filter.setOrientation(LinearLayout.VERTICAL);
            filter.setPadding(convertPxToDp(10), convertPxToDp(10), convertPxToDp(10), convertPxToDp(10));

            // Create an ImageView
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    convertPxToDp(40),
                    convertPxToDp(40)
            );
            imageParams.setMargins(convertPxToDp(2.5), 0, convertPxToDp(2.5), 0);
            imageView.setLayoutParams(imageParams);
            imageView.setImageResource(category.getIcon()); // Assuming category has an icon method
            imageView.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.green_700));

            // Create a TextView
            TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            textView.setLayoutParams(textParams);
            textView.setGravity(Gravity.CENTER);
            textView.setLines(2);
            textView.setText(category.getString()); // Assuming category has a string method
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.green_700));
            textView.setTextSize(10);
            textView.setBreakStrategy(Layout.BREAK_STRATEGY_SIMPLE);

            // Add the image view and text view to the filter
            filter.addView(imageView);
            filter.addView(textView);

            // Set an onClickListener for the filter
            filter.setOnClickListener(v -> onSelectFilter(v, category));

            // Set an onTouchListener for the filter
            filter.setOnTouchListener(this::onTouch);

            // Add the filter to the scroll view
            binding.lostFilterScroll.addView(filter);
        }

        // Set the "All" category as the default selected category
        selectedCategoryView = allFilter;
        selectedCategoryView.setBackground(filterSelectedColor);
    }

    private void onSelectFilter(View v, Category category) {
        if (selectedCategory != category) {
            selectedCategory = category;
            selectedCategoryView.setBackground(filterUnselectedColor);
            selectedCategoryView = (LinearLayout) v;
            selectedCategoryView.setBackground(filterSelectedColor);
            lostItemAdapter.filterByCategory(category);
        }
    }


    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.filter_press);
            v.startAnimation(anim);
        }
        return false;
    }

    private int convertPxToDp(double px) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (px * scale + 0.5f);
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