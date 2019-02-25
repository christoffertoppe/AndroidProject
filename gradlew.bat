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

        /*
        SharedPreferences prefGet = getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        create.setValue(prefGet.getInt("createSave", 0));


        //
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
        upda