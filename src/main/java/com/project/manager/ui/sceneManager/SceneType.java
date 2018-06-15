package com.project.manager.ui.sceneManager;

/**
 * Scene Types includes all types of windows in application
 * Necessary in switching scenes.
 * Contains abstract method getId, so all types must Override that method with unique number!
 */
public enum SceneType {
    LOGIN, REGISTRATION, DASHBOARD, EMPLOYEE_PROJECT_VIEW, MANAGER_PROJECT_VIEW, ADMIN_DASHBOARD,
    ADMIN_PROJECT_VIEW, ADMIN_UPDATE_PROJECT, MESSAGE_VIEW_WINDOW, FIRST_LOGIN, RESET_PASSWORD, ADD_USER, ADMIN_CREATE_NEW_PROJECT,
    GENERATE_REPORT, PERSONAL_DATA, MESSAGES_WINDOW, TASK_WINDOW, NEW_TASK;
}
