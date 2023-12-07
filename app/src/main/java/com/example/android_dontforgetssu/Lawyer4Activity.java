package com.example.android_dontforgetssu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Lawyer4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyer4);
        Button back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Lawyer4Activity.this, LawyerActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
