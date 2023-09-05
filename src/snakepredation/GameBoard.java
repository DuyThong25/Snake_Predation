package snakepredation;

import java.awt.Point;
import java.awt.Rectangle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import snakepredation.Food;
import snakepredation.Snake;

public class GameBoard extends Canvas {

    private static int width;
    private static int height;
    private static int ROWS = 20;
    private static int COLUMNS;
    private static int SQUARE_SIZE;
    private GraphicsContext gc;

    public GameBoard(int tempWidth, int tempHeight) {
        super(tempWidth, tempHeight);
        this.width = tempWidth;
        this.height = tempHeight;
        this.gc = getGraphicsContext2D();
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    // Draw background
    public static void DrawBackground(GraphicsContext gc) {
        COLUMNS = ROWS;
        SQUARE_SIZE = width / ROWS;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                gc.setFill(Color.web("7F8487"));
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                gc.setFill(Color.web("413F42"));
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE - 5, SQUARE_SIZE - 5);

            }
        }
    }
}
