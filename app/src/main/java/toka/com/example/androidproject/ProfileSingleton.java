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
     * Päivittää tulostaulukon näyttämään kuka käyttäjistä on pessyt hampaitaan eniten.
     * Käyttäjiä verrataan keskenään profiiliin tallennetun hamapiden pesuun käytetys sekuntimäärän kautta.
     * Jos sekuntimäärä on käyttäjien kesken sama, sijoitetaan kyseiset käyttäjät aakkosjärjestyksen mukaan.
     */

    public void updateLeaderboards() {
        // Päivitetään tulostaulukko näyttämään, kuka on pessyt hampaita eniten viikon aikana

        for (int i = 0; i < profiles.size(); i++) {     // Verrataan yksittäistä käyttäjää listan kaikiin muihin käyttäjiin käyttämällä kahta for-silmukkaa
            getProfile(i).setLeaderboardRanking(1);     // Resetoidaan jokaisen käyttäjän aikaisempi sijoitus taulukossa antamalla jokaiselle arvo 1

            /*
            Sisäkkäisen silmukan avulla yksittäistä käyttäjää (i) voidaan verrata muihin käyttäjiin (j)
            Kun kaikki listan käyttäjät (j) on käyty läpi, siirrytään seuraavaan käyttäjään (i)
             */

            for (int j = 0; j < profiles.size(); j++) {

                /* Jos verrattavan käyttäjän hampaiden pesuaika on lyhyempi kuin toisen käyttäjän,
                lisätään hänen listasijoitukseensa +1
                Käyttäjää ei voi myöskään verrata itseenä
                 */
                if ((getProfile(i).getBrushingSeconds() < getProfile(j).getBrushingSeconds()) && (i != j)) {
                    getProfile(i).setLeaderboardRanking((getProfile(i).getLeaderboardRanking()) + 1);

                    /*
                    Jos sattuu niin, että kahden käyttäjän hampaiden pesuajat ovat identtiset,
                    verrataan käyttäjiä aakkosten mukaan
                     */
                } else if (getProfile(i).getBrushingSeconds() == getProfile(j).getBrushingSeconds() && (i != j)) {

                    String a = getProfile(i).getName();
                    String b = getProfile(j).getName();

                    int compareAlphabets = a.compareTo(b);

                    if (compareAlphabets < 0) {
                        continue;
                    } else if (compareAlphabets > 0) {
                        getProfile(i).setLeaderboardRanking((getProfile(i).getLeaderboardRanking()) + 1);
                    } else {
                        continue;
                    }
                }
            }
        }
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

    /**
     * Palauttaa profiililistan koon.
     *
     * @return int profiililistan koko
     */

    public int getProfilesSize() {
        return profiles.size();
    }
}
