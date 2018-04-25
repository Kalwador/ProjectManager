package com.project.manager.ui.sceneManager;

import com.project.manager.JavaFXThreadingRule;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import com.project.manager.ui.sceneManager.TestSceneManager;
import javafx.stage.Stage;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class TestDashboardScene {
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void testShowScene(){
        Stage testStage = new Stage();
        testStage.show();
        SceneManager testSceneManager = TestSceneManager.getReadySceneManager(testStage);
        testSceneManager.showScene(SceneType.DASHBOARD);
        assertTrue(testStage.isShowing());
    }

    @Test
    public void testHidedScene(){
        Stage testStage = new Stage();
        testStage.show();
        SceneManager testSceneManager = TestSceneManager.getReadySceneManager(testStage);
        testSceneManager.hideScene(SceneType.DASHBOARD);
        assertTrue(!testStage.isShowing());
    }

    @Test
    public void testClosedScene(){
        Stage testStage = new Stage();
        testStage.show();
        SceneManager testSceneManager = TestSceneManager.getReadySceneManager(testStage);
        testSceneManager.closeScene(SceneType.DASHBOARD);
        assertTrue(!testStage.isShowing());
    }
}
