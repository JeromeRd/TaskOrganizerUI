package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import sample.model.Task;
import sample.model.TaskGrid;
import sample.service.TaskService;
import sample.utils.ColorSelector;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public TaskGrid taskGrid;
    @FXML
    public VBox backlog;
    @FXML
    public Slider complexity;
    @FXML
    public TextField label;
    @FXML
    public ColorPicker color;

    private TaskService taskService;

    private final int CELL_HEIGHT = 25;
    private final int CELL_WIDTH = 20;
    private final Color defaultColor = Color.GRAY;

    private int rowIndex;
    private int colIndex;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        int nbRows = taskGrid.getNbRows();
        int nbColumns = taskGrid.getNbColumns();

        taskService = new TaskService(taskGrid);

        for (int i = 0; i < nbColumns; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.NEVER);
            colConstraints.setMinWidth(CELL_WIDTH);
            colConstraints.setMaxWidth(CELL_WIDTH);
            colConstraints.setPrefWidth(CELL_WIDTH);
            taskGrid.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < nbRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.NEVER);
            rowConstraints.setMinHeight(CELL_HEIGHT);
            rowConstraints.setMaxHeight(CELL_HEIGHT);
            rowConstraints.setPrefHeight(CELL_HEIGHT);
            taskGrid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < nbColumns; i++) {
            for (int j = 0; j < nbRows; j++) {
                addPane(i, j);
            }
        }

        taskGrid.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });
        taskGrid.setOnDragDropped(event -> {
            dropOnGrid(event);
            event.setDropCompleted(true);
            event.consume();
        });

        backlog.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });
        backlog.setOnDragDropped(event -> {
            dropOnBacklog(event);
            event.setDropCompleted(true);
            event.consume();
        });
    }

    private void addPane(int colIndex, int rowIndex) {
        Pane pane = new Pane();
        pane.setOnDragEntered(e -> {
                this.rowIndex = rowIndex;
                this.colIndex = colIndex;
            }
        );
        taskGrid.add(pane, colIndex, rowIndex);
    }

    private void dropOnGrid(DragEvent event) {
        Object source = event.getGestureSource();
        if (source instanceof Task) {
            Task task = (Task) source;
            Parent parent = task.getParent();
            if (taskService.isAddable(colIndex, rowIndex, task)) {
                if (parent.equals(taskGrid)) {
                    taskGrid.getChildren().remove(task);
                }
                taskGrid.add(task, colIndex, rowIndex);
                task.setStartDay(colIndex);
                task.setResource(rowIndex);
            }
        }
    }

    private void dropOnBacklog(DragEvent event) {
        Object source = event.getGestureSource();
        if (source instanceof Task) {
            Task task = (Task) source;
            Parent parent = task.getParent();
            if (parent.equals(taskGrid)) {
                taskGrid.getChildren().remove(task);
            }
            backlog.getChildren().add(task);
            task.setStartDay(0);
            task.setResource(0);
        }
    }

    public void addTask() {
        double width = complexity.getValue() * CELL_WIDTH * 2;
        if (label.getText() != null && !label.getText().isEmpty() && width > 0) {
            Task task = new Task(label.getText(), complexity.getValue());
            task.setMnemonicParsing(false);
            task.setText(label.getText());
            task.setBackground(new Background(new BackgroundFill(color.getValue(), new CornerRadii(4), null)));
            task.setMinHeight(CELL_HEIGHT);
            task.setMaxHeight(CELL_HEIGHT);
            task.setPrefHeight(CELL_HEIGHT);

            task.setMinWidth(width);
            task.setMaxWidth(width);
            task.setPrefWidth(width);

            task.setOnDragDetected(event -> {
                Dragboard db = task.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(task.getText());
                db.setContent(content);
                event.consume();
            });
            task.setOnDragOver(event -> {
                if (!event.getGestureSource().equals(task)) {
                    event.acceptTransferModes(TransferMode.ANY);
                    event.consume();
                }
            });
            task.setOnDragDropped(event -> {
                event.setDropCompleted(false);
                event.consume();
            });
            task.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    MouseButton button = event.getButton();
                    if(button == MouseButton.SECONDARY || button == MouseButton.MIDDLE){
                        Parent parent = task.getParent();
                        if (parent instanceof GridPane) {
                            ((GridPane)parent).getChildren().remove(task);
                        } else if (parent instanceof VBox) {
                            ((VBox)parent).getChildren().remove(task);
                        }
                    }
                }
            });

            backlog.getChildren().add(task);

            clear();
        }
    }

    private void clear() {
        complexity.setValue(0);
        label.clear();
        color.setValue(ColorSelector.select());
    }
}
