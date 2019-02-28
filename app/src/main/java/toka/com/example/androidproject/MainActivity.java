package toka.com.example.androidproject;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN MENU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button next = findViewById(R.id.button);
        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Log.d(TAG, "onItemClick");
                Intent myIntent = new Intent(view.getContext(), UserProfile.class);
                startActivity(myIntent);
            }
        });
    }
}

