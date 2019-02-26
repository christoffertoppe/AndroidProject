package toka.com.example.androidproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ProfileSingleton profile = ProfileSingleton.getInstance();
    private static String TAG = "Troubleshoot";
    public static final String EXTRA = "toka.com.example.androidproject.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.profileView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");
        tv.setTypeface(typeface);

        ListView lv = findViewById(R.id.profileListView);
        lv.setAdapter(new ArrayAdapter<Profile>(this, R.layout.profile_layout, ProfileSingleton.getInstance().getProfiles()));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick(" + i + ")");
                Intent nextActivity = new Intent(MainActivity.this, DummyActivity.class);
                nextActivity.putExtra(EXTRA, i);
                startActivity(nextActivity);
            }
        });
    }

    public void buttonPressed(View view) {
        if (view == findViewById(R.id.newProfileButton)) {
            setContentView(R.layout.create_profile_layout);
            TextView tv = findViewById(R.id.newProfileView);
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");
            tv.setTypeface(typeface);
        } else if (view == findViewById(R.id.saveButton)) {
            EditText nameText = (EditText) findViewById(R.id.insertNameText);
            String name = nameText.getText().toString();
            EditText ageText = (EditText) findViewById(R.id.insertAgeText);
            String age = ageText.getText().toString();
            int ageToNumber = Integer.parseInt(age);
            profile.addProfile(name, ageToNumber);
            updateUI();
        } else if (view == findViewById(R.id.cancelButton)) {
            updateUI();
        }
    }

    private void updateUI() {
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.profileView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");
        tv.setTypeface(typeface);

        ListView lv = findViewById(R.id.profileListView);
        lv.setAdapter(new ArrayAdapter<Profile>(this, R.layout.profile_layout, ProfileSingleton.getInstance().getProfiles()));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick(" + i + ")");
                Intent nextActivity = new Intent(MainActivity.this, DummyActivity.class);
                nextActivity.putExtra(EXTRA, i);
                startActivity(nextActivity);
            }
        });
    }
}
