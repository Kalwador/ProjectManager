package com.project.manager;

import com.project.manager.admin.projects.AdminDashboardTablesComponentTest;
import com.project.manager.services.MessageServiceTest;
import com.project.manager.services.ProjectServiceTest;
import com.project.manager.services.RememberUserServiceTest;
import com.project.manager.services.UserServiceTest;
import com.project.manager.ui.sceneManager.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdminDashboardTablesComponentTest.class,

        MessageServiceTest.class,
        ProjectServiceTest.class,
        RememberUserServiceTest.class,
        UserServiceTest.class,

        TestAddUserScene.class,
        TestAdminDashboardScene.class,
        TestDashboardScene.class,
        TestLoginScene.class,
        TestMessageViewWindowScene.class,
        TestRegistrationScene.class,
        TestRegistrationScene.class,
        TestSceneManager.class,
        TestUpdateProjectScene.class})
public class ProjectManagerTestSuite {
}
