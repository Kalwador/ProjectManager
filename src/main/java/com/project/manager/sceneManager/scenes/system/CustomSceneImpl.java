package com.project.manager.sceneManager.scenes.system;

import com.project.manager.FXMLLoaderProvider;
import com.project.manager.config.SpringConfig;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Optional;

public class CustomSceneImpl implements CustomScene {
    private AnnotationConfigApplicationContext context;
    private Stage primaryStage;
    private Stage newStage;
    private String windowTitle;
    private String pathToFXML;
    private Integer width;
    private Integer height;

    protected CustomSceneImpl(Stage primaryStage) {
        context = new AnnotationConfigApplicationContext(SpringConfig.class);
        this.primaryStage = primaryStage;
        this.newStage = new Stage();
    }

    private Scene createNewScene() {
        try {

            FXMLLoader loader = context.getBean(FXMLLoaderProvider.class).getLoader(pathToFXML);
            if(Optional.ofNullable(width).isPresent() && Optional.ofNullable(height).isPresent()) {
                return new Scene(loader.load(), this.width, this.height);
            } else  {
                return new Scene(loader.load());
            }
        } catch (IOException e) {
            System.err.println("ERROR - FAILURE OF CREATING NEW SCENE");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void show() {
        primaryStage.setTitle(windowTitle);
        primaryStage.setScene(createNewScene());
    }

    @Override
    public void hide() {
        primaryStage.hide();
    }

    @Override
    public void close() {
        primaryStage.close();
    }

    @Override
    public void showInNewScene() {
        newStage.setTitle(windowTitle);
        newStage.setScene(createNewScene());
        newStage.show();
    }

    @Override
    public void hideNewScene() {
        newStage.hide();
    }

    @Override
    public void closeNewScene() {
        newStage.close();
    }

    protected void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    protected void setPathToFXML(String pathToFXML) {
        this.pathToFXML = pathToFXML;
    }


    protected void setWidth(Integer width) {
        this.width = width;
    }


    protected void setHeight(Integer height) {
        this.height = height;
    }
}
