package toka.com.example.androidproject;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

/**
 * Tulostaulukko, jonka avulla käyttäjät voivat seurata kuka on pessyt hampaitaan säännöllisimmin.
 * Käyttäjät asetetaan tulostaulukkoon sen mukaan kuka on käyttänyt eniten aikaa hampaiden pesemiseen.
 * Tiedot haetaan käyttäjä-oliosta, joaka pitää yllä sekä käyttäjän yksittäisiä hampaiden pesukertoja että
 * niihin käytettyä aikaa sekunteina.
 * Aktiviteetin-käynnistämisen yhteydessä käyttäjien sijoitukset taulukossa päivitetään ja päivitetyt
 * tulokset piirretään ruutuun.
 */

public class Leaderboard extends AppCompatActivity {

    public static final String EXTRA = "toka.com.example.androidproject.MESSAGE";
    ProfileSingleton profile = ProfileSingleton.getInstance();

    private int i;

    /**
     * Metodissa päivitetään tulostaulukko ja sen perusteella määritellään ruudulla näkyvät henkilöt
     * ja heidän tietonsa. Edellisestä aktiviteetistä otetaan ylös tämänhetkisen käyttäjän indeksi käyttäjä-olio
     * listalta.
     *
     * @param savedInstanceState Bundle pakettitiedosto
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        Bundle b = getIntent().getExtras();
        i = b.getInt(EXTRA, 0);

        profile.updateLeaderboards();
        updatePodiumProfiles();
    }

    /**
     * Piirretään ruutuun parhaiten menestyneet henkilöt ja heidän tietonsa.
     * Käyttäjien sijoitus tulostaulukossa etsitään käyttämällä hyväksi käyttäjien
     * sijoitusta kuvaavaa muuttujaa, joka löytyy käyttäjän profiilista.
     * Ruudulle piiretään kolme pokaalia, jonka alle kirjoitetaan ensimmäiseksi, toiseksi
     * tai kolmanneksi sijoittuneen nimi sijoituksen mukaan. Jos käyttäjiä ei löydy tarpeeksi,
     * ylimääräisiä pokaaleja ei piirretä.
     * Kolmen parhaan käyttäjän tiedot näytetään pokaalien alla, jotta käyttäjät voivat
     * seurata edistymistään verrattuna muihin.
     */

    private void updatePodiumProfiles() {
        ImageView firstPlaceTrophy = findViewById(R.id.firstPlaceView);
        firstPlaceTrophy.setVisibility(View.INVISIBLE);

        ImageView secondPlaceTrophy = findViewById(R.id.secondPlaceView);
        secondPlaceTrophy.setVisibility(View.INVISIBLE);

        ImageView thirdPlace = findViewById(R.id.thirdPlaceView);
        thirdPlace.setVisibility(View.INVISIBLE);


        for (int i = 0; i < profile.getProfilesSize(); i++) {
            int minutes = (int) profile.getProfile(i).getBrushingSeconds() / 60;
            int seconds = (int) profile.getProfile(i).getBrushingSeconds() % 60;

            if (profile.getProfile(i).getLeaderboardRanking() == 1) {
                TextView firstPlace = findViewById(R.id.firstPlaceText);
                firstPlace.setText(profile.getProfile(i).getName());

                firstPlaceTrophy = findViewById(R.id.firstPlaceView);
                firstPlaceTrophy.setVisibility(View.VISIBLE);


                TextView firstPlaceCongratulation = findViewById(R.id.firstPlaceCongratulation);
                firstPlaceCongratulation.setText("Ahkerin harjaaja on: " + profile.getProfile(i).getName() + "!\n " + minutes + " min " + seconds + " sek - Pesukertoja: " + profile.getProfile(i).getBrushingTotal());

                updateUI();
            } else if (profile.getProfile(i).getLeaderboardRanking() == 2) {
                TextView secondPlace = findViewById(R.id.secondPlaceText);
                secondPlace.setText(profile.getProfile(i).getName());

                secondPlaceTrophy = findViewById(R.id.secondPlaceView);
                secondPlaceTrophy.setVisibility(View.VISIBLE);

                TextView secondPlaceCongratulation = findViewById(R.id.secondPlaceCongratulation);
                secondPlaceCongratulation.setText("Hopeasijalla juhlii: " + profile.getProfile(i).getName() + "!\n " + minutes + " min " + seconds + " sek - Pesukertoja: " + profile.getProfile(i).getBrushingTotal());

                updateUI();
            } else if (profile.getProfile(i).getLeaderboardRanking() == 3) {
                TextView thirdPlaceTrophy = findViewById(R.id.thirdPlaceText);
                thirdPlaceTrophy.setText(profile.getProfile(i).getName());

                thirdPlace = findViewById(R.id.thirdPlaceView);
                thirdPlace.setVisibility(View.VISIBLE);

                TextView thirdPlaceCongratulation = findViewById(R.id.thirdPlaceCongratulation);
                thirdPlaceCongratulation.setText("Pronssisijan on saavuttanut: " + profile.getProfile(i).getName() + "!\n " + minutes + " min " + seconds + " sek - Pesukertoja: " + profile.getProfile(i).getBrushingTotal());

                updateUI();
            }
        }
    }

    /**
     * Mukautetun fontin päivittäminen aktiviteetin tekstikenttiin.
     */

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

    /**
     * onPause-metodissa käyttäjän käyttäjä-olio tallennetaan käyttäen hyödyksi Gson-kirjastoa.
     */
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
