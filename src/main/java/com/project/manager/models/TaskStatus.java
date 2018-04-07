package com.project.manager.models;

public enum TaskStatus {
    PRODUCT_BACKLOG {
        @Override
        int getId() {
            return 0;
        }
    }, SPRINT_BACKLOG {
        @Override
        int getId() {
            return 1;
        }
    }, IN_PROGRESS {
        @Override
        int getId() {
            return 2;
        }
    }, TESTING {
        @Override
        int getId() {
            return 3;
        }
    }, CODE_REVIEW {
        @Override
        int getId() {
            return 4;
        }
    }, DONE {
        @Override
        int getId() {
            return 5;
        }
    };

    abstract int getId();
}
