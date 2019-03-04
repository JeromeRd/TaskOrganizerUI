package sample.service;

import sample.model.Task;
import sample.model.TaskGrid;
import sample.rule.DroppableRule;
import sample.rule.DroppableRuleCase;

public class TaskService {

    private TaskGrid taskGrid;
    private DroppableRule<DroppableRuleCase> droppableRule;

    public TaskService(TaskGrid taskGrid) {
        this.taskGrid = taskGrid;
        droppableRule = new DroppableRule<>();
    }

    public boolean isAddable(int col, int row, Task task) {
        return droppableRule.isSatisfiedBy(new DroppableRuleCase(col, row, task, taskGrid));
    }
}
