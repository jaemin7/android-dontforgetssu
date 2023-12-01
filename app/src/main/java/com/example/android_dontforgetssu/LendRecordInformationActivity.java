package com.example.android_dontforgetssu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_dontforgetssu.databinding.LendRecordInformationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LendRecordInformationActivity extends AppCompatActivity {
    private CheckBox checkBox4;
    LendRecordInformationBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public LendRecordInformationActivity(){
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    //현재 사용자 가져오기
    private void getCurrentUserName(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Firestore에서 사용자 정보 가져오기
            db.collection("Member")
                    .document(currentUser.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // 사용자 정보가 Firestore에 있는 경우
                            String userName = documentSnapshot.getString("name");
                            binding.lendInformationLendPerson.setText(""+userName);
                            if (userName != null && !userName.isEmpty()) {
                                // 사용자 이름이 존재하는 경우
                                System.out.println("현재 사용자의 이름: " + userName);
                            } else {
                                System.out.println("사용자 이름이 없습니다.");
                            }
                        } else {
                            System.out.println("Firestore에 해당 사용자의 정보가 없습니다.");
                        }
                    })
                    .addOnFailureListener(e -> {
                        System.err.println("Firestore에서 사용자 정보 가져오기 실패: " + e.getMessage());
                    });
        } else {
            // 사용자가 로그인하지 않은 경우
            System.out.println("로그인 안 함");
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LendRecordInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //이전화면에서 서버에 저장했던 정보 불러오기
        Intent intent = getIntent();
        String documentId = intent.getStringExtra("documentId");
        displayLendRecordInfo(documentId);
        getCurrentUserName();

        checkBox4=findViewById(R.id.checkBox4);
       Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkBoxChecked = checkBox4.isChecked();
                if(checkBoxChecked){
                    Intent promissoryNoteIntent = new Intent(LendRecordInformationActivity.this, LendPromissoryNoteActivity.class);
                    startActivity(promissoryNoteIntent);
                }else{
                    Intent homeIntent = new Intent(LendRecordInformationActivity.this, NavigationActivity.class);
                    homeIntent.putExtra("fragmentToLoad","home");
                    startActivity(homeIntent);
                }
            }
        });
        Button back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LendRecordInformationActivity.this, LendMoneyRecordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //서버에서 불러와서 보여주는 함수
    private void displayLendRecordInfo(String documentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();
            CollectionReference lendInfoCollection = db.collection("Member").document(uid).collection("빌려준 정보");

            DocumentReference lendRecordDocRef = lendInfoCollection.document(documentId);
            lendRecordDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        // Document에서 필요한 정보 가져오기
                        String borrowerName = documentSnapshot.getString("borrowerName");
                        String lendMoney = documentSnapshot.getString("lendMoney");
                        String exchangeRate = documentSnapshot.getString("exchangeRate");
                        String lendDate = documentSnapshot.getString("lendDate");
                        String lendAcceptDate = documentSnapshot.getString("lendAcceptDate");
                        String interest = documentSnapshot.getString("interest");
                        String country = documentSnapshot.getString("country");

                        // 가져온 정보를 텍스트뷰에 표시
                        binding.lendInformationLendMoney.setText(lendMoney);
                        binding.lendInformationLendDate.setText(lendDate);
                        binding.lendInformationAcceptDate.setText(lendAcceptDate);
                        if (country.equals("한국 환율 KRW(₩)")||country.equals("")){
                            binding.lendInformationCountryRow.setVisibility(View.GONE);
                            binding.lendInformationExchangeRateRow.setVisibility(View.GONE);
                        } else {
                            binding.lendInformationCountry.setText(country);
                            binding.lendInformationExchangeRate.setText(exchangeRate);
                        }
                        if (interest.equals("")){
                            binding.lendInformationInterestRow.setVisibility(View.GONE);
                        } else {
                            binding.lendInformationInterest.setText(interest);
                        }
                        binding.lendInformationBorrowPerson.setText(borrowerName);

                    } else {
                        Log.d("LendRecordInfoActivity", "No such document");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("LendRecordInfoActivity", "Error getting document", e);
                }
            });
        }
    }
}
