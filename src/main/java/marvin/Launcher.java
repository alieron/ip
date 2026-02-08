package marvin;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        // assert false : "asserts are enabled!";
        Application.launch(Main.class, args);
    }
}
