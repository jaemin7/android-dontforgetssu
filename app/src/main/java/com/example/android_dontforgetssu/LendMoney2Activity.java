package com.example.android_dontforgetssu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LendMoney2Activity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_money_2);

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 'button2' 버튼을 눌렀을 때 처리할 코드 추가
                Intent intent = new Intent(LendMoney2Activity.this, RecordInformationActivity.class);
                startActivity(intent);
            }
        });
        Button back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LendMoney2Activity.this,LendMoney1Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
