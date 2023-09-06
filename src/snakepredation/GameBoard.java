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
    private static int ROWS;
    private static int COLUMNS;
    private static int SQUARE_SIZE;
    private static GraphicsContext gc;

    public static int getROWS() {
        return ROWS;
    }

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
    public static void DrawBackground() {
        ROWS = 20;
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

    // Generate food
    public static void DrawRandomFood(Snake snake) {
        while (true) {
            double foodX = (int) (Math.random() * ROWS);
            double foodY = (int) (Math.random() * COLUMNS);
            Food food = new Food(new Point((int) foodX, (int) foodY));

            for (Point snakeBody : snake.getBody()) {
                /*
                    Nếu vị trí x và y của rắn bằng vị trí x và y của thức ăn 
                     => Không tạo ra thức ăn ngay vị trí của con rắn đang nằm 
                 */
                if (snakeBody.getX() == food.getPosition().getX() && snakeBody.getY() == food.getPosition().getY()) {
                    continue;
                }
            }
            // Tạo ra màu ngẫu nhiên
            Color randomColor = food.generateRandomColor();
            Color newColor = food.setRandomColor_FOOD(randomColor);
            gc.setFill(newColor);
            gc.fillRect(food.getPosition().getX() * SQUARE_SIZE, food.getPosition().getY() * SQUARE_SIZE, SQUARE_SIZE - 5, SQUARE_SIZE - 5);
            break;
        }
    }
}
