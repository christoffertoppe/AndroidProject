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
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * UserProfile-luokka on sovelluksen "päävalikko". Aktiviteetin kautta käyttäjä voi aloitaa hampaiden pesun,
 * siirtyä asetusvalikkoon tai tarkastella käyttäjien tilastoista koostuvaa tulostaulukkoa.
 * Keskellä aktiviteettia näkyy satunnaisia hampaiden terveyttä edistäviä vinkkejä, jotka
 * haetaan erillisestä listasta.
 *
 * @author Christoffer Tverin
 * @version 1.0
 */

public class UserProfile extends AppCompatActivity {
    public static final String EXTRA = "toka.com.example.androidproject.MESSAGE";
    private ProfileSingleton profile = ProfileSingleton.getInstance();

    private ArrayList<String> tervehdys = new ArrayList<>();

    private int i;

    private boolean secondViewActive = false;
    String[] spinnerSongs = new String[]{"ArtIss - Happy", "FMT - Imagination Play", "Rip & Tear"};

    /**
     * Tallentaa edellisen aktiviteetin mukana saadun tiedon nykyisen käyttäjän indeksistä
     * käyttäjä-olio listalla.
     * Indeksin avulla sovellus muistaa, kuka käyttäjistä tällä hetkellä käyttää sovellusta.
     *
     * @param savedInstanceState Bundle pakettitiedosto
     */

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

    /**
     *  WelcomeText metodi tervehtii käyttäjää nimellä.
     *
     */

    private void welcomeText() {
        TextView tvWelcome = findViewById(R.id.welcomeText);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");
        tvWelcome.setTypeface(typeface);
        tvWelcome.setText("Terve,\n" + profile.getProfile(i).getName());
    }

    /**
     * washCount kertoo montako kertaa käyttäjä on pessyt hampaitaan.
     *
     */

    private void washCounter() {
        TextView tvWash = findViewById(R.id.washCount);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");
        tvWash.setTypeface(typeface);

        int washCount = profile.getProfile(i).getBrushingTotal();

        if (washCount == 1) {
            tvWash.setText("Olet harjannut hampaitasi: " + Integer.toString(washCount) + " kerran.");
        } else  {
            tvWash.setText("Olet harjannut hampaitasi: " + Integer.toString(washCount) + " kertaa.");
        }

        if (profile.getProfile(i).getBrushingWithSameToothbrush() >= 150) {

            Toast toast = Toast.makeText(getApplicationContext(), "Olisi hyvä vaihtaa hammasharja uuteen.\nKäy asetuksissa ottamassa uusi harja käyttöön.", Toast.LENGTH_LONG);
            toast.show();
        }
    }



    /**
     *  showTip antaa käyttäjälle hampaidenpesuun liittyvä ohje.
     *  showTip metodi hakee ensin kuinka monta riviä/ohjetta löytyy UsefulTips singeltonissa olevassa listassa
     *  sen jälkeen se arpoo satunaisen luvun nollan ja maksimiarvon välillä
     *  maksimiarvo on listankoko miinus yksi.
     *  haetaan arvotun luvun riviltä ohje ja tallenetaan se.
     *  etsitään tipView joka on textView ruutu.
     *  annetaan tekstille fontti colophon.ttf
     *  ja asetetaan se tipView ruutuun näkyviin
     */
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

    /**
     * Reagoi käyttäjän napinpainalluksiin, kun käyttäjä haluaa nollata käyttäjäprofiilinsa tilastot asetusvalikossa.
     * Poistamisen yhteydessä käyttäjältä pyydetään vahvistus, jotta vältyttäisiin vahinkoklikkauksilta.
     * Nollauksen yhteydessä käyttäjän yksittäiset hampaiden pesukerrat ja niihin kuuluvat minuutit nollataan.
     * Onnistuneesta nollauksesta Android lähettää Toast-tyyppisen ilmoituksen.
     *
     * @param view View aktiviteetin näkymä
     */

    public void resetButtonPressed(View view) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");

        TextView reset = findViewById(R.id.resetStatsTextView);
        reset.setTypeface(typeface);

        Button resetButton = findViewById(R.id.resetStatsButton);
        Button yesButton = findViewById(R.id.yesButton);
        Button noButton = findViewById(R.id.noButton);
        Button newBrush = findViewById(R.id.newBrush);

        if (view == findViewById(R.id.resetStatsButton)) {

            reset.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.INVISIBLE);
            yesButton.setVisibility(View.VISIBLE);
            noButton.setVisibility(View.VISIBLE);
            newBrush.setVisibility(View.INVISIBLE);


        } else if (view == findViewById(R.id.noButton)) {

            resetButton.setVisibility(View.VISIBLE);
            reset.setVisibility(View.INVISIBLE);
            yesButton.setVisibility(View.INVISIBLE);
            noButton.setVisibility(View.INVISIBLE);
            newBrush.setVisibility(View.VISIBLE);

        } else if (view == findViewById(R.id.yesButton)) {
            profile.getProfile(i).setBrushingTotal(0);
            profile.getProfile(i).setBrushingSeconds(0);

            resetButton.setVisibility(View.VISIBLE);
            reset.setVisibility(View.INVISIBLE);
            yesButton.setVisibility(View.INVISIBLE);
            noButton.setVisibility(View.INVISIBLE);
            newBrush.setVisibility(View.VISIBLE);

            Toast resetSuccessful = Toast.makeText(getApplicationContext(), "Tilastot nollattu!", Toast.LENGTH_LONG);
            resetSuccessful.show();
        }
    }

    /**
     * Aktiviteetin näkymän eri nappeihin reagoiva metodi.
     * buttonPressed -metodi reagoi aloitus-, asetus- ja tulostaulukko nappien painalluksiin.
     * Aloitusnapin kautta käyttäjä siirtyy ajastinnäkymään, jossa hampaiden pesun voi aloittaa.
     * Asetusvalikosta käyttäjä voi vaihtaa hampaiden pesun aikana soivaa musiikkia ja tulostaulukossa
     * käyttäjä voi seurata edistymistään verrattuna muihin käyttäjiin.
     *
     * @param view tarvittava näkymä
     */

    public void buttonPressed(View view) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");
        if (view == findViewById(R.id.startButton)) {

            Intent nextActivity = new Intent(UserProfile.this, BrushTimer.class);
            nextActivity.putExtra(EXTRA, i);
            startActivity(nextActivity);

        } else if (view == findViewById(R.id.settingsButton)) {
            secondViewActive = true;
            setContentView(R.layout.settings_layout);

            TextView reset = findViewById(R.id.resetStatsTextView);
            Button yesButton = findViewById(R.id.yesButton);
            Button noButton = findViewById(R.id.noButton);

            reset.setVisibility(View.INVISIBLE);
            yesButton.setVisibility(View.INVISIBLE);
            noButton.setVisibility(View.INVISIBLE);

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
        } else if (view == findViewById(R.id.leaderboardButton)) {
            Intent nextActivity = new Intent(UserProfile.this, Leaderboard.class);
            nextActivity.putExtra(EXTRA, i);
            startActivity(nextActivity);
        } else if (view == findViewById(R.id.newBrush)) {
            profile.getProfile(i).setBrushingWithSameToothbrush(0);
            Toast resetSuccessful = Toast.makeText(getApplicationContext(), "Uusi harja otettu käyttöön", Toast.LENGTH_LONG);
            resetSuccessful.show();
        }
    }

    /**
     * onPause-käskyn yhteydessä käyttäjä-olio tallennetaan Gson-kirjaston avulla String-muotoon.
     */

    // onPause-käskyn yhteydessä käyttäjä-olio tallennetaan Gson-kirjaston avulla String-muotoon.
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

    /**
     * onBackPressed-metodin yhteydessä siirrytään edeltävään layouttiin, jos ruudulla on esillä jokin muu
     * kuin päänäkymä, esimerkiksi asetus- tai tulostaulunäkymä.
     * Koska UserProfile-aktiviteetti koostuu useista eri layout-näkymistä, mutta vain yhdestä aktiviteetista, aina ei ole
     * toivottavaa, että back-nappi veisi käyttäjän aina edelliseen aktiviteettiin (tässä tapauksessa profiilin valitsemisnäkymään).
     * Tämän takia onBackPressed -kutsun yhteydessä sovellus katsoo onko käyttäjä päävalikossa vai mahdollisesti jossain muussa layoutissa.
     * Jos käyttäjä ei ole päävalikossa, vanhan aktiviteetin käynnistämisen sijaan, sovellus piirtää ruudulle päävalikon näkymän ikään kuin
     * taaksepäin siirtymisen "simuloimiseksi".
     */

    // Ohjelmoidaan Androidin sisäinen "Back"-nappi vaihtamaan näkymää, koska Päänäkymä on tehty vain yhden Activityn sisälle
    @Override
    public void onBackPressed() {
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

    /**
     * onResume-kutsun yhteydessä sovellus päivittää ruudulla näkyvät TextView-laatikot.
     */

    @Override
    public void onResume() {
        super.onResume();
        showTip();
        washCounter();
        welcomeText();
    }
}