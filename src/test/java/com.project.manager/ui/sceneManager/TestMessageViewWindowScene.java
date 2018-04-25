package com.project.manager.ui.sceneManager;

import com.project.manager.JavaFXThreadingRule;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import com.project.manager.ui.sceneManager.TestSceneManager;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class TestMessageViewWindowScene {

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
        sceneManager.showScene(SceneType.MESSAGE_VIEW_WINDOW);
        assertTrue(stage.isShowing());
    }

    @Test
    public void testHidedScene(){
        sceneManager.hideScene(SceneType.MESSAGE_VIEW_WINDOW);
        assertTrue(!stage.isShowing());
    }

    @Test
    public void testClosedScene(){
        sceneManager.closeScene(SceneType.MESSAGE_VIEW_WINDOW);
        assertTrue(!stage.isShowing());
    }
}
