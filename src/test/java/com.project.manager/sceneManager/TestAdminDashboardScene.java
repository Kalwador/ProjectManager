package com.project.manager.sceneManager;

import com.project.manager.JavaFXThreadingRule;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class TestAdminDashboardScene {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    private SceneManager sceneManager;

    private Stage stage;

    @Before
    public void prepareSceneManager() {
        stage = new Stage();
        stage.show();
        sceneManager = TestSceneManager.getReadySceneManager(stage);
    }

    @Test
    public void testShowScene(){
        sceneManager.showScene(SceneType.ADMIN_DASHBOARD);
        assertTrue(stage.isShowing());
    }

    @Test
    public void testHidedScene(){
        sceneManager.hideScene(SceneType.ADMIN_DASHBOARD);
        assertTrue(!stage.isShowing());
    }

    @Test
    public void testClosedScene(){
        sceneManager.closeScene(SceneType.ADMIN_DASHBOARD);
        assertTrue(!stage.isShowing());
    }
}
