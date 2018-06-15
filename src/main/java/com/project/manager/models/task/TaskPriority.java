package com.project.manager.models.task;

/**
 * This class define how important is task in project schedule
 */
public enum TaskPriority {
    HIGH {
        @Override
        public String getColor() {
            return "#00FF00";
        }
    }, MEDIUM {
        @Override
        public String getColor() {
            return "#ffff00";
        }
    }, LOW {
        @Override
        public String getColor() {
            return "#ff0000";
        }
    };

    public abstract String getColor();
}
