package sample.rule;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import main.java.impl.SpecificationImpl;
import sample.model.Task;
import sample.model.TaskGrid;

import java.util.ArrayList;
import java.util.List;

public class IsInGridBoundsRule<T extends DroppableRuleCase> extends SpecificationImpl<T> {

    @Override
    public boolean isSatisfiedBy(DroppableRuleCase droppableRuleCase) {
        int targetX = droppableRuleCase.getTargetX();
        Task task = droppableRuleCase.getTask();
        TaskGrid taskGrid = droppableRuleCase.getGridPane();

        int nbColumns = taskGrid.getNbColumns();
        return (((double) targetX) + task.getComplexity() < ((double) nbColumns));
    }
}
