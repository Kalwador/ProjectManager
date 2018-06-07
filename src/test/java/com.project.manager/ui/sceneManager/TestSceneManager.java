package com.project.manager.ui.sceneManager;

import com.project.manager.JavaFXThreadingRule;
import javafx.stage.Stage;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class TestSceneManager {
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    public static SceneManager getReadySceneManager(Stage primaryStage){
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.setPrimaryStage(primaryStage);
        return sceneManager;
    }

    @Test
    public void testGetInstance(){
        SceneManager sceneManager1 = SceneManager.getInstance();
        SceneManager sceneManager2 = SceneManager.getInstance();
        assertEquals(sceneManager1.hashCode(),sceneManager2.hashCode());
    }
    @Test
    public void testInitializeScenes(){
        Stage testStage = new Stage();
        testStage.show();
        SceneManager testSceneManager = TestSceneManager.getReadySceneManager(testStage);
        testSceneManager.showInNewWindow(SceneType.LOGIN);
        testSceneManager.showInNewWindow(SceneType.REGISTRATION);
    }
}
