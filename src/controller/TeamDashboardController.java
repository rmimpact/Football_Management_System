
/*--------------- To Do -------------------
        - handleUnsign
        - Refresh table Function
        - Finalise Table + Active team views
        - When an image on the active team is clicked
        unsign that player from the active team but
        keep them in the main team.
*/


package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.application.*;
import javafx.scene.control.Button;
import model.application.Player;
import model.application.League;

public class TeamDashboardController extends Controller<League> {

    @FXML TableView<Player> playerTable;
    @FXML TableColumn<Player,String> playerNameColumn;
    @FXML TableColumn<Player,String> playerPositionColumn;
    @FXML TextField playerSearchTextField;
    @FXML Label teamLabel;
    @FXML Button signPlayerButton;
    @FXML Button unsignPlayerButton;


    @FXML
    private void initialize() {
        System.out.println("Initializing TeamDashboardController");
        System.out.println("Managing: " + model.getLoggedInManager().getTeam());
                //getLoggedInManager().getTeam());
        teamLabel.setText(model.getLoggedInManager().getTeam().toString());
        refresh();

            // Wait until both controls are ready
            playerTable.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null && unsignPlayerButton != null) {
                    unsignPlayerButton.disableProperty().bind(
                            playerTable.getSelectionModel().selectedItemProperty().isNull()
                    );
                }
            });

        System.out.println("playerTable = " + playerTable);
        System.out.println("playerNameColumn = " + playerNameColumn);
        System.out.println("playerPositionColumn = " + playerPositionColumn);
        System.out.println("Team players = " + model.getLoggedInManager().getTeam().getAllPlayers().getPlayers());

        for (Player player : model.getLoggedInManager().getTeam().getAllPlayers().getPlayers()) {
            System.out.println(player);
        }
        System.out.println("-----------------^^^ Team ^^^----------------------");


        //playerNameColumn.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
        //playerPositionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
        //playerTable.setItems(model.getPlayers().getPlayers());



        int playerCount = 0;
        for (Player player : model.getPlayers().getPlayers()) {
            /*Debugging*/ System.out.println(player);
            playerCount++;
        }
        /*Debugging*/ System.out.println("Players count: " + playerCount);

    }

    @FXML
    private void handlePlayerSign() {
        /*Debugging*/ System.out.println("Sign button clicked.");
        try{
            String search = playerSearchTextField.getText();
            Player player = model.getPlayers().player(search);

            model.getPlayers().player(search).setTeam(model.getLoggedInManager().getTeam());
            model.getLoggedInManager().getTeam().getAllPlayers().add(player);

            refresh();
            /*Debugging*/ System.out.println("Signing "+ model.getPlayers().player(search) + " to " + model.getLoggedInManager().getTeam());
        }
        catch (NullPointerException e){
            showError("Player does not exist within the league", "InvalidSigningException");
        }
    }

    private void showError(String description, String name) {
        try {
            ErrorController.setErrorMessage(description, name);
            ViewLoader.showStage(null, "/view/ErrorView.fxml", "Error", new Stage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClose() {
        this.stage.close() ;
    }

    @FXML
    private void handlePlayerUnsign() {

        System.out.println("Unsign button clicked.");
        Player player = playerTable.getSelectionModel().getSelectedItem();
        player.setTeam(null);
        model.getLoggedInManager().getTeam().getAllPlayers().remove(player);
        System.out.println("Unsigning "+ player);

        refresh();
        //model.getLoggedInManager().getTeam().getAllPlayers().;
        //model.getLoggedInManager().getTeam().getAllPlayers().remove(player);
    }

    @FXML
    private void handleKeyRelease() {
        playerSearchTextField.setDisable(false);
        if (playerSearchTextField.getText().isEmpty()) {
            signPlayerButton.setDisable(true);
        }
        else {signPlayerButton.setDisable(false);}
        //Gets all players -------- Testing
        //Try Sebastian Day (Real)
        //Exception Handled
    }

    private void refresh() {
        //playerTable.getItems().clear();

        System.out.println("mode.getLoggedInManager().getTeam() = " + model.getLoggedInManager().getTeam().getAllPlayers().getPlayers());
        playerNameColumn.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
        playerPositionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
        playerTable.setItems(model.getLoggedInManager().getTeam().getAllPlayers().getPlayers());
        playerTable.refresh();
    }


}
