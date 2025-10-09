package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.application.League;

public class ErrorController extends Controller<League> {

    @FXML private Label exceptionMessageLabel;
    @FXML private Label exceptionNameLabel;

    private static String heading;
    private static String description;

    public static void setErrorMessage(String exceptionMessage, String exceptionName) {
        heading = exceptionName;
        description = exceptionMessage;
    }

    @FXML
    private void handleExit() {
        this.stage.close();
    }

    @FXML
    private void initialize() {
        updateLabels();
    }

    private void updateLabels() {
        exceptionNameLabel.setText(heading);
        exceptionMessageLabel.setText(description);
    }


}