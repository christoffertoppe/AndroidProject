package toka.com.example.androidproject;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DummyActivity extends AppCompatActivity {
    public static final String EXTRA = "toka.com.example.androidproject.MESSAGE";
    ProfileSingleton profile = ProfileSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        Bundle b = getIntent().getExtras();
        int i = b.getInt(EXTRA, 0);

        TextView tv = findViewById(R.id.numberView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");
        tv.setTypeface(typeface);
        tv.setText(Integer.toString(i));
        TextView tx = findViewById(R.id.ageView);
        tx.setTypeface(typeface);
        tx.setText(Integer.toString(profile.getProfile(i).getAge()));
    }
}
