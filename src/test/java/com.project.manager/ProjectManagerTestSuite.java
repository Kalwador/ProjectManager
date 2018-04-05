package com.project.manager;

import com.project.manager.services.RegistrationServiceTest;
import com.project.manager.sceneManager.TestLoginScene;
import com.project.manager.sceneManager.TestRegistrationScene;
import com.project.manager.sceneManager.TestSceneManager;

import com.project.manager.admin.projects.AdminDashboardTablesComponentTest;
import com.project.manager.sceneManager.*;
import com.project.manager.services.MessageService;
import com.project.manager.services.ProjectService;
import com.project.manager.services.RegistrationServiceTest;
import com.project.manager.services.UserService;
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
