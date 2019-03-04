package sample.model;

import javafx.scene.control.Button;

public class Task extends Button {
    private double complexity;
    private int startDay;
    private int resource;

    public Task(final String text, final double complexity) {
        super(text);
        this.complexity = complexity;
    }

    public double getComplexity() {
        return complexity;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }
}
