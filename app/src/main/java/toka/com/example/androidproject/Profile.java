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
     * Palauttaa käyttäjän iän.
     *
     * @return int käyttäjän ikä kokonaislukuna
     */

    public int getAge() {
        return age;
    }

    /**
     * Asettaa käyttäjälle uuden iän.
     *
     * @param age int käyttäjän uusi ikä
     */

    public void setAge(int age) {
        this.age = age;
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
     * Asettaa käyttäjän hampaiden pesemiseen kokonaisuudessaan käyttämän ajan.
     *
     * @param brushingSeconds int hampaiden pesemiseen käytetty uusi aika sekunteina
     */

    public void setBrushingSeconds(int brushingSeconds) {
        this.brushingSeconds = brushingSeconds;
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
     * Asettaa käyttäjän hampaiden pesukertojen määrän.
     *
     * @param brushingTotal int uusi pesukertojen määrä kokonaislukuna
     */

    public void setBrushingTotal(int brushingTotal) {
        this.brushingTotal = brushingTotal;
    }

    /**
     * Palauttaa käyttäjän nimen.
     *
     * @return String käyttäjän nimi
     */

    public int getSelectedSong() {
        return selectedSong;
    }

    public void setSelectedSong(int selectedSong) {
        this.selectedSong = selectedSong;
    }

    public int getLeaderboardRanking() {
        return leaderboardRanking;
    }

    public void setLeaderboardRanking(int leaderboardRanking) {
        this.leaderboardRanking = leaderboardRanking;
    }

    public String toString() {
        return this.name;
    }
}
