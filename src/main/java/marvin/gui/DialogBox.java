package marvin.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;
    @FXML
    private VBox messageBubble;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        // Make profile picture circular
        Circle clip = new Circle(16, 16, 16);
        displayPicture.setClip(clip);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Sets the styling for user messages.
     */
    private void setUserStyle() {
        // User messages: right-aligned with blue-green bubble
        messageBubble.setStyle("-fx-background-color: #0d7377; -fx-background-radius: 12; -fx-padding: 10 12 10 12;");
        dialog.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 13px; -fx-line-spacing: 2px;");
    }

    /**
     * Sets the styling for Marvin's messages.
     */
    private void setMarvinStyle() {
        // Marvin messages: left-aligned with darker bubble
        messageBubble.setStyle("-fx-background-color: #2d2d2d; -fx-background-radius: 12; -fx-padding: 10 12 10 12;");
        dialog.setStyle("-fx-text-fill: #e0e0e0; -fx-font-size: 13px; -fx-line-spacing: 2px;");
    }

    /**
     * Sets the styling for error messages.
     */
    private void setErrorStyle() {
        // Error messages: red bubble with lighter red text
        messageBubble.setStyle("-fx-background-color: #8b1e1e; -fx-background-radius: 12; -fx-padding: 10 12 10 12;"
                + " -fx-border-color: #ff4444; -fx-border-width: 1; -fx-border-radius: 12;");
        dialog.setStyle("-fx-text-fill: #ffcccc; -fx-font-size: 13px; -fx-line-spacing: 2px;");
    }

    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.setUserStyle();
        return db;
    }

    public static DialogBox getMarvinDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();

        // Check if this is an error message
        if (text.startsWith("Error:")) {
            db.setErrorStyle();
        } else {
            db.setMarvinStyle();
        }

        return db;
    }
}
