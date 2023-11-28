package com.example.android_dontforgetssu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_dontforgetssu.databinding.FragmentManagementBinding;
import com.google.android.material.tabs.TabLayout;

public class ManagementFragment extends Fragment {

    private FragmentManagementBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentManagementBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        replaceFragment(new ManagementLendFragment());
        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        replaceFragment(new ManagementLendFragment());
                        break;
                    case 1:
                        replaceFragment(new ManagementBorrowFragment());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }
        private void replaceFragment(Fragment fragment) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(binding.managementFrameLayout.getId(), fragment).commit();
        }
}