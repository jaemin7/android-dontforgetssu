package com.example.android_dontforgetssu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_dontforgetssu.databinding.LendManagementDetailInfoBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class LendManagementDetailInfo extends AppCompatActivity {

    LendManagementDetailInfoBinding binding;
    AlertDialog alertDialog;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = LendManagementDetailInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        LendInfo lendInfo = (LendInfo) intent.getSerializableExtra("lendInfo");

        binding.lendManagementName.setText(lendInfo.getBorrowerName());
        binding.lendManagementMoney.setText(lendInfo.getCalculatedMoney()+"원");
        binding.lendManagementLendDate.setText(lendInfo.getLendDate());
        binding.lendManagementLendAcceptDate.setText(lendInfo.getLendAcceptDate());
        binding.lendManagementPhoneNumber.setText(lendInfo.getBorrowerPhoneNumber());

        if (lendInfo.getCountry().equals("한국 환율 KRW(₩)")||lendInfo.getCountry().equals("")) {
            binding.lendManagementCountryCard.setVisibility(View.GONE);
            binding.lendManagementExchangeRateCard.setVisibility(View.GONE);
        } else {
            String[] parts = lendInfo.getCountry().split("\\s+");
            // 띄어쓰기 전 글자 얻기 -> 빌린 국가의 나라 이름 얻기
            if (parts.length > 0) {
                String foreign = parts[0];
                binding.lendManagementCountry.setText(foreign);
                binding.lendManagementCountryMoney.setText(lendInfo.getLendMoney());
                binding.lendManagementExchangeRate.setText(lendInfo.getExchangeRate());
                switch (lendInfo.getCountry()){
                    case("미국 환율 USD($)"):
                    binding.lendManagementCountryUnit.setText("달러($)"); break;
                    case("유럽 환율 EUR(€)"):
                    binding.lendManagementCountryUnit.setText("유로(€)"); break;
                    case("일본 환율 JPY(¥)"):
                    binding.lendManagementCountryUnit.setText("엔(¥)"); break;
                    case("중국 환율 CNY(¥)"):
                    binding.lendManagementCountryUnit.setText("위안(¥)"); break;
                    case("베트남 환율 VND(₫)"):
                    binding.lendManagementCountryUnit.setText("동(₫)"); break;
                    default:
                        break;
                }
            }  else {
                Log.d("LendManagementDetailInfo", "lendInfo.getCountry를 못 불러왔다.");
            }
        }
        if (lendInfo.getInterest().equals("")) {
            binding.lendManagementInterestCard.setVisibility(View.GONE);
        } else {
            binding.lendManagementInterest.setText(lendInfo.getInterest()+"원");
        }
        if (lendInfo.getMemo().equals("")) {
            binding.lendManagementMemo.setText("메모가 없습니다.");
        } else {
            binding.lendManagementMemo.setText(lendInfo.getMemo());
        }

        binding.lendManagementTransactionOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LendManagementDetailInfo.this);
                builder.setTitle("거래 완료");
                builder.setMessage("정말 거래 완료 되었습니까? \n 거래 정보가 완료된 거래로 이동됩니다.");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (lendInfo != null) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            CollectionReference lendInfoCollection = db.collection("Member").document(uid).collection("빌려준 정보");

                            lendInfoCollection.whereEqualTo("borrowerName", lendInfo.getBorrowerName())
                                    .whereEqualTo("calculatedMoney", lendInfo.getCalculatedMoney())
                                    .get().addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                String documentId = document.getId();

                                                // 정보를 완료된 거래 컬렉션으로 이동
                                                db.collection("Member").document(uid).collection("완료된 거래").document(documentId)
                                                        .set(document.getData())  // Copy data to CompletedTransactions
                                                        .addOnSuccessListener(aVoid -> {
                                                            // 완료된 거래로 이동 성공 시의 처리
                                                            // 원래의 정보 삭제
                                                            document.getReference().delete()
                                                                    .addOnSuccessListener(aVoid1 -> {
                                                                        // 리사이클러뷰 갱신
                                                                        startActivity(new Intent(LendManagementDetailInfo.this, NavigationActivity.class));
                                                                        finish();
                                                                    });
                                                        });
                                            }
                                        } else {
                                            Log.d("LendManagementDetailInfo", "Error", task.getException());
                                        }
                                    });
                        }
                    }
                });
                builder.setNegativeButton("취소", null);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}
