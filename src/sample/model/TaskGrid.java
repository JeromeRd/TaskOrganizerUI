package sample.model;

import javafx.scene.layout.GridPane;

public class TaskGrid extends GridPane {
    private static final int NB_COLUMNS = 30;
    private static final int NB_ROWS = 5;

    public int getNbColumns() {
        return NB_COLUMNS;
    }

    public int getNbRows() {
        return NB_ROWS;
    }
}
