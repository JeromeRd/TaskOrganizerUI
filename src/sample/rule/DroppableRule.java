package sample.rule;

import main.java.impl.AndSpecificationImpl;

public class DroppableRule<T extends DroppableRuleCase> extends AndSpecificationImpl<T> {
    public DroppableRule() {
        super(new IsCoveringOtherTaskRule(), new IsInGridBoundsRule());
    }
}
