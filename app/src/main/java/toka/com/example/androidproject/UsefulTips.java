package toka.com.example.androidproject;

import java.util.ArrayList;
import java.util.List;

public class UsefulTips {

    private List<String> tips;

    private static final UsefulTips ourInstance = new UsefulTips();

    static UsefulTips getInstance() {
        return ourInstance;
    }

    private UsefulTips() {
        tips = new ArrayList<>();
        tips.add("Muista käyttää flourihammastahnaa.");
        tips.add("Muista pestä takahampaat.");
        tips.add("Harjaa hellästi hampaitasi.");
    }

    public String getTip(int i) {
        return tips.get(i);
    }

    public int getListSize() {
        return tips.size();
    }

    public void addTip(String tip) {
        tips.add(tip);
    }

    public List<String> getTips() {
        return tips;
    }
}
