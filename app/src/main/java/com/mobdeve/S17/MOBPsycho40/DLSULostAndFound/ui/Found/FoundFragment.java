package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Found;

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

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.CreateFoundActivity;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.R;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.FragmentFoundBinding;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.Category;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.FoundItem;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.ItemStatus;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models.LostItem;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class FoundFragment extends Fragment {

    private FragmentFoundBinding binding;

    private Drawable filterSelectedColor;
    private Drawable filterUnselectedColor;

    private LinearLayout categoryFilterView;
    private Category selectedCategory = null;
    private LinearLayout selectedCategoryView = null;

    private SharedPreferences sharedPreferences;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        binding = FragmentFoundBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        FoundViewModel foundViewModel =
//                new ViewModelProvider(this).get(FoundViewModel.class);
//        final TextView textView = binding.textFound;
//        foundViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        filterSelectedColor = ContextCompat.getDrawable(requireContext(), R.drawable.bg_ripple_default_white);
        filterUnselectedColor = ContextCompat.getDrawable(requireContext(), R.color.white);

        // Make filters for each category in the scroll view
        categoryFilterView = binding.foundFilterScroll;
        this.makeCategoryFilters();

        // Data

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        FoundItem[] foundItemList = new FoundItem[]{
                new FoundItem("Gray Beanie", Category.CLOTHES, "A gray wool beanie. It's slightly stretched but still in good condition.", "BGC", "Found in the outdoor seating area near the café", R.drawable.sample_gray_beanie, formatter.format(new Date())),
                new FoundItem("Keychain with Multiple Keys", Category.ESSENTIALS, "A set of house and car keys on a DLSU keychain. One of the keys has a red sticker.", "Manila", "Found in the parking lot", R.drawable.sample_keychain_with_keys, formatter.format(new Date())),
                new FoundItem("Thermos Flask", Category.ESSENTIALS, "A silver thermos flask with a black lid. Has a few scratches on the surface.", "Laguna", "Found on a bench near the garden", R.drawable.sample_thermos_flask, formatter.format(new Date())),
                new FoundItem("Sketchbook", Category.BOOKS, "A black hardcover sketchbook with several drawings inside. Owner's name is 'Anna'.", "Off-Campus", "Left in a coffee shop", R.drawable.sample_sketch_book, formatter.format(new Date())),
                new FoundItem("Red Scarf", Category.CLOTHES, "A red scarf with a checkered pattern. It's made of soft wool.", "Manila", "Found in the library study area", R.drawable.sample_red_scarf, formatter.format(new Date())),
                new FoundItem("Basketball", Category.SPORTS_EQUIPMENT, "A well-used Spalding basketball. Slightly deflated.", "Laguna", "Found on the basketball court", R.drawable.sample_basketball, formatter.format(new Date())),
                new FoundItem("Sunglasses", Category.ACCESSORIES, "A pair of black Ray-Ban sunglasses in a brown leather case.", "BGC", "Found near the gym entrance", R.drawable.sample_sunglasses, formatter.format(new Date())),
                new FoundItem("Pen Set", Category.STATIONERIES, "A black and gold pen set inside a velvet box. Looks new and unused.", "Manila", "Found in a classroom", R.drawable.sample_pen_set, formatter.format(new Date())),
                new FoundItem("Yoga Mat", Category.SPORTS_EQUIPMENT, "A purple yoga mat rolled up with a strap. Slightly worn.", "Off-Campus", "Found in a fitness center locker room", R.drawable.sample_yoga_mat, formatter.format(new Date())),
                new FoundItem("Airpods Pro", Category.ELECTRONICS, "It is the airpods owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_airpods_pro, formatter.format(new Date())),
                new FoundItem("Macbook Pro 2021", Category.ELECTRONICS, "It is the macbook owned by the one and only Gojo \"Dominic Sia\" Satoru. It has a blue, red, and purple design and contains LIMITLESS (Cursed) Energy", "Manila", "Henry Sy", R.drawable.sample_macbook_pro_2021, formatter.format(new Date()))
        };
        foundItemList[0].setStatus(ItemStatus.CLAIMED);

        if (!sharedPreferences.getBoolean("isAdmin", false)) {
            binding.addFoundItem.setVisibility(View.GONE);
        }

        if (!sharedPreferences.getBoolean("isLoggedIn", false)) {
            binding.addFoundItem.setVisibility(View.GONE);
        }
        
        // RecyclerView and Adapter
        binding.foundItemRecycler.setHasFixedSize(true);
        binding.foundItemRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        FoundItemAdapter foundItemAdapter = new FoundItemAdapter(foundItemList, getActivity());
        binding.foundItemRecycler.setAdapter(foundItemAdapter);

        binding.foundFilterButton.setOnClickListener(v -> {
            showSearchDialog();
        });

        binding.addFoundItem.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateFoundActivity.class);
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

            // Create an ImageView`
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    convertPxToDp(40),
                    convertPxToDp(40)
            );
            imageParams.setMargins(convertPxToDp(2.5), 0, convertPxToDp(2.5), 0);
            imageView.setLayoutParams(imageParams);
            imageView.setImageResource(category.getIcon());
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
            textView.setText(category.getString());
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.green_700));
            textView.setTextSize(10);
            textView.setBreakStrategy(Layout.BREAK_STRATEGY_SIMPLE);

            // Add the image view and text view to the filter
            filter.addView(imageView);
            filter.addView(textView);

            // Set an on click listener for the filter
            filter.setOnClickListener(v -> onSelectFilter(v, category));

            // Set an on touch listener for the filter
            filter.setOnTouchListener(this::onTouch);

            // Add the filter to the scroll view
            categoryFilterView.addView(filter);
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
        }
    }


    private boolean onTouch(View v, MotionEvent event) {
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
        final Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.dialog_search_filter);

        Button btnSearchConfirm = dialog.findViewById(R.id.btn_filteredSearch);

        // Spinner for Campus
        Spinner campusSpinner = dialog.findViewById(R.id.spinner_campus);
        ArrayAdapter<CharSequence> adapterCampus = ArrayAdapter.createFromResource(
                requireContext(),
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