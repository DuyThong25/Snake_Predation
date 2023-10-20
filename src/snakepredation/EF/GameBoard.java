package snakepredation.EF;

import java.awt.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import snakepredation.EF.Food;
import snakepredation.jpa_Model.Snake;

public class GameBoard extends Canvas {

    private static int width;
    private static int height;
    private static int ROWS;
    private static int COLUMNS;
    private static int SQUARE_SIZE;
    private static GraphicsContext gc;

    public GameBoard(int tempWidth, int tempHeight) {
        super(tempWidth, tempHeight);
        this.width = tempWidth;
        this.height = tempHeight;
        this.gc = getGraphicsContext2D();
        this.ROWS = 20;
        this.COLUMNS = this.ROWS;
        this.SQUARE_SIZE = this.width / this.ROWS;
    }

    public static int getCOLUMNS() {
        return COLUMNS;
    }

    public static int getROWS() {
        return ROWS;
    }

    public static int getSQUARE_SIZE() {
        return SQUARE_SIZE;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    // Draw background
    public static void DrawBackground() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                gc.setFill(Color.web("7F8487"));
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                gc.setFill(Color.web("413F42"));
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE - 5, SQUARE_SIZE - 5);
            }
        }
    }

    // Generate food for 1 player
    public static Food GenerateRandomFood(Snake snake) {
        double foodX = (int) (Math.random() * ROWS);
        double foodY = (int) (Math.random() * COLUMNS);
        Food food = new Food(new Point((int) foodX, (int) foodY));
        while (true) {
            for (Point snakeBody : snake.getSnakeBody()) {
                /*
                    Nếu vị trí x và y của rắn bằng vị trí x và y của thức ăn 
                     => Không tạo ra thức ăn ngay vị trí của con rắn đang nằm 
                 */
                if (snakeBody.getX() == food.getPosition().getX() && snakeBody.getY() == food.getPosition().getY()) {
                    foodX = (int) (Math.random() * ROWS);
                    foodY = (int) (Math.random() * COLUMNS);
                    food.setPosition(new Point((int) foodX, (int) foodY));
                }
            }
            break;
        }
        return food;
    }
    // Generate food for 2 player
    public static Food GenerateRandomFoodFor2Player(Snake snake1, Snake snake2) {
        double foodX = (int) (Math.random() * ROWS);
        double foodY = (int) (Math.random() * COLUMNS);
        Food food = new Food(new Point((int) foodX, (int) foodY));
        while (true) {
            for (Point snakeBody : snake1.getSnakeBody()) {
                /*
                    Nếu vị trí x và y của rắn bằng vị trí x và y của thức ăn 
                     => Không tạo ra thức ăn ngay vị trí của con rắn đang nằm 
                 */
                if (snakeBody.getX() == food.getPosition().getX() && snakeBody.getY() == food.getPosition().getY()) {
                    foodX = (int) (Math.random() * ROWS);
                    foodY = (int) (Math.random() * COLUMNS);
                    food.setPosition(new Point((int) foodX, (int) foodY));
                }
            }
            for (Point snakeBody : snake2.getSnakeBody()) {
                /*
                    Nếu vị trí x và y của rắn bằng vị trí x và y của thức ăn 
                     => Không tạo ra thức ăn ngay vị trí của con rắn đang nằm 
                 */
                if (snakeBody.getX() == food.getPosition().getX() && snakeBody.getY() == food.getPosition().getY()) {
                    foodX = (int) (Math.random() * ROWS);
                    foodY = (int) (Math.random() * COLUMNS);
                    food.setPosition(new Point((int) foodX, (int) foodY));
                }
            }
            break;
        }
        return food;
    }
}
