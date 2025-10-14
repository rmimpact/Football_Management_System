
/*--------------- To Do -------------------
        - handleUnsign -> done
        - Refresh table Function -> done
        - Finalise Table + Active team views -> done
        - When an image on the active team is clicked
        unsign that player from the active team but
        keep them in the main team. -> done
*/


package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.application.*;
import model.application.Player;
import model.application.League;

import javax.naming.CannotProceedException;

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

    private Player[] currentTeam;
    private boolean topMidPlayerImageStatus;


    @FXML public void handleTopMidPlayerClick() {
        /*Debugging*/ System.out.println("Clicked Top Mid Player");
        handleShirtAction(0, topMidPlayerImage);
    }

    @FXML public void handleMidLeftPlayerClick() {
        /*Debugging*/ System.out.println("Clicked Mid Left Player");
        handleShirtAction(1, midLeftPlayerImage);
    }

    @FXML public void handleMidMidPlayerImageClick() {
        /*Debugging*/ System.out.println("Clicked Mid Mid Player");
        handleShirtAction(2, midMidPlayerImage);
    }

    @FXML public void handleMidRightPlayerImageClick() {
        /*Debugging*/ System.out.println("Clicked Mid Right Player");
        handleShirtAction(3, midRightPlayerImage);
    }

    @FXML public void handleBotMidPlayerImageClick() {
        /*Debugging*/ System.out.println("Clicked Bot Mid Player");
        handleShirtAction(4, botMidPlayerImage);
    }







    @FXML
    private void initialize() {
        /*Debugging*/ System.out.println("mode.getLoggedInManager().getTeam() = " + model.getLoggedInManager().getTeam().getAllPlayers().getPlayers());
        playerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        playerNameColumn.prefWidthProperty().bind(playerTable.widthProperty().multiply(0.5));
        playerPositionColumn.prefWidthProperty().bind(playerTable.widthProperty().multiply(0.5));


        Tooltip topMid = new Tooltip(getActivePlayerTool(0));
        Tooltip.install(topMidPlayerImage, topMid);
        
        Tooltip midLeft = new Tooltip(getActivePlayerTool(1));
        Tooltip.install(midLeftPlayerImage, midLeft);
        
        Tooltip midMid = new Tooltip(getActivePlayerTool(2));
        Tooltip.install(midMidPlayerImage, midMid);
        
        Tooltip midRight = new Tooltip(getActivePlayerTool(3));
        Tooltip.install(midRightPlayerImage, midRight);
        
        Tooltip botMid = new Tooltip(getActivePlayerTool(4));
        Tooltip.install(topMidPlayerImage, botMid);

        currentTeam = model.getLoggedInManager().getTeam().getActiveTeam();

        teamLabel.setText(model.getLoggedInManager().getTeam().toString());
        playerNameColumn.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
        playerPositionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
        playerTable.setItems(model.getLoggedInManager().getTeam().getAllPlayers().getPlayers());
        playerTable.getSelectionModel().selectedItemProperty().addListener((obs, old, nw) -> {
            unsignPlayerButton.setDisable(nw == null);
        });
        playerTable.refresh();
    }






    private void handleShirtAction(int n, ImageView shirtImage) {
        /*Debugging*/ System.out.println("Clicked Top Mid Player");
        /*Debugging*/ System.out.println(currentTeam[n]);

        Player selectedPlayer = playerTable.getSelectionModel().getSelectedItem();
        Team team = model.getLoggedInManager().getTeam();
        //if n contains a player (not null) and

        if (team.alreadyOnActiveTeam(selectedPlayer) != 0 && selectedPlayer != null) {
            showError(selectedPlayer + " is already in the active playing team", "FillException");
            return;
        }
        else if (selectedPlayer == null) {

        }

        if (currentTeam[n] != null && selectedPlayer == null) {
            shirtImage.setImage(new Image("/view/image/none.png"));
            /*Debugging*/ System.out.println(currentTeam[n] + " is no longer active.");
            currentTeam[n] = null;
        } else if (currentTeam[n] == null && selectedPlayer != null) {
            currentTeam[n] = selectedPlayer;
            shirtImage.setImage(new Image(model.getLoggedInManager().getJerseyPatchPath()));
            /*Debugging*/ System.out.println("Player " + currentTeam[n] + " is now active.");
        }
        else {
            /*Debugging*/ System.out.println("Swapped active player '" + currentTeam[n] + "' for " + selectedPlayer);
            currentTeam[n] = selectedPlayer;

        }
        updateTooltips();
    }

    private void updateTooltips() {
        Tooltip.install(topMidPlayerImage, new Tooltip(getActivePlayerTool(0)));
        Tooltip.install(midLeftPlayerImage, new Tooltip(getActivePlayerTool(1)));
        Tooltip.install(midMidPlayerImage, new Tooltip(getActivePlayerTool(2)));
        Tooltip.install(midRightPlayerImage, new Tooltip(getActivePlayerTool(3)));
        Tooltip.install(botMidPlayerImage, new Tooltip(getActivePlayerTool(4)));
    }
    
    private String getActivePlayerTool(int x) {

        try {
            if (currentTeam[x] == null) {
                return "Unallocated";
            } else {
                return currentTeam[x].toString();
            }
        }
        catch (NullPointerException e) {
            return "Unallocated";
        }
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

        Team team = model.getLoggedInManager().getTeam();
        Player player = playerTable.getSelectionModel().getSelectedItem();

        int playerState = team.alreadyOnActiveTeam(player);

        switch(playerState) {
            case 0:
                player.setTeam(null);
                team.getAllPlayers().remove(player);
                /*Debugging*/
                System.out.println("Unsigning " + player);
                break;
            case 1:
                showError("Cannot remove " + player.getFullName() + ", player is already on the active team", "InvalidUnsigningException");
                break;
            default:
                showError("what the fuck???", "??????");
        }
        }


       /* try {
            if (team.alreadyOnActiveTeam(player)) {

            }
            else
        }
        catch (CannotProceedException e) {

        }*/


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

    public void showError(String description, String name) {
        try {
            ErrorController.setErrorMessage(description, name);
            ViewLoader.showStage(null, "/view/ErrorView.fxml", "Error", new Stage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
