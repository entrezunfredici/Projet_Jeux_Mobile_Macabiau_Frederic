package com.example.intentapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Bundle extras = getIntent().getExtras();
        String s = new String(extras.getString("name"));
        TextView name = findViewById(R.id.textView);
        name.setText("Hi, " + s);
    }

    @Override
    public void onBackPressed() {
        setResult(50);
        super.onBackPressed();
    }
}
