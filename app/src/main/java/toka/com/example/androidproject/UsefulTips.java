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
        tips.add("Muista käyttää Flourihammastahnaa.");
        tips.add("Muista pestä takahampaat.");
        tips.add("Harjaa hellästi hampaitasi.");
        tips.add("Harjaa hampaat aamuin illoin.");
        tips.add("Vaihda hammasharjasi muutaman kuukauden välein.");
        tips.add("Ksylitolipurkka hellii hampaitasi.");
        tips.add("Sokeri ei ole hyväksi hampaille.");
        tips.add("Käy hammaslääkärissä säännöllisin väliajoin.");
        tips.add("Matti Majava on hampaiden ystävä!.");
        tips.add("Harjaa hellästi hampaitasi.");
        tips.add("Harjaa ainakin kaksi minuuttia kerralla.");
        tips.add("Harjaa hellästi hampaitasi.");
    }

    public String getTip(int i) {
        return tips.get(i);
    }

    public int getListSize() {
        return tips.size();
    }
}
