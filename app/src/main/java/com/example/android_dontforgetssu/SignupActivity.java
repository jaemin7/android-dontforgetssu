package com.example.android_dontforgetssu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private EditText mEtEmail, mEtPassword, mEtName;
    private Button mSignBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_signup);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mEtEmail = findViewById(R.id.signEmail);
        mEtPassword = findViewById(R.id.signPassword);
        mEtName = findViewById(R.id.signName);
        mSignBtn = findViewById(R.id.signup_btn);

        mSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = mEtEmail.getText().toString();
                String strPassword = mEtPassword.getText().toString();
                String strName = mEtName.getText().toString();

                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                    String uid = firebaseUser.getUid();

                                    // 사용자 정보를 Member 컬렉션에 저장
                                    Member member = new Member(uid, strName, strEmail);
                                    FirebaseFirestore.getInstance().collection("Member")
                                            .document(uid)
                                            .set(member)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(SignupActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                                                        startActivity(intent);
                                                        // 나머지 코드 추가
                                                    } else {
                                                        Toast.makeText(SignupActivity.this, "Firestore에 정보 저장 실패", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(SignupActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
