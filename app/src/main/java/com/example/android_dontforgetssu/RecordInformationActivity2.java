package com.example.android_dontforgetssu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class RecordInformationActivity2 extends AppCompatActivity{
    private CheckBox checkBox4;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_information);

        checkBox4=findViewById(R.id.checkBox4);
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkBoxChecked = checkBox4.isChecked();
                if(checkBoxChecked){
                    Intent promissoryNoteIntent = new Intent(RecordInformationActivity2.this,PromissoryNote2Activity.class);
                    startActivity(promissoryNoteIntent);
                }else{
                    Intent homeIntent = new Intent(RecordInformationActivity2.this, NavigationActivity.class);
                    homeIntent.putExtra("fragmentToLoad","home");
                    startActivity(homeIntent);
                }
            }
        });
        Button back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(RecordInformationActivity2.this, BorrowMoney2Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
