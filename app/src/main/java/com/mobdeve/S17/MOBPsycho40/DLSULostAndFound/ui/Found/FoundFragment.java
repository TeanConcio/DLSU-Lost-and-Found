package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Found;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.text.LineBreaker;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.datepicker.MaterialDatePicker;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.CreateFoundActivity;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.R;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.FragmentFoundBinding;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.Category;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.FoundItem;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FoundFragment extends Fragment {

    private FragmentFoundBinding binding;
    private SharedPreferences sharedPreferences;

    // Filter Colors
    private Drawable filterSelectedColor;
    private Drawable filterUnselectedColor;

    // Category Filters
    private LinearLayout categoryFilterView;
    private Category selectedCategory = null;
    private LinearLayout selectedCategoryView = null;

    // Found Items
    private ArrayList<FoundItem> foundItemList;

    private FoundItemAdapter foundItemAdapter;

    // Dialog Box Filters
    private String selectedCampus;
    private String location;
    private String dateRange;
    private int selectedSortBy;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        binding = FragmentFoundBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        FoundViewModel foundViewModel =
//                new ViewModelProvider(this).get(FoundViewModel.class);
//        final TextView textView = binding.textFound;
//        foundViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Set up the filter colors
        filterSelectedColor = ContextCompat.getDrawable(requireContext(), R.drawable.bg_ripple_default_white);
        filterUnselectedColor = ContextCompat.getDrawable(requireContext(), R.color.white);

        // Make filters for each category in the scroll view
        categoryFilterView = binding.foundFilterScroll;
        this.makeCategoryFilters();

        // Data and Date Format
//        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//        foundItemList = new FoundItem[]{
//                new FoundItem("Gray Beanie", Category.CLOTHES, "A gray wool beanie. It's slightly stretched but still in good condition.", "BGC", "Found in the outdoor seating area near the café", R.drawable.sample_gray_beanie, formatter.format(new Date())),
//                new FoundItem("Keychain with Multiple Keys", Category.ESSENTIALS, "A set of house and car keys on a DLSU keychain. One of the keys has a red sticker.", "Manila", "Found in the parking lot", R.drawable.sample_keychain_with_keys, formatter.format(new Date())),
//                new FoundItem("Thermos Flask", Category.ESSENTIALS, "A silver thermos flask with a black lid. Has a few scratches on the surface.", "Laguna", "Found on a bench near the garden", R.drawable.sample_thermos_flask, formatter.format(new Date())),
//                new FoundItem("Sketchbook", Category.BOOKS, "A black hardcover sketchbook with several drawings inside. Owner's name is 'Anna'.", "Off-Campus", "Left in a coffee shop", R.drawable.sample_sketch_book, formatter.format(new Date())),
//                new FoundItem("Red Scarf", Category.CLOTHES, "A red scarf with a checkered pattern. It's made of soft wool.", "Manila", "Found in the library study area", R.drawable.sample_red_scarf, formatter.format(new Date())),
//                new FoundItem("Basketball", Category.SPORTS_EQUIPMENT, "A well-used Spalding basketball. Slightly deflated.", "Laguna", "Found on the basketball court", R.drawable.sample_basketball, formatter.format(new Date())),
//                new FoundItem("Sunglasses", Category.ACCESSORIES, "A pair of black Ray-Ban sunglasses in a brown leather case.", "BGC", "Found near the gym entrance", R.drawable.sample_sunglasses, formatter.format(new Date())),
//                new FoundItem("Pen Set", Category.STATIONERIES, "A black and gold pen set inside a velvet box. Looks new and unused.", "Manila", "Found in a classroom", R.drawable.sample_pen_set, formatter.format(new Date())),
//                new FoundItem("Yoga Mat", Category.SPORTS_EQUIPMENT, "A purple yoga mat rolled up with a strap. Slightly worn.", "Off-Campus", "Found in a fitness center locker room", R.drawable.sample_yoga_mat, formatter.format(new Date())),
//                new FoundItem("Airpods Pro", Category.ELECTRONICS, "It is the airpods owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_airpods_pro, formatter.format(new Date())),
//                new FoundItem("Macbook Pro 2021", Category.ELECTRONICS, "It is the macbook owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_macbook_pro_2021, formatter.format(new Date()))
//        };
        foundItemList = new ArrayList<>();


        // RecyclerView and Adapter
        binding.foundItemRecycler.setHasFixedSize(true);
        binding.foundItemRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        foundItemAdapter = new FoundItemAdapter(foundItemList, getActivity());
        binding.foundItemRecycler.setAdapter(foundItemAdapter);

        // Add Found Item Button
        if (!sharedPreferences.getBoolean("isAdmin", false) ||
                !sharedPreferences.getBoolean("isLoggedIn", false)) {
            binding.addFoundItem.setVisibility(View.GONE);
        } else {
            binding.addFoundItem.setVisibility(View.VISIBLE);
            binding.addFoundItem.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), CreateFoundActivity.class);
                startActivity(intent);
            });
        }


        // Filter Button
        binding.foundFilterButton.setOnClickListener(v -> {
            showSearchDialog();
        });

        // Set up SearchView
        binding.foundSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                foundItemAdapter.filterByQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                foundItemAdapter.filterByQuery(newText);
                return false;
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchFoundItemsFromDatabase();
    }

    private void fetchFoundItemsFromDatabase() {
        DatabaseReference databaseFoundItems = FirebaseDatabase.getInstance().getReference("foundItems");
        databaseFoundItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<FoundItem> tempList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoundItem item = snapshot.getValue(FoundItem.class);
                    if (item != null) {
                        tempList.add(item);
                    }
                }

//                FoundItemAdapter foundItemAdapter = new FoundItemAdapter(tempList, getActivity());
//                binding.foundItemRecycler.setAdapter(foundItemAdapter);


                foundItemAdapter.updateData(tempList); // Pass updated data to adapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void makeCategoryFilters() {
        // Create a filter button for the "All" category
        LinearLayout allFilter = binding.allFoundFilter;
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
            textView.setBreakStrategy(LineBreaker.BREAK_STRATEGY_SIMPLE);

            // Add the image view and text view to the filter
            filter.addView(imageView);
            filter.addView(textView);

            // Set an onClickListener for the filter
            filter.setOnClickListener(v -> onSelectFilter(v, category));

            // Set an onTouchListener for the filter
            filter.setOnTouchListener(this::onTouch);

            // Add the filter to the scroll view
            binding.foundFilterScroll.addView(filter);
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
            foundItemAdapter.filterByCategory(category);
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

        //Spinner for Campus
        Spinner campusSpinner = dialog.findViewById(R.id.spinner_campus);
        ArrayAdapter<CharSequence> adapterCampus = ArrayAdapter.createFromResource(
                this.requireContext(),
                R.array.campus,
                R.layout.spinner_item
        );
        adapterCampus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campusSpinner.setAdapter(adapterCampus);

        // EditText for Location
        EditText locationEditText = dialog.findViewById(R.id.input_title);

        //Date Range Picker
        TextView dateRangeTextView = dialog.findViewById(R.id.input_date_range);
        dateRangeTextView.setOnClickListener(v -> showDateRangePicker(dateRangeTextView));

        // Spinner for Sort By
        Spinner sortBySpinner = dialog.findViewById(R.id.spinner_sort_by);
        ArrayAdapter<CharSequence> adapterSortBy = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.sort_by,
                R.layout.spinner_item
        );
        adapterSortBy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBySpinner.setAdapter(adapterSortBy);

        // Set the dialog views to the stored values
        if (this.selectedCampus != null) {
            int campusPosition = adapterCampus.getPosition(this.selectedCampus);
            campusSpinner.setSelection(campusPosition);
        }
        if (this.location != null) {
            locationEditText.setText(this.location);
        }
        if (this.dateRange != null) {
            dateRangeTextView.setText(this.dateRange);
        }
        if (this.selectedSortBy != -1) {
            sortBySpinner.setSelection(this.selectedSortBy);
        }

        //Dialog Position and Dim amount
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

        // Clear Button
        Button btnSearchClear = dialog.findViewById(R.id.btn_clearSearch);
        btnSearchClear.setOnClickListener(v -> {
            // Clear the dialog views
            campusSpinner.setSelection(0);
            locationEditText.setText("");
            dateRangeTextView.setText("");
            sortBySpinner.setSelection(0);
        });

        // Confirm Button
        Button btnSearchConfirm = dialog.findViewById(R.id.btn_filteredSearch);
        btnSearchConfirm.setOnClickListener(v -> {
            this.selectedCampus = campusSpinner.getSelectedItem().toString();
            this.location = locationEditText.getText().toString();
            this.dateRange = dateRangeTextView.getText().toString();
            this.selectedSortBy = sortBySpinner.getSelectedItemPosition();

            // Parse the date range
            String[] dates = dateRange.split(" - ");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            Date startDate = null;
            Date endDate = null;
            try {
                if (dates.length == 2) {
                    startDate = sdf.parse(dates[0]);
                    endDate = sdf.parse(dates[1]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Apply filters
            foundItemAdapter.filterByCampus(selectedCampus, false);
            foundItemAdapter.filterByLocation(location, false);
            foundItemAdapter.filterByDateRange(startDate, endDate);
            foundItemAdapter.sortBy(selectedSortBy);

            dialog.dismiss();
        });

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