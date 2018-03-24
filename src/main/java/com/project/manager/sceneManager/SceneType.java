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
    }, RESETPASSWD {
        @Override
        Integer getId() {
            return 4;
        }
    };

    /**
     * Return unique value of scene, used in changing scenes.
     * @return integer value
     */
    abstract Integer getId();
}
