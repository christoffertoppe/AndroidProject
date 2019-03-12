package toka.com.example.androidproject;

import java.util.ArrayList;
import java.util.List;

/**
 * UsefulTips-luokasta määritelty Singleton-luokka, joka pitää sisällään
 * hampaidenhoito ohjeita.
 * UsefulTips-luokan avulla ohjeet pidetään yhdessä paikassa.
 *
 * @author Christoffer Tverin
 * @version 1.0
 */
public class UsefulTips {

    private List<String> tips; // esitellään tips joka on lista johon voi tallentaa String muuttujia.
    private static final UsefulTips ourInstance = new UsefulTips();

    /**
     * UsefulTips-luokkaan viitatessa käytettävä metodi.
     *
     * @return UsefulTips palauttaa Usefultips
     */

    static UsefulTips getInstance() {
        return ourInstance;
    }

    /**
     * Luokan yksityinen konstruktori, joka luo uuden ArrayListin.
     */

    private UsefulTips() {
        // Luodaan ArrayList  tips ja lisätään erilaisia ohjeita.
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
        tips.add("Harjaa ainakin kaksi minuuttia kerralla.");
    }

    /**
     * getTip palautaa yhden hoitoohjeen.
     *
     * @param i kertoo miltä riviltä ohje haetaan listasta.
     * @return String palauttaa ohjeen String muodossa.
     */

    // palauttaa String ohjeen listalta riviltä i
    public String getTip(int i) {
        return tips.get(i);
    }

    /**
     * getListSize palauttaa listakoon.
     *
     * @return int palauttaa listakoon int arvona.
     */
    // palauttaa int luvun joka on listan koko.
    public int getListSize() {
        return tips.size();
    }
}
