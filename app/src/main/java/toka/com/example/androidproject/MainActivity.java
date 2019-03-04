package toka.com.example.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * MainActivity käsittelee käyttäjäprofiilien valitsemista, luontia ja poistamista.
 * Jokaiseen käyttäjäprofiiliin on talletettu käyttäjän henkilökohtaiset tiedot,
 * jotka sovellus kirjaa muistiin suljettaessa.
 *
 * @author Samuli Salin
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {


    private static final String PREF = "TestPref";
    boolean has_the_app_been_run_before;

    private ProfileSingleton profile = ProfileSingleton.getInstance();
    private boolean secondViewActive = false;

    public static final String EXTRA = "toka.com.example.androidproject.MESSAGE";

    /**
     * Luo tyhjän MainActivityn.
     */

    public MainActivity() {

    }

    /**
     * Määrittelee operaatiot, jotka tehdään OnCreate -kutsun yhteydessä.
     * Esim. haetaan aiemmin muistiin tallennettu data käyttäen avuksi Gson -kirjastoa
     * ja päivitetään käyttöliittymä näyttämään ListView -näkymä
     * profiileista.
     * Jos sovellusta ei ole käynnistetty aikaisemmin, tallennettua käyttäjälistaa ei haeta vaan sovellus
     * siirtää käyttäjän suoraan profiilin luonti -laýouttiin.
     *
     * @param savedInstanceState Bundle viittaus Bundle-objektiin
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tallennettujen tietojen hakeminen käyttäen Gson-kirjastoa apuna

        SharedPreferences prefGet = getSharedPreferences(PREF, MainActivity.MODE_PRIVATE);
        has_the_app_been_run_before = prefGet.getBoolean("App", false);

        if (has_the_app_been_run_before == true) {
            SharedPreferences mPrefs = getSharedPreferences(EXTRA, MODE_PRIVATE);
            String json = mPrefs.getString("Profiles", "");
            Type type = new TypeToken<List<Profile>>() {
            }.getType();
            List<Profile> savedProfiles = new Gson().fromJson(json, type);
            profile.setProfiles(savedProfiles);

            updateUI();
        } else {
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");

            secondViewActive = true;
            setContentView(R.layout.create_profile_layout);

            TextView tv = findViewById(R.id.newProfileView);
            TextView nu = findViewById(R.id.newUserView);
            TextView av = findViewById(R.id.ageView);

            tv.setTypeface(typeface);
            nu.setTypeface(typeface);
            av.setTypeface(typeface);
        }

    }

    /**
     * Määrittelee mitä ruudulla tapahtuu kun nappia painetaan.
     * Nappeja on yhteensä neljä, joilla kaikilla on omat operaationsa, jotka on
     * määritelty erillisillä if-lauseilla.
     *
     * @param view View kertoo mikä näkymä(view) on käytössä
     */

    // Luodaan nappien painamisesta tapahtuvat operaatiot
    public void buttonPressed(View view) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");

        if (view == findViewById(R.id.newProfileButton)) {
            secondViewActive = true;
            setContentView(R.layout.create_profile_layout);

            TextView tv = findViewById(R.id.newProfileView);
            TextView nu = findViewById(R.id.newUserView);
            TextView av = findViewById(R.id.ageView);

            tv.setTypeface(typeface);
            nu.setTypeface(typeface);
            av.setTypeface(typeface);

        } else if (view == findViewById(R.id.deleteUserButton)) {
            EditText nameText = (EditText) findViewById(R.id.insertDeleteName);
            String name = nameText.getText().toString().trim();
            // Käyttäjälle annetaan ilmoitus onnistuiko profiilin poistaminen vai ei
            if (profile.deleteProfile(name)) {
                Toast toast = Toast.makeText(getApplicationContext(), "Käyttäjä poistettu onnistuneesti!", Toast.LENGTH_LONG);
                updateUI();
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Käyttäjää ei löytynyt!\nTarkista oikeinkirjoitus", Toast.LENGTH_LONG);
                toast.show();
            }
        } else if (view == findViewById(R.id.saveButton)) {
            EditText nameText = (EditText) findViewById(R.id.insertNameText);
            String name = nameText.getText().toString().trim();

            EditText ageText = (EditText) findViewById(R.id.insertAgeText);
            String age = ageText.getText().toString();

            // Jos käyttäjä syöttää tyhjän kentän niin iälle annetaan automaattisesti arvo 0 (Arvo ei kelpaa profiilia luodessa)
            if (age.equals("")) {
                age = "0";
            }
            int ageToNumber = Integer.parseInt(age);

            if (!(profile.addProfile(name, ageToNumber))) {
                Toast toast = Toast.makeText(getApplicationContext(), "Tarkista tietojen ehdot tai nimi on jo olemassa!", Toast.LENGTH_LONG);
                toast.show();
            } else {
                updateUI();

                // Snackbar ilmoitus, joka ilmoittaa uuden käyttäjän luomisesta
                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.snackBarLayout), R.string.snackbar_new_profile_created, Snackbar.LENGTH_LONG);
                mySnackbar.show();
            }
        } else if (view == findViewById(R.id.deleteProfileButton)) {
            secondViewActive = true;
            setContentView(R.layout.delete_profile_layout);

            TextView tv = findViewById(R.id.deleteProfileView);
            TextView dp = findViewById(R.id.deleteNameView);

            tv.setTypeface(typeface);
            dp.setTypeface(typeface);
        }
    }

    /**
     * Päivittää käyttöliittymän päänäkymään, josta käyttäjä voi valita profiilin.
     * Tekstikentille haetaan mukautettu fontti.
     */
    private void updateUI() {
        secondViewActive = false;
        setContentView(R.layout.activity_main);

        ListView lv = findViewById(R.id.profileListView);
        lv.setAdapter(new ArrayAdapter<Profile>(this, R.layout.profile_layout, ProfileSingleton.getInstance().getProfiles()));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent nextActivity = new Intent(MainActivity.this, UserProfile.class);
                nextActivity.putExtra(EXTRA, i);
                startActivity(nextActivity);
            }
        });
    }

    /**
     * Tallentaa käyttäjäprofiilit muistiin onPause -metodin yhteydessä.
     * Tallentamisessa käytetään hyödyksi Gson-kirjastoa.
     * Käyttäjistä koostuva lista tallennetaan String-muotoon Gson-kirjaston avulla,
     * joka haetaan sovelluksen käynnistämisen yhteydessä.
     */

    // Tietojen tallentaminen Gson-kirjastoa avuksi käyttäen
    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences prefPut = getSharedPreferences(PREF, MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putBoolean("App", true);
        prefEditor.commit();


        List<Profile> profileList = profile.getProfiles();
        SharedPreferences mPrefs = getSharedPreferences(EXTRA, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String object = gson.toJson(profileList);
        prefsEditor.putString("Profiles", object);
        prefsEditor.commit();
    }

    /**
     * Määrittelee Back-napin toiminnan tilanteessa, jolloin päänäkymän layout ei ole aktiivinen.
     * Koska MainActivity koostuu useista vaihtuvista layouteista MainActivityn sisällä
     * joudutaan tilanteissa, joissa profiilin luonti/profiilin poisto -layout on käytössä muutamaan
     * Back-napin toimintaa.
     */

    // Ohjelmoidaan Androidin sisäinen "Back"-nappi vaihtamaan näkymää, koska Profiilinäkymä on tehty vain yhden Activityn sisälle
    @Override
    public void onBackPressed() {
        if (secondViewActive) {
            updateUI();
        } else {
            super.onBackPressed();
        }
    }
}