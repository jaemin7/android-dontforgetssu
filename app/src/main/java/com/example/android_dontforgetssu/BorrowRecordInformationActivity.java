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

import com.example.android_dontforgetssu.databinding.BorrowRecordInformationBinding;
import com.example.android_dontforgetssu.databinding.LendRecordInformationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BorrowRecordInformationActivity extends AppCompatActivity {
    private CheckBox checkBox4;
    BorrowRecordInformationBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public BorrowRecordInformationActivity(){
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
                            binding.borrowInformationBorrowPerson.setText(""+userName);
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
        binding = BorrowRecordInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //이전화면에서 서버에 저장했던 정보 불러오기
        Intent intent = getIntent();
        String documentId = intent.getStringExtra("documentId");
        displayBorrowRecordInfo(documentId);
        getCurrentUserName();

        checkBox4=findViewById(R.id.checkBox4);
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkBoxChecked = checkBox4.isChecked();
                if(checkBoxChecked){
                    Intent promissoryNoteIntent = new Intent(BorrowRecordInformationActivity.this, BorrowPromissoryNoteActivity.class);
                    startActivity(promissoryNoteIntent);
                }else{
                    Intent homeIntent = new Intent(BorrowRecordInformationActivity.this, NavigationActivity.class);
                    homeIntent.putExtra("fragmentToLoad","home");
                    startActivity(homeIntent);
                }
            }
        });
        Button back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(BorrowRecordInformationActivity.this, BorrowMoneyRecordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //서버에서 불러와서 보여주는 함수
    private void displayBorrowRecordInfo(String documentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();
            CollectionReference borrowInfoCollection = db.collection("Member").document(uid).collection("빌린 정보");

            DocumentReference borrowRecordDocRef = borrowInfoCollection.document(documentId);
            borrowRecordDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        // Document에서 필요한 정보 가져오기
                        String lenderName = documentSnapshot.getString("lenderName");
                        String borrowMoney = documentSnapshot.getString("borrowMoney");
                        String exchangeRate = documentSnapshot.getString("exchangeRate");
                        String borrowDate = documentSnapshot.getString("borrowDate");
                        String borrowAcceptDate = documentSnapshot.getString("borrowAcceptDate");
                        String interest = documentSnapshot.getString("interest");
                        String country = documentSnapshot.getString("country");

                        // 가져온 정보를 텍스트뷰에 표시
                        binding.borrowInformationBorrowMoney.setText(borrowMoney);
                        binding.borrowInformationBorrowDate.setText(borrowDate);
                        binding.borrowInformationAcceptDate.setText(borrowAcceptDate);
                        if (country.equals("한국 환율 KRW(₩)")||country.equals("")){
                            binding.borrowInformationCountryRow.setVisibility(View.GONE);
                            binding.borrowInformationExchangeRateRow.setVisibility(View.GONE);
                        } else {
                            binding.borrowInformationCountry.setText(country);
                            binding.borrowInformationExchangeRate.setText(exchangeRate);
                        }
                        if (interest.equals("")){
                            binding.borrowInformationInterestRow.setVisibility(View.GONE);
                        } else {
                            binding.borrowInformationInterest.setText(interest);
                        }
                        binding.borrowInformationLendPerson.setText(lenderName);

                    } else {
                        Log.d("BorrowRecordInfoActivity", "No such document");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("BorrowRecordInfoActivity", "Error getting document", e);
                }
            });
        }
    }
}
