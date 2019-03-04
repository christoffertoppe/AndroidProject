package toka.com.example.androidproject;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        Bundle b = getIntent().getExtras();
        i = b.getInt(EXTRA, 0);

        profile.updateLeaderboards();
        updatePodiumProfiles();
    }

    private void updatePodiumProfiles() {
        for (int i = 0; i < profile.getProfilesSize(); i++) {
            if (profile.getProfile(i).getLeaderboardRanking() == 1) {
                TextView firstPlace = findViewById(R.id.firstPlaceText);
                firstPlace.setText(profile.getProfile(i).getName());

                TextView firstPlaceCongratulation = findViewById(R.id.firstPlaceCongratulation);
                firstPlaceCongratulation.setText("Ahkerin harjaaja on:\n" + profile.getProfile(i).getName() + "!");

                updateUI();
            } else if (profile.getProfile(i).getLeaderboardRanking() == 2) {
                TextView secondPlace = findViewById(R.id.secondPlaceText);
                secondPlace.setText(profile.getProfile(i).getName());

                TextView secondPlaceCongratulation = findViewById(R.id.secondPlaceCongratulation);
                secondPlaceCongratulation.setText("Hopeasijalla juhlii:\n" + profile.getProfile(i).getName() + "!");

                updateUI();
            } else if (profile.getProfile(i).getLeaderboardRanking() == 3) {
                TextView thirdPlace = findViewById(R.id.thirdPlaceText);
                thirdPlace.setText(profile.getProfile(i).getName());

                TextView thirdPlaceCongratulation = findViewById(R.id.thirdPlaceCongratulation);
                thirdPlaceCongratulation.setText("Pronssisijan on saavuttanut:\n" + profile.getProfile(i).getName() + "!");

                updateUI();
            }
        }
    }

    private void updateUI() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");

        TextView gold = findViewById(R.id.firstPlaceText);
        TextView silver = findViewById(R.id.secondPlaceText);
        TextView bronze = findViewById(R.id.thirdPlaceText);

        TextView goldCongratulations = findViewById(R.id.firstPlaceCongratulation);
        TextView silverCongratulations = findViewById(R.id.secondPlaceCongratulation);
        TextView bronzeCongratulations = findViewById(R.id.thirdPlaceCongratulation);

        gold.setTypeface(typeface);
        silver.setTypeface(typeface);
        bronze.setTypeface(typeface);

        goldCongratulations.setTypeface(typeface);
        silverCongratulations.setTypeface(typeface);
        bronzeCongratulations.setTypeface(typeface);
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
