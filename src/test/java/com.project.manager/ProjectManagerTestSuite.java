package com.project.manager;

import com.project.manager.admin.projects.AdminDashboardTablesComponentTest;
import com.project.manager.sceneManager.TestAdminDashboardScene;
import com.project.manager.sceneManager.TestMessageViewWindowScene;
import com.project.manager.sceneManager.TestUpdateProjectScene;
import com.project.manager.services.RegistrationServiceTest;
import com.project.manager.ui.sceneManager.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestSceneManager.class,
        TestLoginScene.class,
        TestRegistrationScene.class,
        RegistrationServiceTest.class,
        AdminDashboardTablesComponentTest.class,
        TestAdminDashboardScene.class,
        TestMessageViewWindowScene.class,
        TestUpdateProjectScene.class,})
public class ProjectManagerTestSuite {
}
