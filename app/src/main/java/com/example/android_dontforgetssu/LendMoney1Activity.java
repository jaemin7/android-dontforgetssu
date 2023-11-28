package com.example.android_dontforgetssu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.Buffer;

public class LendMoney1Activity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_money_1);

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼이 클릭되었을 때 실행되는 코드
                Intent intent = new Intent(LendMoney1Activity.this, LendMoney2Activity.class);
                startActivity(intent);
            }
        });

        final Switch interestSwitch = findViewById(R.id.interest_switch);
        final EditText interest1EditText = findViewById(R.id.interest_1);
        final EditText interest2EditText = findViewById(R.id.interest_2);

        interestSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 스위치 상태에 따라 EditText의 가시성을 조절
                interest1EditText.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
                interest2EditText.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
            }
        });
        Button back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LendMoney1Activity.this, RecordMoneyActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
