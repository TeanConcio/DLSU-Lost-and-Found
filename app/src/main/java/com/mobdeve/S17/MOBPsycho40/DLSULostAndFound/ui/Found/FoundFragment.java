package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Found;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.FoundItemActivity;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.FragmentFoundBinding;

public class FoundFragment extends Fragment {

    private FragmentFoundBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FoundViewModel foundViewModel =
                new ViewModelProvider(this).get(FoundViewModel.class);

        binding = FragmentFoundBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFound;
        foundViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        //TODO: FOR TESTING ONLY, YOU CAN REMOVE IT
        final Button actionButton = binding.buttonAction;  // Bind the button from XML
        actionButton.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(), FoundItemActivity.class);
            startActivity(intent);

        });







        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}