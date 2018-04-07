package com.project.manager.ui.sceneManager;

/**
 * Scene Types includes all types of windows in application
 * Necessary in switching scenes.
 * Contains abstract method getId, so all types must Override that method with unique number!
 */
public enum SceneType {
    LOGIN {
        @Override
        public Integer getId() {
            return 0;
        }
    }, REGISTRATION {
        @Override
        public Integer getId() {
            return 1;
        }
    }, DASHBOARD {
        @Override
        public Integer getId() {
            return 2;
        }
    }, EMPLOYEE_PROJECT_VIEW {
        @Override
        public Integer getId() {
            return 3;
        }
    }, MANAGER_PROJECT_VIEW {
        @Override
        public Integer getId() {
            return 4;
        }
    }, ADMIN_DASHBOARD {
        @Override
        public Integer getId() {
            return 5;
        }
    }, ADMIN_UPDATE_PROJECT {
        @Override
        public Integer getId() {
            return 6;
        }
    }, MESSAGE_VIEW_WINDOW {
        @Override
        public Integer getId() {
            return 7;
        }
    }, RESETPASSWD {
        @Override
        public Integer getId() {
            return 8;
        }
    };

    /**
     * Return unique value of scene, used in changing scenes.
     *
     * @return integer value of project id
     */
    public abstract Integer getId();
}
