package com.example.shubham.myminesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        editText=findViewById(R.id.name);
        button=findViewById(R.id.start);
//        editText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                name = editText.getText().toString();
//            }
//        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StartActivity.this,MainActivity.class);
                intent.putExtra("intentname",editText.getText().toString());
                startActivity(intent);
            }
        });
    }
}
