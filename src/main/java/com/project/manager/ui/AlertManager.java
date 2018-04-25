package com.project.manager.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Class which have method to show different dialog alerts
 */
public class AlertManager {

    /**
     * This class show information alert
     *
     * @param header  this parameter store information about header text
     * @param message this parameter store information about message in alert
     */
    public static void showInformationAlert(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * This class show error alert
     *
     * @param header  this parameter store information about header text
     * @param message this parameter store information about message in alert
     */
    public static void showErrorAlert(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * This class show confirmation alert
     *
     * @param header  this parameter store information about header text
     * @param message this parameter store information about message in alert
     * @return method return button type as a result of confirmation
     */
    public static Alert showConfirmationAlert(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
	alert.setContentText(message);
        alert.showAndWait();
        return alert;
    }
}
