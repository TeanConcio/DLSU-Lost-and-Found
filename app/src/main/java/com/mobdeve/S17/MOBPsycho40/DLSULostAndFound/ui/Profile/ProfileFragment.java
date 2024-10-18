package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.AboutAppActivity;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ChangePasswordFormActivity;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.EditProfileActivity;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.LoginActivity;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.R;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.databinding.FragmentProfileBinding;
import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Items.ItemsFragment;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedPreferences = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        String idNumber = sharedPreferences.getString("idNumber", "Unknown ID");
        String firstName = sharedPreferences.getString("firstName", "Unknown");
        String lastName = sharedPreferences.getString("lastName", "User");
        String email = sharedPreferences.getString("email", "Unknown email");

        binding.txtUserId.setText("User ID: " + idNumber);
        binding.txtFullName.setText(firstName + " " + lastName);

        binding.sectionMyProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            intent.putExtra("idNumber", idNumber);
            intent.putExtra("firstName", firstName);
            intent.putExtra("lastName", lastName);
            intent.putExtra("email", email);
            startActivity(intent);
        });

        binding.sectionMyRequests.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.navigation_profile, true)
                    .build();

            navController.navigate(R.id.navigation_items, null, navOptions);
        });

        binding.sectionChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePasswordFormActivity.class);
            startActivity(intent);
        });

        binding.sectionLogOut.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        binding.sectionAboutApp.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutAppActivity.class);
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
