package com.project.manager.ui.sceneManager;

/**
 * Scene Types includes all types of windows in application
 * Necessary in switching scenes.
 * Contains abstract method getId, so all types must Override that method with unique number!
 */
public enum SceneType {
    LOGIN, REGISTRATION, DASHBOARD, EMPLOYEE_PROJECT_VIEW, MANAGER_PROJECT_VIEW, ADMIN_DASHBOARD,
    ADMIN_UPDATE_PROJECT, MESSAGE_VIEW_WINDOW, RESETPASSWD, ADD_USER, MESSAGE_SENT_WINDOW,
    GENERATE_REPORT;

}
