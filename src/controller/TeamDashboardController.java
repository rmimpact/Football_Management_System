package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.application.League;
import model.application.Player;

public class TeamDashboardController extends Controller<League> {

    @FXML TableView<Player> playerTable;
    @FXML TableColumn<Player,String> playerNameColumn;
    @FXML TableColumn<Player,String> playerPositionColumn;
    @FXML TextField playerSearchTextField;
    @FXML Label teamLabel;


    @FXML
    private void initialize() {
        System.out.println("Initializing TeamDashboardController");
        System.out.println("Managing: " + model.getLoggedInManager().getTeam());
        teamLabel.setText(model.getLoggedInManager().getTeam().toString());
        //playerNameColumn.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
        //playerPositionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
    }

    @FXML
    private void handlePlayerSign() {
        System.out.println("Sign button clicked.");
    }

    @FXML
    private void handleClose() {
        this.stage.close() ;
    }

    @FXML
    private void handlePlayerUnsign() {
        System.out.println("Unsign button clicked.");
    }

    @FXML
    private void handleKeyRelease() {
        playerSearchTextField.setDisable(false);
    }


}
