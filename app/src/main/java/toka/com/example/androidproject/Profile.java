package toka.com.example.androidproject;

/**
 * Profile-luokka määrittelee sovelluksen yksittäisen käyttäjän ominaisuudet
 * Tietoja voidaan hakea tai muokata tarpeen mukaan
 *
 * @author Samuli Salin
 * @version 1.0
 */
public class Profile {
    private String name;
    private int age;
    private int brushingSeconds;
    private int brushingTotal;
    private int selectedSong;
    private int leaderboardRanking;


    /**
     * Luodaan käyttäjäolio, jolle annetaan luonnin yhteydessä nimi ja ikä.
     * Uutta käyttäjää luodessa hampaiden pesuaika ja pesukerrat ovat aina 0.
     *
     * @param name String käyttäjän nimi
     * @param age  int käyttäjän ikä
     */

    public Profile(String name, int age) {
        this.name = name;
        this.age = age;
        brushingSeconds = 0;
        brushingTotal = 0;
        selectedSong = 0;
        leaderboardRanking = 1;
    }

    /**
     * Pitää yllä yhteensä hampaiden pesuun käytettyä aikaa sekunteina
     *
     * @param time int yksittäisellä kerralla hampaiden pesemiseen käytetty aika sekunteina
     */

    public void addBrushingSeconds(int time) {
        this.brushingSeconds += time;
    }

    /**
     * Pitää yllä yksittäisiä hampaiden pesukertoja.
     */

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
     * @return int kappaleen numero
     */

    public int getSelectedSong() {
        return selectedSong;
    }

    /**
     * Muuttaa käyttäjän valitsemaa kappaletta.
     * @param selectedSong int uusi kappale
     */

    public void setSelectedSong(int selectedSong) {
        this.selectedSong = selectedSong;
    }

    /**
     * Palauttaa käyttäjän sijoituksen tulostaulukossa.
     * @return int käyttäjän sijoitus
     */

    public int getLeaderboardRanking() {
        return leaderboardRanking;
    }

    /**
     * Asettaa käyttäjälle uuden sijoituksen tulostaulukossa.
     * @param leaderboardRanking int uusi sijoitus
     */

    public void setLeaderboardRanking(int leaderboardRanking) {
        this.leaderboardRanking = leaderboardRanking;
    }

    /**
     * Luokan toString -metodi, joka palauttaa nimen.
     * @return String käyttäjän nimi
     */

    public String toString() {
        return this.name;
    }
}
