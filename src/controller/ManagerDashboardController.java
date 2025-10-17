package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.application.League;

public class ManagerDashboardController extends Controller<League> {

    @FXML private Label teamLabel;
    @FXML private ImageView jerseyPatch;
    @FXML private Button withdrawButton;
    @FXML private Button manageButton;
    @FXML public Stage swapStage = null;


    @FXML
    private void initialize() {
        refreshDashboard();
        model.getLoggedInManager().teamProperty().addListener((observable, oldValue, newValue) -> refreshDashboard());
        /*Debugging*/ System.out.println("ManagerDashboardController initialized");
        jerseyPatch.setImage(new Image(model.getLoggedInManager().getJerseyPatchPath()));
    }

    public void refreshDashboard() {
        try {
            teamLabel.setText(model.getLoggedInManager().getTeam().toString());
            jerseyPatch.setImage(new Image(model.getLoggedInManager().getJerseyPatchPath()));
        }
        catch (NullPointerException e) {
            teamLabel.setText("No Team");
            jerseyPatch.setImage(new Image("/view/image/none.png"));
        }
        if (model.getLoggedInManager().getTeam() == null) {
            withdrawButton.setDisable(true);
            manageButton.setDisable(true);
        }
        else {
            withdrawButton.setDisable(false);
            manageButton.setDisable(false);
        }
    }

    public void handleClose() {
        System.exit(0);
    }

    public void handleSwapButton() {
        /*Debugging*/ System.out.println("Swapping");
        swapStage = new Stage();

        ViewLoader.showStage(
                League.getInstance(),
                "/view/SwapView.fxml",
                "Swap",
                swapStage
        );
    }

    public void handleWithdraw() {
        /*Debugging*/ System.out.println("Withdrawing " + model.getLoggedInManager() + " from " + model.getLoggedInManager().getTeam());
        model.withdrawManagerFromTeam(model.getLoggedInManager());
    }

    public void handleManage() {
        /*Debugging*/ System.out.println("Managing");
        ViewLoader.showStage(
                League.getInstance(),
                "/view/TeamDashboardView.fxml",
                "Team Dashboard",
                new Stage()
        );
        if(swapStage != null) {swapStage.close();}
        stage.close();
    }
}
