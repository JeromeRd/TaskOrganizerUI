package sample.rule;

import sample.model.Task;
import sample.model.TaskGrid;

public class DroppableRuleCase {
    private int targetX;
    private int targetY;
    private Task task;
    private TaskGrid gridPane;

    public DroppableRuleCase(int targetX, int targetY, Task task, TaskGrid gridPane) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.task = task;
        this.gridPane = gridPane;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public Task getTask() {
        return task;
    }

    public TaskGrid getGridPane() {
        return gridPane;
    }
}
