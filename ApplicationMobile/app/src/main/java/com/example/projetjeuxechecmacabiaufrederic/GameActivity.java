package com.example.projetjeuxechecmacabiaufrederic;

import static java.lang.Boolean.TRUE;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public class GameActivity extends AppCompatActivity {
    private static final int[] casesColor1 = {255, 67, 47, 20};
    private static final int[] casesColor2 = {255, 232, 220, 202};
    private static final int[] casesColorGreen = {255, 9, 106, 9};
    private static final int[] casesColorRed = {255, 240, 0, 32};
    private Cases[][] echiquier = new Cases[8][8];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Button bDiscontinued = findViewById(R.id.btnDiscontinuedParty);
        EditText player = findViewById(R.id.player);
        Log.v("log","ready top play");
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                String identifier = "Case" + (i+1);
                switch ((k+1)) {
                    case 1:
                        identifier = identifier + "A";
                        break;
                    case 2:
                        identifier = identifier + "B";
                        break;
                    case 3:
                        identifier = identifier + "C";
                        break;
                    case 4:
                        identifier = identifier + "D";
                        break;
                    case 5:
                        identifier = identifier + "E";
                        break;
                    case 6:
                        identifier = identifier + "F";
                        break;
                    case 7:
                        identifier = identifier + "G";
                        break;
                    default:
                        identifier = identifier + "H";
                        break;
                }
                echiquier[i][k] = findViewById(getResources().getIdentifier(identifier, "id", getPackageName()));
                if ((i%2)==0) {
                    if ((k%2)==0) {
                        echiquier[i][k].defColor(casesColor1);
                    } else {
                        echiquier[i][k].defColor(casesColor2);
                    }
                } else {
                    if ((k%2)==0) {
                        echiquier[i][k].defColor(casesColor2);
                    } else {
                        echiquier[i][k].defColor(casesColor1);
                    }
                }
            }
        }
    }
}
