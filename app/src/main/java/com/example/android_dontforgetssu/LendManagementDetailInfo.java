package com.example.android_dontforgetssu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_dontforgetssu.databinding.LendManagementDetailInfoBinding;

public class LendManagementDetailInfo extends AppCompatActivity {

    LendManagementDetailInfoBinding binding;

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
    }
}
