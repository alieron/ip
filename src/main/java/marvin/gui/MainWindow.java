package marvin.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import marvin.Marvin;
import marvin.command.CommandResult;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Marvin marvin;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image marvinImage = new Image(this.getClass().getResourceAsStream("/images/marvin.png"));

    /**
     * Initialize the main window.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Add hover effect for send button
        sendButton.setOnMouseEntered(e ->
                sendButton.setStyle("-fx-background-color: #14919b; -fx-text-fill: white; -fx-font-size: 14px;"
                        + " -fx-background-radius: 6; -fx-cursor: hand; -fx-font-weight: bold;")
        );
        sendButton.setOnMouseExited(e ->
                sendButton.setStyle("-fx-background-color: #0d7377; -fx-text-fill: white; -fx-font-size: 14px;"
                        + " -fx-background-radius: 6; -fx-cursor: hand; -fx-font-weight: bold;")
        );

        // Focus on text field when window opens
        Platform.runLater(() -> userInput.requestFocus());
    }

    /**
     * Injects the Marvin instance and welcome the user
     */
    public void setMarvin(Marvin m) {
        marvin = m;
        dialogContainer.getChildren().add(
                DialogBox.getMarvinDialog(marvin.welcomeUser(), marvinImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Marvin's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String userText = userInput.getText();

        // Don't process empty input
        if (userText.trim().isEmpty()) {
            return;
        }

        CommandResult marvinResult = marvin.runCommand(userInput.getText());
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getMarvinDialog(marvinResult.response(), marvinImage)
        );
        userInput.clear();

        if (marvinResult.shouldExit()) {
            // Delay closing to allow user to see the goodbye message
            new Thread(() -> {
                try {
                    Thread.sleep(1500); // 1.5 second delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.exit();
            }).start();
        }
    }
}
