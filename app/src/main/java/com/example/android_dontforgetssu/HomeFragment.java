package com.example.android_dontforgetssu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_dontforgetssu.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private TextView lendSumTextView,borrowSumTextView;
    private TextView lendCountTextView,borrowCountTextView;
    private TextView currentUserName;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public HomeFragment(){
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button homeButton = view.findViewById(R.id.record_money_button);

        lendSumTextView = view.findViewById(R.id.fragment_home_lend_sum);
        lendCountTextView = view.findViewById(R.id.fragment_home_lend_number);

        borrowSumTextView = view.findViewById(R.id.fragment_home_borrow_sum);
        borrowCountTextView = view.findViewById(R.id.fragment_home_borrow_number);

        currentUserName = view.findViewById(R.id.fragment_home_userName);

        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent recordMoneyIntent = new Intent(getActivity(), RecordMoneyActivity.class);
                startActivity(recordMoneyIntent);
            }
        });

        getCurrentUserName();
        sumLendCalculatedNumbers();
        sumBorrowCalculatedNumbers();

        return view;
    }

    //사용자 이름 가져오는 함수
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
                            currentUserName.setText(""+userName);
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
    // 사용자의 빌려준 정보의 calculatedNumber를 모두 더하는 메서드
    private void sumLendCalculatedNumbers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();

            // 현재 사용자의 '빌려준 정보' 컬렉션에 대한 참조 생성
            CollectionReference lendInfoCollection = db.collection("Member").document(uid).collection("빌려준 정보");

            lendInfoCollection.get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        int totalCalculatedMoney = 0;
                        int lendInfoCount = queryDocumentSnapshots.size();

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            // document에서 calculatedNumber를 가져와서 더함
                            if (document.contains("calculatedMoney")) {
                                String calculatedMoneyString = document.getString("calculatedMoney");
                                int calculatedMoney = Integer.parseInt(calculatedMoneyString);
                                totalCalculatedMoney += calculatedMoney;
                            }
                        }

                        // 총합 출력 또는 다른 작업 수행
                        lendSumTextView.setText(String.valueOf(totalCalculatedMoney));
                        lendCountTextView.setText(String.valueOf(lendInfoCount));
                    })
                    .addOnFailureListener(e -> {
                        // 데이터를 가져오지 못했을 때의 처리
                        Toast.makeText(getActivity(), "데이터 가져오기 실패", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // 사용자가 로그인되어 있지 않은 경우
            // 로그인 화면으로 이동하도록 처리
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            Toast.makeText(getActivity(), "로그인 하세요", Toast.LENGTH_SHORT).show();
        }
    }
    // 사용자의 빌린 정보의 calculatedNumber를 모두 더하는 메서드
    private void sumBorrowCalculatedNumbers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();

            // 현재 사용자의 '빌려준 정보' 컬렉션에 대한 참조 생성
            CollectionReference borrowInfoCollection = db.collection("Member").document(uid).collection("빌린 정보");

            borrowInfoCollection.get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        int totalCalculatedMoney = 0;
                        int borrowInfoCount = queryDocumentSnapshots.size();

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            // document에서 calculatedNumber를 가져와서 더함
                            if (document.contains("calculatedMoney")) {
                                String calculatedMoneyString = document.getString("calculatedMoney");
                                int calculatedMoney = Integer.parseInt(calculatedMoneyString);
                                totalCalculatedMoney += calculatedMoney;
                            }
                        }

                        // 총합 출력 또는 다른 작업 수행
                        borrowSumTextView.setText(String.valueOf(totalCalculatedMoney));
                        borrowCountTextView.setText(String.valueOf(borrowInfoCount));
                    })
                    .addOnFailureListener(e -> {
                        // 데이터를 가져오지 못했을 때의 처리
                        Toast.makeText(getActivity(), "데이터 가져오기 실패", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // 사용자가 로그인되어 있지 않은 경우
            // 로그인 화면으로 이동하도록 처리
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            Toast.makeText(getActivity(), "로그인 하세요", Toast.LENGTH_SHORT).show();
        }
    }
}