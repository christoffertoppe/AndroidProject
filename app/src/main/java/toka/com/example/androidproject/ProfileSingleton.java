package toka.com.example.androidproject;

import java.util.ArrayList;
import java.util.List;

public class ProfileSingleton {
    private List<Profile> profiles;
    private static final ProfileSingleton ourInstance = new ProfileSingleton();

    public static ProfileSingleton getInstance() {
        return ourInstance;
    }

    private ProfileSingleton() {
        profiles = new ArrayList<Profile>();
        profiles.add(new Profile("Miikka",12));
        profiles.add(new Profile("Juho",6));
        profiles.add(new Profile("Mira",5));
        profiles.add(new Profile("Tuukka",8));
        profiles.add(new Profile("Anna",45));
    }

    public void addProfile(String name, int age) {
        profiles.add(new Profile(name,age));
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public Profile getProfile(int i) {
        return profiles.get(i);
    }
}
