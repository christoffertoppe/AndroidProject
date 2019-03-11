package toka.com.example.androidproject;

/**
 * Profile-luokka määrittelee sovelluksen yksittäisen käyttäjän ominaisuudet.
 * Tietoja voidaan hakea tai muokata tarpeen mukaan
 *
 * @author Samuli Salin
 * @version 1.0
 */
public class Profile {
    /**
     * Profiilin nimi ja ikä.
     */
    private String name;                    // Käyttäjän nimi
    private int age;                        // Käyttäjän ikä
    private int brushingSeconds;            // Käyttäjän hampaiden pesemiseen käytetty aika sekunteina
    private int brushingTotal;              // Käyttäjän hampaiden pesemisen yksittäiset kerrat
    private int selectedSong;               // Käyttäjän valitsema kappale
    private int leaderboardRanking;         // Käyttäjän sijoitus tulostaulukossa
    private int brushingWithSameToothbrush; // Käyttäjän pesukertoja nykyisellä hammasharjalla


    /**
     * Luodaan käyttäjäolio, jolle annetaan luonnin yhteydessä nimi ja ikä.
     * Uutta käyttäjää luodessa hampaiden pesuaika ja pesukerrat ovat aina 0.
     * Käyttäjän sijoitus tulostaulukossa on alkuun aina 1, kunnes tulostaulu päivitetään
     * Learderboard-aktiviteettiin siirryttäessä.
     *
     * @param name String käyttäjän nimi, joka rajoitetaan enintään 12:sta merkkiin luonnin yhteydessä
     * @param age  int käyttäjän ikä väliltä 1-99
     */

    //Luodaan käyttäjäolio, joka saa parametreinä nimen ja iän. Muut arvot saavat alkuun perusarvot
    public Profile(String name, int age) {
        this.name = name;
        this.age = age;
        brushingSeconds = 0;
        brushingTotal = 0;
        selectedSong = 0;
        leaderboardRanking = 1;
        brushingWithSameToothbrush = 0;
    }

    /**
     * Pitää yllä yhteensä hampaiden pesuun käytettyä aikaa sekunteina
     *
     * @param time int yksittäisellä kerralla hampaiden pesemiseen käytetty aika sekunteina
     */

    // Lisää käyttäjän hampaiden pesuun käytettyyn aikaan viimesimmän pesuajan
    public void addBrushingSeconds(int time) {
        this.brushingSeconds += time;
    }

    /**
     * Pitää yllä yksittäisiä hampaiden pesukertoja.
     */

    // Lisää käyttäjän pesukertoihin + 1
    public void addBrushingTotal() {
        this.brushingTotal++;
    }

    /**
     * Palauttaa käyttäjän nimen.
     *
     * @return String palauttaa käyttäjän nimen
     */

    public String getName() {
        return name;
    }

    /**
     * Käyttäjän nimen muokkaaminen.
     *
     * @param name String käyttäjän uusi nimi
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Palauttaa käyttäjän hampaiden pesemiseen kokonaisuudessaan käyttämän ajan.
     *
     * @return int hampaiden pesemiseen käytetty aika sekunteina
     */

    public int getBrushingSeconds() {
        return brushingSeconds;
    }

    /**
     * Käyttäjän hampaiden pesukerrat kokonaisuudessaan.
     *
     * @return int käyttäjän hampaiden pesukerrat kokonaislukuna
     */

    public int getBrushingTotal() {
        return brushingTotal;
    }

    /**
     * Palauttaa käyttäjän valitseman kappaleen.
     *
     * @return int kappaleen numero
     */

    public int getSelectedSong() {
        return selectedSong;
    }

    /**
     * Muuttaa käyttäjän valitsemaa kappaletta.
     *
     * @param selectedSong int uusi kappale
     */

    public void setSelectedSong(int selectedSong) {
        this.selectedSong = selectedSong;
    }

    /**
     * Palauttaa käyttäjän sijoituksen tulostaulukossa.
     *
     * @return int käyttäjän sijoitus
     */

    public int getLeaderboardRanking() {
        return leaderboardRanking;
    }

    /**
     * Asettaa käyttäjälle uuden sijoituksen tulostaulukossa.
     *
     * @param leaderboardRanking int uusi sijoitus
     */

    public void setLeaderboardRanking(int leaderboardRanking) {
        this.leaderboardRanking = leaderboardRanking;
    }

    /**
     * Asettaa uuden arvon hampaiden hampaiden puhidstusta seuraavaan sekuntilaskuriin.
     *
     * @param brushingSeconds int uusi sekuntiarvo
     */

    public void setBrushingSeconds(int brushingSeconds) {
        this.brushingSeconds = brushingSeconds;
    }

    /**
     * Asettaa uuden yksittäisten hampaiden pesukertojen arvon.
     *
     * @param brushingTotal int uusi kokonaislukuarvo
     */

    public void setBrushingTotal(int brushingTotal) {
        this.brushingTotal = brushingTotal;
    }

    /**
     * Käyttäjän hampaiden pesukerrat kokonaisuudessaan nykyisellä hammasharjalla
     * @return palauttaa nykyisen harjan käyttökerrat.
     */
    public int getBrushingWithSameToothbrush() {
        return brushingWithSameToothbrush;
    }

    /**
     * Asettaa uuden yksittäisten hampaiden pesukertojen arvon. Mahdollista nollata.
     *
     * @param brushingWithSameToothbrush aina kun pestään niin lisätään yksi.
     *                                   jos käyttäjä vaihtaa uuteen harjaan,
     *                                   hän painaa uusi harja ja laskuri aloittaa nollasta.
     */
    public void setBrushingWithSameToothbrush(int brushingWithSameToothbrush) {
        this.brushingWithSameToothbrush = brushingWithSameToothbrush;
    }

    /**
     * Luokan toString -metodi, joka palauttaa nimen.
     *
     * @return String käyttäjän nimi
     */

    public String toString() {
        return this.name;
    }
}
