package toka.com.example.androidproject;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

/**
 * Brush-timer aktivitetti pitää sisällän ajastimen, jonka mukaan käyttäjä pesee hampaitaan.
 * Ajastimen voi tauottaa, jatkaa tai lopettaa haluttuna ajankohtana.
 * Hampaiden pesun aikana taustalla soi käyttäjän valitsema musiikki ja näytöllä
 * animoitu majava harjaa hampaitaan käyttäjän mukana.
 *
 * @author Patrik Sneck
 * @version 1.0
 */

public class BrushTimer extends AppCompatActivity {
    public static final String EXTRA = "toka.com.example.androidproject.MESSAGE";//sijaintitieto
    private int i;                                                               //käyttäjän indeksi (numero) kuka sovellusta tällä hetkellä käyttää.  i peritään aina edelliseltä aktiviteetilta. Sen avula tiedetään minkä profiilin alle kaikki tiedot pitää tallentaa.
    private static final long START_TIME_IN_MILLIS = 120000;                     //aika millisekunteina
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;                       //otaa start timen ajan
    private TextView mTextViewCountDown;                                         //aika näytöllä
    private Button mButtonStartPause;                                            //nappula start/pause
    private Button mButtonStop;                                                  //noppula stop
    private CountDownTimer mCountDownTimer;                                      //ajastimen olio
    private boolean mTimerRunning;                                               //muutuja joka määrittelee onko ajastin käynnissä
    MediaPlayer musicPlayer;                                                     //Media soitin
    ProfileSingleton profile = ProfileSingleton.getInstance();                   //viitaus profiiliin

    /**
     * Tekee sivun spawnaa majava gifin ja piilotaa sen odottamaan napin painallusta //;;\\
     * katsee if/elseif lausekeita päätelläkseen mitä soittaa
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush_timer);              //avaa brushtimer layoutin

        Bundle b = getIntent().getExtras();                         //sijoitetaaan edelisen aktiviiteetin tiedot muutujaan i
        i = b.getInt(EXTRA, 0);

        GifImageView majavaView = findViewById(R.id.majavaGifView); // etsiii majava gifin ja piilottaa sen näkyvistä
        majavaView.setVisibility(View.INVISIBLE);

        if (profile.getProfile(i).getSelectedSong() == 0) {         // päätelee minkä musiikin pitäisi soida
            musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.artiss_happy);
        } else if (profile.getProfile(i).getSelectedSong() == 1) {
            musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.fmt_imagination_play);
        } else if (profile.getProfile(i).getSelectedSong() == 2) {
            musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.ripandtear);
        }


        mTextViewCountDown = findViewById(R.id.text_view_countdown); //yhdistää widgetit muutujaan
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonStop = findViewById(R.id.button_reset);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                 //onclicklistner joka muutaa buttonin ominaisuutta parametrien mukaan
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                       //kello on pysätynyt nappula seuraavaan ruutuun paljastetaan
                resetTimer();
            }
        });

        updateCountDownText();
    }

    /**
     * Aloitaa ajan ottamisen. \_:I_/
     */

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {     //luo uuden ajastimen ja päätää koinka nopeati aika kuluu

            /**
             * saa parametrinä aijan ennen loppua
             * @param millisUntilFinished = millisekuntteja ennen kuin aika loppuu
             */

            @Override
            public void onTick(long millisUntilFinished) {                                  //päivitää tekstiä jokaisella kuluvalla sekuntilla
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            /**
             * kun aika päättyy piilota start/pause nappula ja kerää ajan, >.>
             * kuinka kauan hampaita on pesty ja taleettaa sen kyseiseen profiiliin ja pysäyttää musiikin
             */

            @Override
            public void onFinish() {
                mTimerRunning = false;                                  // pysättää kellon
                mButtonStartPause.setText("Start");                     // muutaa nappulan tekstin Startiksi
                mButtonStartPause.setVisibility(View.INVISIBLE);        // muuttaa startin näkymättömäksi
                mButtonStop.setVisibility(View.VISIBLE);                //  muuttaa stop nappulan näkyväksi

                int lastTimeTotal = profile.getProfile(i).getBrushingWithSameToothbrush(); // hakee edellisten kertojen määrää samalla harjalla
                profile.getProfile(i).setBrushingWithSameToothbrush(lastTimeTotal + 1); // lisää yhden saman harjan laskuriin

                profile.getProfile(i).addBrushingTotal();               // lisää pesukerran profiiliin
                int seconds = (int) (START_TIME_IN_MILLIS / 1000);      // muuttaa millisekuntit sekunteiksi
                profile.getProfile(i).addBrushingSeconds((seconds));    // lisää pesusekuntit profiiliin

                musicPlayer.stop();                                     // pysäyttää musiikin

                teethBrushed();                                         // siirtyy seuraavaan näkymään
            }
        }.start();

        mTimerRunning = true;                                           // kello käynnistyy
        mButtonStartPause.setText("Tauko");                             // nappula saa nimen tauko
        mButtonStop.setVisibility(View.INVISIBLE);                      // piilottaa nappulan

        musicPlayer.start();                                            // musiikki soi
        musicPlayer.setLooping(true);                                   // musiikki soi toistolla

        GifImageView majavaView = findViewById(R.id.majavaGifView);     // majava tuodaan esille
        majavaView.setVisibility(View.VISIBLE);
    }

    /**
     * Keskeytää hetkellisesti ajan, musiikin ja
     * tuo jatkapesua nappulanesiin pesun/musiikin jatkamiseksi
     */

    private void pauseTimer() {
        mCountDownTimer.cancel();                       // kello seis
        mTimerRunning = false;
        mButtonStartPause.setText("Jatka pesua");       //nappula nimetään uudelleen
        mButtonStop.setVisibility(View.VISIBLE);

        musicPlayer.pause();                            // musiiikki seis
    }

    /**
     * Väärin nimetty nappula jonka pitäisi olla stop, ottaa ajan  muutaa ne minuuteiksi/sekunneiksi
     * pysäytää musiikin ja siirtää käyytäjän "teethbrushed" ruutuun
     */

    private void resetTimer() {
        int seconds = (int) ((START_TIME_IN_MILLIS - mTimeLeftInMillis) / 1000);    //käytiin jo läpi
        profile.getProfile(i).addBrushingSeconds((seconds));
        profile.getProfile(i).addBrushingTotal();

        int lastTimeTotal = profile.getProfile(i).getBrushingWithSameToothbrush(); // hakee edellisten kertojen määrää samalla harjalla
        profile.getProfile(i).setBrushingWithSameToothbrush(lastTimeTotal + 1); // lisää yhden saman harjan laskuriin

        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonStop.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);

        musicPlayer.stop();
        musicPlayer.prepareAsync();                     //pakkollinen valmistelu

        teethBrushed();
    }

    /**
     * päivitäää kelloa
     */

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;        //muuttaa ms ajan minuuteiksi
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;        //muuttaa ms ajan sekunteiksi

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds); // muoto missää kellonaika näkyy

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    /**
     * loppuruutu missä kuva majavasta näytää peukkua ja kertoo ajan jonka käytit hampaiden pesuun
     */

    private void teethBrushed() {
        int minutes = (int) profile.getProfile(i).getBrushingSeconds() / 60;            // hakee ajan profiilista
        int seconds = (int) profile.getProfile(i).getBrushingSeconds() % 60;

        setContentView(R.layout.teeth_brushed_layout);                                          // tuo seuraavan ruudun esille
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");   // asettaa custom ilmais fontti

        TextView tv = findViewById(R.id.teethBrushedMessageView);
        TextView tx = findViewById(R.id.teethBrushedTotalView);

        tv.setText("Hampaat pesty!");
        tx.setText("Olet pessyt hampaitasi yhteensä " + minutes + " minuuttia ja " + seconds + " sekuntia");

        tv.setTypeface(typeface);
        tx.setTypeface(typeface);
    }

    /**
     * Paluttaa mainmenu viewiin
     *
     * @param view
     */
    public void mainMenuButtonPressed(View view) {
        super.onBackPressed();
    }

    /**
     * onPause-käskyn yhteydessä käyttäjä-olio tallennetaan Gson-kirjaston avulla String-muotoon.
     */

    @Override
    public void onPause() {             //onPause()-metodia kutsuttaessa tallennetaan käyttäjän tiedot muistiin Gson-kirjaston avulla
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

