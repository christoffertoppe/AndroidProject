package toka.com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import toka.com.example.androidproject.R;

public class UserProfile extends AppCompatActivity {



    private int washCount = 148;
    private String nimi = "Matti";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile_layout);

        showTip();
        washCounter();
        welcomeText();

    }

    private void welcomeText() {
        TextView tvWelcome = findViewById(R.id.welcomeText);
        tvWelcome.setText("Terve, " + nimi);
    }



    private void washCounter() {
        TextView tvWash = findViewById(R.id.washCount);
        if(washCount == 0 || 1 < washCount && washCount < 150) {
            tvWash.setText("Olet harjannut hampaitasi: " + Integer.toString(washCount)+ " kertaa.");
        } else if (washCount >= 150) {
            tvWash.setText("Olet harjannut hampaitasi: "+ Integer.toString(washCount) + " kertaa." + "\nOlisi hyvä vaihtaa hammasharja uuteen.");
        } else {
            tvWash.setText("Olet harjannut hampaitasi: " +  Integer.toString(washCount)+ " kerran.");
        }


    }


    private void showTip() {
        int listSize = UsefulTips.getInstance().getListSize();
        Random rand = new Random();
        int listValue = rand.nextInt(listSize);
        String tip = UsefulTips.getInstance().getTip(listValue);
        TextView tvTip = findViewById(R.id.tipView);
        tvTip.setText(tip);
    }

    public void buttonPressed(View view) {
        showTip();
        washCount++;
        washCounter();
    }

}