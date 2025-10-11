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
import model.application.Manager;

public class ManagerDashboardController extends Controller<League> {

    @FXML private Button swapButton;
    @FXML private Button closeButton;
    @FXML private Label teamLabel;
    @FXML private ImageView jerseyPatch;
    @FXML private Button withdrawButton;
    @FXML private Button manageButton;


    @FXML
    private void initialize() {
        teamLabel.setText(model.getLoggedInManager().getTeam().toString());
        System.out.println("Hello");
        //System.out.println();
        System.out.println(model.getLoggedInManager().getTeam().toString());
        jerseyPatch.setImage(new Image(model.getLoggedInManager().getJerseyPatchPath()));
    }

    public void handleClose() {
        System.exit(0);
    }

    public void handleSwap() {
        System.out.println("Swapping"); //Debugging

        ViewLoader.showStage(
                League.getInstance(),
                "/view/SwapView.fxml",
                "Swap",
                new Stage()
        );
    }

    public void handleWithdraw() {
        System.out.println("Withdrawing"); //Debugging

    }

    public void handleManage() {
        System.out.println("Managing");
    }
}
