package toka.com.example.androidproject;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Esimerkki fontin vaihdosta
        TextView tx = findViewById(R.id.exampleView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");
        tx.setTypeface(typeface);

    }
}
