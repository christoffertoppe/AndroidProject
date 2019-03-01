package toka.com.example.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;
import java.util.Random;

import toka.com.example.androidproject.R;

public class UserProfile extends AppCompatActivity {

    public static final String EXTRA = "toka.com.example.androidproject.MESSAGE";
    ProfileSingleton profile = ProfileSingleton.getInstance();

    int i = 0;

    private boolean secondViewActive = false;   //////////////////////////////////////////////////////////////////////////////////////////////////////
    String[] spinnerSongs = new String[]{"Toto - Africa", "Baby Shark", "Rip & Tear"};  //////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile_layout);

        Bundle b = getIntent().getExtras();
        i = b.getInt(EXTRA, 0);

        showTip();
        washCounter();
        welcomeText();

    }

    private void welcomeText() {
        TextView tvWelcome = findViewById(R.id.welcomeText);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf"); //
        tvWelcome.setTypeface(typeface);
        tvWelcome.setText("Terve, " + profile.getProfile(i).getName());
    }



    private void washCounter() {
        TextView tvWash = findViewById(R.id.washCount);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");
        tvWash.setTypeface(typeface);

        int washCount = profile.getProfile(i).getBrushingTotal();
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
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");
        tvTip.setTypeface(typeface);

        tvTip.setText(tip);
    }

    public void buttonPressed(View view) {  //////////////////////////////////////////////////////////////////////////////////////////////////////
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");
        if (view == findViewById(R.id.startButton)) {

            Intent nextActivity = new Intent(UserProfile.this, BrushTimer.class);
            nextActivity.putExtra(EXTRA, i);
            startActivity(nextActivity);

        } else if (view == findViewById(R.id.settingsButton)) {
            secondViewActive = true;
            setContentView(R.layout.settings_layout);

            TextView tv = findViewById(R.id.settingsView);
            tv.setTypeface(typeface);
            TextView tx = findViewById(R.id.chooseSongView);
            tx.setTypeface(typeface);

            Spinner songMenu = findViewById(R.id.songSpinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerSongs);
            songMenu.setAdapter(adapter);
            songMenu.setSelection(profile.getProfile(i).getSelectedSong());

            songMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int selectedSong = parent.getSelectedItemPosition();
                    profile.getProfile(i).setSelectedSong(selectedSong);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else if(view == findViewById(R.id.leaderboardButton)) {
            Intent nextActivity = new Intent(UserProfile.this, Leaderboard.class);
            nextActivity.putExtra(EXTRA, i);
            startActivity(nextActivity);
        }
    }

    @Override
    public void onPause() { //////////////////////////////////////////////////////////////////////////////////////////////////////
        super.onPause();

        List<Profile> profileList = profile.getProfiles();
        SharedPreferences mPrefs = getSharedPreferences(EXTRA, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String object = gson.toJson(profileList);
        prefsEditor.putString("Profiles", object);
        prefsEditor.commit();
    }

    @Override
    public void onBackPressed() {   //////////////////////////////////////////////////////////////////////////////////////////////////////
        if (secondViewActive) {
            secondViewActive = false;
            setContentView(R.layout.userprofile_layout);
            showTip();
            washCounter();
            welcomeText();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {    //////////////////////////////////////////////////////////////////////////////////////////////////////
        super.onResume();
        washCounter();
        welcomeText();
    }
}