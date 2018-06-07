package com.project.manager.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.project.manager.models.PersonalData;
import com.project.manager.services.SessionService;
import com.project.manager.services.user.UserService;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import com.project.manager.utils.ImageChooser;
import com.project.manager.utils.ImageConverter;
import com.project.manager.utils.ImageValidator;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Log4j
@Component
public class PersonalDataController implements Initializable {
    @FXML
    private JFXTextField secondNameTextField;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXPasswordField passwordField1;
    @FXML
    private JFXPasswordField passwordField2;
    @FXML
    private JFXTextField firstNameTextField;
    @FXML
    private JFXButton changeAvatarButton;
    @FXML
    private ImageView avatar;
    @FXML
    private BorderPane pane;
    @FXML
    private JFXButton saveButton;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField isAvatarChangedNotVisibleButSuperImportant;
    @FXML
    private Label username;
    @FXML
    private Label changePasswordLabel;

    private UserService userService;
    private SessionService sessionService;
    private SceneManager sceneManager;
    private File avatarFile;

    /**
     * Class provides javafx controller for PersonalData window
     *
     * @param userService user service contains application logic related with management of users
     */
    @Autowired
    public PersonalDataController(UserService userService) {
        this.userService = userService;
        this.sessionService = SessionService.getInstance();
        this.sceneManager = SceneManager.getInstance();
        log.info("Creating PersonalDataController instance");
    }

    /**
     * Main method setting up initial values and linking actions to controlling inside fxml window
     *
     * @param location  standard framework parameter
     * @param resources standard framework parameter
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.username.setText(sessionService.getUserModel().getUsername());
        this.firstNameTextField.setText(sessionService.getUserModel().getFirstName());
        this.secondNameTextField.setText(sessionService.getUserModel().getLastName());
        this.avatar.setImage(ImageConverter.convertToImage(sessionService.getUserModel().getAvatar(), 100, 100));

        changePasswordLabel.setOnMouseClicked(e -> {
            changePasswordLabel.setVisible(false);
            passwordField1.setVisible(true);
            passwordField2.setVisible(true);
        });

        pane.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                if (db.getFiles().size() == 1 && ImageValidator.isFileAnImage(db.getFiles().get(0))) {
                    event.acceptTransferModes(TransferMode.COPY);
                }
            } else {
                event.consume();
            }
        });
        pane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                newImageParser(db.getFiles().get(0));
                log.info("file of type image successfully parsed");
            }
            event.setDropCompleted(success);
            event.consume();
        });

        changeAvatarButton.setOnAction((ActionEvent e) -> {
            try {
                File file = ImageChooser.open();
                newImageParser(file);
            } catch (FileNotFoundException e1) {
                log.warn("ImageChooser closed with no chosen image");
            }
        });

        errorLabel.visibleProperty().bind(
                Bindings.createBooleanBinding(() -> passwordField2.isVisible(), passwordField2.visibleProperty())
                        .and(Bindings.createBooleanBinding(() -> !passwordField2.getText().equals(passwordField1.getText()), passwordField2.textProperty()))
        );

        /*
         * Bindings disable button save when no actions inside window was performed or length of password is not long enough
         */
        saveButton.disableProperty().bind(
                Bindings.createBooleanBinding(() -> firstNameTextField.getText().equals(sessionService.getUserModel().getFirstName()), firstNameTextField.textProperty())
                        .and(Bindings.createBooleanBinding(() -> secondNameTextField.getText().equals(sessionService.getUserModel().getLastName()), secondNameTextField.textProperty()))
                        .and(Bindings.createBooleanBinding(() -> isAvatarChangedNotVisibleButSuperImportant.isDisable(), isAvatarChangedNotVisibleButSuperImportant.disableProperty()))
                        .and(Bindings.createBooleanBinding(() -> !passwordField1.isVisible(), passwordField1.visibleProperty())
                                .or(Bindings.createBooleanBinding(() -> !passwordField2.getText().equals(passwordField1.getText()), passwordField2.textProperty()))
                                .or(Bindings.length(passwordField1.textProperty()).lessThan(8))
                                .or(Bindings.length(passwordField2.textProperty()).lessThan(8)
                                )
                        )
        );


        saveButton.setOnAction(e -> {
            this.userService.changePersonalData(createPersonalDataModelFromFields());
            this.sceneManager.hideNewWindow(SceneType.PERSONAL_DATA);
            log.info("Update user personal data " + username);
        });

        cancelButton.setOnAction(e -> {
            this.sceneManager.hideNewWindow(SceneType.PERSONAL_DATA);
            log.info("Cancel updating personal data" + username);
        });
    }

    /**
     * Create personal data model form inputed data
     *
     * @return PersonalData model with selected data
     */
    private PersonalData createPersonalDataModelFromFields() {
        log.info("creating personal model");
        return PersonalData.builder()
                .firstName(firstNameTextField.getText())
                .lastName(secondNameTextField.getText())
                .password(passwordField1.getText())
                .avatar(Optional.ofNullable(avatarFile).isPresent() ? ImageConverter.convertToBytes(avatarFile) : null)
                .build();
    }

    /**
     * Method parse image file to javafx image and setup avatar in window
     *
     * @param avatarFile file of image type
     */
    private void newImageParser(File avatarFile) {
        Image image = new Image(avatarFile.toURI().toString(), 100, 100, false, false);
        this.avatarFile = avatarFile;
        isAvatarChangedNotVisibleButSuperImportant.setDisable(false);
        avatar.setImage(image);
        log.info("Successful copy of dragged image " + avatarFile.getName());
    }
}
