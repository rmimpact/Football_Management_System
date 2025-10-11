package controller;

import au.edu.uts.ap.javafx.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import model.application.*;
import model.exception.UnauthorisedAccessException;


public class LoginController extends Controller<League> {



    @FXML
    private TextField managerIdField;

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        loginButton.setDisable(true);
    }

    @FXML
    private void handleKeyRelease() {
        boolean containsText = managerIdField.getText().trim().isEmpty();
        System.out.println(containsText);
        loginButton.setDisable(containsText);
    }

    @FXML
    private void handleLogin() {
        try {
            int managerId = Integer.parseInt(managerIdField.getText().trim());
            Manager manager = League.getInstance().validateManager(managerId);
            League.getInstance().setLoggedInManager(manager);
            ViewLoader.showStage(
                    League.getInstance(),
                    "/view/ManagerDashboardView.fxml",
                    "Manager Dashboard",
                    new Stage()
            );
            System.out.println("Login successful for manager ID: " + managerId);
            stage.close();
        } catch (NumberFormatException e) {
            try {
                throw new UnauthorisedAccessException("Incorrect format for manager id");

            } catch (UnauthorisedAccessException ex) {
                System.out.println(ex.getMessage());
                showError(ex.getMessage(), ex.getClass().getSimpleName());
            }
        } catch (UnauthorisedAccessException e) {
            System.out.println(e.getMessage());
            showError(e.getMessage(), e.getClass().getSimpleName());
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
    private void handleExit() {
        System.exit(0);
    }





}
