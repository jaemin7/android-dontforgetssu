package com.example.android_dontforgetssu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.android_dontforgetssu.databinding.ActivityNavigationBinding;
import com.google.android.material.navigation.NavigationBarView;

public class NavigationActivity extends AppCompatActivity {

    private static final String TAG_HOME = "HomeFragment";
    private static final String TAG_MANAGEMENT= "ManagementFragment";
    private static final String TAG_MY = "MyFragment";

    private ActivityNavigationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.navigationView.setSelectedItemId(R.id.home_Fragment);
        binding.navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home_Fragment) {
                    replaceFragment(new HomeFragment());
                } else if (itemId == R.id.managementlend_Fragment) {
                    replaceFragment(new ManagementLendFragment());
                } else if (itemId == R.id.my_Fragment) {
                    replaceFragment(new MyFragment());
                }

                return true;
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(binding.mainFrameLayout.getId(), fragment).commit();
    }

}