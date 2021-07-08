package ch.bbcag.NFController;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppDataManager {

    private String[] splitted = new String[10];

    @Inject
    public AppDataManager() {
    }

    public String[] getSplitted() {
        return splitted;
    }

    public void setSplitted(String[] splitted) {
        this.splitted = splitted;
    }
}
