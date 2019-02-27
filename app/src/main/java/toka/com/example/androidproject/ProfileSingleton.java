package toka.com.example.androidproject;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Profile-luokasta määritelty Singleton-luokka, joka pitää sisällään
 * käyttäjistä luodun listan.
 * ProfileSingleton-luokan avulla tiedot käyttäjistä pidetään yhdessä paikassa.
 *
 * @author Samuli Salin
 * @version 1.0
 */
public class ProfileSingleton {
    private List<Profile> profiles;
    private static final ProfileSingleton ourInstance = new ProfileSingleton();

    /**
     * Profile-luokkaan viitatessa käytettävä metodi.
     *
     * @return ProfileSingleton palauttaa uuden ProfileSingletonin
     */

    public static ProfileSingleton getInstance() {
        return ourInstance;
    }

    /**
     * Luokan yksityinen konstruktori, joka luo uuden ArrayListin.
     */

    private ProfileSingleton() {
        profiles = new ArrayList<Profile>();
    }

    /**
     * Luo uuden profiilin ja lisää sen olemassaolevalle listalle.
     *
     * @param name String käyttäjän antama nimi
     * @param age  int käyttäjän antama ikä
     * @return boolean palauttaa True tai False arvon riippuen oliko profiilin luominen onnistunut
     */

    public boolean addProfile(String name, int age) {
        if ((age > 0 && age < 100) && (name.length() > 0 && name.length() < 13)) {
            for (int i = 0; i < profiles.size(); i++) {
                if (name.equalsIgnoreCase(getProfile(i).getName())) {
                    return false;
                }
            }
            profiles.add(new Profile(name, age));
            return true;
        }
        return false;
    }

    /**
     * Poistaa käyttäjäprofiilin listalta.
     *
     * @param name String käyttäjän antama poistettavan profiilin nimi
     * @return boolean palauttaa True tai False riippuen onnistuiko profiilin poisto
     */

    public boolean deleteProfile(String name) {
        for (int i = 0; i < profiles.size(); i++) {
            if (name.equalsIgnoreCase(getProfile(i).getName())) {
                profiles.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Palauttaa käyttäjistä koostuvan listan.
     *
     * @return List<Person> käyttäjälista
     */

    public List<Profile> getProfiles() {
        return profiles;
    }

    /**
     * Asettaa listan arvot.
     * Käytetään profiilien hakemisessa tallennustilasta.
     *
     * @param profiles List<Person> muistista haettu käyttäjälista
     */

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    /**
     * Hakee yksittäisen käyttäjän käyttäjälistalta.
     *
     * @param i int käyttäjän indeksi
     * @return Profile yksittäinen profiili
     */

    public Profile getProfile(int i) {
        return profiles.get(i);
    }
}
