package io.github.randalf.project;

public class HighscoreController {

    private static HighscoreController instance = null;

    protected HighscoreController() {
        // Exists only to defeat instantiation.
    }

    public static HighscoreController getInstance() {
        if(instance == null) {
            instance = new HighscoreController();
        }
        return instance;
    }
}
