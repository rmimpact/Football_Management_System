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
            League.getInstance().validateManager(managerId);
            System.out.println("Login successful for manager ID: " + managerId);

        } catch (NumberFormatException e) {
            try {
                throw new UnauthorisedAccessException("Incorrect format for manager id.");

            } catch (UnauthorisedAccessException ex) {
                System.out.println(ex.getMessage());
                showError(ex.getMessage());
            }
        } catch (UnauthorisedAccessException e) {
            System.out.println(e.getMessage());
            showError(e.getMessage());
        }
    }

    private void showError(String message) {
        try {
            ErrorController.setErrorMessage(message);
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
