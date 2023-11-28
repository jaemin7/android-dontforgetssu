package com.example.android_dontforgetssu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RecordMoneyActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_money);

        Button borrowButton = findViewById(R.id.button1);
        borrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 'Borrow' 버튼을 눌렀을 때 처리할 코드 추가
                Intent intent = new Intent(RecordMoneyActivity.this, LendMoney1Activity.class);
                startActivity(intent);
            }
        });

        Button lendButton = findViewById(R.id.button2);
        lendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 'Lend' 버튼을 눌렀을 때 처리할 코드 추가
                Intent intent = new Intent(RecordMoneyActivity.this, BorrowMoney1Activity.class);
                startActivity(intent);
            }
        });
        Button back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(RecordMoneyActivity.this,HomeFragment.class);
                startActivity(intent);
            }
        });

    }
}
