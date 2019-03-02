package toka.com.example.androidproject;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

public class Leaderboard extends AppCompatActivity {

    public static final String EXTRA = "toka.com.example.androidproject.MESSAGE";
    ProfileSingleton profile = ProfileSingleton.getInstance();

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");

        Bundle b = getIntent().getExtras();
        i = b.getInt(EXTRA, 0);
        profile.updateLeaderboards();

        TextView tx = findViewById(R.id.firstPlaceText);
        TextView tv = findViewById(R.id.secondPlaceText);
        TextView ty = findViewById(R.id.thirdPlaceText);

        tx.setTypeface(typeface);
        tv.setTypeface(typeface);
        ty.setTypeface(typeface);
    }

    @Override
    public void onPause() {
        super.onPause();

        List<Profile> profileList = profile.getProfiles();
        SharedPreferences mPrefs = getSharedPreferences(EXTRA, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String object = gson.toJson(profileList);
        prefsEditor.putString("Profiles", object);
        prefsEditor.commit();
    }
}
