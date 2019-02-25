package toka.com.example.androidproject;

public class Profile {
    private String name;
    private int age;
    private int brushingSeconds;
    private int brushingTotal;

    public Profile(String name, int age) {
        this.name = name;
        this.age = age;
        brushingSeconds = 0;
        brushingTotal = 0;
    }

    private void addBrushingSeconds(int time) {
        this.brushingSeconds += time;
    }

    private void addBrushingTotal(int total) {
        this.brushingTotal += total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getBrushingSeconds() {
        return brushingSeconds;
    }

    public void setBrushingMinutes(int brushingMinutes) {
        this.brushingSeconds = brushingSeconds;
    }

    public int getBrushingTotal() {
        return brushingTotal;
    }

    public void setBrushingTotal(int brushingTotal) {
        this.brushingTotal = brushingTotal;
    }

    public String toString() {
        return this.name;
    }
}
