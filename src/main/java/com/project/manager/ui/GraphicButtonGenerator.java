package com.project.manager.ui;

import com.jfoenix.controls.JFXButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class provides some method to manage of button {@link JFXButton}
 */
public class GraphicButtonGenerator {

    /**
     * This method provides functionality to generate button with graphic representation
     *
     * @param path this is the path to the image which we want to pass for our button
     * @return button which changed graphic representation on the passed image
     */
    public JFXButton getJfxButtonWithGraphic(String path) {
        JFXButton del = new JFXButton();
        Image image = new Image(getClass().getResourceAsStream(path));
        del.setGraphic(new ImageView(image));
        return del;
    }
}
