package com.example.android_dontforgetssu;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InterestActivity extends AppCompatActivity implements View.OnClickListener{
    private Switch interestSwitch;
    private EditText interest1;
    private EditText interest2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_money_1);

        interestSwitch=findViewById(R.id.interest_switch);
        interest1=findViewById(R.id.interest_1);
        interest2=findViewById(R.id.interest_2);

        interestSwitch.setOnClickListener((View.OnClickListener) this);
    }
    @Override
    public void onClick(View v) {
        if(interestSwitch.isChecked()){
            interest1.setVisibility(View.VISIBLE);
            interest2.setVisibility(View.VISIBLE);
        } else{
            interest1.setVisibility(View.INVISIBLE);
            interest2.setVisibility(View.INVISIBLE);
        }
    }
}
