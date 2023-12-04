package com.example.android_dontforgetssu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_dontforgetssu.databinding.RecordMoneyBinding;

public class RecordMoneyActivity extends AppCompatActivity {
    private RecordMoneyBinding binding;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = RecordMoneyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.lendMoneyRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 'Borrow' 버튼을 눌렀을 때 처리할 코드 추가
                Intent intent = new Intent(RecordMoneyActivity.this, LendMoneyRecordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.borrowMoneyRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 'Lend' 버튼을 눌렀을 때 처리할 코드 추가
                Intent intent = new Intent(RecordMoneyActivity.this, BorrowMoneyRecordActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(RecordMoneyActivity.this,HomeFragment.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
