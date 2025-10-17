package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.application.League;
import model.application.Team;


public class SwapController extends Controller<League> {

    @FXML Button swapButton;
    @FXML Button closeButton;
    @FXML private ListView<Team> teamListView;

    @FXML
    private void initialize() {
        teamListView.setItems(model.getManageableTeams().getTeams());
        teamListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> swapButton.setDisable(newValue == null));
    }

    @FXML
    private void handleClose() {
        this.stage.close();
    }

    public void handleTeamSwap() {
        /*Debugging*/ System.out.println(model.getLoggedInManager() + "has successfully swapped from " + model.getLoggedInManager().getTeam() + " to " + teamListView.getSelectionModel().getSelectedItem());
        model.setManagerForTeam(model.getLoggedInManager(), teamListView.getSelectionModel().getSelectedItem());

    }
}
