package com.project.manager;

import com.project.manager.admin.projects.AdminDashboardTablesComponentTest;
import com.project.manager.services.*;
import com.project.manager.ui.sceneManager.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdminDashboardTablesComponentTest.class,

        LoginServiceTest.class,
        MessageServiceTest.class,
        ProjectServiceTest.class,
        RegistrationServiceTest.class,
        RememberUserServiceTest.class,
        ResetPasswordServiceTest.class,
        UserSelectorServiceTest.class,
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
