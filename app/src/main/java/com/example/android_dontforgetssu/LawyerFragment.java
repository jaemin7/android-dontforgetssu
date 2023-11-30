package com.example.android_dontforgetssu;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_dontforgetssu.databinding.FragmentLawyerBinding;
import com.google.android.material.tabs.TabLayout;

public class LawyerFragment extends Fragment {
    private FragmentLawyerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLawyerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        replaceFragment(new LawyerDetailFragment());
        return view;
    }
        private void replaceFragment(Fragment fragment){
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(binding.lawyerFrameLayout.getId(), fragment).commit();
        }

}