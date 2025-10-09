package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.application.League;
import model.application.Manager;

public class ManagerDashboardController extends Controller<League> {

    @FXML private Button swapButton;
    @FXML private Button closeButton;
    @FXML private Label teamLabel;


    @FXML
    private void initialize() {
        //teamLabel.setText(Manager.new(this))
    }

    public void handleClose() {
        System.exit(0);
    }

    public void handleSwap() {
        System.out.println("Swapping Teams");
        //this.
    }
}
