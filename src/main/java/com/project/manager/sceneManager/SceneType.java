package com.project.manager.sceneManager;

/**
 * Scene Types includes all types of windows in application
 * Necessary in switching scenes.
 * Contains abstract method getId, so all types must Override that method with unique number!
 */
public enum SceneType {
    LOGIN {
        @Override
        Integer getId() {
            return 0;
        }
    }, REGISTRATION {
        @Override
        Integer getId() {
            return 1;
        }
    }, DASHBOARD {
        @Override
        Integer getId() {
            return 2;
        }
    }, PROJECT_VIEW {
        @Override
        Integer getId() {
            return 3;
        }
    }, ADMIN_DASHBOARD {
        @Override
        Integer getId() {
            return 4;
        }
    }, ADMIN_UPDATE_PROJECT {
        @Override
        Integer getId() {
            return 5;
        }
    }, MESSAGE_VIEW_WINDOW {
        @Override
        Integer getId() {
            return 6;
        }
    };

    /**
     * Return unique value of scene, used in changing scenes.
     *
     * @return integer value
     */
    abstract Integer getId();
}
