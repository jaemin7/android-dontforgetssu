package com.example.android_dontforgetssu;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LawyerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyer);

        LinearLayout law1Layout = findViewById(R.id.law1);
        LinearLayout law2Layout = findViewById(R.id.law2);
        LinearLayout law3Layout = findViewById(R.id.law3);
        LinearLayout law4Layout = findViewById(R.id.law4);
        LinearLayout law5Layout = findViewById(R.id.law5);
        LinearLayout law6Layout = findViewById(R.id.law6);


            Button back_btn = findViewById(R.id.back_btn);
            back_btn.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(LawyerActivity.this, HomeFragment.class);
                    startActivity(intent);
                    finish();
                }
            });
        law1Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LawyerActivity.this, Lawyer1Activity.class);
                startActivity(intent);
            }
        });


        law2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LawyerActivity.this, Lawyer2Activity.class);
                startActivity(intent);
            }
        });

        // law3 클릭 이벤트 처리
        law3Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LawyerActivity.this, Lawyer3Activity.class);
                startActivity(intent);
            }
        });

        // law4 클릭 이벤트 처리
        law4Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LawyerActivity.this, Lawyer4Activity.class);
                startActivity(intent);
            }
        });
        law5Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LawyerActivity.this, Lawyer5Activity.class);
                startActivity(intent);
            }
        });
        law6Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LawyerActivity.this, Lawyer6Activity.class);
                startActivity(intent);
            }
        });

    }
}