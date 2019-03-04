package toka.com.example.androidproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class BrushTimer extends AppCompatActivity {
    public static final String EXTRA = "toka.com.example.androidproject.MESSAGE";
    private int i = 0;

    private static final long START_TIME_IN_MILLIS = 120000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;

    MediaPlayer musicPlayer;
    ProfileSingleton profile = ProfileSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush_timer);

        Bundle b = getIntent().getExtras();
        i = b.getInt(EXTRA, 0);

        if (profile.getProfile(i).getSelectedSong() == 0) {
            musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.africa);
        } else if (profile.getProfile(i).getSelectedSong() == 1) {
            musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.babyshark);
        } else if (profile.getProfile(i).getSelectedSong() == 2) {
            musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.ripandtear);
        }


        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);

                profile.getProfile(i).addBrushingTotal();
                int seconds = (int) (START_TIME_IN_MILLIS / 1000);
                profile.getProfile(i).addBrushingSeconds((seconds));

                musicPlayer.stop();

                teethBrushed();
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("Tauko");
        mButtonReset.setVisibility(View.INVISIBLE);

        musicPlayer.start();
        musicPlayer.setLooping(true);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Jatka pesua");
        mButtonReset.setVisibility(View.VISIBLE);

        musicPlayer.pause();
    }

    private void resetTimer() {
        int seconds = (int) ((START_TIME_IN_MILLIS - mTimeLeftInMillis) / 1000);
        profile.getProfile(i).addBrushingSeconds((seconds));
        profile.getProfile(i).addBrushingTotal();

        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);

        musicPlayer.stop();
        musicPlayer.prepareAsync();

        teethBrushed();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void teethBrushed() {
        int minutes = (int) profile.getProfile(i).getBrushingSeconds() / 60;
        int seconds = (int) profile.getProfile(i).getBrushingSeconds() % 60;

        setContentView(R.layout.teeth_brushed_layout);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/colophon.ttf");

        TextView tv = findViewById(R.id.teethBrushedMessageView);
        TextView tx = findViewById(R.id.teethBrushedTotalView);

        tv.setText("Hampaat pesty!");
        tx.setText("Olet pessyt hampaita yhteensä " + minutes + " minuuttia ja " + seconds + " sekuntia");

        tv.setTypeface(typeface);
        tx.setTypeface(typeface);
    }

    public void mainMenuButtonPressed(View view) {
        super.onBackPressed();
    }
}