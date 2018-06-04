package com.project.manager.utils;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class ImageChooser {
    public static File open() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("IMAGE", Arrays.asList("*.jpg","*.jpeg","*.png")));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            return selectedFile;
        } else {
            throw new FileNotFoundException();
        }
    }
}
