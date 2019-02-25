package toka.com.example.androidproject;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = findViewById(R.id.profileListView);
        lv.setAdapter(new ArrayAdapter<Profile>(this, R.layout.profile_layout, ProfileSingleton.getInstance().getProfiles()));


    }
}
