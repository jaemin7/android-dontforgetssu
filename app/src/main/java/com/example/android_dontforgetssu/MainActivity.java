package com.example.android_dontforgetssu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android_dontforgetssu.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}