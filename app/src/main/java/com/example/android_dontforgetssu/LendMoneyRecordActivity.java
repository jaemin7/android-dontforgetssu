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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_dontforgetssu.databinding.LendMoneyRecordBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class LendMoneyRecordActivity extends AppCompatActivity {
    private EditText etLendMoney, etLendDate, etLendAcceptDate, etBorrowerName, etBorrowerPhoneNumber, etMemo, etInterest, etExchangeRate;
    private Button btnRecordInfo;
    private int calculated_Money;
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

        etBorrowerName = findViewById(R.id.lend_money_record_name_editText);
        etBorrowerPhoneNumber = findViewById(R.id.lend_money_record_phonenumber_editText);
        etLendMoney = findViewById(R.id.lend_money_record_money_editText);
        etExchangeRate = findViewById(R.id.lend_money_record_exchange_rate_editText);
        etLendDate = findViewById(R.id.lend_money_record_lend_date_editText);
        etLendAcceptDate = findViewById(R.id.lend_money_record_accept_date_editText);
        etInterest = findViewById(R.id.lend_money_record_interest_editText);
        etMemo = findViewById(R.id.lend_money_record_memo_editText);

        btnRecordInfo = findViewById(R.id.lend_record_information_button);

        binding.globalSelectSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LendMoneyRecordActivity.this);
                    builder.setTitle("선택 가능 국가");
                    builder.setSingleChoiceItems(R.array.global_select_dialog, -1, dialogListener);
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

        btnRecordInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculated_Money = calculateMoney(etLendMoney,etExchangeRate);
                saveLendInfo();
            }
        });
    }

    private void saveLendInfo() {
        String borrowerName = etBorrowerName.getText().toString();
        String borrowerPhoneNumber = etBorrowerPhoneNumber.getText().toString();
        String lendMoney = etLendMoney.getText().toString();
        String exchangeRate = etExchangeRate.getText().toString();
        String lendDate = etLendDate.getText().toString();
        String lendAcceptDate = etLendAcceptDate.getText().toString();
        String interest = etInterest.getText().toString();
        String memo = etMemo.getText().toString();
        String country = binding.lendMoneyRecordExchangeRateTextview.getText().toString();
        String calculatedMoney = String.valueOf(calculated_Money);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference lendInfoCollection = db.collection("Member").document(uid).collection("빌려준 정보");

            LendInfo lendInfo = new LendInfo(borrowerName, borrowerPhoneNumber, lendMoney, exchangeRate, lendDate, lendAcceptDate, interest, memo, country, calculatedMoney);

            lendInfoCollection.add(lendInfo)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(LendMoneyRecordActivity.this, "빌려준 기록 저장 성공", Toast.LENGTH_SHORT).show();
                            // 저장 성공 후 필요한 작업 수행
                            String generatedDocumentId = documentReference.getId(); // 여기서 생성된 Document ID를 가져옵니다.
                            // 이제 이 Document ID를 Intent에 추가하여 LendRecordInformationActivity로 전달할 수 있습니다.
                            Intent intent = new Intent(LendMoneyRecordActivity.this, LendRecordInformationActivity.class);
                            intent.putExtra("documentId", generatedDocumentId);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LendMoneyRecordActivity.this, "빌려준 기록 저장 실패", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LendMoneyRecordActivity.this, LendRecordInformationActivity.class);
                            startActivity(intent);
                        }
                    });
        } else {
            // 사용자가 로그인되어 있지 않은 경우
            // 로그인 화면으로 이동하도록 처리
            Intent intent = new Intent(LendMoneyRecordActivity.this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(LendMoneyRecordActivity.this, "로그인 하세요", Toast.LENGTH_SHORT).show();
        }
    }
    private int calculateMoney(EditText etLendMoney, EditText etExchangeRate){
        if ("한국 환율 KRW(₩)".equals(binding.lendMoneyRecordExchangeRateTextview.getText().toString())) {
            float lend_money = Float.parseFloat(etLendMoney.getText().toString());
            float exchange_rate = 1;
            calculated_Money = (int) (lend_money * exchange_rate);
        }
        else {
            float lend_money = Float.parseFloat(etLendMoney.getText().toString());
            float exchange_rate = Float.parseFloat(etExchangeRate.getText().toString());
            calculated_Money = (int) (lend_money * exchange_rate);
        } return calculated_Money;
    }
}
