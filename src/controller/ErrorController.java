package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ErrorController {
    @FXML
    private Label messageLabel;

    private static String errorMessage;

    public static void setErrorMessage(String msg) {
        errorMessage = msg;
    }

    @FXML
    private void initialize() {
        messageLabel.setText(errorMessage);
    }
}