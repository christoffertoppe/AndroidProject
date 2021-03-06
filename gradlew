package toka.com.example.tehtava5;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Counter create;
    private Counter start;
    private Counter resume;
    private final String TAG = "MainActivity::";

    private static final String PREF = "TestPref";
    private String lastCreateCountSaved;
    private String lastStartCountSaved;
    private String lastResumeCountSaved;
    private String createToSave;
    private String startToSave;
    private String resumeToSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        create = new Counter();
        start = new Counter();
        resume = new Counter();


        SharedPreferences prefGet = getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        create.setValue(prefGet.getInt("createSave", 0));


        /*
        lastCreateCountSaved = prefGet.getString("LC", "Nothing stored");  //key  “LT”, value if nothing saved
        lastStartCountSaved = prefGet.getString("LS", "Nothing stored");  //key  “LT”, value if nothing saved
        lastResumeCountSaved = prefGet.getString("LR", "Nothing stored");  //key  “LT”, value if nothing saved


        if(!lastCreateCountSaved.contains("Nothing stored")) {
            create.setValue(Integer.parseInt(lastCreateCountSaved));
        } else {
            create.setValue(create.getValue());
        }
        if(!lastStartCountSaved.contains("Nothing stored")) {
            start.setValue(Integer.parseInt(lastStartCountSaved));
        } else {
            start.setValue(start.getValue());
        }

        if(!lastResumeCountSaved.contains("Nothing stored")) {
            resume.setValue(Integer.parseInt(lastResumeCountSaved));
        } else {
            resume.setValue(resume.getValue());
        }
        */
        create.addOne();
        updateUI();
    }




    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        start.addOne();
        updateUI();
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        resume.addOne();
        updateUI();
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        createToSave = Integer.toString(create.getValue());
        startToSave = Integer.toString(start.getValue());
        resumeToSave = Integer.toString(resume.getValue());

        SharedPreferences prefPut = getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        /*
        prefEditor.putString("LC", createToSave);
        prefEditor.putString("LS", startToSave);
        prefEditor.putString("LR", resumeToSave);
        */
        prefEditor.putInt("createSave", create.getValue());
        prefEditor.putInt("startSave", start.getValue());
        prefEditor.putInt("resumeSave", resume.getValue());

        prefEditor.commit();

    }

    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    public void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    public void restart(View v) {
        create.reset();
        start.reset();
        resume.reset();
        updateUI();
    }

    private void updateUI() {
        TextView createtext = findViewById(R.id.createText);
        TextView starttext = findViewById(R.id.startText);
        TextView resumetext = findViewById(R.id.resumeText);

        createtext.setText(Integer.toString(create.getValue()));
        starttext.setText(Integer.toString(start.getValue()));
        resumetext.setText(Integer.toString(resume.getValue()));

    }
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          