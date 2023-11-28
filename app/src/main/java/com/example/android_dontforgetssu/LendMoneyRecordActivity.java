package com.example.android_dontforgetssu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_dontforgetssu.databinding.LendMoneyRecordBinding;

public class LendMoneyRecordActivity extends AppCompatActivity {
    LendMoneyRecordBinding binding;
    AlertDialog global_select_dialog;

    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (dialog == global_select_dialog) {
                String[] global_select_data = getResources().getStringArray(R.array.global_select_dialog);
                String[] exchange_rate_hint = getResources().getStringArray(R.array.exchange_rate_hint);
                binding.lendMoneyRecordExchangeRateTextview.setText(global_select_data[which]);
                binding.lendMoneyRecordExchangeRateEditText.setHint(exchange_rate_hint[which]);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = LendMoneyRecordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.globalSelectSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LendMoneyRecordActivity.this);
                    builder.setTitle("선택 가능 국가");
                    builder.setSingleChoiceItems(R.array.global_select_dialog, 0, dialogListener);
                    builder.setPositiveButton("확인", null);
                    builder.setNegativeButton("취소", null);
                    global_select_dialog = builder.create();
                    global_select_dialog.show();
                    binding.lendMoneyRecordExchangeRateCard.setVisibility(View.VISIBLE);
                } else {
                    binding.lendMoneyRecordExchangeRateCard.setVisibility(View.GONE);
                }
            }
        });

        binding.interestSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.lendMoneyRecordInterestCard.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
            }
        });

        binding.lendRecordInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼이 클릭되었을 때 실행되는 코드
                Intent intent = new Intent(LendMoneyRecordActivity.this, LendRecordInformationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LendMoneyRecordActivity.this, RecordMoneyActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
