
/*--------------- To Do -------------------
        - handleUnsign -> pending
        - Refresh table Function -> done
        - Finalise Table + Active team views -> incomplete
        - When an image on the active team is clicked
        unsign that player from the active team but
        keep them in the main team. -> incomplete
*/


package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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


    //---- Images -----
    @FXML ImageView topMidPlayerImage;
    @FXML ImageView midLeftPlayerImage;
    @FXML ImageView midMidPlayerImage;
    @FXML ImageView midRightPlayerImage;
    @FXML ImageView botMidPlayerImage;


    @FXML public void handleTopMidPlayerClick() {
        /*Debugging*/ System.out.println("Clicked Top Mid Player");
    }

    @FXML public void handleMidLeftPlayerClick() {
        /*Debugging*/ System.out.println("Clicked Mid Left Player");
    }

    @FXML public void handleMidMidPlayerImageClick() {
        /*Debugging*/ System.out.println("Clicked Mid Mid Player");
    }

    @FXML public void handleMidRightPlayerImageClick() {
        /*Debugging*/ System.out.println("Clicked Mid Right Player");
    }

    @FXML public void handleBotMidPlayerImageClick() {
        /*Debugging*/ System.out.println("Clicked Bot Mid Player");
    }







    @FXML
    private void initialize() {
        System.out.println("mode.getLoggedInManager().getTeam() = " + model.getLoggedInManager().getTeam().getAllPlayers().getPlayers());
        playerNameColumn.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
        playerPositionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
        playerTable.setItems(model.getLoggedInManager().getTeam().getAllPlayers().getPlayers());
        playerTable.getSelectionModel().selectedItemProperty().addListener((obs, old, nw) -> {
            unsignPlayerButton.setDisable(nw == null);
        });
        playerTable.refresh();
    }












    //----- Signing --------------------------------------------------------------
    @FXML
    private void handlePlayerSign() {
        /*Debugging*/ System.out.println("Sign button clicked.");
        try{
            String search = playerSearchTextField.getText();
            Player player = model.getPlayers().player(search);
            Team team = player.getTeam();
            if (team == model.getLoggedInManager().getTeam()) {
                showError(player.getFullName() + " is already signed to your team", "InvalidSigningException");
            }
            else if (team != null) {
                showError("Cannot sign " + player.getFullName() + ", player is already signed to " + player.getTeam(), "InvalidSigningException");
            }
            else {
                model.getPlayers().player(search).setTeam(model.getLoggedInManager().getTeam());
                model.getLoggedInManager().getTeam().getAllPlayers().add(player);
                /*Debugging*/ System.out.println("Signing "+ model.getPlayers().player(search) + " to " + model.getLoggedInManager().getTeam());
            }

        }
        catch (NullPointerException e){
            showError("Player does not exist within the league", "InvalidSigningException");
        }
    }

    @FXML
    private void handlePlayerUnsign() {
        System.out.println("Unsign button clicked.");
        Player player = playerTable.getSelectionModel().getSelectedItem();
        player.setTeam(null);
        model.getLoggedInManager().getTeam().getAllPlayers().remove(player);
        /*Debugging*/ System.out.println("Unsigning " + player);
    }
    //^^^^^ Signing ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

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

    @FXML
    private void handleClose() {
        this.stage.close() ;
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
}
