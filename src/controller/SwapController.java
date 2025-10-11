package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.application.League;


public class SwapController extends Controller<League> {

    @FXML Button swapButton;
    @FXML Button closeButton;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleClose() {
        this.stage.close();
    }
}
