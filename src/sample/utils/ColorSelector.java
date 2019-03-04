package sample.utils;

import javafx.scene.paint.Color;

import java.util.concurrent.ThreadLocalRandom;

public class ColorSelector {
    private static Color[] colors = {
    Color.BLUE, Color.BROWN, Color.GREEN, Color.GREY, Color.ORANGE, Color.PINK, Color.PURPLE, Color.RED, Color.WHITE,
    Color.YELLOW};

    public static Color select() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 10);
        return colors[randomNum];
    }
}
