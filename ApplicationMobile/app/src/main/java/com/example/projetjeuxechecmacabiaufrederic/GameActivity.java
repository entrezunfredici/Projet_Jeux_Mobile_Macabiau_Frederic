package com.example.projetjeuxechecmacabiaufrederic;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private static final int [] borderColor={255,23,23,26};
    private static final int [] casesColor1={255,67,47,20};
    private static final int [] casesColor2={255,232,220,202};
    private static final int [] casesColorGreen={255,9,106,9};
    private static final int [] casesColorRed={255,240,0,32};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Button bDiscontinued=findViewById(R.id.btnDiscontinuedParty);

    }
}
