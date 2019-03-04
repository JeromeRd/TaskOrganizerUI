package sample.rule;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import main.java.impl.SpecificationImpl;
import sample.model.Task;

import java.util.ArrayList;
import java.util.List;

public class IsCoveringOtherTaskRule<T extends DroppableRuleCase> extends SpecificationImpl<T> {

    @Override
    public boolean isSatisfiedBy(DroppableRuleCase droppableRuleCase) {
        int targetX = droppableRuleCase.getTargetX();
        int targetY = droppableRuleCase.getTargetY();
        Task task = droppableRuleCase.getTask();
        GridPane targetGrid = droppableRuleCase.getGridPane();
        int endOfTask = targetX + (int)(task.getComplexity() * 2);

        for (Node node : targetGrid.getChildren()) {
            if (node instanceof Task
                    && ((Task) node).getResource() == targetY
                    && ((Task) node).getStartDay() > targetX
                    && ((Task) node).getStartDay() < endOfTask) {
                return false;
            }
        }

        return true;
    }
}
