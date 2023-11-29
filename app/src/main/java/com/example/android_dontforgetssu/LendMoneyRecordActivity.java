package com.example.android_dontforgetssu;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_dontforgetssu.databinding.LendMoneyRecordBinding;

import java.util.Calendar;

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
    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
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
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            binding.lendMoneyRecordExchangeRateCard.setVisibility(View.VISIBLE);
                            showToast(binding.lendMoneyRecordExchangeRateTextview.getText().toString()+"가 선택되었습니다.");
                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //취소 눌렀을 때 exchange_rate_card 없어지고, 스위치 false 상태되고, exchange_rate_card에 입력된 정보 초기화
                            binding.lendMoneyRecordExchangeRateCard.setVisibility(View.GONE);
                            binding.globalSelectSwitch.setChecked(false);
                            binding.lendMoneyRecordExchangeRateTextview.setText("한국 환율 KRW(₩)");
                            binding.lendMoneyRecordExchangeRateEditText.setHint(null);
                            showToast(binding.lendMoneyRecordExchangeRateTextview.getText().toString()+"가 선택되었습니다.");
                        }
                    });
                    global_select_dialog = builder.create();
                    global_select_dialog.show();
                } else {
                    binding.lendMoneyRecordExchangeRateCard.setVisibility(View.GONE);
                    binding.lendMoneyRecordExchangeRateTextview.setText("한국 환율 KRW(₩)");
                    showToast(binding.lendMoneyRecordExchangeRateTextview.getText().toString()+"가 선택되었습니다.");
                }
            }
        });

        binding.lendMoneyRecordLendDateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog lend_date_dialog = new DatePickerDialog(LendMoneyRecordActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                binding.lendMoneyRecordLendDateEditText.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                            }
                        }, year, month, day);
                lend_date_dialog.show();
            }
        });

        binding.lendMoneyRecordAcceptDateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog accept_date_dialog = new DatePickerDialog(LendMoneyRecordActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                binding.lendMoneyRecordAcceptDateEditText.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                            }
                        }, year, month, day);
                accept_date_dialog.show();
            }
        });

        binding.interestSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.lendMoneyRecordInterestCard.setVisibility(isChecked ? View.VISIBLE : View.GONE);
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
