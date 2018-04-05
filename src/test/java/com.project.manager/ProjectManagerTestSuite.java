package com.project.manager;

import com.project.manager.services.RegistrationServiceTest;
import com.project.manager.sceneManager.TestLoginScene;
import com.project.manager.sceneManager.TestRegistrationScene;
import com.project.manager.sceneManager.TestSceneManager;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestSceneManager.class,
        TestLoginScene.class,
        TestRegistrationScene.class,
        RegistrationServiceTest.class})
public class ProjectManagerTestSuite {
}
