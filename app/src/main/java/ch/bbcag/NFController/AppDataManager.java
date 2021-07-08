package ch.bbcag.NFController;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppDataManager {

    private String[] splitted;

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
